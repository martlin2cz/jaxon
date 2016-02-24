package cz.martlin.jaxon.klaxon.data;

import java.io.PrintStream;

import cz.martlin.jaxon.klaxon.abstracts.HasValue;
import cz.martlin.jaxon.utils.Utils;

public class KlaxonAttribute extends KlaxonEntry implements HasValue {

	private final String value;

	public KlaxonAttribute(String name, String value) {
		super(name);
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		KlaxonAttribute other = (KlaxonAttribute) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KlaxonAttribute [getName()=" + getName() + ", value=" + value
				+ "]";
	}

	@Override
	public void print(int padding, PrintStream out) {
		Utils.printPaddedLine(out, padding, //
				"Name: " + getName() + ", value:" + value);

	}

}
