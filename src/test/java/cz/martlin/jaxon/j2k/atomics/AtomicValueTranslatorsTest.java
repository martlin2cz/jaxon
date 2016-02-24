package cz.martlin.jaxon.j2k.atomics;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import cz.martlin.jaxon.Primitives;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.j2k.translators.BigDecimalTranslator;
import cz.martlin.jaxon.j2k.translators.PrimitiveTypeTranslator;
import cz.martlin.jaxon.j2k.translators.StringTranslator;
import cz.martlin.jaxon.jack.JackTestsUtils;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackAtomicValue;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.KlaxonTestUtils;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.klaxon.data.KlaxonAttribute;
import cz.martlin.jaxon.klaxon.data.KlaxonEntry;

public class AtomicValueTranslatorsTest {

	@Test
	public void testStringTranslator() throws JackToKlaxonException {
//		final StringTranslator stringsByAttr = new StringTranslator(
//				AtmValFrmtToKlaxonStyle.ATTRIBUTE,
//				AtmValFrmtFromKlaxonStyle.SAME_AS_TO_KLAXON);

		final StringTranslator stringsByValuedChild = new StringTranslator(
				AtmValFrmtToKlaxonStyle.CHILD_WITH_TEXT_VALUE,
				AtmValFrmtFromKlaxonStyle.SAME_AS_TO_KLAXON);

//		final StringTranslator stringByAttributedChild = new StringTranslator(
//				AtmValFrmtToKlaxonStyle.CHILD_WITH_ATTRIBUTE,
//				AtmValFrmtFromKlaxonStyle.SAME_AS_TO_KLAXON);

//		final SingleValuedTranslator<Double> doubleByAttribute = new PrimitiveTypeTranslator<>(
//				new Primitives.DoubleConverter(),
//				AtmValFrmtToKlaxonStyle.ATTRIBUTE,
//				AtmValFrmtFromKlaxonStyle.SAME_AS_TO_KLAXON);

		final SingleValuedTranslator<Boolean> booleanByValuedChild = new PrimitiveTypeTranslator<>(
				new Primitives.BooleanConverter(),
				AtmValFrmtToKlaxonStyle.CHILD_WITH_TEXT_VALUE,
				AtmValFrmtFromKlaxonStyle.SAME_AS_TO_KLAXON);

//		final SingleValuedTranslator<Character> charByAttributedChild = new PrimitiveTypeTranslator<>(
//				new Primitives.CharConverter(),
//				AtmValFrmtToKlaxonStyle.CHILD_WITH_ATTRIBUTE,
//				AtmValFrmtFromKlaxonStyle.SAME_AS_TO_KLAXON);

//		final SingleValuedTranslator<Gender> genderByAttribute = new EnumsTranslator<>(
//				AtmValFrmtToKlaxonStyle.ATTRIBUTE,
//				AtmValFrmtFromKlaxonStyle.SAME_AS_TO_KLAXON);

		final SingleValuedTranslator<BigDecimal> decimalByValuedChild = new BigDecimalTranslator(
				AtmValFrmtToKlaxonStyle.CHILD_WITH_TEXT_VALUE,
				AtmValFrmtFromKlaxonStyle.SAME_AS_TO_KLAXON);

		// test all string variants
//		testFormatByAttribute(stringsByAttr, //
//				"whatever", String.class, "foo", "foo");

		testFormatByValuedChild(stringsByValuedChild, //
				"whatever", String.class, "foo", "foo");

//		testFormatByAttributedChild(stringByAttributedChild, //
//				"whatever", String.class, "foo", "foo");

		// test primitives: double, boolean and char (each of each type)
//		testFormatByAttribute(doubleByAttribute, //
//				"cost", double.class, 45.0000, "45.0");

		testFormatByValuedChild(booleanByValuedChild, //
				"can-swim", boolean.class, true, "true");

//		testFormatByAttributedChild(charByAttributedChild, //
//				"cost", char.class, '@', "@");

		// test various: enum, big decimal, ...
//		testFormatByAttribute(genderByAttribute, //
//				"gender", Gender.class, Gender.FEMALE, "FEMALE");

		testFormatByValuedChild(decimalByValuedChild, //
				"gender", BigDecimal.class, //
				new BigDecimal(145623556l), "145623556");
	}

	@Deprecated
	private <T> void testFormatByAttribute(
			SingleValuedTranslator<T> translator, String attrName,
			Class<T> attrType, Object jackValue, String klaxonValue)
			throws JackToKlaxonException {

		// inits
		JackValueType type = new JackValueType(attrType);
		// JackObjectField field = new JackObjectField(attrName, type);

		// make expected
		KlaxonEntry klaxonE = new KlaxonAttribute(attrName, klaxonValue);
		JackValue jackE = new JackAtomicValue(jackValue);

		// calculate actual
		KlaxonEntry klaxonA = translator.toKlaxon(attrName, type, jackE);
		JackValue jackA = translator.toJack(type, klaxonE);

		// and compare
		JackTestsUtils.printActualAndExcepted(klaxonA, klaxonE);
		JackTestsUtils.printActualAndExcepted(jackA, jackE);

		assertEquals(klaxonE, klaxonA);
		assertEquals(jackE, jackA);
	}

	private <T> void testFormatByValuedChild(
			SingleValuedTranslator<T> translator, String attrName,
			Class<T> attrType, Object jackValue, String klaxonValue)
			throws JackToKlaxonException {

		// inits
		JackValueType type = new JackValueType(attrType);
		// JackObjectField field = new JackObjectField(attrName, type);

		// create expected
		KlaxonEntry klaxonE = KlaxonTestUtils.createWithOneAttr(attrName, null,
				null, klaxonValue);
		JackValue jackE = new JackAtomicValue(jackValue);

		// calculate actual
		KlaxonEntry klaxonA = translator.toKlaxon(attrName, type, jackE);
		JackValue jackA = translator.toJack(type, klaxonE);

		// and compare
		JackTestsUtils.printActualAndExcepted(klaxonA, klaxonE);
		JackTestsUtils.printActualAndExcepted(jackA, jackE);

		assertEquals(klaxonE, klaxonA);
		assertEquals(jackE, jackA);
	}

	@Deprecated
	private <T> void testFormatByAttributedChild(
			SingleValuedTranslator<T> translator, String attrName,
			Class<T> attrType, Object jackValue, String klaxonValue)
			throws JackToKlaxonException {

		// inits
		JackValueType type = new JackValueType(attrType);
		// JackObjectField field = new JackObjectField(attrName, type);

		// create expected
		KlaxonAbstractElement klaxonE = KlaxonTestUtils.createWithOneAttr(
				attrName, "value", klaxonValue);
		JackValue jackE = new JackAtomicValue(jackValue);

		// calculate actual
		KlaxonEntry klaxonA = translator.toKlaxon(attrName, type, jackE);
		JackValue jackA = translator.toJack(type, klaxonE);

		// and compare
		JackTestsUtils.printActualAndExcepted(klaxonA, klaxonE);
		JackTestsUtils.printActualAndExcepted(jackA, jackE);

		assertEquals(klaxonE, klaxonA);
		assertEquals(jackE, jackA);
	}

}
