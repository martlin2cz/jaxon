package cz.martlin.jaxon.klaxon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.w3c.dom.Document;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.config.ImplProvider;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.testings.KlaxonTestTuple;
import cz.martlin.jaxon.testings.klaxon.FoodKlaxonCreator;

public class KlaxonTest {

	private final Config config = ImplProvider.getTestingConfig();
	private final KlaxonConverter converter = new KlaxonConverter(config);

	@Test
	public void testToDocument() throws KlaxonException {
		testToDocument(FoodKlaxonCreator.createHamburger());

	}

	private void testToDocument(KlaxonTestTuple tuple) throws KlaxonException {

		KlaxonAbstractElement klaxonE = tuple.createKlaxon();
		Document documentA = converter.toDocument(klaxonE);
		Document documentE = tuple.createDocument();

		assertEquals(KlaxonTestUtils.toString(documentE), //
				KlaxonTestUtils.toString(documentA));
	}

	@Test
	public void testToKlaxon() throws KlaxonException {
		testToKlaxon(FoodKlaxonCreator.createHamburger());

	}

	private void testToKlaxon(KlaxonTestTuple tuple) throws KlaxonException {

		Document documentE = tuple.createDocument();
		KlaxonAbstractElement klaxonA = converter.toKlaxon(documentE);

		KlaxonAbstractElement klaxonE = tuple.createKlaxon();

		assertEquals(klaxonE, klaxonA);
	}

	@Test
	public void testBiConvert() throws KlaxonException {
		testBiConvert(FoodKlaxonCreator.createHamburger());
	}

	private void testBiConvert(KlaxonTestTuple tuple) throws KlaxonException {
		Document document1 = tuple.createDocument();

		// System.out.println(KlaxonTestUtils.toString(document1));

		KlaxonAbstractElement klaxon1 = converter.toKlaxon(document1);
		Document newDocument1 = converter.toDocument(klaxon1);

		assertEquals(KlaxonTestUtils.toString(document1),
				KlaxonTestUtils.toString(newDocument1));

		KlaxonAbstractElement klaxon2 = tuple.createKlaxon();
		Document document2 = converter.toDocument(klaxon2);
		KlaxonAbstractElement newKlaxon2 = converter.toKlaxon(document2);

		// JackTestsUtils.printActualAndExcepted(klaxon1, klaxon2);

		assertEquals(klaxon2, newKlaxon2);
	}

}
