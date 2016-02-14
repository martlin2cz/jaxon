package cz.martlin.jaxon.process;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.w3c.dom.Document;

import cz.martlin.jaxon.exception.JackException;
import cz.martlin.jaxon.object.KlaxonObject;
import cz.martlin.jaxon.testClasses.TestingInstances;
import cz.martlin.jaxon.utils.XMLUtils;

public class KlaxonConverterTest {
	private final KlaxonConverter converter = new KlaxonConverter();

	@Test
	public void testToDocument() throws JackException {
		KlaxonObject klaxon = TestingInstances.createKlaxon();
		Document document = converter.toDocument(klaxon);

		// TODO ...
	}

	@Test
	public void testToKlaxon() throws JackException {
		Document document = TestingInstances.createDocument();
		KlaxonObject klaxon = converter.toKlaxon(document);

		// TODO ...
	}

	@Test
	public void testBiConvert() throws JackException {
		KlaxonObject klaxon1 = TestingInstances.createKlaxon();
		Document document1 = converter.toDocument(klaxon1);
		KlaxonObject newKlaxon1 = converter.toKlaxon(document1);

		assertEquals(klaxon1, newKlaxon1);

		Document document2 = TestingInstances.createDocument();
		KlaxonObject klaxon2 = converter.toKlaxon(document2);
		Document newDocument2 = converter.toDocument(klaxon2);

		assertEquals(XMLUtils.toString(document2), XMLUtils.toString(newDocument2));

	}

}
