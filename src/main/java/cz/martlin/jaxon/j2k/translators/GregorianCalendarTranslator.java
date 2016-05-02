package cz.martlin.jaxon.j2k.translators;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.serializer.AbstractToStringSerializer;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;

/**
 * Translator of gregorian calendar, quick fix of {@link CalendarTranslator}'s
 * bug (not worked with {@link GregorianCalendar}'s instances).
 * 
 * @author martin
 *
 */
public class GregorianCalendarTranslator extends SingleValuedTranslator<GregorianCalendar> {

	public GregorianCalendarTranslator(J2KConfig config) {
		super(new GregorianCalendarToStringSerializer(config));
	}

	public static class GregorianCalendarToStringSerializer implements AbstractToStringSerializer<GregorianCalendar> {

		private final SimpleDateFormat format;

		public GregorianCalendarToStringSerializer(J2KConfig config) {
			this.format = config.getDateFormat();
		}

		@Override
		public Class<GregorianCalendar> supportedType() {
			return GregorianCalendar.class;
		}

		@Override
		public GregorianCalendar parse(JackValueType type, String value) throws Exception {
			if (value == null) {
				return null;
			} else {
				GregorianCalendar cal = new GregorianCalendar();
				Date date = format.parse(value);
				cal.setTime(date);
				return cal;
			}
		}

		@Override
		public String serialize(JackValueType type, GregorianCalendar value) throws Exception {
			if (value == null) {
				return null;
			} else {
				Date date = value.getTime();
				return format.format(date);
			}
		}

	}
}