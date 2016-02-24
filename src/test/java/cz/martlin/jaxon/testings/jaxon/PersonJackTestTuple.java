package cz.martlin.jaxon.testings.jaxon;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cz.martlin.jaxon.jack.JackTestsUtils;
import cz.martlin.jaxon.jack.data.design.JackObjectDesign;
import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.testings.JackTestTuple;

public class PersonJackTestTuple implements JackTestTuple<Person> {

	private final String testingName;
	private final int testingAge;
	private final boolean testingEmp;

	public PersonJackTestTuple(String testingName, int testingAge,
			boolean testingEmp) {
		super();
		this.testingName = testingName;
		this.testingAge = testingAge;
		this.testingEmp = testingEmp;
	}

	@Override
	public JackValueType getType() {
		return new JackValueType(Person.class);
	}

	@Override
	public Person createObject() {
		Person p = new Person();

		p.setName(testingName);
		p.setAge(testingAge);
		p.setEmployed(testingEmp);

		return p;
	}

	@Override
	public JackObject createJackObject() {
		Map<JackObjectField, JackValue> values = new LinkedHashMap<>();

		JackTestsUtils.putFT(values, "name", String.class, testingName);
		JackTestsUtils.putFT(values, "age", int.class, testingAge);
		JackTestsUtils.putFT(values, "employed", boolean.class, testingEmp);

		return new JackObject(getType(), values);
	}

	@Override
	public JackObjectDesign createJackDesign() {
		List<JackObjectField> fields = new ArrayList<>();

		JackTestsUtils.addF(fields, "name", String.class);
		JackTestsUtils.addF(fields, "age", int.class);
		JackTestsUtils.addF(fields, "employed", boolean.class);

		return new JackObjectDesign(getType(), fields);
	}

	public static PersonJackTestTuple createMe() {
		return create(TesingPersons.createMe());
	}

	public static PersonJackTestTuple createFooBar() {
		return new PersonJackTestTuple("foo bar", 256, true);
	}

	public static PersonJackTestTuple create(Person person) {
		boolean emp = person.isEmployed();
		int age = person.getAge();
		String name = person.getName();

		return new PersonJackTestTuple(name, age, emp);
	}

}
