package cz.martlin.jaxon.jaxon.converts;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cz.martlin.jaxon.jaxon.AbstractTypeSerializer;
import cz.martlin.jaxon.jaxon.PrimitiveTypeSerializer;

/**
 * https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
 * 
 * @author martin
 * 
 */
public class Primitives {

	private static final Map<Class<?>, PrimitiveTypeSerializer<?>> converters = initialize();

	private static Map<Class<?>, PrimitiveTypeSerializer<?>> initialize() {
		Map<Class<?>, PrimitiveTypeSerializer<?>> converters = new HashMap<>();

		converters.put(byte.class, new ByteConverter());
		converters.put(short.class, new ShortConverter());
		converters.put(int.class, new IntConverter());
		converters.put(long.class, new LongConverter());
		converters.put(float.class, new FloatConverter());
		converters.put(double.class, new DoubleConverter());
		converters.put(boolean.class, new BooleanConverter());
		converters.put(char.class, new CharConverter());

		return converters;
	}

	public Primitives() {
	}

	public static Map<String, AbstractTypeSerializer<?>> getConvertersWithString() {
		Map<String, AbstractTypeSerializer<?>> result = new HashMap<>();

		for (PrimitiveTypeSerializer<?> converter : converters.values()) {
			String type = converter.getType();
			result.put(type, converter);
		}

		return result;
	}

	public static Map<Class<?>, AbstractTypeSerializer<?>> getConvertersWithClass() {
		Map<Class<?>, AbstractTypeSerializer<?>> result = new HashMap<>();

		for (Entry<Class<?>, PrimitiveTypeSerializer<?>> entry : converters
				.entrySet()) {
			Class<?> type = entry.getKey();
			AbstractTypeSerializer<?> converter = entry.getValue();
			result.put(type, converter);
		}

		return result;
	}

	public String serializeType(Class<?> type) {
		PrimitiveTypeSerializer<?> converter = converters.get(type);
		return converter.getType();
	}

	public <T> PrimitiveTypeSerializer<?> getConverterFor(Class<?> clazz) {
		return converters.get(clazz);
	}

	public Class<?> parseType(String string) {
		return find(string);
	}

	public String serializeValue(Class<?> type, Object value) {
		PrimitiveTypeSerializer<?> converter = getConverterFor(type);
		return converter.serializeObject(value);
	}

	public Object parseValue(Class<?> type, String string) throws Exception {
		PrimitiveTypeSerializer<?> converter = getConverterFor(type);
		return converter.parse(string);
	}

	public String getTypeAsString(Class<?> type) {
		return getConverterFor(type).getType();
	}

	private static Class<?> find(String type) {
		for (Entry<Class<?>, PrimitiveTypeSerializer<?>> entry : converters
				.entrySet()) {

			if (entry.getValue().getType().equals(type)) {
				return entry.getKey();
			}
		}

		return null;
	}

	// /////////////////////////////////////////////////////////////////////////

	public static class CharConverter extends
			PrimitiveTypeSerializer<Character> {

		private static final String TYPE = "char";

		@Override
		public String getType() {
			return TYPE;
		}

		@Override
		public Class<?> getItemType() {
			return char.class;
		}

		@Override
		public Character parse(String val) throws Exception {
			return val.charAt(0);
		}

		@Override
		public String serialize(Character value) {
			return new String(new char[] { value });
		}

	}

	public static class BooleanConverter extends
			PrimitiveTypeSerializer<Boolean> {

		private static final String TYPE = "boolean";
		private static final String TRUE = "true";
		private static final String FALSE = "false";

		@Override
		public String getType() {
			return TYPE;
		}

		@Override
		public Class<?> getItemType() {
			return boolean.class;
		}

		@Override
		public Boolean parse(String val) throws Exception {
			if (TRUE.equalsIgnoreCase(val)) {
				return true;
			} else if (FALSE.equalsIgnoreCase(val)) {
				return false;
			} else {
				throw new IllegalArgumentException("Not a boolean: " + val);
			}
		}

		@Override
		public String serialize(Boolean value) {
			if (value) {
				return TRUE;
			} else {
				return FALSE;
			}
		}

	}

	public static class DoubleConverter extends PrimitiveTypeSerializer<Double> {

		private static final String TYPE = "double";

		@Override
		public String getType() {
			return TYPE;
		}

		@Override
		public Class<?> getItemType() {
			return double.class;
		}

		@Override
		public Double parse(String val) throws Exception {
			return Double.parseDouble(val);
		}

		@Override
		public String serialize(Double value) {
			return value.toString();
		}

	}

	public static class FloatConverter extends PrimitiveTypeSerializer<Float> {

		private static final String TYPE = "float";

		@Override
		public String getType() {
			return TYPE;
		}

		@Override
		public Class<?> getItemType() {
			return float.class;
		}

		@Override
		public Float parse(String val) throws Exception {
			return Float.parseFloat(val);
		}

		@Override
		public String serialize(Float value) {
			return value.toString();
		}

	}

	public static class LongConverter extends PrimitiveTypeSerializer<Long> {

		private static final String TYPE = "long";

		@Override
		public String getType() {
			return TYPE;
		}

		@Override
		public Class<?> getItemType() {
			return long.class;
		}

		@Override
		public Long parse(String val) throws Exception {
			return Long.parseLong(val);
		}

		@Override
		public String serialize(Long value) {
			return value.toString();
		}

	}

	public static class IntConverter extends PrimitiveTypeSerializer<Integer> {

		private static final String TYPE = "int";

		@Override
		public String getType() {
			return TYPE;
		}

		@Override
		public Class<?> getItemType() {
			return int.class;
		}

		@Override
		public Integer parse(String val) throws Exception {
			return Integer.parseInt(val);
		}

		@Override
		public String serialize(Integer value) {
			return value.toString();
		}

	}

	public static class ShortConverter extends PrimitiveTypeSerializer<Short> {

		private static final String TYPE = "short";

		@Override
		public String getType() {
			return TYPE;
		}

		@Override
		public Class<?> getItemType() {
			return short.class;
		}

		@Override
		public Short parse(String val) throws Exception {
			return Short.parseShort(val);
		}

		@Override
		public String serialize(Short value) {
			return value.toString();
		}

	}

	public static class ByteConverter extends PrimitiveTypeSerializer<Byte> {

		private static final String TYPE = "byte";

		@Override
		public String getType() {
			return TYPE;
		}

		@Override
		public Class<?> getItemType() {
			return byte.class;
		}

		@Override
		public Byte parse(String val) throws Exception {
			return Byte.parseByte(val);
		}

		@Override
		public String serialize(Byte value) {
			return value.toString();
		}

	}

}
