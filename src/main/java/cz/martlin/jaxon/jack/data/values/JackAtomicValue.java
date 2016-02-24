package cz.martlin.jaxon.jack.data.values;

import java.io.PrintStream;
import java.net.URL;

import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.utils.Utils;

/**
 * Represents atomic value, value which can be no more processed by Jack as
 * composite object. The atomic value has only the real java value - i.e.
 * number, string, enum instance or {@link URL}.
 * 
 * @author martin
 * 
 */
public class JackAtomicValue extends JackValue {
	private final Object value;

	public JackAtomicValue(Object value) {
		super(JackValueType.of(value));

		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JackAtomicValue other = (JackAtomicValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JackAtomicValue [value=" + value + ", getType()=" + getType()
				+ "]";
	}

	@Override
	public void print(int padding, PrintStream out) {
		Utils.printPaddedLine(out, padding, "Jack atomic value " + value);

		Utils.printPaddedLine(out, padding + 1, "- type");
		getType().print(padding + 2, out);
	}

}
