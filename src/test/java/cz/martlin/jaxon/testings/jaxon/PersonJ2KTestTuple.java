package cz.martlin.jaxon.testings.jaxon;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.j2k.transformers.AbstractFuturamaJ2KTransformer;
import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackAtomicValue;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.KlaxonTestUtils;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.klaxon.data.KlaxonAttribute;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithChildren;
import cz.martlin.jaxon.testings.JackToKlaxonTestTuple;

public class PersonJ2KTestTuple implements JackToKlaxonTestTuple {

	private final String testingName;
	private final int testingAge;
	private final boolean testingEmp;

	public PersonJ2KTestTuple(String name, int age, boolean emp) {
		super();
		this.testingName = name;
		this.testingAge = age;
		this.testingEmp = emp;
	}

	@Override
	public JackObject createJack(J2KConfig config) {
		Map<JackObjectField, JackValue> values = new LinkedHashMap<>();

		JackValueType nameType = new JackValueType(String.class);
		JackObjectField nameField = new JackObjectField("name", nameType);
		values.put(nameField, new JackAtomicValue(testingName));

		JackValueType ageType = new JackValueType(int.class);
		JackObjectField ageField = new JackObjectField("age", ageType);
		values.put(ageField, new JackAtomicValue(testingAge));

		JackValueType empType = new JackValueType(boolean.class);
		JackObjectField empField = new JackObjectField("employed", empType);
		values.put(empField, new JackAtomicValue(testingEmp));

		JackValueType type = new JackValueType(Person.class);
		return new JackObject(type, values, "Some Person");
	}

	@Override
	public KlaxonAbstractElement createKlaxon(J2KConfig config,
			JackObjectsTransformer transformer) {

		if (!(transformer instanceof AbstractFuturamaJ2KTransformer)) {
			throw new IllegalArgumentException("Unsupported transformer "
					+ transformer);
		}

		List<KlaxonAbstractElement> children = new ArrayList<>();

		// attributes
		Set<KlaxonAttribute> attributes = new TreeSet<>();
		attributes.add(new KlaxonAttribute("type", Person.class.getName()));

		switch (config.getAVFStyleToKlaxon()) {
		case ATTRIBUTE:
			addByAttribute(attributes);
			break;
		case CHILD_WITH_ATTRIBUTE:
			addByChildrenWithAttribute(children);
			break;
		case CHILD_WITH_TEXT_VALUE:
			addByChildrenWithText(children);
			break;
		default:
			break;

		}

		String elemName = "person";
		return new KlaxonElemWithChildren(elemName, attributes, children);
	}

	private void addByChildrenWithText(List<KlaxonAbstractElement> children) {
		KlaxonAbstractElement nameElem = KlaxonTestUtils.createWithOneAttr(//
				"name", null, null, testingName.toString());
		children.add(nameElem);

		KlaxonAbstractElement ageElem = KlaxonTestUtils.createWithOneAttr(//
				"age", null, null, Integer.toString(testingAge));
		children.add(ageElem);

		KlaxonAbstractElement empElem = KlaxonTestUtils.createWithOneAttr(//
				"employed", null, null, Boolean.toString(testingEmp));
		children.add(empElem);
	}

	private void addByChildrenWithAttribute(List<KlaxonAbstractElement> children) {
		KlaxonAbstractElement nameElem = KlaxonTestUtils.createWithOneAttr(//
				"name", "value", testingName.toString(), null);
		children.add(nameElem);

		KlaxonAbstractElement ageElem = KlaxonTestUtils.createWithOneAttr(//
				"age", "value", Integer.toString(testingAge), null);
		children.add(ageElem);

		KlaxonAbstractElement empElem = KlaxonTestUtils.createWithOneAttr(//
				"employed", "value", Boolean.toString(testingEmp), null);
		children.add(empElem);

	}

	private void addByAttribute(Set<KlaxonAttribute> attributes) {
		KlaxonAttribute nameAttr = new KlaxonAttribute(//
				"name", testingName.toString());
		attributes.add(nameAttr);

		KlaxonAttribute ageAttr = new KlaxonAttribute(//
				"age", Integer.toString(testingAge));
		attributes.add(ageAttr);

		KlaxonAttribute empAttr = new KlaxonAttribute(//
				"employed", Boolean.toString(testingEmp));
		attributes.add(empAttr);
	}

	public static PersonJ2KTestTuple createMe() {
		return create(TesingPersons.createMe());
	}

	public static PersonJ2KTestTuple create(Person person) {
		boolean emp = person.isEmployed();
		int age = person.getAge();
		String name = person.getName();

		return new PersonJ2KTestTuple(name, age, emp);
	}

}
