package cz.martlin.jaxon.jack.process;

import cz.martlin.jaxon.jack.abstracts.ValueTypeProcessor;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.jack.data.values.JackNullValue;
import cz.martlin.jaxon.jack.data.values.JackValue;

/**
 * Processes null value.
 * @author martin
 *
 */
public class NullValuesProcessor extends ValueTypeProcessor {

	@Override
	public boolean isApplicableTo(JackValueType type) {
		return type.isNull();
	}

	@Override
	public JackValue toJack(Object value) throws JackException {
		return JackNullValue.INSTANCE;
	}

	@Override
	public Object toObject(JackValue value) throws JackException {
		return null;
	}

}
