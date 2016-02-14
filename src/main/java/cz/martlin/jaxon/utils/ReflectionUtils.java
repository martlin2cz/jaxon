package cz.martlin.jaxon.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import cz.martlin.jaxon.exception.JackException;
import cz.martlin.jaxon.object.JackField;

public class ReflectionUtils {

	public static Set<Field> getFields(Object object) {
		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();

		return Utils.arrayToSet(fields);
	}

	public static Field getField(Object object, String name)
			throws JackException {
		Class<?> clazz = object.getClass();
		try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new JackException("Problem with field " + name, e);
		}
	}

	public static Object getValueOf(Object object, Field field)
			throws JackException {
		
		Class<?> clazz = object.getClass();
		Method getter = createGetter(clazz, field);
		return invokeGetter(getter, object);
	}

	public static void setValueTo(Object object, JackField field)
			throws JackException {
		
		Class<?> clazz = object.getClass();
		Method setter = createSetter(clazz, field);
		Object value = field.getValue();
		invokeSetter(setter, object, value);
	}

	private static Method createGetter(Class<?> clazz, Field field)
			throws JackException {

		String name = createGetterName(field);

		try {
			return clazz.getMethod(name);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new JackException("Could not create getter of "
					+ field.getName(), e);
		}
	}

	private static Method createSetter(Class<?> clazz, JackField field)
			throws JackException {

		String name = createSetterName(field);
		Class<?> type = field.getType();
		try {
			return clazz.getMethod(name, type);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new JackException("Could not create setter of "
					+ field.getName(), e);
		}
	}

	private static void invokeSetter(Method setter, Object object, Object value)
			throws JackException {
		try {
			setter.invoke(object, value);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new JackException("Could not invoke setter "
					+ setter.getName(), e);
		}
	}

	private static Object invokeGetter(Method getter, Object object)
			throws JackException {

		try {
			return getter.invoke(object);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new JackException("Could not invoke getter "
					+ getter.getName(), e);
		}
	}

	public static Object createNew(Class<?> clazz) throws JackException {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new JackException("Cannot instantite " + clazz.getName(), e);
		}
	}

	protected static String createGetterName(Field field) {
		String fieldName = field.getName();
		String fwfuc = Utils.firstToUpperCase(fieldName);
		return "get" + fwfuc;
	}

	protected static String createSetterName(JackField field) {
		String fieldName = field.getName();
		String fwfuc = Utils.firstToUpperCase(fieldName);
		return "set" + fwfuc;
	}

}
