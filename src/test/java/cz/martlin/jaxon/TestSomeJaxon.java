package cz.martlin.jaxon;

import java.io.File;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.jaxon.JaxonConverter;
import cz.martlin.jaxon.jaxon.JaxonException;
import cz.martlin.jaxon.testings.jaxon.cv.CV;
import cz.martlin.jaxon.testings.jaxon.cv.CVsJaxonTestTuple;
import cz.martlin.jaxon.testings.jaxon.person.Person;
import cz.martlin.jaxon.testings.jaxon.person.PersonTestTuples;

/**
 * Runs some simple and demonstrating use case.
 * 
 * @author martin
 *
 */
public class TestSomeJaxon {
	private final static File file = new File("linus.xml");

	public static void main(String[] args) throws JaxonException {
		// set up
		final Config config = new Config();

		// modify configuration
		// config.setBaseTransformerName(J2KTransformWithHeaderVerboseImpl.NAME);
		// config.setObjectsTransformerName(HermesSimpleJ2KTransformerImpl.NAME);
		// config.setFormat(K2DocFormat.ATTRS_FOR_HEADERS);

		final JaxonConverter converter = new JaxonConverter(config);

		// me -> string
		PersonTestTuples persons = PersonTestTuples.createMe();
		Person me = persons.createObject();
		System.out.println("It's me, " + me + ":");

		String string = converter.objectToString(me);
		System.out.println(string);

		// CV -> file -> CV
		CVsJaxonTestTuple cvs = CVsJaxonTestTuple.getLinusTorvalds();
		CV cv = cvs.createObject();
		System.out.println("CV before save: " + cv);
		converter.objectToFile(cv, file);

		CV cv2 = (CV) converter.objectFromFile(file);
		System.out.println("CV after save:  " + cv2);

	}

}
