package cz.martlin.jaxon.process;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import cz.martlin.jaxon.exception.JackException;
import cz.martlin.jaxon.object.JackField;
import cz.martlin.jaxon.object.JackObject;
import cz.martlin.jaxon.utils.ReflectionUtils;
import cz.martlin.jaxon.utils.Utils;

public class JackConverter {
	public JackConverter() {
	}

	public JackObject toJack(Object object) throws JackException {
		Set<Field> fields = ReflectionUtils.getFields(object);

		Set<JackField> fieldsSet;
		try {
			fieldsSet = toFields(object, fields);
		} catch (JackException e) {
			throw new JackException("Cannot convert fields", e);
		}

		Map<String, JackField> fieldsMap = Utils.setToMapWithNames(fieldsSet);
		Class<?> clazz = object.getClass();

		return new JackObject(clazz, fieldsMap);
	}

	private Set<JackField> toFields(Object object, Set<Field> fields)
			throws JackException {

		Set<JackField> result = new LinkedHashSet<>(fields.size());

		for (Field field : fields) {

			String name = field.getName();
			Class<?> type = field.getType();
			Object value;

			try {
				value = ReflectionUtils.getValueOf(object, field);
			} catch (JackException e) {
				throw new JackException("Cannot get value of field " + name, e);
			}

			JackField jack = new JackField(name, type, value);
			result.add(jack);
		}

		return result;
	}

	public Object toObject(JackObject jack) throws JackException {
		Object object;

		try {
			object = ReflectionUtils.createNew(jack.getClazz());
		} catch (JackException e) {
			throw new JackException("Cannot create object", e);
		}

		for (JackField field : jack.getFields()) {
			ReflectionUtils.setValueTo(object, field);
		}

		return object;
	}
}
