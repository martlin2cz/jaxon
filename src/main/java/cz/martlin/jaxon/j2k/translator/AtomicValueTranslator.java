package cz.martlin.jaxon.j2k.translator;

import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.data.KlaxonEntry;

public abstract class AtomicValueTranslator<T> {

	public AtomicValueTranslator() {
	}

	/**
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
	 * 
	 * @return
	 */
	public abstract Class<T> supportedType();

	/**
	 * 
	 * @param name
	 * @param type
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	public abstract KlaxonEntry toKlaxon(String name, JackValueType type,
			JackValue jack) throws JackToKlaxonException;

	/**
	 * 
	 * @param type
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	public abstract JackValue toJack(JackValueType type, KlaxonEntry klaxon)
			throws JackToKlaxonException;
}
