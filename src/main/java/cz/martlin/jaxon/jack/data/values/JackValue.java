package cz.martlin.jaxon.jack.data.values;

import cz.martlin.jaxon.abstracts.Printable;
import cz.martlin.jaxon.jack.abstracts.HasType;
import cz.martlin.jaxon.jack.data.design.JackValueType;

/**
 * Represents abstract value in jack meaning. Jack objects, collections and maps
 * are composite of {@link JackValue}s, each of them is Jack value too. The
 * abstract value has only type.
 * 
 * @author martin
 * 
 */
public abstract class JackValue implements HasType, Printable {

	private final JackValueType type;

	public JackValue(JackValueType type) {
		this.type = type;

	}

	@Override
	public JackValueType getType() {
		return type;
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
		JackValue other = (JackValue) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JackValue [type=" + type + "]";
	}

}