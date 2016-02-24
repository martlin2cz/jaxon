package cz.martlin.jaxon.jack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.config.ImplProvider;
import cz.martlin.jaxon.jack.data.design.JackObjectDesign;
import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.jack.data.values.JackAtomicValue;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.testings.JackTestTuple;
import cz.martlin.jaxon.testings.jack.Drink;
import cz.martlin.jaxon.testings.jack.DrinkJackTest;
import cz.martlin.jaxon.testings.jaxon.Person;
import cz.martlin.jaxon.testings.jaxon.PersonJackTestTuple;

public class JackTest {
	private final Config config = ImplProvider.getTestingConfig();
	private final JackConverter converter = new JackConverter(config);

	private final JackTestTuple<Person> persons = PersonJackTestTuple.createMe();
	private final JackTestTuple<Drink> drinks = DrinkJackTest.createLatte();

	@Test
	public void testToJack() throws JackException {
		// person
		Person person = persons.createObject();
		JackObject jack = (JackObject) converter.toJack(person);

		JackValue nameA = jack.getValue("name");
		JackValue nameE = new JackAtomicValue("m@rtlin");
		assertEquals(nameE, nameA);

		JackValue ageA = jack.getValue("age");
		JackValue ageE = new JackAtomicValue(42);
		assertEquals(ageE, ageA);

		JackValue empA = jack.getValue("employed");
		JackValue empE = new JackAtomicValue(false);
		assertEquals(empE, empA);
	}

	@Test
	public void testToObject() throws JackException {
		JackObject jack = persons.createJackObject();
		Person person = (Person) converter.toObject(jack);

		assertEquals("m@rtlin", person.getName());
		assertEquals(42, person.getAge());
		assertEquals(false, person.isEmployed());
	}

	@Test
	public void testBiConvert() throws JackException {
		// object -> jack -> object
		Person person1 = persons.createObject();
		JackObject jack1 = (JackObject) converter.toJack(person1);
		Person newPerson1 = (Person) converter.toObject(jack1);
		assertEquals(person1, newPerson1);

		// jack -> object -> jack
		JackObject jack2 = persons.createJackObject();
		Person person2 = (Person) converter.toObject(jack2);
		JackValue newJack2 = converter.toJack(person2);
		assertEquals(jack2, newJack2);

	}

	@Test
	public void testDesign() throws JackException {
		JackObjectDesign design = converter.getDesign(Person.class);

		assertEquals(3, design.getFields().size());

		JackObjectField name = new JackObjectField("name", //
				new JackValueType(String.class));
		assertTrue(design.getFields().contains(name));

		JackObjectField age = new JackObjectField("age", //
				new JackValueType(int.class));
		assertTrue(design.getFields().contains(age));

		JackObjectField emp = new JackObjectField("employed", //
				new JackValueType(boolean.class));
		assertTrue(design.getFields().contains(emp));

	}

	@Test
	public void testDrinks() throws JackException {
		Drink objectE = drinks.createObject();
		JackObject jackE = drinks.createJackObject();

		JackValue jackA = converter.toJack(objectE);
		Drink objectA = (Drink) converter.toObject(jackE);

		assertEquals(jackE, jackA);
		assertEquals(objectE, objectA);

	}
}
