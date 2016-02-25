package cz.martlin.jaxon.testings.j2k;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.j2k.transformers.AbstractFuturamaJ2KTransformer;
import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackAtomicValue;
import cz.martlin.jaxon.jack.data.values.JackMap;
import cz.martlin.jaxon.jack.data.values.JackNullValue;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.KlaxonTestUtils;
import cz.martlin.jaxon.klaxon.data.EntriesCollection;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.klaxon.data.KlaxonAttribute;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithChildren;
import cz.martlin.jaxon.klaxon.data.KlaxonEntry;
import cz.martlin.jaxon.testings.JackToKlaxonTestTuple;
import cz.martlin.jaxon.testings.jaxon.Person;
import cz.martlin.jaxon.testings.jaxon.PersonJ2KTestTuple;
import cz.martlin.jaxon.testings.jaxon.PersonJackTestTuple;
import cz.martlin.jaxon.testings.jaxon.TesingPersons;

public class BikeJ2KTestTuple implements JackToKlaxonTestTuple {

	private int weight;
	private Integer maxSpeed;

	private double cost;
	private Person currentlyBorrowedBy;

	private Person previouslyBy1;
	private Integer previouslyDays1;

	public BikeJ2KTestTuple(int weight, Integer maxSpeed, double cost,
			Person currentlyBorrowedBy, Person previouslyBy1,
			Integer previouslyDays1) {
		super();
		this.weight = weight;
		this.maxSpeed = maxSpeed;
		this.cost = cost;
		this.currentlyBorrowedBy = currentlyBorrowedBy;
		this.previouslyBy1 = previouslyBy1;
		this.previouslyDays1 = previouslyDays1;
	}

	@Override
	public JackObject createJack(J2KConfig config) {

		Map<JackObjectField, JackValue> values = new LinkedHashMap<>();

		JackValueType weightType = new JackValueType(int.class);
		JackObjectField weightField = new JackObjectField("weight", weightType);
		values.put(weightField, new JackAtomicValue(weight));

		JackValueType msType = new JackValueType(Integer.class);
		JackObjectField msField = new JackObjectField("maxSpeed", msType);
		values.put(msField, new JackAtomicValue(maxSpeed));

		JackValueType costType = new JackValueType(double.class);
		JackObjectField costField = new JackObjectField("cost", costType);
		values.put(costField, new JackAtomicValue(cost));

		JackValueType cbbType = new JackValueType(Person.class);
		JackObjectField cbbField = new JackObjectField("currentlyBorrowedBy",
				cbbType);
		values.put(cbbField, currentlyBorrowedToJack());

		JackValueType pbdType = new JackValueType(Map.class);
		JackObjectField pbdField = new JackObjectField(
				"previouslyBorrowedDays", pbdType);
		values.put(pbdField, previouslyBorrowedDaysToJack());

		JackValueType type = new JackValueType(BikeToBorrow.class);
		return new JackObject(type, values, "Bike to rent or what");
	}

	private JackValue currentlyBorrowedToJack() {
		if (currentlyBorrowedBy == null) {
			return JackNullValue.INSTANCE;
		}

		PersonJackTestTuple tuple = PersonJackTestTuple
				.create(currentlyBorrowedBy);

		return tuple.createJackObject();
	}

	private JackValue previouslyBorrowedDaysToJack() {

		Map<JackValue, JackValue> data = new LinkedHashMap<>();

		PersonJackTestTuple tuple = PersonJackTestTuple.create(previouslyBy1);

		JackValue pb1Jack = tuple.createJackObject();
		JackValue pbd1Jack = new JackAtomicValue(previouslyDays1);

		data.put(pb1Jack, pbd1Jack);

		JackValueType type = new JackValueType(LinkedHashMap.class);
		return new JackMap(type, data);
	}

	@Override
	public KlaxonAbstractElement createKlaxon(J2KConfig config,
			JackObjectsTransformer transformer) {

		if (!(transformer instanceof AbstractFuturamaJ2KTransformer)) {
			throw new IllegalArgumentException("Unsupported transformer "
					+ transformer);
		}

		EntriesCollection entries = new EntriesCollection();
		entries.add(new KlaxonAttribute("type", BikeToBorrow.class.getName()));

		switch (config.getAVFStyleToKlaxon()) {
		case ATTRIBUTE:
			addByAttribute(entries);
			break;
		case CHILD_WITH_ATTRIBUTE:
			addByChildrenWithAttribute(entries);
			break;
		case CHILD_WITH_TEXT_VALUE:
			addByChildrenWithText(entries);
			break;
		default:
			break;
		}

		if (currentlyBorrowedBy != null) {
			KlaxonEntry cbbEntry = personToKlaxon(currentlyBorrowedBy, config,
					transformer);
			entries.add(cbbEntry);
		}

		KlaxonEntry pbdAttr = previouslyBorrowedToKlaxon(config, transformer);
		entries.add(pbdAttr);

		String elemName = "bikeToBorrow";
		return new KlaxonElemWithChildren(elemName, entries);
	}

	@Deprecated
	private void addByAttribute(EntriesCollection entries) {

		KlaxonAttribute weightAttr = new KlaxonAttribute(//
				"weight", Integer.toString(weight));
		entries.add(weightAttr);

		if (maxSpeed != null) {
			KlaxonAttribute msAttr = new KlaxonAttribute(//
					"maxSpeed", maxSpeed.toString());
			entries.add(msAttr);
		}

		KlaxonAttribute costAttr = new KlaxonAttribute(//
				"cost", Double.toString(cost));
		entries.add(costAttr);
	}

	@Deprecated
	private void addByChildrenWithAttribute(EntriesCollection entries) {

		KlaxonAbstractElement weightElem = KlaxonTestUtils.createWithOneAttr(//
				"weight", "value", Integer.toString(weight), null);
		entries.add(weightElem);

		if (maxSpeed != null) {
			KlaxonAbstractElement msElem = KlaxonTestUtils.createWithOneAttr(//
					"maxSpeed", "value", maxSpeed.toString(), null);
			entries.add(msElem);
		}

		KlaxonAbstractElement costElem = KlaxonTestUtils.createWithOneAttr(//
				"cost", "value", Double.toString(cost), null);
		entries.add(costElem);
	}

	private void addByChildrenWithText(EntriesCollection entries) {

		KlaxonAbstractElement weightElem = KlaxonTestUtils.createWithOneAttr(//
				"weight", null, null, Integer.toString(weight));
		entries.add(weightElem);

		if (maxSpeed != null) {
			KlaxonAbstractElement msElem = KlaxonTestUtils.createWithOneAttr(//
					"maxSpeed", null, null, maxSpeed.toString());
			entries.add(msElem);
		}

		KlaxonAbstractElement costElem = KlaxonTestUtils.createWithOneAttr(//
				"cost", null, null, Double.toString(cost));
		entries.add(costElem);
	}

	private KlaxonEntry personToKlaxon(Person person, J2KConfig config,
			JackObjectsTransformer transformer) {

		PersonJ2KTestTuple tuple = PersonJ2KTestTuple.create(person);

		return tuple.createKlaxon(config, transformer);
	}

	private KlaxonEntry personToKlaxon(String name, Person person,
			J2KConfig config, JackObjectsTransformer transformer) {

		KlaxonAbstractElement elem = (KlaxonAbstractElement) personToKlaxon(
				person, config, transformer);

		EntriesCollection entries = elem.asEntries();
		return new KlaxonElemWithChildren(name, entries);
	}

	private KlaxonEntry previouslyBorrowedToKlaxon(J2KConfig config,
			JackObjectsTransformer transformer) {

		EntriesCollection mapEntryEntries = new EntriesCollection();

		KlaxonEntry key = personToKlaxon("key", previouslyBy1, config,
				transformer);
		mapEntryEntries.add(key);

		switch (config.getAVFStyleToKlaxon()) {
		case ATTRIBUTE:
			KlaxonEntry valueA = new KlaxonAttribute(//
					"value", previouslyDays1.toString());
			mapEntryEntries.add(valueA);
			break;
		case CHILD_WITH_ATTRIBUTE:
			KlaxonEntry valueCA = KlaxonTestUtils.createWithOneAttr(//
					"value", "value", previouslyDays1.toString(), null);
			mapEntryEntries.add(valueCA);
			break;
		case CHILD_WITH_TEXT_VALUE:
			KlaxonEntry valueCT = KlaxonTestUtils.createWithOneAttr(
					//
					"value", "type", Integer.class.getName(),
					previouslyDays1.toString());
			mapEntryEntries.add(valueCT);
			break;
		default:
			break;
		}

		KlaxonElemWithChildren entryElem = new KlaxonElemWithChildren("entry",
				mapEntryEntries);

		List<KlaxonAbstractElement> entriesList = new ArrayList<>();
		entriesList.add(entryElem);
		return new KlaxonElemWithChildren("previouslyBorrowedDays", entriesList);
	}

	public static BikeJ2KTestTuple createSome() {
		int weight = 8;
		Integer maxSpeed = 45;

		double cost = 1500;
		Person currentlyBorrowedBy = null;

		Person previouslyBy1 = TesingPersons.createJohn();
		Integer previouslyDays1 = 14;

		return new BikeJ2KTestTuple(weight, maxSpeed, cost,
				currentlyBorrowedBy, previouslyBy1, previouslyDays1);
	}

}
