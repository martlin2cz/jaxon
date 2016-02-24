package cz.martlin.jaxon.klaxon.data;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import cz.martlin.jaxon.utils.Utils;

public abstract class KlaxonAbstractElement extends KlaxonEntry {

	private final Map<String, KlaxonAttribute> attributes;

	public KlaxonAbstractElement(String name) {
		super(name);
		this.attributes = Collections.emptyMap();
	}

	public KlaxonAbstractElement(String name, Set<KlaxonAttribute> attributes) {
		super(name);
		this.attributes = Utils.setToMapWithNames(attributes);
	}

	public Set<KlaxonAttribute> getAttributes() {
		return new TreeSet<>(attributes.values());
	}

	public KlaxonAttribute getAttribute(String name) {
		return attributes.get(name);
	}

	public abstract EntriesCollection asEntries();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
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
		KlaxonAbstractElement other = (KlaxonAbstractElement) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "KlaxonAbstractElement [attributes="
				+ (attributes != null ? Utils.toString(attributes.values(),
						maxLen) : null) + "]";
	}

}
