package cz.martlin.jaxon.jack.data.values;

import cz.martlin.jaxon.jack.data.design.JackValueType;

/**
 * Represents abstract value composed of some others JackValues.
 * 
 * @author martin
 * 
 */
public abstract class JackCompositeValue extends JackValue {

	public JackCompositeValue(JackValueType type) {
		super(type);
	}

}
