package cz.martlin.jaxon.j2k.transformers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.j2k.transformers.AbstractFuturamaJ2KTransformer;
import cz.martlin.jaxon.j2k.transformers.FryJ2KTransformerImpl;
import cz.martlin.jaxon.jack.JackTestsUtils;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.testings.JackToKlaxonTestTuple;
import cz.martlin.jaxon.testings.j2k.BikeJ2KTestTuple;
import cz.martlin.jaxon.testings.j2k.PetsJ2KTestTuple;
import cz.martlin.jaxon.testings.jaxon.PersonJ2KTestTuple;

public class J2KFryTransformerTest {

	private final J2KConfig config = new Config();
	private final AbstractFuturamaJ2KTransformer transformer = new FryJ2KTransformerImpl(
			config);

	@Test
	public void testBi() throws JackToKlaxonException {
		testIt(PetsJ2KTestTuple.createDog());

		testIt(PersonJ2KTestTuple.createMe());

		testIt(BikeJ2KTestTuple.createSome());

	}

	private void testIt(JackToKlaxonTestTuple tuple)
			throws JackToKlaxonException {
		JackObject jack1 = tuple.createJack(config);
		KlaxonAbstractElement klaxon1 = tuple.createKlaxon(config, transformer);

		JackObject jack2 = transformer.toJack(klaxon1);
		KlaxonAbstractElement klaxon2 = transformer.toKlaxon(jack1);

		JackTestsUtils.printActualAndExcepted(klaxon2, klaxon1);
		JackTestsUtils.printActualAndExcepted(jack2, jack1);

		assertEquals(klaxon1.toString(), klaxon2.toString());
		assertEquals(jack1.toString(), jack2.toString());

		JackObject jack3 = transformer.toJack(klaxon2);
		KlaxonAbstractElement klaxon3 = transformer.toKlaxon(jack2);

		JackTestsUtils.printActualAndExcepted(klaxon3, klaxon2);
		JackTestsUtils.printActualAndExcepted(jack3, jack2);

		assertEquals(klaxon2, klaxon3);
		assertEquals(jack2, jack3);
	}

}
