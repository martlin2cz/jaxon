package cz.martlin.jaxon.jack.process;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cz.martlin.jaxon.j2k.abstracts.JackToKlaxonSerializable;
import cz.martlin.jaxon.jack.JackImplementation;
import cz.martlin.jaxon.jack.ReflectionAndJack;
import cz.martlin.jaxon.jack.abstracts.ValueTypeProcessor;
import cz.martlin.jaxon.jack.data.design.JackObjectDesign;
import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.jack.data.values.JackValue;

/**
 * Processes data objects.
 * 
 * @author martin
 * 
 */
public class DataObjectsFieldsProcessor extends ValueTypeProcessor {

	private final JackImplementation impl;
	private final ReflectionAndJack raj;

	public DataObjectsFieldsProcessor(JackImplementation impl, ReflectionAndJack raj) {

		this.impl = impl;
		this.raj = raj;
	}

	@Override
	public boolean isApplicableTo(JackValueType type) {
		return type.isJackObject();
	}

	@Override
	public JackValue toJack(Object object) throws JackException {

		JackValueType type = JackValueType.of(object);

		JackObjectDesign design = getDesignOf(type);
		JackObject jack = evaluate(design, object);

		return jack;
	}

	@Override
	public Object toObject(JackValue jack) throws JackException {

		JackObject jackObj = (JackObject) jack;
		Object object = build(jackObj);

		return object;
	}

	public JackObjectDesign getDesignOf(JackValueType type) throws JackException {

		List<JackObjectField> fields = raj.getFields(type);
		return new JackObjectDesign(type, fields);
	}

	private JackObject evaluate(JackObjectDesign design, Object object) throws JackException {

		Map<JackObjectField, JackValue> values = new LinkedHashMap<>();

		for (JackObjectField field : design.getFields()) {
			Object value = raj.getValueOf(object, field);
			JackValue jack = impl.convertToJack(value);

			values.put(field, jack);
		}

		JackValueType type = design.getType();
		String description = tryToGetDescription(object);
		return new JackObject(type, values, description);
	}

	private String tryToGetDescription(Object object) {
		// huuh, not the best, but the simpliest way:

		if (object instanceof JackToKlaxonSerializable) {
			JackToKlaxonSerializable serializable = (JackToKlaxonSerializable) object;
			return serializable.jaxonDescription();
		}
		
		return null;
	}

	private Object build(JackObject jack) throws JackException {

		JackValueType type = jack.getType();
		Object object = raj.createNew(type);

		for (Entry<JackObjectField, JackValue> entry : jack.getValues().entrySet()) {
			JackObjectField field = entry.getKey();
			JackValue val = entry.getValue();

			Object value = impl.convertToObject(val);
			raj.setValueTo(object, field, value);
		}

		return object;
	}

}
