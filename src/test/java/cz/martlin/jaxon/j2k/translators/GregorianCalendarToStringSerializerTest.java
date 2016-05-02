package cz.martlin.jaxon.j2k.translators;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.translators.GregorianCalendarTranslator.GregorianCalendarToStringSerializer;
import cz.martlin.jaxon.jack.data.design.JackValueType;

public class GregorianCalendarToStringSerializerTest {

	private final J2KConfig config = new Config();
	private final GregorianCalendarTranslator.GregorianCalendarToStringSerializer //
	serializer = new GregorianCalendarToStringSerializer(config);

	@Test
	public void testSerialize() throws Exception {
		GregorianCalendar cal = new GregorianCalendar(2016, Calendar.APRIL, 19, 20, 52, 56);
		String expected = "19.04.2016 20:52:56";

		String actual = serializer.serialize(JackValueType.of(cal), cal);

		assertEquals(expected, actual);
	}

	@Test
	public void testDeserialize() throws Exception {
		String str = "19.04.2016 20:57:49";
		GregorianCalendar expected = new GregorianCalendar(2016, Calendar.APRIL, 19, 20, 57, 49);

		GregorianCalendar actual = serializer.parse(JackValueType.of(expected), str);

		assertEquals(expected, actual);
	}

	// TODO cleaninfy
}
