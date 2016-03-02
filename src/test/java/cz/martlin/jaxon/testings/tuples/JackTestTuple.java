package cz.martlin.jaxon.testings.tuples;

import cz.martlin.jaxon.jack.data.design.JackObjectDesign;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackObject;

/**
 * Creates jack test instances tuple.
 * 
 * @author martin
 *
 * @param <T>
 */
public interface JackTestTuple<T> {

	/**
	 * Returns type of T.
	 * 
	 * @return
	 */
	public abstract JackValueType getType();

	/**
	 * Creates testing object.
	 * 
	 * @return
	 */
	public abstract T createObject();

	/**
	 * Creates testing jack object.
	 * 
	 * @return
	 */
	public abstract JackObject createJack();

	/**
	 * Creates design of this type.
	 * 
	 * @return
	 */
	public abstract JackObjectDesign createJackDesign();

}