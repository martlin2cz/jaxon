package cz.martlin.jaxon.j2k.translator;

import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.j2k.serializer.AbstractToStringSerializer;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackAtomicValue;
import cz.martlin.jaxon.jack.data.values.JackNullValue;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;

/**
 * Implements {@link AtomicValueTranslator} as translator with one single value.
 * 
 * @author martin
 *
 * @param <T>
 */
public class SingleValuedTranslator<T> extends AtomicValueTranslator<T> {

	protected final AbstractToStringSerializer<T> serializer;

	public SingleValuedTranslator(AbstractToStringSerializer<T> serializer) {
		this.serializer = serializer;
	}

	@Override
	public Class<T> supportedType() {
		return serializer.supportedType();
	}

	@Override
	public KlaxonValue toKlaxon(String name, JackValueType type, JackValue jack) throws JackToKlaxonException {

		T object = jackToT(jack);

		String value = tToString(type, object);

		return new KlaxonStringValue(name, value);
	}

	@Override
	public JackValue toJack(JackValueType type, KlaxonValue klaxon) throws JackToKlaxonException {

		KlaxonStringValue string = klaxonToKlaxonString(klaxon);

		String val = klaxonToString(string);
		if (val == null) {
			return JackNullValue.INSTANCE;
		}

		T value = stringToT(type, val);

		return new JackAtomicValue(value);
	}

	/**
	 * Converts given object to its string representation.
	 * 
	 * @param type
	 * @param object
	 * @return
	 * @throws JackToKlaxonException
	 */
	private String tToString(JackValueType type, T object) throws JackToKlaxonException {
		String value;

		try {
			value = serializer.serialize(type, object);
		} catch (Exception e) {
			throw new JackToKlaxonException("Cannot serialize value", e);
		}

		return value;
	}

	/**
	 * Converts given string to object of type T.
	 * 
	 * @param type
	 * @param value
	 * @return
	 * @throws JackToKlaxonException
	 */
	private T stringToT(JackValueType type, String value) throws JackToKlaxonException {
		Object object;
		try {
			object = serializer.parse(type, value);
		} catch (Exception e) {
			throw new JackToKlaxonException("Cannot parse value " + value, e);
		}

		@SuppressWarnings("unchecked")
		T ofT = (T) object;
		return ofT;
	}

	/**
	 * Converts given klaxon to KlaxonStringValue.
	 * 
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	private KlaxonStringValue klaxonToKlaxonString(KlaxonValue klaxon) throws JackToKlaxonException {

		if (!(klaxon instanceof KlaxonStringValue)) {
			Exception e = new IllegalArgumentException(klaxon + " is not KlaxonStringValue");
			throw new JackToKlaxonException("Bad type", e);
		}

		KlaxonStringValue string = (KlaxonStringValue) klaxon;
		return string;
	}

	/**
	 * Converts given jack to its value.
	 * 
	 * @param jack
	 * @return
	 */
	private T jackToT(JackValue jack) {
		JackAtomicValue atomic = (JackAtomicValue) jack;
		Object object = atomic.getValue();

		@SuppressWarnings("unchecked")
		T ofT = (T) object;
		return ofT;
	}

	/**
	 * Converts given klaxon to its value.
	 * 
	 * @param string
	 * @return
	 */
	private String klaxonToString(KlaxonStringValue string) {
		String val = string.getValue();
		return val;
	}

}
