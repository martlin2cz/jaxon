package cz.martlin.jaxon.testings.klaxon;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jaxon.k2xml.KlaxonToXMLException;
import cz.martlin.jaxon.k2xml.KlaxonToXMLImpl;
import cz.martlin.jaxon.klaxon.KlaxonException;
import cz.martlin.jaxon.klaxon.KlaxonTestUtils;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.klaxon.data.KlaxonAttribute;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithChildren;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithValue;
import cz.martlin.jaxon.testings.KlaxonTestTuple;

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

	public FoodKlaxonCreator(String name, String cost, String forChildren,
			String ingredient1Name, String ingredient1Amount,
			String ingredient2Name, String ingredient2Amount,
			String photoFormat, String photoFile, String reciepeLang,
			String reciepeText) {
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
	public KlaxonAbstractElement createKlaxon() {
		// attributes
		Set<KlaxonAttribute> attributes = new TreeSet<>();

		KlaxonAttribute nameAttr = new KlaxonAttribute("name", name);
		attributes.add(nameAttr);

		KlaxonAttribute costAttr = new KlaxonAttribute("cost", cost);
		attributes.add(costAttr);

		KlaxonAttribute forChildrenAttr = new KlaxonAttribute("forChildren",
				forChildren);
		attributes.add(forChildrenAttr);

		// children
		List<KlaxonAbstractElement> children = new ArrayList<>();

		KlaxonElemWithChildren ingredientsChild = createKlaxonIngredients();
		children.add(ingredientsChild);

		KlaxonElemWithValue photoChild = createKlaxonPhoto();
		children.add(photoChild);

		KlaxonElemWithValue reciepeChild = createKlaxonReciepe();
		children.add(reciepeChild);

		return new KlaxonElemWithChildren("food", attributes, children);
	}

	private KlaxonElemWithChildren createKlaxonIngredients() {
		List<KlaxonAbstractElement> children = new ArrayList<>();

		KlaxonAbstractElement ingredient1 = createIngredient(ingredient1Name,
				ingredient1Amount);
		children.add(ingredient1);

		KlaxonAbstractElement ingredient2 = createIngredient(ingredient2Name,
				ingredient2Amount);
		children.add(ingredient2);

		return new KlaxonElemWithChildren("ingredients", children);

	}

	private KlaxonAbstractElement createIngredient(String name,
			String amountOrNull) {

		return KlaxonTestUtils.createWithOneAttr("ingredient", "amount",
				amountOrNull, name);
	}

	private KlaxonElemWithValue createKlaxonPhoto() {
		return (KlaxonElemWithValue) KlaxonTestUtils.createWithOneAttr(//
				"photo", "format", photoFormat, photoFile);
	}

	private KlaxonElemWithValue createKlaxonReciepe() {
		return (KlaxonElemWithValue) KlaxonTestUtils.createWithOneAttr(//
				"reciepe", "lang", reciepeLang, reciepeText);

	}

	@Override
	public Element createElement(Document document) {
		Element food = document.createElement("food");

		food.setAttribute("cost", cost);
		food.setAttribute("forChildren", forChildren);

		food.setAttribute("name", name);

		Element ingredientsElem = createIngredientsElement(document);
		food.appendChild(ingredientsElem);

		Element photoElem = createPhotoElement(document);
		food.appendChild(photoElem);

		Element recpiepeElem = createReciepeElement(document);
		food.appendChild(recpiepeElem);

		return food;
	}

	private Element createIngredientsElement(Document document) {

		Element ingredients = document.createElement("ingredients");

		Element ingredient1 = createIngredient(document, //
				ingredient1Name, ingredient1Amount);
		ingredients.appendChild(ingredient1);

		Element ingredient2 = createIngredient(document, //
				ingredient2Name, ingredient2Amount);
		ingredients.appendChild(ingredient2);

		return ingredients;
	}

	private Element createIngredient(Document document, String name,
			String amountOrNull) {

		return KlaxonTestUtils.createTextNode(document, "ingredient", "amount",
				amountOrNull, name);
	}

	private Element createPhotoElement(Document document) {
		return KlaxonTestUtils.createTextNode(document, "photo", "format",
				photoFormat, photoFile);
	}

	private Element createReciepeElement(Document document) {
		return KlaxonTestUtils.createTextNode(document, "reciepe", "lang",
				reciepeLang, reciepeText);
	}

	@Override
	public Document createDocument() throws KlaxonException {
		Document document;
		try {
			document = KlaxonToXMLImpl.createDocument();
		} catch (KlaxonToXMLException e) {
			throw new KlaxonException("Cannot create doc", e);
		}
		Element element = createElement(document);
		document.appendChild(element);

		return document;
	}

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

		return new FoodKlaxonCreator(name, cost, forChildren, ingredient1Name,
				ingredient1Amount, ingredient2Name, ingredient2Amount,
				photoFormat, photoFile, reciepeLang, reciepeText);
	}
}
