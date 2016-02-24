package cz.martlin.jaxon.jack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import org.junit.Test;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.jack.abstracts.JackSerializable;
import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.misc.JackException;
import cz.martlin.jaxon.testings.jack.Drink;
import cz.martlin.jaxon.testings.jaxon.Person;
import cz.martlin.jaxon.testings.jaxon.PersonJackTestTuple;

public class ReflectionAndJackTest {
	private final Config config = new Config();
	private final ReflectionAndJack raj = new ReflectionAndJack(config);

	private final JackValueType objectType = new JackValueType(Object.class);
	private final JackValueType stringType = new JackValueType(String.class);
	private final JackValueType intType = new JackValueType(int.class);
	private final JackValueType integerType = new JackValueType(Integer.class);
	private final JackValueType arrlistType = new JackValueType(ArrayList.class);
	private final JackValueType boolType = new JackValueType(boolean.class);
	private final JackValueType fileType = new JackValueType(File.class);
	private final JackValueType personType = new JackValueType(Person.class);
	private final JackValueType drinkType = new JackValueType(Drink.class);

	private final JackObjectField nameField = new JackObjectField(//
			"name", stringType);
	private final JackObjectField ageField = new JackObjectField(//
			"age", intType);
	private final JackObjectField empField = new JackObjectField(//
			"employed", boolType);

	@Test
	public void testCreateType() throws JackException {
		JackValueType stringA = raj.createType("java.lang.String");
		assertEquals(stringType, stringA);

		JackValueType personA = raj
				.createType("cz.martlin.jaxon.testings.jaxon.Person");
		assertEquals(personType, personA);

	}

	@Test
	public void testCreateNew() throws JackException {
		ArrayList<?> listA = (ArrayList<?>) raj.createNew(arrlistType);
		ArrayList<?> listE = new ArrayList<>();
		assertEquals(listE, listA);

		Person personA = (Person) raj.createNew(personType);
		Person personE = new Person();
		assertEquals(personE, personA);
	}

	@Test
	public void testRepresentsTypeChildOf() {
		// yeah
		assertTrue(ReflectionAndJack.representsTypeChildOf(//
				stringType, Object.class));
		assertTrue(ReflectionAndJack.representsTypeChildOf(//
				arrlistType, List.class));
		assertTrue(ReflectionAndJack.representsTypeChildOf(//
				new JackValueType(FileInputStream.class), InputStream.class));

		// inverted, sub is not super of sup
		assertFalse(ReflectionAndJack.representsTypeChildOf(//
				objectType, String.class));
		assertFalse(ReflectionAndJack.representsTypeChildOf(//
				new JackValueType(InputStream.class), FileInputStream.class));

		// boxed and unboxed
		assertFalse(ReflectionAndJack.representsTypeChildOf(//
				intType, Integer.class));
		assertFalse(ReflectionAndJack.representsTypeChildOf(//
				integerType, int.class));

		// completely different classes
		assertFalse(ReflectionAndJack.representsTypeChildOf(//
				stringType, Integer.class));
		assertFalse(ReflectionAndJack.representsTypeChildOf(//
				boolType, String.class));
		assertFalse(ReflectionAndJack.representsTypeChildOf(//
				fileType, JButton.class));

		// and of course - in our case
		assertTrue(ReflectionAndJack.representsTypeChildOf(//
				personType, JackSerializable.class));
		assertTrue(ReflectionAndJack.representsTypeChildOf(//
				drinkType, JackSerializable.class));

	}

	@Test
	public void testGetFields() {
		List<JackObjectField> fields = raj.getFields(personType);
		assertEquals(3, fields.size());

		assertTrue(fields.contains(nameField));
		assertTrue(fields.contains(ageField));
		assertTrue(fields.contains(empField));
	}

	// @Test
	// public void testGetField() {
	// }

	@Test
	public void testGetValueOf() throws JackException {
		Person person = PersonJackTestTuple.createMe().createObject();

		assertEquals("m@rtlin", raj.getValueOf(person, nameField));

		assertEquals(42, raj.getValueOf(person, ageField));

		assertEquals(false, raj.getValueOf(person, empField));
	}

	@Test
	public void testSetValueTo() throws JackException {
		Person person = PersonJackTestTuple.createMe().createObject();

		raj.setValueTo(person, nameField, "anonymous");
		assertEquals("anonymous", person.getName());

		raj.setValueTo(person, ageField, 111);
		assertEquals(111, person.getAge());

		raj.setValueTo(person, empField, true);
		assertEquals(true, person.isEmployed());
	}

	@Test
	public void testCreateGetterName() {

		assertEquals("getName", raj.createGetterName(nameField));
		assertEquals("getAge", raj.createGetterName(ageField));
		assertEquals("isEmployed", raj.createGetterName(empField));
	}

	@Test
	public void testCreateSetterName() {
		assertEquals("setName", raj.createSetterName(nameField));
		assertEquals("setAge", raj.createSetterName(ageField));
		assertEquals("setEmployed", raj.createSetterName(empField));

	}

}
