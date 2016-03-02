package cz.martlin.jaxon.jack;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import cz.martlin.jaxon.abstracts.Printable;
import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackAtomicValue;
import cz.martlin.jaxon.jack.data.values.JackNullValue;
import cz.martlin.jaxon.jack.data.values.JackValue;

/**
 * Utilities for jack testing.
 * 
 * @author martin
 *
 */
public class JackTestsUtils {

	/**
	 * Adds jack value of given clazz with given type as field of given name
	 * into given map.
	 * 
	 * @param values
	 * @param name
	 * @param clazz
	 * @param value
	 */
	public static void putFT(Map<JackObjectField, JackValue> values, String name, Class<?> clazz, Object value) {

		JackValueType type = new JackValueType(clazz);
		JackObjectField field = new JackObjectField(name, type);
		JackValue val;
		if (value != null) {
			val = new JackAtomicValue(value);
		} else {
			val = JackNullValue.INSTANCE;
		}

		values.put(field, val);
	}

	/**
	 * Adds field with given name and of given type into given fields list.
	 * 
	 * @param fields
	 * @param name
	 * @param clazz
	 */
	public static void addF(List<JackObjectField> fields, String name, Class<?> clazz) {

		JackValueType type = new JackValueType(clazz);
		JackObjectField field = new JackObjectField(name, type);

		fields.add(field);
	}

	/**
	 * Prints actual and expected object.
	 * 
	 * @param actual
	 * @param expected
	 */
	public static void printActualAndExcepted(Printable actual, Printable expected) {

		PrintStream out = System.out;

		out.println("ACTUAL, " + actual);
		actual.print(0, out);
		out.println("----------------------");

		out.println("EXPECTED, " + expected);
		expected.print(0, out);
		out.println("----------------------");

		out.println();

	}

}
