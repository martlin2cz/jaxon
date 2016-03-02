package cz.martlin.jaxon.j2k.translator;

import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;

/**
 * Converts jack atomic value to some klaxon value.
 * 
 * @author martin
 *
 * @param <T>
 */
public abstract class AtomicValueTranslator<T> {

	public AtomicValueTranslator() {
	}

	/**
	 * Returns true if is this translator applicable (this translator can handle
	 * jacks of given type) to given jack type.
	 * 
	 * @param type
	 * @return
	 */
	public boolean isApplicableTo(JackValueType type) {
		Class<?> supportedClazz = supportedType();
		Class<?> fieldTypeClazz = type.getType();

		return supportedClazz.equals(fieldTypeClazz);
	}

	/**
	 * Returns class which's instance can this translator handle.
	 * 
	 * @return
	 */
	public abstract Class<T> supportedType();

	/**
	 * Converts given jack value (of given type) into some klaxon element with
	 * given name.
	 * 
	 * @param name
	 * @param type
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	public abstract KlaxonValue toKlaxon(String name, JackValueType type, JackValue jack) throws JackToKlaxonException;

	/**
	 * Converts given klaxon object into jack of given type.
	 * 
	 * @param type
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	public abstract JackValue toJack(JackValueType type, KlaxonValue klaxon) throws JackToKlaxonException;
}
