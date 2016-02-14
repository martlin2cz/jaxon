package cz.martlin.jaxon.utils;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import cz.martlin.jaxon.abstracts.HasName;

public class Utils {
	public static <T> Set<T> arrayToSet(T[] array) {
		Set<T> result = new LinkedHashSet<>(array.length);

		for (T item : array) {
			result.add(item);
		}

		return result;
	}

	public static <V extends HasName> Map<String, V> setToMapWithNames(
			Set<V> set) {
		Map<String, V> map = new HashMap<>(set.size());

		for (V item : set) {
			String name = item.getName();
			map.put(name, item);
		}

		return map;
	}

	public static <T extends HasName> T popItemWithName(String name, Set<T> items) {
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
	
	

	// TODO test
	public static String firstToUpperCase(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

}
