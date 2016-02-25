package cz.martlin.jaxon.j2k.transformers;

import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.j2k.transformer.J2KTransformerWithRootObject;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.EntriesCollection;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.klaxon.data.KlaxonAttribute;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithChildren;

public class J2KTransformWithRootObjSimpleImpl extends J2KTransformerWithRootObject {

	public static final String NAME = "TransformerWithRootedObjectSimpleImpl";

	private static final String TYPE_ATTR_NAME = "type";
	private static final String DESC_ATTR_NAME = "description";
	private static final String FORMAT_ATTR_NAME = "format";

	public J2KTransformWithRootObjSimpleImpl(JackObjectsTransformer transformer) {
		super(transformer);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected KlaxonAbstractElement addMetadata(KlaxonAbstractElement klaxon, JackObject jack)
			throws JackToKlaxonException {
		EntriesCollection entries = klaxon.asEntries();
		String name = klaxon.getName();

		String typeStr = jack.getType().getTypeAsString();
		KlaxonAttribute typeAttr = new KlaxonAttribute(TYPE_ATTR_NAME, typeStr);
		entries.add(typeAttr);

		String formatStr = transformer.getName();
		KlaxonAttribute formatAttr = new KlaxonAttribute(FORMAT_ATTR_NAME, formatStr);
		entries.add(formatAttr);

		String descStr = jack.getDescription();
		if (descStr != null) {
			KlaxonAttribute descAttr = new KlaxonAttribute(DESC_ATTR_NAME, descStr);
			entries.add(descAttr);
		}

		return new KlaxonElemWithChildren(name, entries);
	}

	@Override
	protected void check(KlaxonAbstractElement klaxonRoot) throws JackToKlaxonException {
		try {
			KlaxonAttribute typeAttr = klaxonRoot.getAttribute(TYPE_ATTR_NAME);
			if (typeAttr == null) {
				throw new IllegalArgumentException("Missing " + TYPE_ATTR_NAME + " attribute");
			}

			KlaxonAttribute formatAttr = klaxonRoot.getAttribute(FORMAT_ATTR_NAME);
			if (formatAttr == null) {
				throw new IllegalArgumentException("Missing " + FORMAT_ATTR_NAME + " attribute");
			}
			if (!transformer.getName().equals(formatAttr.getValue())) {
				throw new IllegalArgumentException("Unsupported format " + formatAttr.getValue());
			}
		} catch (IllegalArgumentException e) {
			throw new JackToKlaxonException("Invalid root", e);
		}
	}

	@Override
	protected JackObject parse(KlaxonAbstractElement klaxonRoot) throws JackToKlaxonException {
		// TODO remove metadata attributes, may cause problems later
		return transformer.toJack(klaxonRoot);
	}

}
