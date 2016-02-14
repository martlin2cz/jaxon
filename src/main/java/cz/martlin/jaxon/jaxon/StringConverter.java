package cz.martlin.jaxon.jaxon;

import java.util.Map;

import cz.martlin.jaxon.exception.JaxonException;

public class StringConverter extends ClassConverter<String> {
	public StringConverter() {
		super(String.class);
	}

	@Override
	public String parseFrom(Map<String, String> value) throws JaxonException {
		return value.get(VALUE_ATTR_NAME);
	}

	@Override
	public void serializeInto(String value, Map<String, String> result)
			throws JaxonException {

		result.put(VALUE_ATTR_NAME, value);
	}
	

}
