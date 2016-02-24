package cz.martlin.jaxon.klaxon.data;

import java.io.PrintStream;
import java.util.Set;

import cz.martlin.jaxon.klaxon.abstracts.HasValue;
import cz.martlin.jaxon.utils.Utils;

public class KlaxonElemWithValue extends KlaxonAbstractElement implements HasValue {

	private final String value;

	public KlaxonElemWithValue(String name, Set<KlaxonAttribute> attributes,
			String value) {
		super(name, attributes);

		this.value = value;
	}

	public KlaxonElemWithValue(String name, String value) {
		super(name);

		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public EntriesCollection asEntries() {
		return new EntriesCollection(getAttributes(), value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		KlaxonElemWithValue other = (KlaxonElemWithValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KlaxonElemWithValue [getName()=" + getName()
				+ ", getAttributes()=" + getAttributes() + ", value=" + value
				+ "]";
	}

	@Override
	public void print(int padding, PrintStream out) {
		Utils.printPaddedLine(out, padding, //
				"Klaxon element with value, name " + getName());

		Utils.printPaddedLine(out, padding + 1, "- Attributes:");
		for (KlaxonAttribute attribute : getAttributes()) {
			attribute.print(padding + 2, out);
		}

		Utils.printPaddedLine(out, padding + 1, //
				"- Value: " + value);

	}

}
