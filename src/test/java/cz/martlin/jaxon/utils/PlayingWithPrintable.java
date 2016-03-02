package cz.martlin.jaxon.utils;

import cz.martlin.jaxon.abstracts.Printable;
import cz.martlin.jaxon.jack.data.design.JackObjectDesign;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.testings.jack.drink.DrinkJackTestTuple;
import cz.martlin.jaxon.testings.jaxon.person.PersonTestTuples;
import cz.martlin.jaxon.testings.klaxon.food.FoodKlaxonCreator;
import cz.martlin.jaxon.testings.tuples.JackTestTuple;
import cz.martlin.jaxon.testings.tuples.KlaxonTestTuple;

/**
 * Simple testing class for testing {@link Printable}.
 * 
 * @author martin
 *
 */
public class PlayingWithPrintable {

	public static void main(String[] args) {
		printJacks(PersonTestTuples.createMe());
		printJacks(DrinkJackTestTuple.createCaffeLate());

		printKlaxons(FoodKlaxonCreator.createHamburger());

	}

	/**
	 * Prints object, jack and design of given tuple.
	 * 
	 * @param tuple
	 */
	private static <T> void printJacks(JackTestTuple<T> tuple) {
		Object object = tuple.createObject();
		JackObjectDesign design = tuple.createJackDesign();
		JackObject jack = tuple.createJack();

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

	/**
	 * Prints klaxon object of given tuple.
	 * 
	 * @param tuple
	 */
	private static void printKlaxons(KlaxonTestTuple tuple) {
		KlaxonObject klaxon = tuple.createKlaxon();

		System.out.println("Klaxon:");
		klaxon.print(0, System.out);
		System.out.println("------------------");

		System.out.println();
	}

}
