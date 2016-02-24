package cz.martlin.jaxon.jack.data.values;

import java.io.PrintStream;

import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.utils.Utils;

/**
 * Represents null value.
 * 
 * @author martin
 * 
 */
public class JackNullValue extends JackValue {

	public static final JackNullValue INSTANCE = new JackNullValue();

	public JackNullValue() {
		super(new JackValueType(null));
	}

	@Override
	public String toString() {
		return "JackNullValue []";
	}

	@Override
	public void print(int padding, PrintStream out) {
		Utils.printPaddedLine(out, padding, "Null value");
	}

}
