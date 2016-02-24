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

public class JackTestsUtils {

	// public static void put(Map<JackValueType, JackValue> result, Class<?>
	// type,
	// Object value) {
	//
	// JackValueType spec = new XX_JackAtomicSpec(type);
	// JackValue val = new JackAtomicValue(value);
	//
	// result.put(spec, val);
	// }
	//
	// public static void put(Map<String, JackValueType> result, String name,
	// Class<?> type) {
	//
	// JackValueType spec = new XX_JackAtomicSpec(type);
	//
	// result.put(name, spec);
	// }

	public static void putFT(Map<JackObjectField, JackValue> values,
			String name, Class<?> clazz, Object value) {

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

	public static void addF(List<JackObjectField> fields, String name,
			Class<?> clazz) {

		JackValueType type = new JackValueType(clazz);
		JackObjectField field = new JackObjectField(name, type);

		fields.add(field);
	}

	public static void printActualAndExcepted(Printable actual,
			Printable expected) {

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
