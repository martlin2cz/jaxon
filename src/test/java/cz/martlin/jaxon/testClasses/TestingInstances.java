package cz.martlin.jaxon.testClasses;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jaxon.exception.KlaxonException;
import cz.martlin.jaxon.object.JackField;
import cz.martlin.jaxon.object.JackObject;
import cz.martlin.jaxon.object.KlaxonItem;
import cz.martlin.jaxon.object.KlaxonObject;
import cz.martlin.jaxon.utils.XMLUtils;

public class TestingInstances {

	private static final String TESTING_NAME = "m@rtlin";
	private static final int TESTING_AGE = 42;

	public static Person createObject() {
		Person p = new Person();

		p.setName(TESTING_NAME);
		p.setAge(TESTING_AGE);

		return p;
	}

	public static JackObject createJack() {
		Set<JackField> fields = new LinkedHashSet<>();

		JackField name = new JackField("name", String.class, TESTING_NAME);
		fields.add(name);

		JackField age = new JackField("age", int.class, TESTING_AGE);
		fields.add(age);

		return new JackObject(Person.class, fields);
	}

	public static Document createDocument() throws KlaxonException {
		Document document = XMLUtils.createDocument();

		Element root = document.createElement("jaxon-object");
		document.appendChild(root);

		Element name = document.createElement("name");
		name.setAttribute("name", TESTING_NAME);
		root.appendChild(name);

		Element age = document.createElement("age");
		age.setAttribute("age", Integer.toString(TESTING_AGE));
		root.appendChild(age);

		return document;
	}

	public static KlaxonObject createKlaxon() {
		Set<KlaxonItem> fields = new LinkedHashSet<>();

		Map<String, String> nameAttrs = new HashMap<>();
		nameAttrs.put("name", TESTING_NAME);

		KlaxonItem name = new KlaxonItem("name", nameAttrs);
		fields.add(name);

		Map<String, String> ageAttrs = new HashMap<>();
		ageAttrs.put("age", Integer.toString(TESTING_AGE));

		KlaxonItem age = new KlaxonItem("age", ageAttrs);
		fields.add(age);

		return new KlaxonObject("person", fields);
	}
}
