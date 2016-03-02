package cz.martlin.jaxon.j2k.transformers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.j2k.data.SupportedAtomicsTranslators;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.j2k.translator.AtomicValueTranslator;
import cz.martlin.jaxon.jack.JackImplementation;
import cz.martlin.jaxon.jack.ReflectionAndJack;
import cz.martlin.jaxon.jack.data.design.JackObjectDesign;
import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.jack.data.values.JackAtomicValue;
import cz.martlin.jaxon.jack.data.values.JackCollection;
import cz.martlin.jaxon.jack.data.values.JackMap;
import cz.martlin.jaxon.jack.data.values.JackNullValue;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;

/**
 * Implements simple, abstract, transformer. This transformer uses fields
 * everywhere for storing values, metainformation (like types or atomic values
 * stuff) will store in attributes. Each such element is named by field or the
 * name is provided by concrete implementation.
 * 
 * @author martin
 * 
 */
public abstract class AbstractFuturamaJ2KTransformer implements JackObjectsTransformer {

	protected final SupportedAtomicsTranslators transformers;

	protected ReflectionAndJack raj;
	protected JackImplementation jackImpl;

	public AbstractFuturamaJ2KTransformer(J2KConfig config) {
		this.transformers = new SupportedAtomicsTranslators(config);

		this.raj = new ReflectionAndJack(config);
		this.jackImpl = new JackImplementation(config);
	}

	/**
	 * Converts jack map entry to klaxon.
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected abstract KlaxonValue jackMapEntryToKlaxon(JackValue key, JackValue value) throws JackToKlaxonException;

	/**
	 * Creates name (element) of one item of jack collection.
	 * 
	 * @param item
	 * @return
	 */
	protected abstract String createNameOfCollectionItem(JackValue item);

	/**
	 * Creates and returns some metadata of given jack. For instance type or
	 * description.
	 * 
	 * @param jack
	 * @param expectedType
	 * @return
	 */
	protected abstract List<KlaxonValue> getJackMetadata(JackValue jack, JackValueType expectedType);

	/**
	 * Creates name for "root" element (jack object which is not stored in any
	 * field and is not item of collection/map).
	 * 
	 * @param jack
	 * @return
	 */
	protected abstract String createRootName(JackObject jack);

	/**
	 * Parses value from collection element child.
	 * 
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected abstract JackValue inferCollectionItem(KlaxonValue child) throws JackToKlaxonException;

	/**
	 * Parses entry of map entry element.
	 * 
	 * @param child
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected abstract Entry<JackValue, JackValue> inferEntryOfMap(KlaxonValue child) throws JackToKlaxonException;

	/**
	 * Tries however to get type of given entry.
	 * 
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected abstract JackValueType tryToInferTypeOf(KlaxonValue klaxon) throws JackToKlaxonException;

	@Override
	public JackObject toJack(KlaxonObject klaxon) throws JackToKlaxonException {

		return (JackObject) klaxonEntryToJackValue(null, klaxon);
	}

	/**
	 * Converts each given klaxon entry to jack value. If typeOrNull is really
	 * null, tries to infer type by calling
	 * {@link #tryToInferTypeOf(KlaxonValue)}.
	 * 
	 * @param typeOrNull
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	public JackValue klaxonEntryToJackValue(JackValueType typeOrNull, KlaxonValue klaxon) throws JackToKlaxonException {
		if (klaxon == null) {
			return JackNullValue.INSTANCE;
		}

		JackValueType type;
		try {
			JackValueType realType = tryToInferTypeOf(klaxon);
			if (realType != null) {
				type = realType;
			} else {
				type = typeOrNull;
			}

			if (type == null) {
				throw new IllegalArgumentException("Missing type of " + klaxon);
			}
		} catch (IllegalArgumentException | JackToKlaxonException e) {
			throw new JackToKlaxonException("Cannot infer type of " + klaxon, e);
		}

		return typedNonNullKlaxonEntryToJackValue(type, klaxon);
	}

	/**
	 * With known type, processes given klaxon entry and parses its jack value
	 * from.
	 * 
	 * @param value
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected JackValue typedNonNullKlaxonEntryToJackValue(JackValueType type, KlaxonValue klaxon)
			throws JackToKlaxonException {

		if (type.isJackObject() && klaxon instanceof KlaxonObject) {
			return klaxonElementToJackObject(type, (KlaxonObject) klaxon);
		}

		if (type.isCollection() && klaxon instanceof KlaxonObject) {
			return klaxonElementToJackCollection(type, (KlaxonObject) klaxon);
		}

		if (type.isMap() && klaxon instanceof KlaxonObject) {
			return klaxonElementToJackMap(type, (KlaxonObject) klaxon);
		}

		if (type.isArray() && klaxon instanceof KlaxonObject) {
			return klaxonElementToJackArray(type, (KlaxonObject) klaxon);
		}

		if (type.isAtomicValue()) {
			return klaxonElementToJackAtomic(type, klaxon);
		}

		Exception e = new IllegalArgumentException("Unsupported type " + type);
		throw new JackToKlaxonException("Unknown type " + type, e);
	}

	/**
	 * Parses klaxon element as jack object of given type.
	 * 
	 * @param ofType
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected JackObject klaxonElementToJackObject(JackValueType ofType, KlaxonObject klaxon)
			throws JackToKlaxonException {

		Map<JackObjectField, JackValue> values = inferValuesOfObject(ofType, klaxon);

		return new JackObject(ofType, values, null);
	}

	/**
	 * Infers values of fields. Field has name by entry, so feel free to
	 * override and implement custom (like attribute "name").
	 * 
	 * @param ofType
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected Map<JackObjectField, JackValue> inferValuesOfObject(JackValueType ofType, KlaxonObject klaxon)
			throws JackToKlaxonException {

		Map<JackObjectField, JackValue> values = new LinkedHashMap<>();

		try {
			JackObjectDesign design = jackImpl.getDesignOfObject(ofType);
			for (JackObjectField field : design.getFields()) {
				String name = field.getName();
				JackValueType type = field.getType();

				KlaxonValue child = klaxon.findFirst(name);
				JackValue value = klaxonEntryToJackValue(type, child);

				values.put(field, value);
			}
		} catch (JackException e) {
			throw new JackToKlaxonException("Cannot infer values from klaxon", e);
		}

		return values;

	}

	/**
	 * Processes klaxon element as atomic value of given type.
	 * 
	 * @param ofType
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected JackValue klaxonElementToJackAtomic(JackValueType ofType, KlaxonValue klaxon)
			throws JackToKlaxonException {

		AtomicValueTranslator<?> transformer = transformers.find(ofType);
		return transformer.toJack(ofType, klaxon);
	}

	/**
	 * Process klaxon element as array of given type. Arrays are currently not
	 * supported yet, so throws {@link UnsupportedOperationException}.
	 * 
	 * @param ofType
	 * @param klaxon
	 * @return
	 */
	protected JackValue klaxonElementToJackArray(JackValueType ofType, KlaxonObject klaxon) {

		throw new UnsupportedOperationException("arrays");
	}

	/**
	 * Process klaxon element as collection of given type.
	 * 
	 * @param ofType
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected JackValue klaxonElementToJackCollection(JackValueType ofType, KlaxonObject klaxon)
			throws JackToKlaxonException {

		Collection<JackValue> data = inferCollectionData(klaxon);
		return new JackCollection(ofType, data);
	}

	/**
	 * From given collection element infers collection of its items.
	 * 
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected Collection<JackValue> inferCollectionData(KlaxonObject klaxon) throws JackToKlaxonException {

		List<JackValue> values = new ArrayList<>();

		for (KlaxonValue child : klaxon.getFields()) {
			JackValue jack = inferCollectionItem(child);
			values.add(jack);
		}

		return values;
	}

	/**
	 * Process klaxon element as map of given type.
	 * 
	 * @param ofType
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected JackValue klaxonElementToJackMap(JackValueType ofType, KlaxonObject klaxon) throws JackToKlaxonException {

		Map<JackValue, JackValue> data = inferEntriesOfMap(klaxon);
		return new JackMap(ofType, data);
	}

	/**
	 * Infers map of values of given map element.
	 * 
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected Map<JackValue, JackValue> inferEntriesOfMap(KlaxonObject klaxon) throws JackToKlaxonException {

		Map<JackValue, JackValue> map = new LinkedHashMap<>();

		for (KlaxonValue child : klaxon.getFields()) {
			Entry<JackValue, JackValue> entry = inferEntryOfMap(child);
			map.put(entry.getKey(), entry.getValue());
		}

		return map;
	}

	@Override
	public KlaxonObject toKlaxon(JackObject jack) throws JackToKlaxonException {

		String name = createRootName(jack);

		return (KlaxonObject) jackValueToKlaxonElem(name, null, jack);
	}

	/**
	 * Converts given each jack value to klaxon entry. If is expected type null,
	 * we would not infer type of jack, so we should put type specifier withing
	 * the returned element (take a look to
	 * {@link #getJackMetadata(JackValue, JackValueType)} ).
	 * 
	 * @param name
	 * @param expectedType
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected KlaxonValue jackValueToKlaxonElem(String name, JackValueType expectedType, JackValue jack)
			throws JackToKlaxonException {

		if (jack instanceof JackNullValue) {
			return jackNullToKlaxon(//
					name, expectedType, (JackNullValue) jack);
		} else if (jack instanceof JackAtomicValue) {
			return jackAtomicToKlaxon(//
					name, expectedType, (JackAtomicValue) jack);
		} else if (jack instanceof JackObject) {
			return jackObjectToKlaxon(//
					name, expectedType, (JackObject) jack);
		} else if (jack instanceof JackCollection) {
			return jackCollectionToKlaxon(//
					name, expectedType, (JackCollection) jack);
		} else if (jack instanceof JackMap) {
			return jackMapToKlaxon(name, expectedType, (JackMap) jack);
		} else {
			Exception e = new IllegalArgumentException("Unknown jack object type: " + jack);
			throw new JackToKlaxonException("Unknown type", e);
		}
	}

	/**
	 * Converts jack null value to klaxon element. So, in fact, returns null.
	 * 
	 * @param name
	 * @param expectedType
	 * @param jack
	 * @return
	 */
	protected KlaxonObject jackNullToKlaxon(String name, JackValueType expectedType, JackNullValue jack) {
		return null;
	}

	/**
	 * Converts jack atomic value to klaxon entry.
	 * 
	 * @param name
	 * @param expectedType
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected KlaxonValue jackAtomicToKlaxon(String name, JackValueType expectedType, JackAtomicValue jack)
			throws JackToKlaxonException {

		JackValueType type = jack.getType();
		AtomicValueTranslator<?> translator = transformers.find(type);
		KlaxonValue atomic = translator.toKlaxon(name, type, jack);

		KlaxonValue klaxon = processAtomic(jack, expectedType, atomic);
		return klaxon;
	}

	/**
	 * Processes generated atomic element (adds metadata).
	 * 
	 * @param jack
	 * @param expectedType
	 * @param klaxon
	 * @return
	 */
	protected KlaxonValue processAtomic(JackAtomicValue jack, JackValueType expectedType, KlaxonValue klaxon) {

		String name = klaxon.getName();
		List<KlaxonValue> headers = klaxon.getHeaders();

		List<KlaxonValue> news = getJackMetadata(jack, expectedType);
		headers.addAll(news);

		if (klaxon instanceof KlaxonObject) {
			KlaxonObject object = (KlaxonObject) klaxon;
			List<KlaxonValue> fields = object.getFields();
			return new KlaxonObject(name, headers, fields);

		} else if (klaxon instanceof KlaxonStringValue) {
			KlaxonStringValue string = (KlaxonStringValue) klaxon;
			String value = string.getValue();
			return new KlaxonStringValue(name, headers, value);

		} else {
			throw new IllegalArgumentException("Unknown child");
		}
	}

	/**
	 * Converts jack object to klaxon element.
	 * 
	 * @param name
	 * @param expectedType
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected KlaxonObject jackObjectToKlaxon(String name, JackValueType expectedType, JackObject jack)
			throws JackToKlaxonException {

		List<KlaxonValue> headers = getJackMetadata(jack, expectedType);
		List<KlaxonValue> fields = getJackObjectFields(jack);

		return new KlaxonObject(name, headers, fields);
	}

	/**
	 * Adds to given entries collection fields of given object.
	 * 
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected List<KlaxonValue> getJackObjectFields(JackObject jack) throws JackToKlaxonException {

		Map<JackObjectField, JackValue> values = jack.getValues();
		List<KlaxonValue> result = new ArrayList<>(values.size());

		for (Entry<JackObjectField, JackValue> entry : values.entrySet()) {
			JackObjectField field = entry.getKey();
			JackValue value = entry.getValue();

			JackValueType type = field.getType();
			String name = field.getName();

			KlaxonValue klaxon = jackValueToKlaxonElem(name, type, value);
			if (klaxon != null) {
				result.add(klaxon);
			}
		}

		return result;
	}

	/**
	 * Converts jack collection into klaxon element.
	 * 
	 * @param name
	 * @param expectedType
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected KlaxonObject jackCollectionToKlaxon(String name, JackValueType expectedType, JackCollection jack)
			throws JackToKlaxonException {

		List<KlaxonValue> headers = getJackMetadata(jack, expectedType);
		List<KlaxonValue> fields = getJackCollectionFields(jack);

		return new KlaxonObject(name, headers, fields);
	}

	/**
	 * Adds data of given jack collection into given entries.
	 * 
	 * @param jack
	 * @param entries
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected List<KlaxonValue> getJackCollectionFields(JackCollection jack) throws JackToKlaxonException {

		Collection<JackValue> items = jack.getData();
		List<KlaxonValue> result = new ArrayList<>(items.size());

		for (JackValue item : items) {
			String name = createNameOfCollectionItem(item);
			KlaxonValue klaxon = jackValueToKlaxonElem(name, null, item);
			result.add(klaxon);
		}

		return result;
	}

	/**
	 * Converts jack map to klaxon element.
	 * 
	 * @param name
	 * @param expectedType
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected KlaxonObject jackMapToKlaxon(String name, JackValueType expectedType, JackMap jack)
			throws JackToKlaxonException {

		List<KlaxonValue> headers = getJackMetadata(jack, expectedType);
		List<KlaxonValue> fields = getJackMapFields(jack);

		return new KlaxonObject(name, headers, fields);
	}

	/**
	 * Adds values of given jack map to given entries.
	 * 
	 * @param jack
	 * @param entries
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected List<KlaxonValue> getJackMapFields(JackMap jack) throws JackToKlaxonException {
		Map<JackValue, JackValue> items = jack.getData();

		List<KlaxonValue> result = new ArrayList<>(items.size());

		for (Entry<JackValue, JackValue> entry : items.entrySet()) {
			JackValue key = entry.getKey();
			JackValue value = entry.getValue();

			KlaxonValue klaxon = jackMapEntryToKlaxon(key, value);
			result.add(klaxon);
		}

		return result;
	}

}