package cz.martlin.jaxon.j2k.transformers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
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
import cz.martlin.jaxon.klaxon.data.EntriesCollection;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithChildren;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithValue;
import cz.martlin.jaxon.klaxon.data.KlaxonEntry;

/**
 * Implements simple, abstract, transformer. This transformer uses child nodes
 * everywhere for storing values (metainformation, like types or atomic values
 * stuff) could be stored in attributes. Each such element is named by field or
 * provided by concrete implementation.
 * 
 * @author martin
 * 
 */
public abstract class AbstractFuturamaJ2KTransformer implements JackObjectsTransformer {

	protected final SupportedAtomicsTranslators transformers;

	protected ReflectionAndJack raj;
	protected JackImplementation jackImpl;

	public AbstractFuturamaJ2KTransformer(J2KConfig config) {
		this.transformers = new SupportedAtomicsTranslators(config,// 
				AtmValFrmtToKlaxonStyle.CHILD_WITH_TEXT_VALUE,
				AtmValFrmtFromKlaxonStyle.SAME_AS_TO_KLAXON);

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
	protected abstract KlaxonEntry jackMapEntryToKlaxon(JackValue key, JackValue value) throws JackToKlaxonException;

	/**
	 * Creates name (element) of one item of jack collection.
	 * 
	 * @param item
	 * @return
	 */
	protected abstract String createNameOfCollectionItem(JackValue item);

	/**
	 * Adds some metadata do given entries computed of object. For instance type
	 * or description.
	 * 
	 * @param jack
	 * @param expectedType
	 * @param entries
	 */
	protected abstract void addJackMetadata(JackValue jack, JackValueType expectedType, EntriesCollection entries);

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
	protected abstract JackValue inferCollectionItem(KlaxonAbstractElement child) throws JackToKlaxonException;

	/**
	 * Parses entry of map entry element.
	 * 
	 * @param child
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected abstract Entry<JackValue, JackValue> inferEntryOfMap(KlaxonAbstractElement child)
			throws JackToKlaxonException;

	/**
	 * Tries however to get type of given entry.
	 * 
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected abstract JackValueType tryToInferTypeOf(KlaxonEntry klaxon) throws JackToKlaxonException;

	@Override
	public JackObject toJack(KlaxonAbstractElement klaxon) throws JackToKlaxonException {

		return (JackObject) klaxonEntryToJackValue(null, klaxon);
	}

	/**
	 * Converts each given klaxon entry to jack value. If typeOrNull is really
	 * null, tries to infer type by calling
	 * {@link #tryToInferTypeOf(KlaxonEntry)}.
	 * 
	 * @param typeOrNull
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	public JackValue klaxonEntryToJackValue(JackValueType typeOrNull, KlaxonEntry klaxon) throws JackToKlaxonException {
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

		return typedKlaxonEntryToJackValue(type, klaxon);
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
	protected JackValue typedKlaxonEntryToJackValue(JackValueType type, KlaxonEntry klaxon)
			throws JackToKlaxonException {

		if (klaxon == null) {
			return JackNullValue.INSTANCE;
		}

		if (type.isJackObject() && klaxon instanceof KlaxonElemWithChildren) {
			return klaxonElementToJackObject(type, (KlaxonElemWithChildren) klaxon);
		}

		if (type.isCollection() && klaxon instanceof KlaxonElemWithChildren) {
			return klaxonElementToJackCollection(type, (KlaxonElemWithChildren) klaxon);
		}

		if (type.isMap() && klaxon instanceof KlaxonElemWithChildren) {
			return klaxonElementToJackMap(type, (KlaxonElemWithChildren) klaxon);
		}

		if (type.isArray() && klaxon instanceof KlaxonElemWithChildren) {
			return klaxonElementToJackArray(type, (KlaxonElemWithChildren) klaxon);
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
	protected JackObject klaxonElementToJackObject(JackValueType ofType, KlaxonAbstractElement klaxon)
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
	protected Map<JackObjectField, JackValue> inferValuesOfObject(JackValueType ofType, KlaxonAbstractElement klaxon)
			throws JackToKlaxonException {

		Map<JackObjectField, JackValue> values = new LinkedHashMap<>();

		try {
			JackObjectDesign design = jackImpl.getDesignOfObject(ofType);
			for (JackObjectField field : design.getFields()) {
				String name = field.getName();
				JackValueType type = field.getType();

				KlaxonEntry child = klaxon.asEntries().getFirst(name);
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
	protected JackValue klaxonElementToJackAtomic(JackValueType ofType, KlaxonEntry klaxon)
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
	protected JackValue klaxonElementToJackArray(JackValueType ofType, KlaxonElemWithChildren klaxon) {

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
	protected JackValue klaxonElementToJackCollection(JackValueType ofType, KlaxonElemWithChildren klaxon)
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
	protected Collection<JackValue> inferCollectionData(KlaxonElemWithChildren klaxon) throws JackToKlaxonException {

		List<JackValue> values = new ArrayList<>();

		for (KlaxonAbstractElement child : klaxon.getChildren()) {
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
	protected JackValue klaxonElementToJackMap(JackValueType ofType, KlaxonElemWithChildren klaxon)
			throws JackToKlaxonException {

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
	protected Map<JackValue, JackValue> inferEntriesOfMap(KlaxonElemWithChildren klaxon) throws JackToKlaxonException {

		Map<JackValue, JackValue> map = new LinkedHashMap<>();

		for (KlaxonAbstractElement child : klaxon.getChildren()) {
			Entry<JackValue, JackValue> entry = inferEntryOfMap(child);
			map.put(entry.getKey(), entry.getValue());
		}

		return map;
	}

	@Override
	public KlaxonAbstractElement toKlaxon(JackObject jack) throws JackToKlaxonException {

		String name = createRootName(jack);

		return (KlaxonAbstractElement) jackValueToKlaxonElem(name, null, jack);
	}

	/**
	 * Converts given each jack value to klaxon entry. If is expected type null,
	 * we would not infer type of jack, so we should put type specifier withing
	 * the returned element (take a look to
	 * {@link #addJackMetadata(JackValue, JackValueType, EntriesCollection)}).
	 * 
	 * @param name
	 * @param expectedType
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected KlaxonEntry jackValueToKlaxonElem(String name, JackValueType expectedType, JackValue jack)
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
	protected KlaxonAbstractElement jackNullToKlaxon(String name, JackValueType expectedType, JackNullValue jack) {
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
	protected KlaxonEntry jackAtomicToKlaxon(String name, JackValueType expectedType, JackAtomicValue jack)
			throws JackToKlaxonException {

		JackValueType type = jack.getType();
		AtomicValueTranslator<?> translator = transformers.find(type);
		KlaxonEntry atomic = translator.toKlaxon(name, type, jack);

		KlaxonEntry klaxon = processAtomic(jack, expectedType, atomic);
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
	protected KlaxonEntry processAtomic(JackAtomicValue jack, JackValueType expectedType, KlaxonEntry klaxon) {
		if (!(klaxon instanceof KlaxonAbstractElement)) {
			return klaxon; // Nothing to do, sorry...
		}

		KlaxonAbstractElement elem = (KlaxonAbstractElement) klaxon;
		EntriesCollection entries = elem.asEntries();

		addJackMetadata(jack, expectedType, entries);

		String name = elem.getName();
		// FIXME TODO huh ... klaxon -> entries -> modify -> build back klaxon
		// of particular type
		// some ElementsFactory.create(EntriesCollection) ?
		if (elem instanceof KlaxonElemWithChildren) {
			return new KlaxonElemWithChildren(name, entries);
		} else if (elem instanceof KlaxonElemWithValue) {
			return new KlaxonElemWithValue(name, entries.getAttributes(), entries.getValue());
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
	protected KlaxonAbstractElement jackObjectToKlaxon(String name, JackValueType expectedType, JackObject jack)
			throws JackToKlaxonException {

		EntriesCollection entries = new EntriesCollection();

		addJackMetadata(jack, expectedType, entries);
		addJackObjectFields(jack, entries);

		return new KlaxonElemWithChildren(name, entries);
	}

	/**
	 * Adds to given entries collection fields of given object.
	 * 
	 * @param jack
	 * @param entries
	 * @throws JackToKlaxonException
	 */
	protected void addJackObjectFields(JackObject jack, EntriesCollection entries) throws JackToKlaxonException {

		Map<JackObjectField, JackValue> values = jack.getValues();

		for (Entry<JackObjectField, JackValue> entry : values.entrySet()) {
			JackObjectField field = entry.getKey();
			JackValue value = entry.getValue();

			JackValueType type = field.getType();
			String name = field.getName();

			KlaxonEntry klaxon = jackValueToKlaxonElem(name, type, value);
			entries.add(klaxon);
		}

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
	protected KlaxonAbstractElement jackCollectionToKlaxon(String name, JackValueType expectedType, JackCollection jack)
			throws JackToKlaxonException {
		EntriesCollection entries = new EntriesCollection();

		addJackMetadata(jack, expectedType, entries);
		addJackCollectionFields(jack, entries);

		return new KlaxonElemWithChildren(name, entries);
	}

	/**
	 * Adds data of given jack collection into given entries.
	 * 
	 * @param jack
	 * @param entries
	 * @throws JackToKlaxonException
	 */
	protected void addJackCollectionFields(JackCollection jack, EntriesCollection entries)
			throws JackToKlaxonException {

		for (JackValue item : jack.getData()) {
			String name = createNameOfCollectionItem(item);
			KlaxonEntry klaxon = jackValueToKlaxonElem(name, null, item);
			entries.add(klaxon);
		}
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
	protected KlaxonAbstractElement jackMapToKlaxon(String name, JackValueType expectedType, JackMap jack)
			throws JackToKlaxonException {
		EntriesCollection entries = new EntriesCollection();

		addJackMetadata(jack, expectedType, entries);
		addJackMapFields(jack, entries);

		return new KlaxonElemWithChildren(name, entries);
	}

	/**
	 * Adds values of given jack map to given entries.
	 * 
	 * @param jack
	 * @param entries
	 * @throws JackToKlaxonException
	 */
	protected void addJackMapFields(JackMap jack, EntriesCollection entries) throws JackToKlaxonException {
		Map<JackValue, JackValue> items = jack.getData();

		for (Entry<JackValue, JackValue> entry : items.entrySet()) {
			JackValue key = entry.getKey();
			JackValue value = entry.getValue();

			KlaxonEntry klaxon = jackMapEntryToKlaxon(key, value);
			entries.add(klaxon);
		}
	}

}