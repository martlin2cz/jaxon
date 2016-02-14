package cz.martlin.jaxon.jaxon;

import java.util.Map;

import cz.martlin.jaxon.exception.JaxonException;

public abstract class ClassConverter<T> implements AbstractTypeSerializer<T> {

	private final Class<T> clazz;

	public ClassConverter(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	@Override
	public void serializeObjectInto(Object value, Map<String, String> result)
			throws JaxonException {

		@SuppressWarnings("unchecked")
		T casted = (T) value;

		serializeInto(casted, result);
	}

	public String getType() {
		return clazz.getName();
	}

	@Override
	public Class<?> getItemType() {
		return clazz;
	}
}
