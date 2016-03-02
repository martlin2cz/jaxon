package cz.martlin.jaxon.j2k.translators;

import java.math.BigDecimal;

import cz.martlin.jaxon.j2k.serializer.AbstractToStringSerializer;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;

/**
 * Represents {@link BigDecimal} instances translator.
 * 
 * @author martin
 *
 */
public class BigDecimalTranslator extends SingleValuedTranslator<BigDecimal> {

	public BigDecimalTranslator() {
		super(new BigDecimalsSerializer());
	}

	public static class BigDecimalsSerializer implements AbstractToStringSerializer<BigDecimal> {

		@Override
		public Class<BigDecimal> supportedType() {
			return BigDecimal.class;
		}

		@Override
		public BigDecimal parse(JackValueType type, String value) throws Exception {

			return new BigDecimal(value);
		}

		@Override
		public String serialize(JackValueType type, BigDecimal value) throws Exception {

			return value.toPlainString();
		}

	}

}
