package cz.martlin.jaxon.jaxon;

import java.util.Map;

import cz.martlin.jaxon.exception.JaxonException;

public abstract class PrimitiveTypeSerializer<T> implements
		AbstractTypeSerializer<T> {

	@Override
	public T parseFrom(Map<String, String> value) throws JaxonException {
		String val = value.get(VALUE_ATTR_NAME);

		try {
			return parse(val);
		} catch (Exception e) {
			throw new JaxonException("Cannot parse value " + val);
		}
	}

	public abstract T parse(String val) throws Exception;

	@Override
	public void serializeObjectInto(Object value, Map<String, String> result)
			throws JaxonException {

		@SuppressWarnings("unchecked")
		T casted = (T) value;
		serializeInto(casted, result);
	}

	@Override
	public void serializeInto(T value, Map<String, String> result) {
		String val = serializeObject(value);
		result.put(VALUE_ATTR_NAME, val);
	}

	public String serializeObject(Object value) {
		@SuppressWarnings("unchecked")
		T val = (T) value;

		return serialize(val);
	}

	public abstract String serialize(T value);

}
