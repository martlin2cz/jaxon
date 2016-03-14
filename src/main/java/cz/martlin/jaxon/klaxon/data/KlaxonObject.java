package cz.martlin.jaxon.klaxon.data;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import cz.martlin.jaxon.utils.Utils;

/**
 * Represents composite klaxon object. This object copounds from list of others
 * klaxon values, and - inherittely - headers and name.
 * 
 * @author martin
 *
 */
public class KlaxonObject extends KlaxonValue {
	private final List<KlaxonValue> fields;

	public KlaxonObject(String name, List<KlaxonValue> headers, List<KlaxonValue> fields) {
		super(name, headers);
		this.fields = fields;
	}

	public KlaxonObject(String name, List<KlaxonValue> fields) {
		super(name);
		this.fields = fields;
	}

	public List<KlaxonValue> getFields() {
		return fields;
	}

	public List<KlaxonValue> list() {
		List<KlaxonValue> result = new ArrayList<>(headers.size() + fields.size());

		result.addAll(headers);
		result.addAll(fields);

		return result;
	}

	public KlaxonValue findFirst(String name) {
		for (KlaxonValue value : list()) {
			if (value.getName().equals(name)) {
				return value;
			}
		}
		
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
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
		KlaxonObject other = (KlaxonObject) obj;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KlaxonObject [name=" + name + ", headers=" + headers + ", fields=" + fields + "]";
	}

	@Override
	public void print(int padding, PrintStream out) {
		Utils.printPaddedLine(out, padding, "Klaxon Object, name: " + name);

		Utils.printPaddedLine(out, padding + 1, "- headers:");
		for (KlaxonValue header : headers) {
			header.print(padding + 2, out);
		}

		Utils.printPaddedLine(out, padding + 1, "- fields:");
		for (KlaxonValue field : fields) {
			field.print(padding + 2, out);
		}
	}
}
