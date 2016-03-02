package cz.martlin.jaxon.j2k.translators;

import cz.martlin.jaxon.Primitives;
import cz.martlin.jaxon.j2k.serializer.PrimitiveTypeSerializer;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;

/**
 * Implements {@link SingleValuedTranslator} to use with Java primitive types
 * (and they're wrappers).
 * 
 * @author martin
 * @see Primitives
 *
 * @param <T>
 */
public class PrimitiveTypeTranslator<T> extends SingleValuedTranslator<T> {

	public PrimitiveTypeTranslator(PrimitiveTypeSerializer<T> serializer) {

		super(serializer);
	}

	/**
	 * Returns true, if is type corresponding primitive type or its wrapper
	 * type.
	 */
	@Override
	public boolean isApplicableTo(JackValueType type) {
		PrimitiveTypeSerializer<T> ser = (PrimitiveTypeSerializer<T>) serializer;

		Class<?> primitive = ser.getPrimitiveType();
		Class<?> wrapper = ser.getWrapperType();

		Class<?> clazz = type.getType();

		return primitive.equals(clazz) || wrapper.equals(clazz);
	}

}
