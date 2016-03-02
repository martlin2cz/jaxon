package cz.martlin.jaxon.jack.process;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import cz.martlin.jaxon.jack.JackImplementation;
import cz.martlin.jaxon.jack.ReflectionAndJack;
import cz.martlin.jaxon.jack.abstracts.ValueTypeProcessor;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.jack.data.values.JackMap;
import cz.martlin.jaxon.jack.data.values.JackValue;

/**
 * Processes {@link Map}s.
 * @author martin
 *
 */
public class MapsFieldsProcessor extends ValueTypeProcessor {

	private final JackImplementation impl;
	private final ReflectionAndJack raj;

	public MapsFieldsProcessor(JackImplementation impl, ReflectionAndJack raj) {
		this.impl = impl;
		this.raj = raj;
	}

	@Override
	public boolean isApplicableTo(JackValueType type) {
		return type.isMap();
	}

	@Override
	public JackValue toJack(Object object) throws JackException {
		Map<?, ?> map = (Map<?, ?>) object;

		JackMap col = parse(map);
		return col;
	}

	@Override
	public Object toObject(JackValue jack) throws JackException {
		JackMap jmap = (JackMap) jack;

		Map<?, ?> col = construct(jmap);
		return col;
	}

	private JackMap parse(Map<?, ?> map) throws JackException {
		JackValueType type = JackValueType.of(map);

		Map<JackValue, JackValue> data = new LinkedHashMap<>(map.size());
		for (Entry<?, ?> entry : map.entrySet()) {
			JackValue key = impl.convertToJack(entry.getKey());
			JackValue value = impl.convertToJack(entry.getValue());
			data.put(key, value);
		}

		return new JackMap(type, data);
	}

	private Map<?, ?> construct(JackMap map) throws JackException {

		JackValueType type = map.getType();

		@SuppressWarnings("unchecked")
		Map<Object, Object> jmap = (Map<Object, Object>) raj.createNew(type);

		for (Entry<JackValue, JackValue> entry : map.getData().entrySet()) {
			Object key = impl.convertToObject(entry.getKey());
			Object value = impl.convertToObject(entry.getValue());
			jmap.put(key, value);
		}

		return jmap;
	}
}
