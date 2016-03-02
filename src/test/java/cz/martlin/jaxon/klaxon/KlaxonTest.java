package cz.martlin.jaxon.klaxon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.w3c.dom.Document;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.klaxon.config.K2DocFormat;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.testings.klaxon.food.FoodKlaxonCreator;
import cz.martlin.jaxon.testings.tuples.KlaxonTestTuple;

/**
 * Tests {@link KlaxonConverter}.
 * 
 * @author martin
 *
 */
public class KlaxonTest {

	private final Config config = new Config();
	private final KlaxonConverter converter = new KlaxonConverter(config);

	@Test
	public void testToDocument() throws KlaxonException {
		testToDocument(FoodKlaxonCreator.createHamburger());

	}

	private void testToDocument(KlaxonTestTuple tuple) throws KlaxonException {

		KlaxonObject klaxonE = tuple.createKlaxon();
		Document documentA = converter.toDocument(klaxonE);
		Document documentE = tuple.createDocument(K2DocFormat.ATTRS_FOR_HEADERS);

		assertEquals(KlaxonTestUtils.toString(documentE), //
				KlaxonTestUtils.toString(documentA));
	}

	@Test
	public void testToKlaxon() throws KlaxonException {
		testToKlaxon(FoodKlaxonCreator.createHamburger());

	}

	private void testToKlaxon(KlaxonTestTuple tuple) throws KlaxonException {

		Document documentE = tuple.createDocument(K2DocFormat.ATTRS_FOR_HEADERS);
		KlaxonObject klaxonA = converter.toKlaxon(documentE);

		KlaxonObject klaxonE = tuple.createKlaxon();

		assertEquals(klaxonE, klaxonA);
	}

	@Test
	public void testBiConvert() throws KlaxonException {
		testBiConvert(FoodKlaxonCreator.createHamburger());
	}

	private void testBiConvert(KlaxonTestTuple tuple) throws KlaxonException {
		Document document1 = tuple.createDocument(K2DocFormat.ATTRS_FOR_HEADERS);

		// System.out.println(KlaxonTestUtils.toString(document1));

		KlaxonObject klaxon1 = converter.toKlaxon(document1);
		Document newDocument1 = converter.toDocument(klaxon1);

		assertEquals(KlaxonTestUtils.toString(document1), KlaxonTestUtils.toString(newDocument1));

		KlaxonObject klaxon2 = tuple.createKlaxon();
		Document document2 = converter.toDocument(klaxon2);
		KlaxonObject newKlaxon2 = converter.toKlaxon(document2);

		// JackTestsUtils.printActualAndExcepted(klaxon1, klaxon2);

		assertEquals(klaxon2, newKlaxon2);
	}

}
