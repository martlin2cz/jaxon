package cz.martlin.jaxon.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.junit.Test;

import cz.martlin.jaxon.exception.JackException;
import cz.martlin.jaxon.object.JackField;
import cz.martlin.jaxon.testClasses.Person;
import cz.martlin.jaxon.testClasses.TestingInstances;
import cz.martlin.jaxon.utils.ReflectionUtils;

public class ReflectionUtilsTest {
	private final Person person = TestingInstances.createObject();

	@Test
	public void testGetFields() {
		assertEquals(2, ReflectionUtils.getFields(person).size());
	}

	@Test
	public void testGetField() throws JackException {
		assertNotNull(ReflectionUtils.getField(person, "name"));
		assertNotNull(ReflectionUtils.getField(person, "age"));

		try {
			assertNotNull(ReflectionUtils.getField(person, "girlfriend"));
			fail("Should fail");
		} catch (JackException e) {
		}
	}

	@Test
	public void testGetValueOf() throws JackException {

		Field name = ReflectionUtils.getField(person, "name");
		assertEquals("m@rtlin", ReflectionUtils.getValueOf(person, name));

		Field age = ReflectionUtils.getField(person, "age");
		assertEquals(42, ReflectionUtils.getValueOf(person, age));
	}
	
	@Test
	public void testSetValueTo() throws JackException {
		Person person2 = TestingInstances.createObject();
		
		JackField name = new JackField("name", String.class, "foo bar");
		ReflectionUtils.setValueTo(person2, name);
		
		JackField age = new JackField("age", int.class, 256);
		ReflectionUtils.setValueTo(person2, age);
	}
	
	@Test
	public void testCreateNew() throws JackException {
		assertNotNull(ReflectionUtils.createNew(Person.class));
	}

	@Test
	public void testCreateGetterName() throws JackException {

		Field name = ReflectionUtils.getField(person, "name");
		assertEquals("getName", ReflectionUtils.createGetterName(name));

		Field age = ReflectionUtils.getField(person, "age");
		assertEquals("getAge", ReflectionUtils.createGetterName(age));
	}

	@Test
	public void testCreateSetterName() throws JackException {
		JackField name = new JackField("name", String.class, "m@rtlin");
		assertEquals("setName", ReflectionUtils.createSetterName(name));

		JackField age = new JackField("age", int.class, 42);
		assertEquals("setAge", ReflectionUtils.createSetterName(age));
	}

}
