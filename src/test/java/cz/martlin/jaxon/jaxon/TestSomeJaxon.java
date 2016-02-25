package cz.martlin.jaxon.jaxon;

import java.io.File;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.j2k.transformers.FryJ2KTransformerImpl;
import cz.martlin.jaxon.j2k.transformers.J2KTransformWithRootObjSimpleImpl;
import cz.martlin.jaxon.testings.jaxon.Person;
import cz.martlin.jaxon.testings.jaxon.TesingPersons;

public class TestSomeJaxon {
	private static final Config config = new Config();

	// change format here...
	private static final JaxonConverter converter = new JaxonConverter(config, //
			J2KTransformWithRootObjSimpleImpl.NAME, //
			FryJ2KTransformerImpl.NAME //
	);

	// ... can try theese values of second param:
	// J2KTransformWithRootObjSimpleImpl.NAME
	// or
	// J2KTransformWithHeaderVerboseImpl.NAME
	// and the third param supports
	// FryJ2KTransformerImpl.NAME
	// or
	// HermesSimpleJ2KTransformerImpl.NAME

	public static void main(String[] args) throws JaxonException {
		final File file = new File("john.xml");

		// me -> string
		Person me = TesingPersons.createMe();
		System.out.println("It's me, " + me + ":");

		String string = converter.objectToString(me);
		System.out.println(string);

		// john -> file -> john2
		Person john = TesingPersons.createJohn();
		System.out.println("John before: " + john);
		converter.objectToFile(john, file);

		Person john2;
		john2 = (Person) converter.objectFromFile(file);
		System.out.println("John after:  " + john2);

	}

}
