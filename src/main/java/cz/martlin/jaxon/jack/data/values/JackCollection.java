package cz.martlin.jaxon.jack.data.values;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;

import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.utils.Utils;

/**
 * Represents collection of values. In fact, encapsulates every of java
 * {@link Collection} implementation.
 * 
 * @author martin
 * 
 */
public class JackCollection extends JackCompositeValue {

	private final ArrayList<JackValue> data;

	public JackCollection(JackValueType type, Collection<JackValue> data) {
		super(type);

		this.data = new ArrayList<>(data);
	}

	public Collection<JackValue> getData() {
		return data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((data == null) ? 0 : data.hashCode());
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
		JackCollection other = (JackCollection) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "JackCollection [data="
				+ (data != null ? data
						.subList(0, Math.min(data.size(), maxLen)) : null)
				+ "]";
	}

	@Override
	public void print(int padding, PrintStream out) {
		Utils.printPaddedLine(out, padding, "Jack Collection");

		Utils.printPaddedLine(out, padding + 1, "- type");
		getType().print(padding + 2, out);

		Utils.printPaddedLine(out, padding + 1, "- fields");

		for (JackValue item : data) {

			Utils.printPaddedLine(out, padding + 2, "item");
			item.print(padding + 3, out);
		}

	}

}
