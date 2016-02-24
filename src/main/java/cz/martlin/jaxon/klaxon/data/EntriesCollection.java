package cz.martlin.jaxon.klaxon.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.martlin.jaxon.klaxon.abstracts.HasValue;

public class EntriesCollection implements HasValue {
	private final Set<KlaxonAttribute> attributes;
	private final List<KlaxonAbstractElement> children;
	private String value;

	public EntriesCollection(Set<KlaxonAttribute> attributes,
			List<KlaxonAbstractElement> children, String value) {
		super();
		this.attributes = attributes;
		this.children = children;
		this.value = value;
	}

	public EntriesCollection(Set<KlaxonAttribute> attributes, String value) {
		super();
		this.attributes = attributes;
		this.children = new ArrayList<>();
		this.value = value;
	}

	public EntriesCollection(Set<KlaxonAttribute> attributes,
			List<KlaxonAbstractElement> children) {
		super();
		this.attributes = attributes;
		this.children = children;
		this.value = null;
	}

	public EntriesCollection() {
		super();
		this.attributes = new HashSet<>();
		this.children = new ArrayList<>();
		this.value = null;
	}

	public Set<KlaxonAttribute> getAttributes() {
		return attributes;
	}

	public List<KlaxonAbstractElement> getChildren() {
		return children;
	}

	@Override
	public String getValue() {
		return value;
	}

	public Collection<KlaxonEntry> list() {
		List<KlaxonEntry> entries = new ArrayList<>(//
				attributes.size() + children.size());

		entries.addAll(attributes);
		entries.addAll(children);

		if (value != null) {
			KlaxonAttribute attr = new KlaxonAttribute(null, value);
			entries.add(attr);
		}

		return entries;
	}

	public KlaxonEntry getFirst(String name) {
		for (KlaxonEntry entry : list()) {
			if (entry.getName().equals(name)) {
				return entry;
			}
		}

		return null;
	}

	public void add(KlaxonEntry klaxon) {
		if (klaxon == null) {
			return;
		} else if (klaxon instanceof KlaxonAbstractElement) {
			add((KlaxonAbstractElement) klaxon);
		} else if (klaxon instanceof KlaxonAttribute) {
			add((KlaxonAttribute) klaxon);
		} else {
			throw new IllegalArgumentException("Unknown entry type");
		}
	}

	public void add(KlaxonAbstractElement entry) {
		this.children.add(entry);
	}

	public void add(KlaxonAttribute entry) {
		this.attributes.add(entry);
	}

	public void setValue(String value) {
		this.value = value;
	}

}
