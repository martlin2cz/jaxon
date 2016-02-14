package cz.martlin.jaxon.jaxon;

import java.util.LinkedHashMap;
import java.util.Map;

import cz.martlin.jaxon.exception.JaxonException;
import cz.martlin.jaxon.object.JackField;
import cz.martlin.jaxon.object.KlaxonItem;

//TODO rename
public class FieldsConverter {

	private static final FieldsValuesSerializer valuesSerializer = new FieldsValuesSerializer();

	public KlaxonItem convert(JackField field) throws JaxonException {
		Map<String, String> attributes = new LinkedHashMap<>();

		String name = field.getName();
		attributes.put("name", name);

		Class<?> type = field.getType();
		String serializedType = serializeType(type);
		attributes.put("type", serializedType);

		Object value = field.getValue();
		serializeValue(type, value, attributes);

		return new KlaxonItem("field", attributes);
	}

	public JackField convert(KlaxonItem item) throws JaxonException {
		if (!"field".equals(item.getName())) {
			throw new JaxonException("Not a field, found " + item.getName());
		}

		// TODO null check
		Map<String, String> attributes = item.getAttributes();
		String name = attributes.get("name");
		String typeStr = attributes.get("type");

		Class<?> clazz = parseType(typeStr);
		Object value = parseValue(clazz, attributes);

		return new JackField(name, clazz, value);
	}

	private String serializeType(Class<?> type) {
		AbstractTypeSerializer<?> converter = valuesSerializer.get(type);
		return converter.getType();
	}

	private void serializeValue(Class<?> type, Object value,
			Map<String, String> result) throws JaxonException {

		AbstractTypeSerializer<?> converter = valuesSerializer.get(type);
		converter.serializeObjectInto(value, result);
	}

	private Object parseValue(Class<?> type, Map<String, String> value)
			throws JaxonException {

		AbstractTypeSerializer<?> converter = valuesSerializer.get(type);
		return converter.parseFrom(value);
	}

	private Class<?> parseType(String type) throws JaxonException {
		AbstractTypeSerializer<?> converter = valuesSerializer.get(type);
		return converter.getItemType();
	}

}
