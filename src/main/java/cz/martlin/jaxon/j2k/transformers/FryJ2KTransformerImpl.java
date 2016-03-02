package cz.martlin.jaxon.j2k.transformers;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import cz.martlin.jaxon.Primitives;
import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.jack.ReflectionAndJack;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;
import cz.martlin.jaxon.utils.Utils;

/**
 * The basic {@link AbstractFuturamaJ2KTransformer} implementation. Stores type
 * specifier only and only where it is really required.
 * 
 * @author martin
 *
 */
public class FryJ2KTransformerImpl extends AbstractFuturamaJ2KTransformer {
	public static final String NAME = "Fry";

	protected static final String TYPE_ATTR_NAME = "type";
	protected static final String KEY_ENTRY_NAME = "key";
	protected static final String VALUE_ENTRY_NAME = "value";
	protected static final String ENTRY_ELEM_NAME = "entry";
	protected static final String COLLECTION_ITEM_NAME = "item";

	public FryJ2KTransformerImpl(J2KConfig config) {
		super(config);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected JackValueType tryToInferTypeOf(KlaxonValue klaxon) throws JackToKlaxonException {
		try {
			KlaxonStringValue attr = (KlaxonStringValue) klaxon.findFirst(TYPE_ATTR_NAME);

			if (attr == null) {
				return null;
			}

			String value = attr.getValue();
			return raj.createType(value);

		} catch (JackException | IllegalArgumentException e) {
			throw new JackToKlaxonException("Missing or invalid type", e);
		}
	}

	@Override
	protected Entry<JackValue, JackValue> inferEntryOfMap(KlaxonValue klaxon) throws JackToKlaxonException {

		if (!klaxon.getName().equals(ENTRY_ELEM_NAME)) {
			Exception e = new IllegalArgumentException("Required " + ENTRY_ELEM_NAME + " element.");
			throw new JackToKlaxonException("Invalid element", e);
		}
		if (!(klaxon instanceof KlaxonObject)) {
			Exception e = new IllegalArgumentException("Not a KlaxonObject");
			throw new JackToKlaxonException("Invalid element type", e);
		}
		KlaxonObject object = (KlaxonObject) klaxon;

		KlaxonValue keyEntry = object.findFirst(KEY_ENTRY_NAME);
		JackValue keyValue = klaxonEntryToJackValue(null, keyEntry);

		KlaxonValue valueEntry = object.findFirst(VALUE_ENTRY_NAME);
		JackValue valueValue = klaxonEntryToJackValue(null, valueEntry);

		return new AbstractMap.SimpleImmutableEntry<>(keyValue, valueValue);
	}

	@Override
	protected JackValue inferCollectionItem(KlaxonValue child) throws JackToKlaxonException {
		if (!child.getName().equals(COLLECTION_ITEM_NAME)) {
			Exception e = new IllegalArgumentException("Required " + COLLECTION_ITEM_NAME + " element.");
			throw new JackToKlaxonException("Invalid elemnt", e);
		}

		JackValue jack = klaxonEntryToJackValue(null, child);
		return jack;
	}

	@Override
	protected String createRootName(JackObject jack) {
		return Utils.firstToLowerCase(jack.getType().getType().getSimpleName());
	}

	@Override
	protected List<KlaxonValue> getJackMetadata(JackValue jack, JackValueType expectedType) {
		List<KlaxonValue> result = new ArrayList<>();

		if (isToAddType(jack, expectedType)) {
			addTypeAttr(jack, result);
		}

		return result;
	}

	/**
	 * Returns true if given jack in field (if so) of given expectedType
	 * requires add type information or not.
	 * 
	 * @param jack
	 * @param expectedType
	 * @return
	 */
	protected boolean isToAddType(JackValue jack, JackValueType expectedType) {
		JackValueType type = jack.getType();

		if (expectedType == null) {
			return true;
		}

		if (type.equals(expectedType)) {
			return false;
		}

		if (expectedType.getType().isPrimitive()) {
			Class<?> wrapper = Primitives.getWrappers().get(type);
			if (type.equals(wrapper)) {
				return false;
			}
		}

		if (ReflectionAndJack.representsTypeChildOf(type, expectedType.getType())) {
			return true;
		}

		return false;
	}

	/**
	 * Adds type attribute of given jack value int given klaxon values.
	 * 
	 * @param jack
	 * @param result
	 */
	protected void addTypeAttr(JackValue jack, List<KlaxonValue> result) {
		JackValueType type = jack.getType();
		String string = type.getTypeAsString();
		KlaxonStringValue attr = new KlaxonStringValue(TYPE_ATTR_NAME, string);

		result.add(attr);
	}

	@Override
	protected String createNameOfCollectionItem(JackValue item) {
		return COLLECTION_ITEM_NAME;
	}

	@Override
	protected KlaxonValue jackMapEntryToKlaxon(JackValue key, JackValue value) throws JackToKlaxonException {

		List<KlaxonValue> fields = new ArrayList<>();

		KlaxonValue keyKlaxon = jackValueToKlaxonElem(KEY_ENTRY_NAME, null, key);
		fields.add(keyKlaxon);

		KlaxonValue valueKlaxon = jackValueToKlaxonElem(VALUE_ENTRY_NAME, null, value);
		fields.add(valueKlaxon);

		return new KlaxonObject(ENTRY_ELEM_NAME, fields);
	}

}
