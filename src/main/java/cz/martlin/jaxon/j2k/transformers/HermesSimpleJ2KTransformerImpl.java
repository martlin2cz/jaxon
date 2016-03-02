package cz.martlin.jaxon.j2k.transformers;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackValue;

/**
 * Implements transformer simmilar to Fry ( {@link FryJ2KTransformerImpl} ), but
 * this Hermes is more precise in exporting types. In Precisely, adds type
 * attribute to very the each field. Due its implementation simplicity just
 * simply overrides existing Fry transformer and overrides
 * {@link #isToAddType(JackValue, JackValueType)} method.
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
	protected boolean isToAddType(JackValue jack, JackValueType expectedType) {
		return true;
	}
}
