package cz.martlin.jaxon.testings.jaxon.person;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.j2k.transformers.FryJ2KTransformerImpl;
import cz.martlin.jaxon.jack.JackTestsUtils;
import cz.martlin.jaxon.jack.data.design.JackObjectDesign;
import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;
import cz.martlin.jaxon.testings.tuples.JackTestTuple;
import cz.martlin.jaxon.testings.tuples.JackToKlaxonObjectsTestTuple;
import cz.martlin.jaxon.testings.tuples.JaxonTestTuple;

/**
 * Test tuple for whole process of simple class {@link Person}.
 * 
 * @author martin
 *
 */
public class PersonTestTuples implements JackTestTuple<Person>, JackToKlaxonObjectsTestTuple, JaxonTestTuple<Person> {

	private final String testingName;
	private final int testingAge;
	private final boolean testingEmp;

	public PersonTestTuples(String name, int age, boolean emp) {
		super();
		this.testingName = name;
		this.testingAge = age;
		this.testingEmp = emp;
	}

	@Override
	public JackValueType getType() {
		return new JackValueType(Person.class);
	}

	@Override
	public Person createObject() {
		Person person = new Person();

		person.setName(testingName);
		person.setAge(testingAge);
		person.setEmployed(testingEmp);

		return person;
	}

	@Override
	public JackObject createJack() {
		Map<JackObjectField, JackValue> values = new LinkedHashMap<>();

		JackTestsUtils.putFT(values, "name", String.class, testingName);
		JackTestsUtils.putFT(values, "age", int.class, testingAge);
		JackTestsUtils.putFT(values, "employed", boolean.class, testingEmp);

		return new JackObject(getType(), values, null);
	}

	@Override
	public JackObjectDesign createJackDesign() {
		List<JackObjectField> fields = new ArrayList<>();

		JackTestsUtils.addF(fields, "name", String.class);
		JackTestsUtils.addF(fields, "age", int.class);
		JackTestsUtils.addF(fields, "employed", boolean.class);

		return new JackObjectDesign(getType(), fields);
	}

	@Override
	public KlaxonObject createKlaxon(J2KConfig config, JackObjectsTransformer transformer) {

		if (!(transformer instanceof FryJ2KTransformerImpl)) {
			throw new IllegalArgumentException("Unsupported transformer " + transformer);
		}

		List<KlaxonValue> headers = new ArrayList<>();
		List<KlaxonValue> fields = new ArrayList<>();

		KlaxonValue type = new KlaxonStringValue("type", Person.class.getName());
		headers.add(type);

		KlaxonValue nameElem = new KlaxonStringValue("name", testingName.toString());
		fields.add(nameElem);

		KlaxonValue ageElem = new KlaxonStringValue("age", Integer.toString(testingAge));
		fields.add(ageElem);

		KlaxonValue empElem = new KlaxonStringValue("employed", Boolean.toString(testingEmp));
		fields.add(empElem);

		String elemName = "person";
		return new KlaxonObject(elemName, headers, fields);
	}

	public static Person createPerson(String name, int age, boolean employed) {
		Person person = new Person();

		person.setName(name);
		person.setAge(age);
		person.setEmployed(employed);

		return person;
	}

	/**
	 * Creates test tuple with me, m@rtlin.
	 * 
	 * @return
	 */
	public static PersonTestTuples createMe() {
		Person me = createPerson("m@rtlin", 24, false);
		return create(me);
	}

	/**
	 * Creates test tuple with some John.
	 * 
	 * @return
	 */
	public static PersonTestTuples createJohn() {
		Person john = createPerson("John", 42, true);
		return create(john);
	}

	/**
	 * Creates test tuple for given person instance.
	 * 
	 * @param person
	 * @return
	 */
	public static PersonTestTuples create(Person person) {
		boolean emp = person.isEmployed();
		int age = person.getAge();
		String name = person.getName();

		return new PersonTestTuples(name, age, emp);
	}

}
