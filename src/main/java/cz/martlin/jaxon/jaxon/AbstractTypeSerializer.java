package cz.martlin.jaxon.jaxon;

import java.util.Map;

import cz.martlin.jaxon.exception.JaxonException;

public interface AbstractTypeSerializer<T> {
	public static final String VALUE_ATTR_NAME = "value";

	public abstract T parseFrom(Map<String, String> value)
			throws JaxonException;

	
	public abstract void serializeObjectInto(Object value, Map<String, String> result)
			throws JaxonException;
	
	public abstract void serializeInto(T value, Map<String, String> result)
			throws JaxonException;

	public abstract String getType();
	
	public abstract Class<?> getItemType();

}
