package cz.martlin.jaxon.j2k.serializer;

import cz.martlin.jaxon.Primitives;

/**
 * Represents to-string serializer of primitive type.
 * 
 * @see Primitives
 * 
 * @author martin
 *
 * @param <T>
 */
public abstract class PrimitiveTypeSerializer<T> implements AbstractToStringSerializer<T> {

	public PrimitiveTypeSerializer() {
	}

	@Override
	public Class<T> supportedType() {
		return getPrimitiveType();
	}

	/**
	 * Returns class of corresponding primitive type.
	 * 
	 * @return
	 */
	public abstract Class<T> getPrimitiveType();

	/**
	 * Returns class of corresponding primitive type wrapper.
	 * 
	 * @return
	 */
	public abstract Class<T> getWrapperType();

}
