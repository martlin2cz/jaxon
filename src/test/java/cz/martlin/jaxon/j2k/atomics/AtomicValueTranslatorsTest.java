package cz.martlin.jaxon.j2k.atomics;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import cz.martlin.jaxon.Primitives;
import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.j2k.translator.AtomicValueTranslator;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.j2k.translators.BigDecimalTranslator;
import cz.martlin.jaxon.j2k.translators.EnumsTranslator;
import cz.martlin.jaxon.j2k.translators.PrimitiveTypeTranslator;
import cz.martlin.jaxon.j2k.translators.StringTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackAtomicValue;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;
import cz.martlin.jaxon.testings.j2k.pet.Gender;

/**
 * Tests various {@link AtomicValueTranslator}s.
 * 
 * @author martin
 *
 */
public class AtomicValueTranslatorsTest {

	@Test
	public void testBasicAndPrimitives() throws JackToKlaxonException {
		final SingleValuedTranslator<Double> doubles = new PrimitiveTypeTranslator<>(//
				new Primitives.DoubleSerializer());

		final SingleValuedTranslator<Boolean> booleans = new PrimitiveTypeTranslator<>(//
				new Primitives.BooleanSerializer());

		final SingleValuedTranslator<Character> chars = new PrimitiveTypeTranslator<>(//
				new Primitives.CharSerializer());

		// test all string variants
		testConversion(new StringTranslator(), //
				"whatever", String.class, "foo", "foo");

		// test primitives: double, boolean and char (each of each type)
		testConversion(doubles, //
				"cost", double.class, 45.0000, "45.0");

		testConversion(booleans, //
				"can-swim", boolean.class, true, "true");

		testConversion(chars, //
				"cost", char.class, '@', "@");

		// FIXME cannot compile under java 1.7
		// testConversion(new EnumsTranslator<>(), //
		// "gender", Gender.class, Gender.FEMALE, "FEMALE");
	}

	public void testSomeMoore() throws JackToKlaxonException {

		testConversion(new BigDecimalTranslator(), //
				"gender", BigDecimal.class, //
				new BigDecimal(145623556l), "145623556");

		// TODO test all the others

	}

	/**
	 * Tests with given translator jack to klaxon and back.
	 * 
	 * @param translator
	 * @param attrName
	 * @param attrType
	 * @param jackValue
	 * @param klaxonValue
	 * @throws JackToKlaxonException
	 */
	private <T> void testConversion(SingleValuedTranslator<T> translator, //
			String attrName, Class<T> attrType, Object jackValue, String klaxonValue)//
					throws JackToKlaxonException {

		// inits
		JackValueType type = new JackValueType(attrType);
		// JackObjectField field = new JackObjectField(attrName, type);

		// make expected
		KlaxonStringValue klaxonE = new KlaxonStringValue(attrName, klaxonValue);
		JackValue jackE = new JackAtomicValue(jackValue);

		// calculate actual
		KlaxonValue klaxonA = translator.toKlaxon(attrName, type, jackE);
		JackValue jackA = translator.toJack(type, klaxonE);

		// and compare
		// JackTestsUtils.printActualAndExcepted(klaxonA, klaxonE);
		// JackTestsUtils.printActualAndExcepted(jackA, jackE);

		assertEquals(klaxonE, klaxonA);
		assertEquals(jackE, jackA);
	}

}
