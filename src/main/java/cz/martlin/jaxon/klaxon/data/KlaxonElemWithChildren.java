package cz.martlin.jaxon.klaxon.data;

import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import cz.martlin.jaxon.utils.Utils;

public class KlaxonElemWithChildren extends KlaxonAbstractElement {

	private final List<KlaxonAbstractElement> children;

	public KlaxonElemWithChildren(String name, Set<KlaxonAttribute> attributes,
			List<KlaxonAbstractElement> children) {
		super(name, attributes);
		this.children = children;
	}

	public KlaxonElemWithChildren(String name,
			List<KlaxonAbstractElement> children) {
		super(name);
		this.children = children;
	}

	public KlaxonElemWithChildren(String name, Set<KlaxonAttribute> attributes) {
		super(name, attributes);
		this.children = Collections.emptyList();
	}

	public KlaxonElemWithChildren(String name, EntriesCollection entries) {
		super(name, entries.getAttributes());
		this.children = entries.getChildren();
	}

	public List<KlaxonAbstractElement> getChildren() {
		return children;
	}

	public boolean isEmpty() {
		return children.isEmpty();
	}

	public KlaxonAbstractElement findFirstChild(String name) {
		for (KlaxonAbstractElement child : children) {
			if (child.getName().equals(name)) {
				return child;
			}
		}

		return null;
	}

	@Override
	public EntriesCollection asEntries() {
		return new EntriesCollection(getAttributes(), children);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
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
		KlaxonElemWithChildren other = (KlaxonElemWithChildren) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KlaxonElemWithChildren [getName()=" + getName()
				+ ", getAttributes()=" + getAttributes() + ", children="
				+ children + "]";
	}

	@Override
	public void print(int padding, PrintStream out) {
		Utils.printPaddedLine(out, padding, //
				"Klaxon element with children, name " + getName());

		Utils.printPaddedLine(out, padding + 1, "- Attributes:");
		for (KlaxonAttribute attribute : getAttributes()) {
			attribute.print(padding + 2, out);
		}

		Utils.printPaddedLine(out, padding + 1, "- Children:");
		for (KlaxonAbstractElement child : children) {
			child.print(padding + 2, out);
		}
	}

}
