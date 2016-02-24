package cz.martlin.jaxon.jack.abstracts;

import cz.martlin.jaxon.jack.data.design.JackValueType;

/**
 * Represents typed object, object with type.
 * 
 * @see JackValueType
 * @author martin
 * 
 */
public interface HasType {
	/**
	 * Returns type.
	 * 
	 * @return
	 */
	public abstract JackValueType getType();
}
