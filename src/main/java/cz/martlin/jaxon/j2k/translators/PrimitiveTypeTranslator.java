package cz.martlin.jaxon.j2k.translators;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.serializer.PrimitiveTypeSerializer;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;

public class PrimitiveTypeTranslator<T> extends SingleValuedTranslator<T> {

	public PrimitiveTypeTranslator(PrimitiveTypeSerializer<T> serializer,
			AtmValFrmtToKlaxonStyle toKlaxonStyle,
			AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {

		super(serializer, toKlaxonStyle, fromKlaxonStyle);
	}

	@Override
	public boolean isApplicableTo(JackValueType type) {
		PrimitiveTypeSerializer<T> ser = (PrimitiveTypeSerializer<T>) serializer;

		Class<?> primitive = ser.getPrimitiveType();
		Class<?> wrapper = ser.getWrapperType();

		Class<?> clazz = type.getType();

		return primitive.equals(clazz) || wrapper.equals(clazz);
	}

}
