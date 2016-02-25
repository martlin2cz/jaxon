package cz.martlin.jaxon.j2k.translators;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.serializer.AbstractToStringSerializer;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;

public class DateTranslator extends SingleValuedTranslator<Date> {

	public DateTranslator(J2KConfig config, AtmValFrmtToKlaxonStyle toKlaxonStyle,
			AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {

		super(new DateToStringSerializer(config), toKlaxonStyle, fromKlaxonStyle);
	}


	public static class DateToStringSerializer implements AbstractToStringSerializer<Date> {

		private final SimpleDateFormat format;

		public DateToStringSerializer(J2KConfig config) {
			this.format = config.getDateFormat();
		}

		@Override
		public Class<Date> supportedType() {
			return Date.class;
		}

		@Override
		public Date parse(JackValueType type, String value) throws Exception {
			if (value == null) {
				return null;
			} else {
				return format.parse(value);
			}
		}

		@Override
		public String serialize(JackValueType type, Date value) throws Exception {
			if (value == null) {
				return null;
			} else {
				return format.format(value);
			}
		}

	}

}
