package cz.martlin.jaxon.klaxon.data;

import java.io.PrintStream;
import java.util.List;

import cz.martlin.jaxon.utils.Utils;

/**
 * Represents klaxon entity with single value (an, inheritted, name and header
 * fields).
 * 
 * @author martin
 *
 */
public class KlaxonStringValue extends KlaxonValue {
	private final String value;

	public KlaxonStringValue(String name, List<KlaxonValue> headers, String value) {
		super(name, headers);

		this.value = value;
	}

	public KlaxonStringValue(String name, String value) {
		super(name);

		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public KlaxonValue findFirst(String name) {
		for (KlaxonValue header : headers) {
			if (header.getName().equals(name)) {
				return header;
			}
		}

		return null;
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
		KlaxonStringValue other = (KlaxonStringValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KlaxonStringValue [name=" + name + ", headers=" + headers + ", value=" + value + "]";
	}

	@Override
	public void print(int padding, PrintStream out) {
		Utils.printPaddedLine(out, padding, "Klaxon String value, name: " + name);
		Utils.printPaddedLine(out, padding + 1, "- value: " + value);

		Utils.printPaddedLine(out, padding + 1, "- headers:");
		for (KlaxonValue header : headers) {
			header.print(padding + 2, out);
		}
	}

}
