package cz.martlin.jaxon.jack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.jack.data.design.JackObjectDesign;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.testings.JackTestTuple;
import cz.martlin.jaxon.testings.jack.DrinkJackTest;
import cz.martlin.jaxon.testings.jaxon.PersonJackTestTuple;

public class JackImplementationTest {
	private final Config config = new Config();
	private final JackImplementation impl = new JackImplementation(config);

	@Test
	public void testConvertObjectToJack() throws JackException {
		testConvertObjectToJack(PersonJackTestTuple.createMe());
		testConvertObjectToJack(DrinkJackTest.createLatte());
	}

	private <T> void testConvertObjectToJack(JackTestTuple<T> creator)
			throws JackException {

		T object = creator.createObject();
		JackObject jackA = impl.convertObjectToJack(object);
		JackObject jackE = creator.createJackObject();

		JackTestsUtils.printActualAndExcepted(jackA, jackE);

		assertEquals(jackE, jackA);
		// assertEquals(jackE.toString(), jackA.toString());
	}

	@Test
	public void testConvertJackToObject() throws JackException {
		testConvertJackToObject(PersonJackTestTuple.createMe());
		testConvertJackToObject(DrinkJackTest.createLatte());
	}

	private <T> void testConvertJackToObject(JackTestTuple<T> creator)
			throws JackException {

		JackObject jack = creator.createJackObject();
		Object objectA = impl.convertJackToObject(jack);
		Object objectE = creator.createObject();

		assertEquals(objectE, objectA);
		// assertEquals(objectE.toString(), objectA.toString());
	}

	@Test
	public void testGetDesignOfObject() throws JackException {
		testGetDesignOfObject(PersonJackTestTuple.createMe());
		testGetDesignOfObject(DrinkJackTest.createLatte());
	}

	private <T> void testGetDesignOfObject(JackTestTuple<T> creator)
			throws JackException {

		JackObjectDesign specA = impl.getDesignOfObject(creator.getType());
		JackObjectDesign specE = creator.createJackDesign();

		// JackTestsUtils.printActualAndExcepted(specA, specE);

		assertEquals(specE, specA);
		// assertEquals(specE.toString(), specA.toString());

	}

}
