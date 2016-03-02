package cz.martlin.jaxon.testings.klaxon.food;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jaxon.k2xml.KlaxonToXMLException;
import cz.martlin.jaxon.k2xml.KlaxonToXMLImpl;
import cz.martlin.jaxon.klaxon.KlaxonException;
import cz.martlin.jaxon.klaxon.KlaxonTestUtils;
import cz.martlin.jaxon.klaxon.config.K2DocFormat;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;
import cz.martlin.jaxon.testings.tuples.KlaxonTestTuple;

/**
 * {@link KlaxonTestTuple} which works with "food" objects. 
 * @author martin
 *
 */
public class FoodKlaxonCreator implements KlaxonTestTuple {

	private final String name;
	private final String cost;
	private final String forChildren;

	private final String ingredient1Name;
	private final String ingredient1Amount;
	private final String ingredient2Name;
	private final String ingredient2Amount;

	private final String photoFormat;
	private final String photoFile;

	private final String reciepeLang;
	private final String reciepeText;

	public FoodKlaxonCreator(String name, String cost, String forChildren, String ingredient1Name,
			String ingredient1Amount, String ingredient2Name, String ingredient2Amount, String photoFormat,
			String photoFile, String reciepeLang, String reciepeText) {
		super();
		this.name = name;
		this.cost = cost;
		this.forChildren = forChildren;
		this.ingredient1Name = ingredient1Name;
		this.ingredient1Amount = ingredient1Amount;
		this.ingredient2Name = ingredient2Name;
		this.ingredient2Amount = ingredient2Amount;
		this.photoFormat = photoFormat;
		this.photoFile = photoFile;
		this.reciepeLang = reciepeLang;
		this.reciepeText = reciepeText;
	}

	@Override
	public KlaxonObject createKlaxon() {
		// headers
		List<KlaxonValue> headers = new ArrayList<>();

		KlaxonStringValue nameAttr = new KlaxonStringValue("name", name);
		headers.add(nameAttr);

		// fields
		List<KlaxonValue> fields = new ArrayList<>();

		KlaxonStringValue costAttr = new KlaxonStringValue("cost", cost);
		fields.add(costAttr);

		KlaxonStringValue forChildrenAttr = new KlaxonStringValue("forChildren", forChildren);
		fields.add(forChildrenAttr);

		KlaxonObject ingredientsChild = createKlaxonIngredients();
		fields.add(ingredientsChild);

		KlaxonStringValue photoChild = createKlaxonPhoto();
		fields.add(photoChild);

		KlaxonStringValue reciepeChild = createKlaxonReciepe();
		fields.add(reciepeChild);

		return new KlaxonObject("food", headers, fields);
	}

	private KlaxonObject createKlaxonIngredients() {
		List<KlaxonValue> fields = new ArrayList<>();

		KlaxonValue ingredient1 = createIngredient(ingredient1Name, ingredient1Amount);
		fields.add(ingredient1);

		KlaxonValue ingredient2 = createIngredient(ingredient2Name, ingredient2Amount);
		fields.add(ingredient2);

		return new KlaxonObject("ingredients", fields);

	}

	private KlaxonValue createIngredient(String name, String amountOrNull) {
		return KlaxonTestUtils.createWithOneHeader("ingredient", "amount", amountOrNull, name);
	}

	private KlaxonStringValue createKlaxonPhoto() {
		return (KlaxonStringValue) KlaxonTestUtils.createWithOneHeader(//
				"photo", "format", photoFormat, photoFile);
	}

	private KlaxonStringValue createKlaxonReciepe() {
		return (KlaxonStringValue) KlaxonTestUtils.createWithOneHeader(//
				"reciepe", "lang", reciepeLang, reciepeText);

	}

	@Override
	public Element createElement(Document document, K2DocFormat format) {
		Element food = document.createElement("food");

		switch (format) {
		case ATTRS_FOR_HEADERS: {
			food.setAttribute("name", name);
			Element costElem = KlaxonTestUtils.createTextNode(document, "cost", null, null, cost);
			food.appendChild(costElem);
			Element fcElem = KlaxonTestUtils.createTextNode(document, "forChildren", null, null, forChildren);
			food.appendChild(fcElem);
			break;
		}
		case ATTRS_WHERE_POSSIBLE: {
			food.setAttribute("name", name);
			food.setAttribute("cost", cost);
			food.setAttribute("forChildren", forChildren);
			break;
		}
		case CHILDREN_EVERYWHERE: {
			Element nameElem = KlaxonTestUtils.createTextNode(document, "name", null, null, name);
			food.appendChild(nameElem);
			Element costElem = KlaxonTestUtils.createTextNode(document, "cost", null, null, cost);
			food.appendChild(costElem);
			Element fcElem = KlaxonTestUtils.createTextNode(document, "forChildren", null, null, forChildren);
			food.appendChild(fcElem);
			break;
		}
		default:
			throw new IllegalArgumentException("Unknown type");
		}

		Element ingredientsElem = createIngredientsElement(document, format);
		food.appendChild(ingredientsElem);

		Element photoElem = createPhotoElement(document, format);
		food.appendChild(photoElem);

		Element recpiepeElem = createReciepeElement(document, format);
		food.appendChild(recpiepeElem);

		return food;
	}

	private Element createIngredientsElement(Document document, K2DocFormat format) {

		Element ingredients = document.createElement("ingredients");

		Element ingredient1 = createIngredient(document, format, //
				ingredient1Name, ingredient1Amount);
		ingredients.appendChild(ingredient1);

		Element ingredient2 = createIngredient(document, format, //
				ingredient2Name, ingredient2Amount);
		ingredients.appendChild(ingredient2);

		return ingredients;
	}

	private Element createIngredient(Document document, K2DocFormat format, String name, String amountOrNull) {
		switch (format) {
		case ATTRS_FOR_HEADERS:
			return KlaxonTestUtils.createTextNode(document, "ingredient", "amount", amountOrNull, name);
		case ATTRS_WHERE_POSSIBLE: {
			Element child = document.createElement("ingredient");
			child.setAttribute("ingredient", name);
			
			if (amountOrNull != null) {	
				child.setAttribute("amount", amountOrNull);
			}
			return child;
		}
		case CHILDREN_EVERYWHERE: {
			Element elem = document.createElement("ingredient");
			Element nameChild = KlaxonTestUtils.createTextNode(document, "ingredient", null, null, name);
			elem.appendChild(nameChild);

			if (amountOrNull != null) {
				Element amtChild = KlaxonTestUtils.createTextNode(document, "amount", null, null, amountOrNull);
				elem.appendChild(amtChild);
			}
			return elem;
		}
		default:
			throw new IllegalArgumentException("Unknown type");
		}
	}

	private Element createPhotoElement(Document document, K2DocFormat format) {
		switch (format) {
		case ATTRS_FOR_HEADERS:
			return KlaxonTestUtils.createTextNode(document, "photo", "format", photoFormat, photoFile);
		case ATTRS_WHERE_POSSIBLE: {
			Element child = document.createElement("photo");
			child.setAttribute("format", photoFormat);
			child.setAttribute("value", photoFile);
			return child;
		}
		case CHILDREN_EVERYWHERE: {
			Element elem = document.createElement("photo");
			Element nameChild = KlaxonTestUtils.createTextNode(document, "format", null, null, photoFormat);
			elem.appendChild(nameChild);

			Element amtChild = KlaxonTestUtils.createTextNode(document, "photo", null, null, photoFile);
			elem.appendChild(amtChild);
			return elem;
		}
		default:
			throw new IllegalArgumentException("Unknown type");
		}

	}

	private Element createReciepeElement(Document document, K2DocFormat format) {
		switch (format) {
		case ATTRS_FOR_HEADERS:
			return KlaxonTestUtils.createTextNode(document, "reciepe", "lang", reciepeLang, reciepeText);
		case ATTRS_WHERE_POSSIBLE: {
			Element child = document.createElement("reciepe");
			child.setAttribute("lang", reciepeLang);
			child.setAttribute("value", reciepeText);
			return child;
		}
		case CHILDREN_EVERYWHERE: {
			Element elem = document.createElement("reciepe");
			Element nameChild = KlaxonTestUtils.createTextNode(document, "lang", null, null, reciepeLang);
			elem.appendChild(nameChild);

			Element amtChild = KlaxonTestUtils.createTextNode(document, "reciepe", null, null, reciepeText);
			elem.appendChild(amtChild);
			return elem;
		}
		default:
			throw new IllegalArgumentException("Unknown type");
		}
	}

	@Override
	public Document createDocument(K2DocFormat format) throws KlaxonException {
		Document document;
		try {
			document = KlaxonToXMLImpl.createDocument();
		} catch (KlaxonToXMLException e) {
			throw new KlaxonException("Cannot create doc", e);
		}
		Element element = createElement(document, format);
		document.appendChild(element);

		return document;
	}

	/**
	 * Creates testing tuple - a Hamburger.
	 * @return
	 */
	public static FoodKlaxonCreator createHamburger() {
		String name = "Hamburger";
		String cost = "39.9";
		String forChildren = "NO";
		String ingredient1Name = "Ham";
		String ingredient1Amount = null;
		String ingredient2Name = "Burger";
		String ingredient2Amount = "2 pieces";
		String photoFormat = "jpg";
		String photoFile = "~/photos/hamburger.jpg";
		String reciepeLang = "en";
		String reciepeText = "Take ham and burgers and make a hamburger";

		return new FoodKlaxonCreator(name, cost, forChildren, ingredient1Name, ingredient1Amount, ingredient2Name,
				ingredient2Amount, photoFormat, photoFile, reciepeLang, reciepeText);
	}
}
