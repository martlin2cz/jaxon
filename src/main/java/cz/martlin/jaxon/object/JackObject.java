package cz.martlin.jaxon.object;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import cz.martlin.jaxon.utils.Utils;

public class JackObject {
	private final Class<?> clazz;
	private final Map<String, JackField> fields;

	public JackObject(Class<?> clazz, Set<JackField> fields) {
		this.clazz = clazz;
		this.fields = Utils.setToMapWithNames(fields);
	}

	public JackObject(Class<?> clazz, Map<String, JackField> fields) {
		this.clazz = clazz;
		this.fields = fields;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public JackField getField(String name) {
		return fields.get(name);
	}

	public Set<String> getFieldsNames() {
		return fields.keySet();
	}

	public Collection<JackField> getFields() {
		return fields.values();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
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
		JackObject other = (JackObject) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JackObject [fields=" + fields.values() + "]";
	}

}
