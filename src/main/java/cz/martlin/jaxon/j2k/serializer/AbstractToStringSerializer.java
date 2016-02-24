package cz.martlin.jaxon.j2k.serializer;

import cz.martlin.jaxon.jack.data.design.JackValueType;

public interface AbstractToStringSerializer<T> {

	public abstract Class<T> supportedType();

	public abstract T parse(JackValueType type, String value) throws Exception;

	public abstract String serialize(JackValueType type, T value)
			throws Exception;

}
