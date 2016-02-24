package cz.martlin.jaxon.jack.data.design;

import java.io.PrintStream;

import cz.martlin.jaxon.abstracts.HasName;
import cz.martlin.jaxon.abstracts.Printable;
import cz.martlin.jaxon.jack.abstracts.HasType;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.utils.Utils;

/**
 * Represents field of {@link JackObject} (or {@link JackObjectDesign}). Field
 * has name and type.
 * 
 * @author martin
 * 
 */
public class JackObjectField implements HasName, HasType, Printable {
	
	private final String name;
	private final JackValueType type;

	public JackObjectField(String name, JackValueType type) {
		super();
		this.name = name;
		this.type = type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public JackValueType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		JackObjectField other = (JackObjectField) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
		return "JackObjectField [name=" + name + ", type=" + type + "]";
	}

	@Override
	public void print(int padding, PrintStream out) {
		Utils.printPaddedLine(out, padding, "Jack Object's field " + name);

		Utils.printPaddedLine(out, padding + 1, "- type");
		getType().print(padding + 2, out);

	}

}
