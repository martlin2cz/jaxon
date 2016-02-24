package cz.martlin.jaxon.j2k.translators;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.serializer.AbstractToStringSerializer;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;

public class StringTranslator extends SingleValuedTranslator<String> {

	public StringTranslator(AtmValFrmtToKlaxonStyle toKlaxonStyle,
			AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {

		super(new StringToStringSerializer(), //
				toKlaxonStyle, fromKlaxonStyle);
	}

	public static class StringToStringSerializer implements
			AbstractToStringSerializer<String> {

		@Override
		public Class<String> supportedType() {
			return String.class;
		}

		@Override
		public String parse(JackValueType type, String value) throws Exception {
			return value;
		}

		@Override
		public String serialize(JackValueType type, String value)
				throws Exception {
			return value;
		}

	}

}
