package cz.martlin.jaxon.testings.j2k.pet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.j2k.transformers.FryJ2KTransformerImpl;
import cz.martlin.jaxon.jack.JackTestsUtils;
import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;
import cz.martlin.jaxon.testings.tuples.JackToKlaxonObjectsTestTuple;

/**
 * Test tuple with {@link Pet}.
 * @author martin
 *
 */
public class PetsJ2KTestTuple implements JackToKlaxonObjectsTestTuple {
	private static final JackValueType type = new JackValueType(Pet.class);

	private final String name;
	private final Gender gender;
	private final Integer legs;
	private final boolean canSwim;

	public PetsJ2KTestTuple(String name, Gender gender, Integer legs, boolean canSwim) {
		super();
		this.name = name;
		this.gender = gender;
		this.legs = legs;
		this.canSwim = canSwim;
	}

	@Override
	public JackObject createJack() {

		Map<JackObjectField, JackValue> values = new LinkedHashMap<>();

		JackTestsUtils.putFT(values, "name", String.class, name);
		JackTestsUtils.putFT(values, "gender", Gender.class, gender);
		JackTestsUtils.putFT(values, "legs", Integer.class, legs);
		JackTestsUtils.putFT(values, "canSwim", boolean.class, canSwim);

		return new JackObject(type, values, "Yeah, pet!");
	}

	@Override
	public KlaxonObject createKlaxon(J2KConfig config, JackObjectsTransformer transformer) {

		if (!(transformer instanceof FryJ2KTransformerImpl)) {
			throw new IllegalArgumentException("Unsupported transformer " + transformer);
		}

		List<KlaxonValue> headers = new ArrayList<>();
		List<KlaxonValue> fields = new ArrayList<>();

		KlaxonValue type = new KlaxonStringValue("type", Pet.class.getName());
		headers.add(type);

		KlaxonValue nameAttr = new KlaxonStringValue("name", name.toString());
		fields.add(nameAttr);

		KlaxonValue genderAttr = new KlaxonStringValue("gender", gender.name());
		fields.add(genderAttr);

		KlaxonValue legsAttr = new KlaxonStringValue("legs", legs != null ? legs.toString() : null);
		fields.add(legsAttr);

		KlaxonValue swimAttr = new KlaxonStringValue("canSwim", Boolean.toString(canSwim));
		fields.add(swimAttr);

		String elemName = "pet";
		return new KlaxonObject(elemName, headers, fields);
	}

	/**
	 * Creates dog.
	 * 
	 * @return
	 */
	public static PetsJ2KTestTuple createDog() {
		String name = "Dog";
		Gender gender = Gender.MALE;
		Integer legs = 4;
		boolean canSwim = true;

		return new PetsJ2KTestTuple(name, gender, legs, canSwim);
	}

	/**
	 * Creates snake. Just female snake...
	 * 
	 * @return
	 */
	public static PetsJ2KTestTuple createSnakie() {
		String name = "Snake";
		Gender gender = Gender.FEMALE;
		Integer legs = null;
		boolean canSwim = false;

		return new PetsJ2KTestTuple(name, gender, legs, canSwim);
	}
}
