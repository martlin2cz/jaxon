package cz.martlin.jaxon.testings.j2k.bike;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.j2k.transformers.FryJ2KTransformerImpl;
import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackAtomicValue;
import cz.martlin.jaxon.jack.data.values.JackMap;
import cz.martlin.jaxon.jack.data.values.JackNullValue;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;
import cz.martlin.jaxon.testings.jaxon.person.Person;
import cz.martlin.jaxon.testings.jaxon.person.PersonTestTuples;
import cz.martlin.jaxon.testings.tuples.JackToKlaxonObjectsTestTuple;

/**
 * Test tuple with {@link BikeToBorrow}s.
 * @author martin
 *
 */
public class BikeJ2KTestTuple implements JackToKlaxonObjectsTestTuple {

	private int weight;
	private Integer maxSpeed;

	private double cost;
	private Person currentlyBorrowedBy;

	private Person previouslyBy1;
	private Integer previouslyDays1;

	public BikeJ2KTestTuple(int weight, Integer maxSpeed, double cost, Person currentlyBorrowedBy, Person previouslyBy1,
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
	public JackObject createJack() {

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
		JackObjectField cbbField = new JackObjectField("currentlyBorrowedBy", cbbType);
		values.put(cbbField, currentlyBorrowedToJack());

		JackValueType pbdType = new JackValueType(Map.class);
		JackObjectField pbdField = new JackObjectField("previouslyBorrowedDays", pbdType);
		values.put(pbdField, previouslyBorrowedDaysToJack());

		JackValueType type = new JackValueType(BikeToBorrow.class);
		return new JackObject(type, values, "Bike to rent or what");
	}

	private JackValue currentlyBorrowedToJack() {
		if (currentlyBorrowedBy == null) {
			return JackNullValue.INSTANCE;
		}

		PersonTestTuples tuple = PersonTestTuples.create(currentlyBorrowedBy);

		return tuple.createJack();
	}

	private JackValue previouslyBorrowedDaysToJack() {

		Map<JackValue, JackValue> data = new LinkedHashMap<>();

		PersonTestTuples tuple = PersonTestTuples.create(previouslyBy1);

		JackValue pb1Jack = tuple.createJack();
		JackValue pbd1Jack = new JackAtomicValue(previouslyDays1);

		data.put(pb1Jack, pbd1Jack);

		JackValueType type = new JackValueType(LinkedHashMap.class);
		return new JackMap(type, data);
	}

	@Override
	public KlaxonObject createKlaxon(J2KConfig config, JackObjectsTransformer transformer) {

		if (!(transformer instanceof FryJ2KTransformerImpl)) {
			throw new IllegalArgumentException("Unsupported transformer " + transformer);
		}

		List<KlaxonValue> headers = new ArrayList<>();
		List<KlaxonValue> fields = new ArrayList<>();

		KlaxonValue type = new KlaxonStringValue("type", BikeToBorrow.class.getName());
		headers.add(type);

		KlaxonValue weightAttr = new KlaxonStringValue(//
				"weight", Integer.toString(weight));
		fields.add(weightAttr);

		if (maxSpeed != null) {
			KlaxonValue msAttr = new KlaxonStringValue(//
					"maxSpeed", maxSpeed.toString());
			fields.add(msAttr);
		}

		KlaxonValue costAttr = new KlaxonStringValue(//
				"cost", Double.toString(cost));
		fields.add(costAttr);

		if (currentlyBorrowedBy != null) {
			KlaxonValue cbbEntry = personToKlaxon(currentlyBorrowedBy, config, transformer);
			fields.add(cbbEntry);
		}

		KlaxonValue pbdAttr = previouslyBorrowedToKlaxon(config, transformer);
		fields.add(pbdAttr);

		String elemName = "bikeToBorrow";
		return new KlaxonObject(elemName, headers, fields);
	}

	private KlaxonValue personToKlaxon(Person person, J2KConfig config, JackObjectsTransformer transformer) {

		PersonTestTuples tuple = PersonTestTuples.create(person);

		return tuple.createKlaxon(config, transformer);
	}

	private KlaxonObject personToKlaxon(String name, Person person, J2KConfig config,
			JackObjectsTransformer transformer) {

		KlaxonObject elem = (KlaxonObject) personToKlaxon(person, config, transformer);

		return new KlaxonObject(name, elem.getHeaders(), elem.getFields());
	}

	private KlaxonValue previouslyBorrowedToKlaxon(J2KConfig config, JackObjectsTransformer transformer) {

		KlaxonValue key = personToKlaxon("key", previouslyBy1, config, transformer);

		List<KlaxonValue> valuesHeaders = new ArrayList<>();
		valuesHeaders.add(new KlaxonStringValue("type", Integer.class.getName()));

		KlaxonValue value = new KlaxonStringValue(//
				"value", valuesHeaders, previouslyDays1.toString());

		List<KlaxonValue> entryFields = new ArrayList<>();
		entryFields.add(key);
		entryFields.add(value);

		KlaxonObject entryObject = new KlaxonObject("entry", entryFields);
		List<KlaxonValue> entries = new ArrayList<>();
		entries.add(entryObject);

		List<KlaxonValue> headers = new ArrayList<>();
		KlaxonValue typeHeader = new KlaxonStringValue("type", LinkedHashMap.class.getName());
		headers.add(typeHeader);

		return new KlaxonObject("previouslyBorrowedDays", headers, entries);
	}

	/**
	 * Creates some instance.
	 * @return
	 */
	public static BikeJ2KTestTuple createSome() {
		int weight = 8;
		Integer maxSpeed = 45;

		double cost = 1500;
		Person currentlyBorrowedBy = null;

		PersonTestTuples tuple = PersonTestTuples.createJohn();
		Person previouslyBy1 = tuple.createObject();
		Integer previouslyDays1 = 14;

		return new BikeJ2KTestTuple(weight, maxSpeed, cost, currentlyBorrowedBy, previouslyBy1, previouslyDays1);
	}

}
