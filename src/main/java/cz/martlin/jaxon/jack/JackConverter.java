package cz.martlin.jaxon.jack;

import cz.martlin.jaxon.jack.data.design.JackObjectDesign;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackConfig;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.jack.data.values.JackObject;

/**
 * The main entry class for the Jack. Converts Java objects to Jack object and
 * back. In addition, generates Jack object design for given class.
 * 
 * @see Object
 * @see JackObject
 * @see JackObjectDesign
 * 
 * @author martin
 * 
 */
public class JackConverter {
	private final JackConfig config;
	private final JackImplementation impl;

	public JackConverter(JackConfig config) {
		this.config = config;
		this.impl = new JackImplementation(config);
	}

	/**
	 * Converts given Java object into Jack object.
	 * 
	 * @param object
	 * @return
	 * @throws JackException
	 */
	public JackObject toJack(Object object) throws JackException {
		try {
			return impl.convertObjectToJack(object);
		} catch (JackException e) {
			throw new JackException("Converting to Jack failed", e);
		}
	}

	/**
	 * Converts given Jack object to Java object.
	 * 
	 * @param jack
	 * @return
	 * @throws JackException
	 */
	public Object toObject(JackObject jack) throws JackException {
		try {
			return impl.convertJackToObject(jack);
		} catch (JackException e) {
			throw new JackException("Converting to Object failed", e);
		}
	}

	/**
	 * Returns Jack object design of given Java object's class.
	 * 
	 * @param clazz
	 * @return
	 * @throws JackException
	 */
	public JackObjectDesign getDesign(Class<?> clazz) throws JackException {
		JackValueType type = new JackValueType(clazz);

		try {
			return impl.getDesignOfObject(type);
		} catch (JackException e) {
			throw new JackException("Getting of Jack specification failed", e);
		}

	}

	@Override
	public String toString() {
		return "JackConverter [config=" + config + "]";
	}
}
