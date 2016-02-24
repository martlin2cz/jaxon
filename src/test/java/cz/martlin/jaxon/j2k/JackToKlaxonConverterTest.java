package cz.martlin.jaxon.j2k;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.jack.JackTestsUtils;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.testings.j2k.PetsJ2KTestTuple;

public class JackToKlaxonConverterTest {

	private final Config config = new Config();
	private final JackToKlaxonConverter converter = new JackToKlaxonConverter(
			config);

	@Test
	public void testKlaxonToJack() {

		// TODO fail("Not yet implemented");
	}

	// @Test
	// public void testJackToKlaxon() throws JackToKlaxonException {
	// PetsJ2KTestTuple pets = PetsJ2KTestTuple.createDog();
	//
	// JackObject jackE = pets.createJack(config);
	// KlaxonAbstractElement klaxonE = null;// TODO pets.createKlaxon(config,
	// // converter.getObjectsTransformer());//.createRoot
	//
	// JackObject jackA = converter.klaxonToJack(klaxonE);
	// KlaxonAbstractElement klaxonA = converter.jackToKlaxon(jackE);
	//
	// JackTestsUtils.printActualAndExcepted(klaxonA, klaxonE);
	// JackTestsUtils.printActualAndExcepted(jackA, jackE);
	//
	// assertEquals(jackE, jackA);
	// assertEquals(klaxonE, klaxonA);
	//
	// //
	//
	// // jackE.print(0, System.out);
	// // klaxonE.print(0, System.out);
	//
	// // TODO fail("Not yet implemented");
	// }

}
