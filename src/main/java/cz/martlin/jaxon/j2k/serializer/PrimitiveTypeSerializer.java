package cz.martlin.jaxon.j2k.serializer;


public abstract class PrimitiveTypeSerializer<T> implements
		AbstractToStringSerializer<T> {

	public PrimitiveTypeSerializer() {
	}

	@Override
	public Class<T> supportedType() {
		return getPrimitiveType();
	}

	public abstract Class<T> getPrimitiveType();

	public abstract Class<T> getWrapperType();

}
