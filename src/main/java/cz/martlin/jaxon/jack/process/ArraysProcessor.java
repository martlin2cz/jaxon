package cz.martlin.jaxon.jack.process;

import cz.martlin.jaxon.jack.abstracts.ValueTypeProcessor;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.jack.data.values.JackValue;

/**
 * Processes arrays. Huh, In fact, does not process them, because is not
 * implemented and so throws {@link UnsupportedOperationException}.
 * 
 * @author martin
 * 
 */
public class ArraysProcessor extends ValueTypeProcessor {

	@Override
	public boolean isApplicableTo(JackValueType type) {
		return type.isArray();
	}

	@Override
	public JackValue toJack(Object value) throws JackException {
		// TODO impl
		throw new UnsupportedOperationException("arrays processor, sorry...");
	}

	@Override
	public Object toObject(JackValue value) throws JackException {
		// TODO impl
		throw new UnsupportedOperationException("arrays processor, sorry...");
	}

}
