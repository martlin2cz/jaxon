package cz.martlin.jaxon.process;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.martlin.jaxon.exception.JaxonException;
import cz.martlin.jaxon.object.JackObject;
import cz.martlin.jaxon.object.KlaxonObject;
import cz.martlin.jaxon.testClasses.TestingInstances;

public class JaxonConverterTest {

	private final JackAndKlaxonConverter converter = new JackAndKlaxonConverter();

	@Test
	public void testToKlaxon() throws JaxonException {
		JackObject jack = TestingInstances.createJack();
		KlaxonObject klaxon = converter.toKlaxon(jack);

		// TODO

	}

	@Test
	public void testBidirectional() throws JaxonException {
		JackObject jack = TestingInstances.createJack();
		KlaxonObject klaxon = converter.toKlaxon(jack);
		JackObject newJack = converter.toJack(klaxon);

		assertEquals(jack, newJack);
		System.out.println(jack);
		System.out.println(newJack);

	}

	@Test
	public void testToJack() throws JaxonException {
		//
		// TODO FIXME
		// je rozdíl mezi obyčejným klaxonem a klaxonem s metadatama pro
		// deserializaci!!!
		KlaxonObject klaxon = TestingInstances.createKlaxon();
		JackObject jack = converter.toJack(klaxon);

		// TODO
	}

}
