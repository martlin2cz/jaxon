package cz.martlin.jaxon.process;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.martlin.jaxon.exception.JackException;
import cz.martlin.jaxon.object.JackField;
import cz.martlin.jaxon.object.JackObject;
import cz.martlin.jaxon.testClasses.Person;
import cz.martlin.jaxon.testClasses.TestingInstances;

public class JackConverterTest {
	private final JackConverter converter = new JackConverter();

	@Test
	public void testToJack() throws JackException {
		Person person = TestingInstances.createObject();
		JackObject jack = converter.toJack(person);

		JackField nameA = jack.getField("name");
		JackField nameE = new JackField("name", String.class, "m@rtlin");
		assertEquals(nameE, nameA);

		JackField ageA = jack.getField("age");
		JackField ageE = new JackField("age", int.class, 42);
		assertEquals(ageE, ageA);
	}

	@Test
	public void testToObject() throws JackException {
		JackObject jack = TestingInstances.createJack();
		Person person = (Person) converter.toObject(jack);

		assertEquals("m@rtlin", person.getName());
		assertEquals(42, person.getAge());
	}

	@Test
	public void testBiConvert() throws JackException {
		Person person1 = TestingInstances.createObject();
		JackObject jack1 = converter.toJack(person1);
		Person newPerson1 = (Person) converter.toObject(jack1);
		assertEquals(person1, newPerson1);

		JackObject jack2 = TestingInstances.createJack();
		Person person2 = (Person) converter.toObject(jack2);
		JackObject newJack2 = converter.toJack(person2);
		assertEquals(jack2, newJack2);

	}

}
