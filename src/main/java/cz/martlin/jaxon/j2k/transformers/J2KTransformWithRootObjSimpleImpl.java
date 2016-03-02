package cz.martlin.jaxon.j2k.transformers;

import java.util.List;

import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.j2k.transformer.J2KTransformerWithRootObject;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;

/**
 * An simple implementation of {@link J2KTransformerWithRootObject}. The
 * resulting root element is the same as the transformed from jack, but enriched
 * by headers' metadatas' fields.
 * 
 * @author martin
 *
 */
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
	protected KlaxonObject addMetadata(KlaxonObject klaxon, JackObject jack) throws JackToKlaxonException {

		List<KlaxonValue> headers = klaxon.getHeaders();
		List<KlaxonValue> fields = klaxon.getFields();
		String name = klaxon.getName();

		// type added by #transformer
		// String typeStr = jack.getType().getTypeAsString();
		// KlaxonStringValue typeAttr = new KlaxonStringValue(TYPE_ATTR_NAME,
		// typeStr);
		// headers.add(typeAttr);

		String formatStr = transformer.getName();
		KlaxonStringValue formatAttr = new KlaxonStringValue(FORMAT_ATTR_NAME, formatStr);
		headers.add(formatAttr);

		String descStr = jack.getDescription();
		if (descStr != null) {
			KlaxonStringValue descAttr = new KlaxonStringValue(DESC_ATTR_NAME, descStr);
			headers.add(descAttr);
		}

		return new KlaxonObject(name, headers, fields);
	}

	@Override
	protected void check(KlaxonObject klaxonRoot) throws JackToKlaxonException {
		try {
			KlaxonValue typeAttr = klaxonRoot.findFirst(TYPE_ATTR_NAME);
			if (typeAttr == null) {
				throw new IllegalArgumentException("Missing " + TYPE_ATTR_NAME + " attribute");
			}

			KlaxonValue formatAttr = klaxonRoot.findFirst(FORMAT_ATTR_NAME);
			if (formatAttr == null) {
				throw new IllegalArgumentException("Missing " + FORMAT_ATTR_NAME + " attribute");
			}

			KlaxonStringValue formatStr = (KlaxonStringValue) formatAttr;
			if (!transformer.getName().equals(formatStr.getValue())) {
				throw new IllegalArgumentException("Unsupported format " + formatStr.getValue());
			}
		} catch (IllegalArgumentException e) {
			throw new JackToKlaxonException("Invalid root", e);
		}
	}

	@Override
	protected JackObject parse(KlaxonObject klaxonRoot) throws JackToKlaxonException {
		return transformer.toJack(klaxonRoot);
	}

}
