package cz.martlin.jaxon.utils;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cz.martlin.jaxon.abstracts.HasName;

public class Utils {
	private static final String PADDING = "|  ";

	public static <T> Set<T> arrayToSet(T[] array) {
		Set<T> result = new LinkedHashSet<>(array.length);

		for (T item : array) {
			result.add(item);
		}

		return result;
	}

	public static <V extends HasName> Map<String, V> setToMapWithNames(
			Collection<V> set) {

		Map<String, V> map = new LinkedHashMap<>(set.size());

		for (V item : set) {
			String name = item.getName();
			map.put(name, item);
		}

		return map;
	}

	public static <T extends HasName> T popItemWithName(String name,
			Set<T> items) {
		T found = null;

		for (T item : items) {
			if (name.equals(item.getName())) {
				found = item;
			}
		}

		if (found != null) {
			items.remove(found);
		}

		return found;
	}

	// TODO test&refactor
	public static String firstToUpperCase(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	// TODO test&refactor
	public static String firstToLowerCase(String name) {
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	public static String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
				&& i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	public static void printPaddedLine(PrintStream out, int padding,
			String value) {

		printPadded(out, padding, value);
		out.println();
	}

	public static void printPadded(PrintStream out, int padding, String value) {

		for (int i = 0; i < padding; i++) {
			out.print(PADDING);
		}

		out.print(value);
	}

	public static <K, V> K findKey(Map<K, V> map, V value) {
		for (Entry<K, V> entry: map.entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}

}
