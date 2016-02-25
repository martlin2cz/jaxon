package cz.martlin.jaxon.j2k.translators;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.serializer.AbstractToStringSerializer;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;

public class EnumsTranslator<T extends Enum<T>> extends SingleValuedTranslator<T> {

	public EnumsTranslator(AtmValFrmtToKlaxonStyle toKlaxonStyle, AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {

		super(new EnumSerializer<T>(), //
				toKlaxonStyle, fromKlaxonStyle);
	}

	@Override
	public boolean isApplicableTo(JackValueType type) {
		return type.getType().isEnum();
	}

	public static class EnumSerializer<T extends Enum<T>> implements AbstractToStringSerializer<T> {

		public EnumSerializer() {
		}

		@Override
		public Class<T> supportedType() {
			// fix for javac bug, see i.e.:
			// http://stackoverflow.com/questions/1609531/same-source-code-eclipse-build-success-but-maven-javac-fails
			
			Class<?> clazz = (Class<?>) Enum.class;
			@SuppressWarnings("unchecked")
			Class<T> clazzT = (Class<T>) clazz;

			return clazzT;
		}

		@Override
		public T parse(JackValueType type, String value) throws Exception {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) type.getType();

			return Enum.valueOf(clazz, value);
		}

		@Override
		public String serialize(JackValueType type, T value) throws Exception {
			return value.toString(); // TODO null check
		}

	}
}
