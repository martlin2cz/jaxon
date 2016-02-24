package cz.martlin.jaxon.jack.data.values;

import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;

import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.utils.Utils;

/**
 * Represents map. In fact, encapsulates java {@link Map} interface
 * implementations.
 * 
 * @author martin
 * 
 */
public class JackMap extends JackCompositeValue {
	private final Map<JackValue, JackValue> data;

	public JackMap(JackValueType type, Map<JackValue, JackValue> data) {
		super(type);
		this.data = data;
	}

	public Map<JackValue, JackValue> getData() {
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
		JackMap other = (JackMap) obj;
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
		return "JackMap [data="
				+ (data != null ? Utils.toString(data.entrySet(), maxLen)
						: null) + "]";
	}

	@Override
	public void print(int padding, PrintStream out) {
		Utils.printPaddedLine(out, padding, "Jack Map");

		Utils.printPaddedLine(out, padding + 1, "- type");
		getType().print(padding + 2, out);

		Utils.printPaddedLine(out, padding + 1, "- size: " + data.size());
		Utils.printPaddedLine(out, padding + 1, "- items");

		for (Entry<JackValue, JackValue> jack : data.entrySet()) {
			JackValue key = jack.getKey();
			JackValue value = jack.getValue();

			Utils.printPaddedLine(out, padding + 2, "entry");

			Utils.printPaddedLine(out, padding + 3, "- key");
			key.print(padding + 4, out);

			Utils.printPaddedLine(out, padding + 3, "- value");
			value.print(padding + 4, out);
		}
	}
}
