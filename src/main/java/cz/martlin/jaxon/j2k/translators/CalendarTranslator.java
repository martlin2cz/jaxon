package cz.martlin.jaxon.j2k.translators;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.serializer.AbstractToStringSerializer;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;

public class CalendarTranslator extends SingleValuedTranslator<Calendar> {

	public CalendarTranslator(J2KConfig config, AtmValFrmtToKlaxonStyle toKlaxonStyle,
			AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {
		super(new CalendarToStringSerializer(config), toKlaxonStyle, fromKlaxonStyle);
	}

	public static class CalendarToStringSerializer implements AbstractToStringSerializer<Calendar> {

		private final SimpleDateFormat format;

		public CalendarToStringSerializer(J2KConfig config) {
			this.format = config.getDateFormat();
		}

		@Override
		public Class<Calendar> supportedType() {
			return Calendar.class;
		}

		@Override
		public Calendar parse(JackValueType type, String value) throws Exception {
			if (value == null) {
				return null;
			} else {
				Calendar cal = Calendar.getInstance();
				Date date = format.parse(value);
				cal.setTime(date);
				return cal;
			}
		}

		@Override
		public String serialize(JackValueType type, Calendar value) throws Exception {
			if (value == null) {
				return null;
			} else {
				Date date = value.getTime();
				return format.format(date);
			}
		}

	}
}
