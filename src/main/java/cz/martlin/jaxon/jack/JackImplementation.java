package cz.martlin.jaxon.jack;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jaxon.jack.abstracts.ValueTypeProcessor;
import cz.martlin.jaxon.jack.data.design.JackObjectDesign;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackConfig;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.jack.process.ArraysProcessor;
import cz.martlin.jaxon.jack.process.AtomicsProcessor;
import cz.martlin.jaxon.jack.process.CollectionsFieldsProcessor;
import cz.martlin.jaxon.jack.process.DataObjectsFieldsProcessor;
import cz.martlin.jaxon.jack.process.MapsFieldsProcessor;
import cz.martlin.jaxon.jack.process.NullValuesProcessor;

/**
 * Implements {@link JackConverter}'s stuff.
 * 
 * @see JackConverter
 * 
 * @author martin
 * 
 */
public class JackImplementation {

	private final ReflectionAndJack raj;

	private final List<ValueTypeProcessor> processors;
	private final DataObjectsFieldsProcessor objectProcessor;

	public JackImplementation(JackConfig config) {
		this.raj = new ReflectionAndJack(config);

		this.processors = createProcessorts(config);
		this.objectProcessor = new DataObjectsFieldsProcessor(this, raj);
	}

	/**
	 * Initializes list of {@link ValueTypeProcessor}s to use. Processors are
	 * searching to match with respect to their order, so this method should
	 * their order respect.
	 * 
	 * @param config
	 * @return
	 */
	private List<ValueTypeProcessor> createProcessorts(JackConfig config) {
		List<ValueTypeProcessor> processors = new ArrayList<>();

		processors.add(new NullValuesProcessor());
		processors.add(new DataObjectsFieldsProcessor(this, raj));
		processors.add(new CollectionsFieldsProcessor(this, raj));
		processors.add(new MapsFieldsProcessor(this, raj));
		processors.add(new ArraysProcessor());
		processors.add(new AtomicsProcessor());

		return processors;
	}

	/**
	 * Converts given Java object to Jack object.
	 * 
	 * @param object
	 * @return
	 * @throws JackException
	 */
	public JackObject convertObjectToJack(Object object) throws JackException {

		JackValueType type = JackValueType.of(object);
		checkAndThrow(type);

		JackObject jack = (JackObject) objectProcessor.toJack(object);
		return jack;

	}

	/**
	 * Converts given Jack object to Java object.
	 * 
	 * @param jack
	 * @return
	 * @throws JackException
	 */
	public Object convertJackToObject(JackObject jack) throws JackException {

		checkAndThrow(jack.getType());

		Object object = objectProcessor.toObject(jack);
		return object;
	}

	/**
	 * Loads design of given type.
	 * 
	 * @param type
	 * @return
	 * @throws JackException
	 */
	public JackObjectDesign getDesignOfObject(JackValueType type)
			throws JackException {

		checkAndThrow(type);

		return objectProcessor.getDesignOf(type);
	}

	/**
	 * Finds and returns first applicable value processor for given type. If no
	 * such processor is available, throws exception.
	 * 
	 * @param clazz
	 * @return
	 * @throws JackException
	 */
	private ValueTypeProcessor findFor(JackValueType type) throws JackException {

		for (ValueTypeProcessor processor : processors) {
			if (processor.isApplicableTo(type)) {
				return processor;
			}
		}

		Exception e = new IllegalStateException("No processor for " + type);
		throw new JackException(e);
	}

	/**
	 * Converts given jack value to value.
	 * 
	 * @param jack
	 * @return
	 * @throws JackException
	 */
	public Object convertToObject(JackValue jack) throws JackException {

		JackValueType type = jack.getType();
		ValueTypeProcessor processor = findFor(type);
		return processor.toObject(jack);
	}

	/**
	 * Converts given value to jack value.
	 * 
	 * @param object
	 * @return
	 * @throws JackException
	 */
	public JackValue convertToJack(Object object) throws JackException {

		JackValueType type = JackValueType.of(object);
		ValueTypeProcessor processor = findFor(type);
		return processor.toJack(object);
	}

	/**
	 * 
	 * Checks if given type is not null and its class is data object class.
	 * Throws exception if check fails.
	 * 
	 * @param typeOrNull
	 * @throws JackException
	 */
	private void checkAndThrow(JackValueType typeOrNull) throws JackException {
		Exception e = null;

		if (typeOrNull == null) {
			e = new NullPointerException("The object must not be null");
		} else {
			if (!objectProcessor.isApplicableTo(typeOrNull)) {
				e = new IllegalArgumentException("(Object of) class "
						+ typeOrNull + " cannot be processed to/as Jack object");
			}
		}

		if (e != null) {
			throw new JackException("Bad object", e);
		}
	}

}
