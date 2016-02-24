package cz.martlin.jaxon.j2k.transformers;

import java.util.AbstractMap;
import java.util.Map.Entry;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.data.EntriesCollection;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.klaxon.data.KlaxonAttribute;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithChildren;
import cz.martlin.jaxon.klaxon.data.KlaxonEntry;
import cz.martlin.jaxon.utils.Utils;

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
	protected JackValueType tryToInferTypeOf(KlaxonEntry klaxon)
			throws JackToKlaxonException {
		try {
			if (!(klaxon instanceof KlaxonAbstractElement)) {
				return null;
			}

			KlaxonAbstractElement elem = (KlaxonAbstractElement) klaxon;
			KlaxonAttribute attr = elem.getAttribute(TYPE_ATTR_NAME);

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
	protected Entry<JackValue, JackValue> inferEntryOfMap(
			KlaxonAbstractElement klaxon) throws JackToKlaxonException {

		if (!klaxon.getName().equals(ENTRY_ELEM_NAME)) {
			Exception e = new IllegalArgumentException("Required "
					+ ENTRY_ELEM_NAME + " element.");
			throw new JackToKlaxonException("Invalid elemnt", e);
		}

		KlaxonEntry keyEntry = klaxon.asEntries().getFirst(KEY_ENTRY_NAME);
		JackValue keyValue = klaxonEntryToJackValue(null, keyEntry);

		KlaxonEntry valueEntry = klaxon.asEntries().getFirst(VALUE_ENTRY_NAME);
		JackValue valueValue = klaxonEntryToJackValue(null, valueEntry);

		return new AbstractMap.SimpleImmutableEntry<>(keyValue, valueValue);
	}

	@Override
	protected JackValue inferCollectionItem(KlaxonAbstractElement child)
			throws JackToKlaxonException {
		if (!child.getName().equals(COLLECTION_ITEM_NAME)) {
			Exception e = new IllegalArgumentException("Required "
					+ COLLECTION_ITEM_NAME + " element.");
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
	protected void addJackMetadata(JackValue jack, JackValueType expectedType,
			EntriesCollection entries) {

		// TODO if expected !=null && expected !=jack.type ||
		// jack.type.isWrapperOf(expectedType)
		if (expectedType == null) {
			JackValueType type = jack.getType();
			String string = type.getTypeAsString();
			KlaxonAttribute attr = new KlaxonAttribute(TYPE_ATTR_NAME, string);
			entries.add(attr);
		}

	}

	@Override
	protected String createNameOfCollectionItem(JackValue item) {
		return COLLECTION_ITEM_NAME;
	}

	@Override
	protected KlaxonEntry jackMapEntryToKlaxon(JackValue key, JackValue value)
			throws JackToKlaxonException {

		EntriesCollection entries = new EntriesCollection();

		KlaxonEntry keyKlaxon = jackValueToKlaxonElem(KEY_ENTRY_NAME, null, key);
		entries.add(keyKlaxon);

		KlaxonEntry valueKlaxon = jackValueToKlaxonElem(VALUE_ENTRY_NAME, null,
				value);
		entries.add(valueKlaxon);

		return new KlaxonElemWithChildren(ENTRY_ELEM_NAME, entries);
	}

}
