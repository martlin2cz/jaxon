package cz.martlin.jaxon.testings.jack;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cz.martlin.jaxon.jack.JackTestsUtils;
import cz.martlin.jaxon.jack.data.design.JackObjectDesign;
import cz.martlin.jaxon.jack.data.design.JackObjectField;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackAtomicValue;
import cz.martlin.jaxon.jack.data.values.JackCollection;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.testings.JackTestTuple;
import cz.martlin.jaxon.testings.jaxon.Person;
import cz.martlin.jaxon.testings.jaxon.PersonJackTestTuple;

public class DrinkJackTest implements JackTestTuple<Drink> {

	private final String name;
	private final DrinkType type;
	private final int volume;
	private final Integer alcohol;

	private final File image;
	private final Date since;
	private final double cost;

	private final JackCollection ingredientsJack;
	private final List<String> ingredientsObj;

	private final JackObject addedByJack;
	private final Person addedByObj;

	public DrinkJackTest(String name, DrinkType type, int volume,
			Integer alcohol, File image, Date since, double cost,
			JackCollection ingredientsJack, List<String> ingredientsObj,
			JackObject addedByJack, Person addedByObj) {

		super();
		this.name = name;
		this.type = type;
		this.volume = volume;
		this.alcohol = alcohol;
		this.image = image;
		this.since = since;
		this.cost = cost;
		this.ingredientsJack = ingredientsJack;
		this.ingredientsObj = ingredientsObj;
		this.addedByJack = addedByJack;
		this.addedByObj = addedByObj;
	}

	@Override
	public JackValueType getType() {
		return new JackValueType(Drink.class);
	}

	@Override
	public Drink createObject() {
		Drink drink = new Drink();

		drink.setName(name);
		drink.setType(type);
		drink.setVolume(volume);
		drink.setAlcohol(alcohol);
		drink.setSince(since);
		drink.setCost(cost);
		drink.setImage(image);
		drink.setIngredients(ingredientsObj);
		drink.setAddedBy(addedByObj);

		return drink;
	}

	@Override
	public JackObject createJackObject() {
		Map<JackObjectField, JackValue> values = new LinkedHashMap<>();

		JackTestsUtils.putFT(values, "name", String.class, name);
		JackTestsUtils.putFT(values, "type", DrinkType.class, type);
		JackTestsUtils.putFT(values, "volume", int.class, volume);
		JackTestsUtils.putFT(values, "alcohol", Integer.class, alcohol);

		JackObjectField ingredientsField = new JackObjectField("ingredients",
				new JackValueType(List.class));
		values.put(ingredientsField, ingredientsJack);

		JackTestsUtils.putFT(values, "image", File.class, image);

		JackTestsUtils.putFT(values, "since", Date.class, since);

		JackObjectField addedByField = new JackObjectField("addedBy",
				new JackValueType(Person.class));
		values.put(addedByField, addedByJack);

		JackTestsUtils.putFT(values, "cost", double.class, cost);

		return new JackObject(getType(), values);
	}

	@Override
	public JackObjectDesign createJackDesign() {
		List<JackObjectField> fields = new ArrayList<>();

		JackTestsUtils.addF(fields, "name", String.class);
		JackTestsUtils.addF(fields, "type", DrinkType.class);
		JackTestsUtils.addF(fields, "volume", int.class);
		JackTestsUtils.addF(fields, "alcohol", Integer.class);

		JackObjectField ingredientsField = new JackObjectField("ingredients",
				new JackValueType(List.class));
		fields.add(ingredientsField);

		JackTestsUtils.addF(fields, "image", File.class);

		JackTestsUtils.addF(fields, "since", Date.class);

		JackObjectField addedByField = new JackObjectField("addedBy",
				new JackValueType(Person.class));
		fields.add(addedByField);

		JackTestsUtils.addF(fields, "cost", double.class);

		return new JackObjectDesign(getType(), fields);
	}

	public static DrinkJackTest createLatte() {
		String name = "Caffe Latte";
		DrinkType type = DrinkType.HOT;

		int volume = 200;
		Integer alcohol = null;
		double cost = 45;

		Date since = new Date(124552233432l);
		File image = new File("C:\\images\\latte.jpg");

		String ingredient1 = "Milk";
		String ingredient2 = "Coffe";

		JackCollection ingredientsJack = createIngredientsJack(ingredient1,
				ingredient2);
		List<String> ingredientsObj = createIngredientsObj(ingredient1,
				ingredient2);

		PersonJackTestTuple foobar = PersonJackTestTuple.createFooBar();
		JackObject addedByJack = foobar.createJackObject();
		Person addedByObj = foobar.createObject();

		return new DrinkJackTest(name, type, volume, alcohol, image, since,
				cost, ingredientsJack, ingredientsObj, addedByJack, addedByObj);
	}

	private static JackCollection createIngredientsJack(String... ingredients) {
		ArrayList<JackValue> values = new ArrayList<>();

		for (String ingredient : ingredients) {
			JackValue jack = new JackAtomicValue(ingredient);
			values.add(jack);
		}

		JackValueType type = new JackValueType(ArrayList.class);
		return new JackCollection(type, values);
	}

	private static List<String> createIngredientsObj(String... ingredients) {
		List<String> result = new ArrayList<>();

		for (String ingredient : ingredients) {
			result.add(ingredient);
		}

		return result;
	}

}
