package cz.martlin.jaxon.klaxon.data;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jaxon.abstracts.HasName;
import cz.martlin.jaxon.abstracts.Printable;

/**
 * Abstract klaxon entity. Has name and some header informations.
 * 
 * @author martin
 *
 */
public abstract class KlaxonValue implements HasName, Printable {

	protected final String name;
	protected final List<KlaxonValue> headers;

	public KlaxonValue(String name, List<KlaxonValue> headers) {
		this.name = name;
		this.headers = headers;
	}

	public KlaxonValue(String name) {
		this.name = name;
		this.headers = new ArrayList<>();
	}

	@Override
	public String getName() {
		return name;
	}

	public List<KlaxonValue> getHeaders() {
		return headers;
	}

	/**
	 * Finds and returns first field of gien name, or null if there is no such
	 * that.
	 * 
	 * @param name
	 * @return
	 */
	public abstract KlaxonValue findFirst(String name);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((headers == null) ? 0 : headers.hashCode());
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
		KlaxonValue other = (KlaxonValue) obj;
		if (headers == null) {
			if (other.headers != null)
				return false;
		} else if (!headers.equals(other.headers))
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
		return "KlaxonValue [name=" + name + ", headers=" + headers + "]";
	}

}
