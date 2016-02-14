package cz.martlin.jaxon.object;

import java.util.LinkedHashSet;
import java.util.Set;

import cz.martlin.jaxon.abstracts.HasName;

public class KlaxonObject implements HasName {

	private final String name;
	private final Set<KlaxonItem> fields;

	public KlaxonObject(String name, Set<KlaxonItem> fields) {
		super();
		this.name = name;
		this.fields = fields;
	}

	@Override
	public String getName() {
		return name;
	}

	public Set<KlaxonItem> getItems() {
		return new LinkedHashSet<>(fields);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		KlaxonObject other = (KlaxonObject) obj;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KlaxonObject [name=" + name + ", fields=" + fields + "]";
	}

}
