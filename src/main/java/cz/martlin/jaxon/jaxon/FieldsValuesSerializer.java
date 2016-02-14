package cz.martlin.jaxon.jaxon;

import java.util.LinkedHashMap;
import java.util.Map;

import cz.martlin.jaxon.jaxon.converts.Primitives;

public class FieldsValuesSerializer {

	private static final AbstractTypeSerializer<?> DEFAULT_CONVERTER = null; // TODO
																		// ?
	private static final Map<String, AbstractTypeSerializer<?>> convertersWithStrings = new LinkedHashMap<>();
	private static final Map<Class<?>, AbstractTypeSerializer<?>> convertersWithClasses = new LinkedHashMap<>();

	static {
		initialize(convertersWithStrings, convertersWithClasses);
	}

	public FieldsValuesSerializer() {

	}

	private static void initialize(Map<String, AbstractTypeSerializer<?>> cws,
			Map<Class<?>, AbstractTypeSerializer<?>> cwc) {

		cws.putAll(Primitives.getConvertersWithString());
		cwc.putAll(Primitives.getConvertersWithClass());

		StringConverter string = new StringConverter();
		cws.put(string.getType(), string);
		cwc.put(string.getItemType(), string);

	}

	public AbstractTypeSerializer<?> get(Class<?> type) {
		AbstractTypeSerializer<?> converter = find(type);
		if (converter != null) {
			return converter;
		} else {
			return DEFAULT_CONVERTER;
		}
	}

	public AbstractTypeSerializer<?> get(String type) {
		AbstractTypeSerializer<?> converter = find(type);
		if (converter != null) {
			return converter;
		} else {
			return DEFAULT_CONVERTER;
		}
	}

	private AbstractTypeSerializer<?> find(Class<?> type) {
		return convertersWithClasses.get(type);
	}

	private AbstractTypeSerializer<?> find(String type) {
		return convertersWithStrings.get(type);
	}

}
