package cz.martlin.jaxon.j2k.transformers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.testings.j2k.bike.BikeJ2KTestTuple;
import cz.martlin.jaxon.testings.j2k.pet.PetsJ2KTestTuple;
import cz.martlin.jaxon.testings.jaxon.person.PersonTestTuples;
import cz.martlin.jaxon.testings.tuples.JackToKlaxonObjectsTestTuple;

/**
 * Tests {@link FryJ2KTransformerImpl}.
 * 
 * @author martin
 *
 */
public class J2KFryTransformerTest {

	private final J2KConfig config = new Config();
	private final AbstractFuturamaJ2KTransformer transformer = new FryJ2KTransformerImpl(config);

	@Test
	public void testBi() throws JackToKlaxonException {
		testIt(PetsJ2KTestTuple.createDog());

		testIt(PersonTestTuples.createMe());

		testIt(BikeJ2KTestTuple.createSome());

	}

	/**
	 * Tests bi-conversion of given tuple.
	 * 
	 * @param tuple
	 * @throws JackToKlaxonException
	 */
	private void testIt(JackToKlaxonObjectsTestTuple tuple) throws JackToKlaxonException {
		JackObject jack1 = tuple.createJack();
		KlaxonObject klaxon1 = tuple.createKlaxon(config, transformer);

		JackObject jack2 = transformer.toJack(klaxon1);
		KlaxonObject klaxon2 = transformer.toKlaxon(jack1);

		// JackTestsUtils.printActualAndExcepted(klaxon2, klaxon1);
		// JackTestsUtils.printActualAndExcepted(jack2, jack1);

		assertEquals(klaxon1.toString(), klaxon2.toString());
		assertEquals(jack1.toString(), jack2.toString());

		JackObject jack3 = transformer.toJack(klaxon2);
		KlaxonObject klaxon3 = transformer.toKlaxon(jack2);

		// JackTestsUtils.printActualAndExcepted(klaxon3, klaxon2);
		// JackTestsUtils.printActualAndExcepted(jack3, jack2);

		assertEquals(klaxon2, klaxon3);
		assertEquals(jack2, jack3);
	}

}
