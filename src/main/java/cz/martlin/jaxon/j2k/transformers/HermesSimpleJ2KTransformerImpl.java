package cz.martlin.jaxon.j2k.transformers;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.data.EntriesCollection;
import cz.martlin.jaxon.klaxon.data.KlaxonAttribute;

/**
 * Implements transformer simmilar to Fry ( {@link FryJ2KTransformerImpl} ), but
 * this Hermes is more precise in exporting types. In Precisely, adds type
 * attribute to very the each field. Due its implementation simplicity just
 * simply overrides existing Fry transformer and overrides
 * {@link #addJackMetadata(JackValue, JackValueType, EntriesCollection)} method.
 * 
 * @author martin
 *
 */
public class HermesSimpleJ2KTransformerImpl extends FryJ2KTransformerImpl implements JackObjectsTransformer {

	public static final String NAME = "Hermes";
	
	public HermesSimpleJ2KTransformerImpl(J2KConfig config) {
		super(config);
	}
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected void addJackMetadata(JackValue jack, JackValueType expectedType, EntriesCollection entries) {
		JackValueType type = jack.getType();
		String string = type.getTypeAsString();
		KlaxonAttribute attr = new KlaxonAttribute(TYPE_ATTR_NAME, string);
		entries.add(attr);
	}
}
