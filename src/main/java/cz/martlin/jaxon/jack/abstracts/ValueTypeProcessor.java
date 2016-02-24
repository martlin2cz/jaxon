package cz.martlin.jaxon.jack.abstracts;

import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.jack.data.values.JackValue;

/**
 * Processor of some jack type. When is applicable to type, should correctly
 * implement conversion of its java objects to jack objects and back (java
 * objects to jack).
 * 
 * @author martin
 * 
 */
public abstract class ValueTypeProcessor {

	public ValueTypeProcessor() {
	}

	/**
	 * Returns true, if this processor can handle given type.
	 * 
	 * @param type
	 * @return
	 */
	public abstract boolean isApplicableTo(JackValueType type);

	/**
	 * Converts given java value to jack value.
	 * 
	 * @param value
	 * @return
	 * @throws JackException
	 */
	public abstract JackValue toJack(Object value) throws JackException;

	/**
	 * Converts given jack value to java value.
	 * 
	 * @param value
	 * @return
	 * @throws JackException
	 */
	public abstract Object toObject(JackValue value) throws JackException;
}
