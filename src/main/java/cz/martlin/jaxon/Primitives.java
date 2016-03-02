package cz.martlin.jaxon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.martlin.jaxon.j2k.serializer.PrimitiveTypeSerializer;
import cz.martlin.jaxon.jack.data.design.JackValueType;

/**
 * Lists Java primitive data types and related stuff (serializers, names of
 * types and wrapping types).
 * https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
 * 
 * @author martin
 * 
 */
public class Primitives {

	private static final List<PrimitiveTypeSerializer<?>> serializers = initSerializers();
	private static final Map<String, Class<?>> names = initNames();
	private static final Map<Class<?>, Class<?>> wrappers = initWrappers(serializers);

	/**
	 * Creates list of primitive type serializers for all primitives.
	 * 
	 * @return
	 */
	private static List<PrimitiveTypeSerializer<?>> initSerializers() {
		List<PrimitiveTypeSerializer<?>> serializers = new ArrayList<>();

		serializers.add(new ByteSerializer());
		serializers.add(new ShortSerializer());
		serializers.add(new IntSerializer());
		serializers.add(new LongSerializer());
		serializers.add(new FloatSerializer());
		serializers.add(new DoubleSerializer());
		serializers.add(new BooleanSerializer());
		serializers.add(new CharSerializer());

		return serializers;
	}

	/**
	 * Initializes list of names of all privitives.
	 * 
	 * @return
	 */
	private static Map<String, Class<?>> initNames() {
		Map<String, Class<?>> names = new HashMap<>();

		names.put("byte", byte.class);
		names.put("short", short.class);
		names.put("int", int.class);
		names.put("long", long.class);
		names.put("float", float.class);
		names.put("double", double.class);
		names.put("boolean", boolean.class);
		names.put("char", char.class);

		return names;
	}

	/**
	 * Initializes list of primitives and theirs wrappers.
	 * 
	 * @param serializers
	 * @return
	 */
	private static Map<Class<?>, Class<?>> initWrappers(List<PrimitiveTypeSerializer<?>> serializers) {
		Map<Class<?>, Class<?>> names = new HashMap<>();

		for (PrimitiveTypeSerializer<?> serializer : serializers) {
			names.put(serializer.getPrimitiveType(), serializer.getWrapperType());
		}

		return names;
	}

	private Primitives() {
	}

	/**
	 * Returns primitives serializers.
	 * 
	 * @return
	 */
	public static List<PrimitiveTypeSerializer<?>> getSerializers() {
		return serializers;
	}

	/**
	 * Returns names of primitive data types.
	 * 
	 * @return
	 */
	public static Map<String, Class<?>> getNames() {
		return names;
	}

	/**
	 * Returns map with entries primitive type - wrapper type.
	 * 
	 * @return
	 */
	public static Map<Class<?>, Class<?>> getWrappers() {
		return wrappers;
	}

	// /////////////////////////////////////////////////////////////////////////

	public static class CharSerializer extends PrimitiveTypeSerializer<Character> {

		@Override
		public Class<Character> getPrimitiveType() {
			return char.class;
		}

		@Override
		public Class<Character> getWrapperType() {
			return Character.class;
		}

		@Override
		public Character parse(JackValueType type, String val) throws Exception {
			return val.charAt(0);
		}

		@Override
		public String serialize(JackValueType type, Character value) {
			return new String(new char[] { value });
		}

	}

	public static class BooleanSerializer extends PrimitiveTypeSerializer<Boolean> {

		private static final String TRUE = "true";
		private static final String FALSE = "false";

		@Override
		public Class<Boolean> getPrimitiveType() {
			return boolean.class;
		}

		@Override
		public Class<Boolean> getWrapperType() {
			return Boolean.class;
		}

		@Override
		public Boolean parse(JackValueType type, String val) throws Exception {
			if (TRUE.equalsIgnoreCase(val)) {
				return true;
			} else if (FALSE.equalsIgnoreCase(val)) {
				return false;
			} else {
				throw new IllegalArgumentException("Not a boolean: " + val);
			}
		}

		@Override
		public String serialize(JackValueType type, Boolean value) {
			if (value) {
				return TRUE;
			} else {
				return FALSE;
			}
		}

	}

	public static class DoubleSerializer extends PrimitiveTypeSerializer<Double> {

		@Override
		public Class<Double> getPrimitiveType() {
			return double.class;
		}

		@Override
		public Class<Double> getWrapperType() {
			return Double.class;
		}

		@Override
		public Double parse(JackValueType type, String val) throws Exception {
			return Double.parseDouble(val);
		}

		@Override
		public String serialize(JackValueType type, Double value) {
			return value.toString();
		}

	}

	public static class FloatSerializer extends PrimitiveTypeSerializer<Float> {

		@Override
		public Class<Float> getPrimitiveType() {
			return float.class;
		}

		@Override
		public Class<Float> getWrapperType() {
			return Float.class;
		}

		@Override
		public Float parse(JackValueType type, String val) throws Exception {
			return Float.parseFloat(val);
		}

		@Override
		public String serialize(JackValueType type, Float value) {
			return value.toString();
		}

	}

	public static class LongSerializer extends PrimitiveTypeSerializer<Long> {

		@Override
		public Class<Long> getPrimitiveType() {
			return long.class;
		}

		@Override
		public Class<Long> getWrapperType() {
			return Long.class;
		}

		@Override
		public Long parse(JackValueType type, String val) throws Exception {
			return Long.parseLong(val);
		}

		@Override
		public String serialize(JackValueType type, Long value) {
			return value.toString();
		}

	}

	public static class IntSerializer extends PrimitiveTypeSerializer<Integer> {

		@Override
		public Class<Integer> getPrimitiveType() {
			return int.class;
		}

		@Override
		public Class<Integer> getWrapperType() {
			return Integer.class;
		}

		@Override
		public Integer parse(JackValueType type, String val) throws Exception {
			return Integer.parseInt(val);
		}

		@Override
		public String serialize(JackValueType type, Integer value) {
			return value.toString();
		}

	}

	public static class ShortSerializer extends PrimitiveTypeSerializer<Short> {

		@Override
		public Class<Short> getPrimitiveType() {
			return short.class;
		}

		@Override
		public Class<Short> getWrapperType() {
			return Short.class;
		}

		@Override
		public Short parse(JackValueType type, String val) throws Exception {
			return Short.parseShort(val);
		}

		@Override
		public String serialize(JackValueType type, Short value) {
			return value.toString();
		}

	}

	public static class ByteSerializer extends PrimitiveTypeSerializer<Byte> {

		@Override
		public Class<Byte> getPrimitiveType() {
			return byte.class;
		}

		@Override
		public Class<Byte> getWrapperType() {
			return Byte.class;
		}

		@Override
		public Byte parse(JackValueType type, String val) throws Exception {
			return Byte.parseByte(val);
		}

		@Override
		public String serialize(JackValueType type, Byte value) {
			return value.toString();
		}

	}

}
