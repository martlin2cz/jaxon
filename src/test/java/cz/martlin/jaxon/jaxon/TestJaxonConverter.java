package cz.martlin.jaxon.jaxon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.testings.jaxon.cv.CVsJaxonTestTuple;
import cz.martlin.jaxon.testings.jaxon.person.PersonTestTuples;
import cz.martlin.jaxon.testings.tuples.JaxonTestTuple;

/**
 * Test simply complete {@link JaxonConverter}.
 * 
 * @author martin
 *
 */
public class TestJaxonConverter {

	private final Config config = new Config();
	private final JaxonConverter converter = new JaxonConverter(config);

	@Test
	public void testIt() throws JaxonException {
		testTuple(PersonTestTuples.createMe());
		testTuple(CVsJaxonTestTuple.getLinusTorvalds());
	}

	private <T extends JaxonSerializable> void testTuple(JaxonTestTuple<T> tuple) throws JaxonException {
		T object = tuple.createObject();
		// System.out.println("Object: " + object);

		String xml = converter.objectToString(object);
		// System.out.println("XML: " + xml);

		Object object2 = converter.objectFromString(xml);
		// System.out.println("Object2: " + object2);

		assertEquals(object.toString(), object2.toString());
		// warning: fails on deserialization of Date
		// assertEquals(object, object2);
	}
}
