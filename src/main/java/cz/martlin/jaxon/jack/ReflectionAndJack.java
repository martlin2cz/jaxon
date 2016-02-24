package cz.martlin.jaxon.jack;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.martlin.jaxon.Primitives;
import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackConfig;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.utils.Utils;

/**
 * Implements connection of Java reflection to Jack.
 * 
 * @author martin
 * 
 */
public class ReflectionAndJack {

	private static final String SETTER_PREFIX = "set";
	private static final String OTHER_GETTER_PREFIX = "get";
	private static final String BOOL_GETTER_PREFIX = "is";

	public ReflectionAndJack(JackConfig config) {
	}

	/**
	 * Returns true, if given type represents child (class) of given superClass.
	 * 
	 * @param type
	 * @param superClass
	 * @return
	 */
	public static boolean representsTypeChildOf(JackValueType type,
			Class<?> superClass) {

		Class<?> subClass = type.getType();

		return superClass.isAssignableFrom(subClass);
	}

	/**
	 * Creates jack type for given name. WArning, works only with classes, not
	 * primitive objects(!).
	 * 
	 * @param value
	 * @return
	 * @throws JackException
	 */
	public JackValueType createType(String type) throws JackException {
		Class<?> clazz;

		clazz = Primitives.getNames().get(type);
		if (clazz != null) {
			return new JackValueType(clazz);
		}

		try {
			clazz = Class.forName(type);
		} catch (ClassNotFoundException e) {
			throw new JackException("Cannot create type", e);
		}

		return new JackValueType(clazz);
	}

	/**
	 * Creates new instance of given type.
	 * 
	 * @param type
	 * @return
	 * @throws JackException
	 */
	public Object createNew(JackValueType type) throws JackException {
		Class<?> clazz = type.getType();
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new JackException("Cannot instantite " + type, e);
		}
	}

	/**
	 * Returns list of (jack) fields of given type.
	 * 
	 * @param type
	 * @return
	 */
	public List<JackObjectField> getFields(JackValueType type) {
		Class<?> clazz = type.getType();

		List<Field> fields = getFields(clazz);

		return toJackFields(fields);
	}

	/**
	 * Returns list of java fields of given class.
	 * 
	 * @param clazz
	 * @return
	 */
	private List<Field> getFields(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();

		List<Field> javas = Arrays.asList(fields);

		return javas;
	}

	/**
	 * Converts given list of java fields to jack fields.
	 * 
	 * @param fields
	 * @return
	 */
	private List<JackObjectField> toJackFields(List<Field> fields) {
		List<JackObjectField> result = new ArrayList<>(fields.size());

		for (Field field : fields) {
			JackObjectField jack = fieldToJackField(field);
			result.add(jack);
		}

		return result;
	}

	/**
	 * Converts given java field to jack field.
	 * 
	 * @param field
	 * @return
	 */
	private JackObjectField fieldToJackField(Field field) {
		String name = field.getName();
		Class<?> clazz = field.getType();
		JackValueType type = new JackValueType(clazz);

		JackObjectField jack = new JackObjectField(name, type);
		return jack;
	}

	/**
	 * Returns value of object's field.
	 * 
	 * @param object
	 * @param field
	 * @return
	 * @throws JackException
	 */
	public Object getValueOf(Object object, JackObjectField field)
			throws JackException {

		Class<?> clazz = object.getClass();
		Method getter = createGetter(clazz, field);
		return invokeGetter(getter, object);
	}

	/**
	 * Sets given value to given object's field.
	 * 
	 * @param object
	 * @param field
	 * @param value
	 * @throws JackException
	 */
	public void setValueTo(Object object, JackObjectField field, Object value)
			throws JackException {

		Class<?> clazz = object.getClass();
		Method setter = createSetter(clazz, field);
		invokeSetter(setter, object, value);
	}

	/**
	 * Creates getter for given field in given class.
	 * 
	 * @param clazz
	 * @param field
	 * @return
	 * @throws JackException
	 */
	private Method createGetter(Class<?> clazz, JackObjectField field)
			throws JackException {

		String name = field.getName();
		String getter = createGetterName(field);

		try {
			return clazz.getMethod(getter);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new JackException("Could not create getter of " + name, e);
		}
	}

	/**
	 * Creates setter for given field in given class.
	 * 
	 * @param clazz
	 * @param field
	 * @return
	 * @throws JackException
	 */
	private Method createSetter(Class<?> clazz, JackObjectField field)
			throws JackException {

		String name = field.getName();
		Class<?> type = field.getType().getType();

		String setter = createSetterName(field);
		try {
			return clazz.getMethod(setter, type);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new JackException("Could not create setter of " + name, e);
		}
	}

	/**
	 * Calls getter on given object.
	 * 
	 * @param getter
	 * @param object
	 * @return
	 * @throws JackException
	 */
	private Object invokeGetter(Method getter, Object object)
			throws JackException {

		try {
			return getter.invoke(object);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new JackException("Could not invoke getter "
					+ getter.getName(), e);
		}
	}

	/**
	 * Calls setter on given object with given value.
	 * 
	 * @param setter
	 * @param object
	 * @param value
	 * @throws JackException
	 */
	private void invokeSetter(Method setter, Object object, Object value)
			throws JackException {
		try {
			setter.invoke(object, value);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new JackException("Could not invoke setter "
					+ setter.getName(), e);
		}
	}

	/**
	 * Create name of getter method name of given field.
	 * 
	 * @param name
	 * 
	 * @param field
	 * @return
	 */
	protected String createGetterName(JackObjectField field) {
		String name = field.getName();
		Class<?> type = field.getType().getType();

		String prefix;
		if (boolean.class.equals(type)) {
			prefix = BOOL_GETTER_PREFIX;
		} else {
			prefix = OTHER_GETTER_PREFIX;
		}

		String nwfluc = Utils.firstToUpperCase(name);
		return prefix + nwfluc;
	}

	/**
	 * Create name of setter method name of given field.
	 * 
	 * @param name
	 * 
	 * @param field
	 * @return
	 */
	protected String createSetterName(JackObjectField field) {
		String name = field.getName();

		String nwfluc = Utils.firstToUpperCase(name);
		return SETTER_PREFIX + nwfluc;
	}

}
