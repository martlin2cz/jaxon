package cz.martlin.jaxon;

import cz.martlin.jaxon.jack.data.design.JackObjectDesign;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.testings.JackTestTuple;
import cz.martlin.jaxon.testings.jack.DrinkJackTest;
import cz.martlin.jaxon.testings.jaxon.PersonJackTestTuple;

public class PlayingWithPrintable {

	public static void main(String[] args) {
		printJacks(PersonJackTestTuple.createMe());
		printJacks(PersonJackTestTuple.createFooBar());
		printJacks(DrinkJackTest.createLatte());

	}

	private static <T> void printJacks(JackTestTuple<T> tuple) {
		Object object = tuple.createObject();
		JackObjectDesign design = tuple.createJackDesign();
		JackObject jack = tuple.createJackObject();

		System.out.println("Object:");
		System.out.println(object);
		System.out.println("------------------");

		System.out.println("Jack:");
		jack.print(0, System.out);
		System.out.println("------------------");

		System.out.println("Design:");
		design.print(0, System.out);
		System.out.println("------------------");

		System.out.println();
	}

}
