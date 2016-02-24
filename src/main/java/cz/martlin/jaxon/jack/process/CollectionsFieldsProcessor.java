package cz.martlin.jaxon.jack.process;

import java.util.ArrayList;
import java.util.Collection;

import cz.martlin.jaxon.jack.JackImplementation;
import cz.martlin.jaxon.jack.ReflectionAndJack;
import cz.martlin.jaxon.jack.abstracts.ValueTypeProcessor;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.jack.data.values.JackCollection;
import cz.martlin.jaxon.jack.data.values.JackValue;

/**
 * Processes collections.
 * 
 * @author martin
 * 
 */
public class CollectionsFieldsProcessor extends ValueTypeProcessor {

	private final JackImplementation impl;
	private final ReflectionAndJack raj;

	public CollectionsFieldsProcessor(JackImplementation impl,
			ReflectionAndJack raj) {
		this.impl = impl;
		this.raj = raj;
	}

	@Override
	public boolean isApplicableTo(JackValueType type) {
		return type.isCollection();
	}

	@Override
	public JackValue toJack(Object object) throws JackException {

		Collection<?> collection = (Collection<?>) object;

		JackCollection col = parse(collection);
		return col;
	}

	@Override
	public Object toObject(JackValue jack) throws JackException {
		JackCollection collection = (JackCollection) jack;

		Collection<?> col = construct(collection);
		return col;
	}

	private JackCollection parse(Collection<?> collection) throws JackException {
		JackValueType type = JackValueType.of(collection);

		ArrayList<JackValue> data = new ArrayList<>(collection.size());
		for (Object object : collection) {
			JackValue jack = impl.convertToJack(object);
			data.add(jack);
		}

		return new JackCollection(type, data);
	}

	private Collection<?> construct(JackCollection collection)
			throws JackException {

		JackValueType type = collection.getType();

		@SuppressWarnings("unchecked")
		Collection<Object> col = (Collection<Object>) raj.createNew(type);

		for (JackValue jack : collection.getData()) {
			Object object = impl.convertToObject(jack);
			col.add(object);
		}

		return col;
	}

}
