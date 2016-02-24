package cz.martlin.jaxon.jack.data.design;

import java.io.PrintStream;
import java.util.List;

import cz.martlin.jaxon.abstracts.Printable;
import cz.martlin.jaxon.jack.abstracts.HasType;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.utils.Utils;

/**
 * Represents design of jack object. The design consists from type of designed
 * object and fields list.
 * 
 * @see JackObject
 * @author martin
 * 
 */
public class JackObjectDesign implements HasType, Printable {
	private final JackValueType type;
	private final List<JackObjectField> fields;

	public JackObjectDesign(JackValueType type, List<JackObjectField> fields) {
		super();
		this.type = type;
		this.fields = fields;
	}

	@Override
	public JackValueType getType() {
		return type;
	}

	public List<JackObjectField> getFields() {
		return fields;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JackObjectDesign other = (JackObjectDesign) obj;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "JackObjectDesign [type="
				+ type
				+ ", fields="
				+ (fields != null ? fields.subList(0,
						Math.min(fields.size(), maxLen)) : null) + "]";
	}

	@Override
	public void print(int padding, PrintStream out) {
		Utils.printPaddedLine(out, padding, "Jack object's design");

		Utils.printPaddedLine(out, padding + 1, "- type:");
		getType().print(padding + 2, out);

		Utils.printPaddedLine(out, padding + 1, "- fields:");
		for (JackObjectField field : fields) {
			field.print(padding + 2, out);
		}
	}

}
