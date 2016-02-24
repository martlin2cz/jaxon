package cz.martlin.jaxon.jack.data.design;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;

import cz.martlin.jaxon.Primitives;
import cz.martlin.jaxon.abstracts.Printable;
import cz.martlin.jaxon.jack.ReflectionAndJack;
import cz.martlin.jaxon.jack.abstracts.JackSerializable;
import cz.martlin.jaxon.utils.Utils;

/**
 * Represents type of value. Type is represented by java type (java class).
 * 
 * @author martin
 * 
 */
public class JackValueType implements Printable {
	private Class<?> type;

	public JackValueType(Class<?> type) {
		super();
		this.type = type;
	}

	public Class<?> getType() {
		return type;
	}

	public boolean isNull() {
		return type == null;
	}

	public boolean isAtomicValue() {
		return true;
	}

	public boolean isComposite() {
		return isJackObject() || isArray() || isCollection() || isMap();
	}

	public boolean isJackObject() {
		return ReflectionAndJack.representsTypeChildOf(this,
				JackSerializable.class);
	}

	public boolean isArray() {
		return type != null && type.isArray();
	}

	public boolean isCollection() {
		return ReflectionAndJack.representsTypeChildOf(this, Collection.class);
	}

	public boolean isMap() {
		return ReflectionAndJack.representsTypeChildOf(this, Map.class);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		JackValueType other = (JackValueType) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public String getTypeAsString() {
		if (type.isPrimitive()) {
			return Utils.findKey(Primitives.getNames(), type);
		} else {
			return type.getName();
		}
	}

	@Override
	public String toString() {
		return "JackValueType [type=" + type + "]";
	}

	public static JackValueType of(Object objectOrNull) {
		Class<?> clazz;

		if (objectOrNull == null) {
			clazz = null;
		} else {
			clazz = objectOrNull.getClass();
		}

		return new JackValueType(clazz);
	}

	@Override
	public void print(int padding, PrintStream out) {
		Utils.printPaddedLine(out, padding, "Jack value type: "
				+ getTypeAsString());
	}

}
