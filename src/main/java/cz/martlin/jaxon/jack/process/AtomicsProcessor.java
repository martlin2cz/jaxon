package cz.martlin.jaxon.jack.process;

import cz.martlin.jaxon.jack.abstracts.ValueTypeProcessor;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.jack.data.values.JackAtomicValue;
import cz.martlin.jaxon.jack.data.values.JackValue;

/**
 * Processes atomic values.
 * @author martin
 *
 */
public class AtomicsProcessor extends ValueTypeProcessor {

	public AtomicsProcessor() {
		super();
	}

	@Override
	public boolean isApplicableTo(JackValueType type) {
		return type.isAtomicValue();
	}

	@Override
	public JackValue toJack(Object value)
			throws JackException {

		return new JackAtomicValue(value);
	}

	@Override
	public Object toObject(JackValue value)
			throws JackException {

		JackAtomicValue atomic = (JackAtomicValue) value;
		return atomic.getValue();
	}

}
