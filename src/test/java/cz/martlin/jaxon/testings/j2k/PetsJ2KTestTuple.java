package cz.martlin.jaxon.testings.j2k;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.j2k.transformers.AbstractFuturamaJ2KTransformer;
import cz.martlin.jaxon.jack.JackTestsUtils;
import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.KlaxonTestUtils;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.klaxon.data.KlaxonAttribute;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithChildren;
import cz.martlin.jaxon.testings.JackToKlaxonTestTuple;

public class PetsJ2KTestTuple implements JackToKlaxonTestTuple {
	private static final JackValueType type = new JackValueType(Pet.class);

	private final String name;
	private final Gender gender;
	private final Integer legs;
	private final boolean canSwim;

	public PetsJ2KTestTuple(String name, Gender gender, Integer legs,
			boolean canSwim) {
		super();
		this.name = name;
		this.gender = gender;
		this.legs = legs;
		this.canSwim = canSwim;
	}

	@Override
	public JackObject createJack(J2KConfig config) {

		Map<JackObjectField, JackValue> values = new LinkedHashMap<>();

		JackTestsUtils.putFT(values, "name", String.class, name);
		JackTestsUtils.putFT(values, "gender", Gender.class, gender);
		JackTestsUtils.putFT(values, "legs", Integer.class, legs);
		JackTestsUtils.putFT(values, "canSwim", boolean.class, canSwim);

		return new JackObject(type, values, "Yeah, pet!");
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
		attributes.add(new KlaxonAttribute("type", Pet.class.getName()));

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

		String elemName = "pet";
		return new KlaxonElemWithChildren(elemName, attributes, children);
	}

	private void addByChildrenWithText(List<KlaxonAbstractElement> children) {
		KlaxonAbstractElement nameElem = KlaxonTestUtils.createWithOneAttr(//
				"name", null, null, name.toString());
		children.add(nameElem);

		KlaxonAbstractElement genderElem = KlaxonTestUtils.createWithOneAttr(//
				"gender", null, null, gender.name());
		children.add(genderElem);

		KlaxonAbstractElement legsElem = KlaxonTestUtils.createWithOneAttr(//
				"legs", null, null, legs != null ? legs.toString() : null);
		children.add(legsElem);

		KlaxonAbstractElement swimElem = KlaxonTestUtils.createWithOneAttr(//
				"canSwim", null, null, Boolean.toString(canSwim));
		children.add(swimElem);

	}

	private void addByChildrenWithAttribute(List<KlaxonAbstractElement> children) {
		KlaxonAbstractElement nameElem = KlaxonTestUtils.createWithOneAttr(//
				"name", "value", name.toString(), null);
		children.add(nameElem);

		KlaxonAbstractElement genderElem = KlaxonTestUtils.createWithOneAttr(//
				"gender", "value", gender.name(), null);
		children.add(genderElem);

		KlaxonAbstractElement legsElem = KlaxonTestUtils.createWithOneAttr(//
				"legs", "value", legs != null ? legs.toString() : null, null);
		children.add(legsElem);

		KlaxonAbstractElement swimElem = KlaxonTestUtils.createWithOneAttr(//
				"canSwim", "value", Boolean.toString(canSwim), null);
		children.add(swimElem);
	}

	private void addByAttribute(Set<KlaxonAttribute> attributes) {
		KlaxonAttribute nameAttr = new KlaxonAttribute(//
				"name", name.toString());
		attributes.add(nameAttr);

		KlaxonAttribute genderAttr = new KlaxonAttribute(//
				"gender", gender.name());
		attributes.add(genderAttr);

		KlaxonAttribute legsAttr = new KlaxonAttribute(//
				"legs", legs != null ? legs.toString() : null);
		attributes.add(legsAttr);

		KlaxonAttribute swimAttr = new KlaxonAttribute(//
				"canSwim", Boolean.toString(canSwim));
		attributes.add(swimAttr);
	}

	public static PetsJ2KTestTuple createDog() {
		String name = "Dog";
		Gender gender = Gender.MALE;
		Integer legs = 4;
		boolean canSwim = true;

		return new PetsJ2KTestTuple(name, gender, legs, canSwim);
	}

}
