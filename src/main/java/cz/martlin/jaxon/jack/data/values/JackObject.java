package cz.martlin.jaxon.jack.data.values;

import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;

import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.utils.Utils;

/**
 * Represents object. Each object has fields and their values.
 * 
 * @author martin
 * 
 */
public class JackObject extends JackCompositeValue {
	private final Map<JackObjectField, JackValue> values;
	private final String jackDescription = null;	//TODO read via JackSerializable#jackDescription

	public JackObject(JackValueType type, Map<JackObjectField, JackValue> values) {
		super(type);
		this.values = values;
	}

	public Map<JackObjectField, JackValue> getValues() {
		return values;
	}

	/**
	 * Finds value of given field. Returns null if no such field this object
	 * has.
	 * 
	 * @param fieldName
	 * @return
	 */
	public JackValue getValue(String fieldName) {

		for (JackObjectField field : values.keySet()) {
			String name = field.getName();
			if (name.equals(fieldName)) {
				return values.get(field);
			}
		}

		return null;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "JackObject [values="
				+ (values != null ? Utils.toString(values.entrySet(), maxLen)
						: null) + ", getType()=" + getType() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		JackObject other = (JackObject) obj;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	@Override
	public void print(int padding, PrintStream out) {
		Utils.printPaddedLine(out, padding, "Jack Object");

		Utils.printPaddedLine(out, padding + 1, "- type");
		getType().print(padding + 2, out);

		Utils.printPaddedLine(out, padding + 1, "- fields");

		for (Entry<JackObjectField, JackValue> entry : values.entrySet()) {
			Utils.printPaddedLine(out, padding + 2, "- field");

			JackObjectField field = entry.getKey();
			Utils.printPaddedLine(out, padding + 3, "- field spec");
			field.print(padding + 4, out);

			JackValue value = entry.getValue();
			Utils.printPaddedLine(out, padding + 3, "- value");
			value.print(padding + 4, out);
		}
	}

}
