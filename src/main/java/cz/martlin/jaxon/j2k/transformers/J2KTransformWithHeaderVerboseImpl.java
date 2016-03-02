package cz.martlin.jaxon.j2k.transformers;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.j2k.transformer.J2KTransformerWithHeader;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;

/**
 * Implements {@link J2KTransformerWithHeader}. Creates separate header and body
 * elements. Header contains fields with metadata and body is directly generated
 * by transformer.
 * 
 * @author martin
 *
 */
public class J2KTransformWithHeaderVerboseImpl extends J2KTransformerWithHeader {
	public static final String NAME = "TransformerWithHeaderVerboseImpl";

	private static final String HEADER_ELEM_NAME = "header";
	private static final String ROOT_ELEM_NAME = "jaxon";

	private static final String DESC_ATTR_NAME = "description";
	private static final String TYPE_ATTR_NAME = "type";
	private static final String FORMAT_ATTR_NAME = "format";

	public J2KTransformWithHeaderVerboseImpl(JackObjectsTransformer transformer) {
		super(transformer);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected KlaxonObject createHeaderElement(JackObject jack) throws JackToKlaxonException {

		List<KlaxonValue> fields = new ArrayList<>();

		String format = transformer.getName();
		KlaxonStringValue formatField = new KlaxonStringValue(FORMAT_ATTR_NAME, format);
		fields.add(formatField);

		String type = jack.getType().getTypeAsString();
		KlaxonStringValue typeField = new KlaxonStringValue(TYPE_ATTR_NAME, type);
		fields.add(typeField);

		if (jack.getDescription() != null) {
			String desc = jack.getDescription();
			KlaxonStringValue descField = new KlaxonStringValue(DESC_ATTR_NAME, desc);
			fields.add(descField);
		}

		return new KlaxonObject(HEADER_ELEM_NAME, fields);
	}

	@Override
	protected String getRootElemName() {
		return ROOT_ELEM_NAME;
	}

	@Override
	protected void checkRoot(KlaxonObject klaxonRoot) throws JackToKlaxonException {

		try {
			if (!klaxonRoot.getName().equals(ROOT_ELEM_NAME)) {
				throw new IllegalArgumentException("Not a root");
			}

			KlaxonValue header = klaxonRoot.findFirst(HEADER_ELEM_NAME);
			if (header == null) {
				throw new IllegalArgumentException("Missing header");
			}

			KlaxonObject head = (KlaxonObject) header;

			KlaxonValue formatAttr = head.findFirst(FORMAT_ATTR_NAME);
			if (formatAttr == null || !(formatAttr instanceof KlaxonStringValue)) {
				throw new IllegalArgumentException("Missing transformer or in bad format");
			}
			KlaxonStringValue formatStr = (KlaxonStringValue) formatAttr;

			if (!formatStr.getValue().equals(transformer.getName())) {
				throw new IllegalArgumentException("Unsupported format " + formatStr.getValue());
			}

			KlaxonValue typeAttr = head.findFirst(TYPE_ATTR_NAME);
			if (typeAttr == null || !(typeAttr instanceof KlaxonStringValue)) {
				throw new IllegalArgumentException("Missing or bad format of type");
			}
		} catch (Exception e) {
			throw new JackToKlaxonException("Invalid structure", e);
		}
	}
}
