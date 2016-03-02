package cz.martlin.jaxon.j2k.serializer;

import cz.martlin.jaxon.jack.data.design.JackValueType;

/**
 * Converts non-null values between T and String.
 * 
 * @author martin
 *
 * @param <T>
 */
public interface AbstractToStringSerializer<T> {

	/**
	 * Returns class of supported type.
	 * 
	 * @return
	 */
	public abstract Class<T> supportedType();

	/**
	 * Parses value from given string.
	 * 
	 * @param type
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public abstract T parse(JackValueType type, String value) throws Exception;

	/**
	 * Serializes given value to string.
	 * 
	 * @param type
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public abstract String serialize(JackValueType type, T value) throws Exception;

}
