package cz.martlin.jaxon.j2k.translators;

import java.text.SimpleDateFormat;

import cz.martlin.jaxon.j2k.serializer.AbstractToStringSerializer;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;

/**
 * Implements translator for {@link SimpleDateFormat} instances.
 * 
 * @author martin
 *
 */
public class SimpleDateFormatTranslator extends SingleValuedTranslator<SimpleDateFormat> {

	public SimpleDateFormatTranslator() {

		super(new SimpleDateFormatToStrSerializer());
	}

	public static class SimpleDateFormatToStrSerializer implements AbstractToStringSerializer<SimpleDateFormat> {

		@Override
		public Class<SimpleDateFormat> supportedType() {
			return SimpleDateFormat.class;
		}

		@Override
		public SimpleDateFormat parse(JackValueType type, String value) throws Exception {
			return new SimpleDateFormat(value);
		}

		@Override
		public String serialize(JackValueType type, SimpleDateFormat value) throws Exception {
			return value.toPattern();
		}

	}

}
