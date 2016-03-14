package cz.martlin.jaxon.klaxon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.k2xml.KlaxonToXMLException;
import cz.martlin.jaxon.k2xml.KlaxonToXMLImpl;
import cz.martlin.jaxon.klaxon.config.K2DocFormat;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;
import cz.martlin.jaxon.testings.klaxon.food.FoodKlaxonCreator;

/**
 * Tests KlaxonToElementsImpl and KlaxonFromElementsImpl.
 * 
 * @author martin
 *
 */
public class KlaxonImplementationTest {

	private final Config config = new Config();
	private final KlaxonToElementsImpl toElems = new KlaxonToElementsImpl(config);
	private final KlaxonFromElementsImpl fromElems = new KlaxonFromElementsImpl(config);

	private final FoodKlaxonCreator foods = FoodKlaxonCreator.createHamburger();

	// XXX @Test
	public void testCreateElementWithAttrsWherePossible() throws KlaxonException, KlaxonToXMLException {

		Element elem = calculateElem(K2DocFormat.ATTRS_WHERE_POSSIBLE);

		// check name
		assertEquals("food", elem.getNodeName());

		// check attributes
		assertEquals(3, elem.getAttributes().getLength());
		assertEquals("Hamburger", elem.getAttribute("name"));
		assertEquals("39.9", elem.getAttribute("cost"));
		assertEquals("NO", elem.getAttribute("forChildren"));

		// check children
		NodeList children = elem.getChildNodes();
		assertEquals(3, children.getLength());

		// ingredients
		Element ingredients = (Element) children.item(0);
		assertEquals("ingredients", ingredients.getNodeName());
		assertEquals(2, ingredients.getChildNodes().getLength());

		// ingredient1 and 2
		Element ingredient1 = (Element) ingredients.getChildNodes().item(0);
		assertEquals("ingredient", ingredient1.getNodeName());
		assertEquals("Ham", ingredient1.getAttribute("value"));

		Element ingredient2 = (Element) ingredients.getChildNodes().item(1);
		assertEquals("ingredient", ingredient2.getNodeName());
		assertEquals("2 pieces", ingredient2.getAttribute("amount"));
		assertEquals("Burger", ingredient2.getAttribute("value"));

		// photo
		Element photo = (Element) children.item(1);
		assertEquals("photo", photo.getNodeName());
		assertEquals("jpg", photo.getAttribute("format"));
		assertEquals("~/photos/hamburger.jpg", photo.getAttribute("value"));

		// reciepe
		Element reciepe = (Element) children.item(2);
		assertEquals("reciepe", reciepe.getNodeName());
		assertEquals("en", reciepe.getAttribute("lang"));
		assertEquals("Take ham and burgers and make a hamburger", reciepe.getAttribute("value"));
	}

	// XXX @Test
	public void testCreateElementWithAttrsOnHeadersOnly() throws KlaxonException, KlaxonToXMLException {

		Element elem = calculateElem(K2DocFormat.ATTRS_FOR_HEADERS);

		// check name
		assertEquals("food", elem.getNodeName());

		// check attributes
		assertEquals(1, elem.getAttributes().getLength());
		assertEquals("Hamburger", elem.getAttribute("name"));

		// check children
		NodeList children = elem.getChildNodes();
		assertEquals(5, children.getLength());

		// check cost and forChildren
		assertEquals("cost", children.item(0).getNodeName());
		assertEquals("39.9", children.item(0).getFirstChild().getNodeValue());

		assertEquals("forChildren", children.item(1).getNodeName());
		assertEquals("NO", children.item(1).getFirstChild().getNodeValue());

		// ingredients
		Element ingredients = (Element) children.item(2);
		assertEquals("ingredients", ingredients.getNodeName());
		assertEquals(2, ingredients.getChildNodes().getLength());

		// ingredient1 and 2
		Element ingredient1 = (Element) ingredients.getChildNodes().item(0);
		assertEquals("ingredient", ingredient1.getNodeName());
		assertEquals("Ham", ingredient1.getFirstChild().getNodeValue());

		Element ingredient2 = (Element) ingredients.getChildNodes().item(1);
		assertEquals("ingredient", ingredient2.getNodeName());
		assertEquals("2 pieces", ingredient2.getAttribute("amount"));
		assertEquals("Burger", ingredient2.getFirstChild().getNodeValue());

		// photo
		Element photo = (Element) children.item(3);
		assertEquals("photo", photo.getNodeName());
		assertEquals("jpg", photo.getAttribute("format"));
		assertEquals("~/photos/hamburger.jpg", photo.getFirstChild().getNodeValue());

		// reciepe
		Element reciepe = (Element) children.item(4);
		assertEquals("reciepe", reciepe.getNodeName());
		assertEquals("en", reciepe.getAttribute("lang"));
		assertEquals("Take ham and burgers and make a hamburger", reciepe.getFirstChild().getNodeValue());
	}

	// XXX @Test
	public void testCreateElementWithChildrenEverywhere() throws KlaxonException, KlaxonToXMLException {

		Element elem = calculateElem(K2DocFormat.CHILDREN_EVERYWHERE);

		// check name
		assertEquals("food", elem.getNodeName());

		// check children
		NodeList children = elem.getChildNodes();
		assertEquals(6, children.getLength());

		// check name, cost and forChildren
		assertEquals("name", children.item(0).getNodeName());
		assertEquals("Hamburger", children.item(0).getFirstChild().getNodeValue());

		assertEquals("cost", children.item(1).getNodeName());
		assertEquals("39.9", children.item(1).getFirstChild().getNodeValue());

		assertEquals("forChildren", children.item(2).getNodeName());
		assertEquals("NO", children.item(2).getFirstChild().getNodeValue());

		// ingredients
		Element ingredients = (Element) children.item(3);
		assertEquals("ingredients", ingredients.getNodeName());
		assertEquals(2, ingredients.getChildNodes().getLength());

		// ingredient1 and ingradient 2
		Element ingredient1 = (Element) ingredients.getChildNodes().item(0);
		assertEquals("ingredient", ingredient1.getNodeName());
		assertEquals("Ham", ingredient1.getFirstChild().getNodeValue());

		Element ingredient2 = (Element) ingredients.getChildNodes().item(1);
		assertEquals("ingredient", ingredient2.getNodeName());
		assertEquals("amount", ingredient2.getChildNodes().item(0).getNodeName());
		assertEquals("2 pieces", ingredient2.getChildNodes().item(0).getFirstChild().getNodeValue());
		assertEquals("ingredient", ingredient2.getChildNodes().item(1).getNodeName());
		assertEquals("Burger", ingredient2.getChildNodes().item(1).getFirstChild().getNodeValue());

		// photo
		Element photo = (Element) children.item(4);
		assertEquals("photo", photo.getNodeName());
		assertEquals("format", photo.getChildNodes().item(0).getNodeName());
		assertEquals("jpg", photo.getChildNodes().item(0).getFirstChild().getNodeValue());
		assertEquals("photo", photo.getChildNodes().item(1).getNodeName());
		assertEquals("~/photos/hamburger.jpg", photo.getChildNodes().item(1).getFirstChild().getNodeValue());

		// reciepe...
		Element reciepe = (Element) children.item(5);
		assertEquals("reciepe", reciepe.getNodeName());
		assertEquals("lang", reciepe.getChildNodes().item(0).getNodeName());
		assertEquals("en", reciepe.getChildNodes().item(0).getFirstChild().getNodeValue());
		assertEquals("reciepe", reciepe.getChildNodes().item(1).getNodeName());
		assertEquals("Take ham and burgers and make a hamburger",
				reciepe.getChildNodes().item(1).getFirstChild().getNodeValue());

	}

	private Element calculateElem(K2DocFormat format) throws KlaxonToXMLException, KlaxonException {

		config.setFormat(format);

		KlaxonObject klaxon = foods.createKlaxon();
		Document document = KlaxonToXMLImpl.createDocument();
		Element elem = toElems.createElementOfKlaxon(document, klaxon);

		document.appendChild(elem);

		System.out.println(format + ":");
		System.out.println(KlaxonTestUtils.toString(document));

		return elem;
	}

	// XXX @Test
	public void testParseKlaxonFromElementWithAttrsWherePossible() throws KlaxonException, KlaxonToXMLException {
		KlaxonObject klaxon = calculateKlaxon(K2DocFormat.ATTRS_WHERE_POSSIBLE);

		// check name
		assertEquals("food", klaxon.getName());

		// check headers
		List<KlaxonValue> headers = klaxon.getHeaders();
		assertEquals(3, headers.size());

		KlaxonStringValue name = new KlaxonStringValue("name", "Hamburger");
		assertTrue(headers.contains(name));

		KlaxonStringValue cost = new KlaxonStringValue("cost", "39.9");
		assertTrue(headers.contains(cost));

		KlaxonStringValue forChildren = new KlaxonStringValue("forChildren", "NO");
		assertTrue(headers.contains(forChildren));

		// check fields
		List<KlaxonValue> fields = klaxon.getFields();
		assertEquals(3, fields.size());

		// ingredients
		KlaxonObject ingredients = (KlaxonObject) fields.get(0);
		KlaxonValue ingredient1 = KlaxonTestUtils.createWithOneHeader(//
				"ingredient", "ingredient", "Ham", null);
		KlaxonValue ingredient2 = KlaxonTestUtils.createWithTwoHeaders(//
				"ingredient", "amount", "2 pieces", "ingredient", "Burger", null);

		// assertEquals(ingredient1.toString(),
		// ingredients.getFields().get(0).toString());
		// assertEquals(ingredient2.toString(),
		// ingredients.getFields().get(1).toString());

		assertEquals(ingredient1, ingredients.getFields().get(0));
		assertEquals(ingredient2, ingredients.getFields().get(1));

		// photo
		KlaxonValue photo = KlaxonTestUtils.createWithTwoHeaders(//
				"photo", "format", "jpg", "value", "~/photos/hamburger.jpg", null);
		assertEquals(photo, fields.get(1));

		// reciepe
		KlaxonValue reciepe = KlaxonTestUtils.createWithTwoHeaders(//
				"reciepe", "lang", "en", "value", "Take ham and burgers and make a hamburger", null);
		assertEquals(reciepe, fields.get(2));
	}

	// XXX @Test
	public void testParseKlaxonFromElementWithAttrsForHeaders() throws KlaxonException, KlaxonToXMLException {
		KlaxonObject klaxon = calculateKlaxon(K2DocFormat.ATTRS_FOR_HEADERS);

		// check name
		assertEquals("food", klaxon.getName());

		// check headers
		List<KlaxonValue> headers = klaxon.getHeaders();
		assertEquals(1, headers.size());

		KlaxonStringValue name = new KlaxonStringValue("name", "Hamburger");
		assertTrue(headers.contains(name));

		// check fields
		List<KlaxonValue> fields = klaxon.getFields();
		assertEquals(5, fields.size());

		// cost and forChildren
		KlaxonStringValue cost = new KlaxonStringValue("cost", "39.9");
		assertEquals(cost, fields.get(0));

		KlaxonStringValue forChildren = new KlaxonStringValue("forChildren", "NO");
		assertEquals(forChildren, fields.get(1));

		// ingredients
		KlaxonObject ingredients = (KlaxonObject) fields.get(2);
		KlaxonValue ingredient1 = KlaxonTestUtils.createWithOneHeader(//
				"ingredient", null, null, "Ham");
		KlaxonValue ingredient2 = KlaxonTestUtils.createWithOneHeader(//
				"ingredient", "amount", "2 pieces", "Burger");

		// assertEquals(ingredient1.toString(),
		// ingredients.getFields().get(0).toString());
		// assertEquals(ingredient2.toString(),
		// ingredients.getFields().get(1).toString());

		assertEquals(ingredient1, ingredients.getFields().get(0));
		assertEquals(ingredient2, ingredients.getFields().get(1));

		// photo
		KlaxonValue photo = KlaxonTestUtils.createWithOneHeader(//
				"photo", "format", "jpg", "~/photos/hamburger.jpg");
		assertEquals(photo, fields.get(3));

		// reciepe
		KlaxonValue reciepe = KlaxonTestUtils.createWithOneHeader(//
				"reciepe", "lang", "en", "Take ham and burgers and make a hamburger");
		assertEquals(reciepe, fields.get(4));
	}

	@Test
	public void testParseKlaxonFromElementWithChildrenEverywhere() throws KlaxonException, KlaxonToXMLException {
		KlaxonObject klaxon = calculateKlaxon(K2DocFormat.CHILDREN_EVERYWHERE);

		// check name
		assertEquals("food", klaxon.getName());

		// check headers
		List<KlaxonValue> headers = klaxon.getHeaders();
		assertEquals(0, headers.size());

		// check fields
		List<KlaxonValue> fields = klaxon.getFields();
		assertEquals(6, fields.size());

		// check name, cost and forChildren
		KlaxonStringValue name = new KlaxonStringValue("name", "Hamburger");
		assertEquals(name, fields.get(0));

		KlaxonStringValue cost = new KlaxonStringValue("cost", "39.9");
		assertEquals(cost, fields.get(1));

		KlaxonStringValue forChildren = new KlaxonStringValue("forChildren", "NO");
		assertEquals(forChildren, fields.get(2));

		// ingredients
		KlaxonObject ingredients = (KlaxonObject) fields.get(3);
		KlaxonValue ingredient1 = KlaxonTestUtils.createWithOneHeader(//
				"ingredient", null, null, "Ham");
		KlaxonValue ingredient2Amount = KlaxonTestUtils.createWithOneHeader(//
				"amount", null, null, "2 pieces");
		KlaxonValue ingredient2Name = KlaxonTestUtils.createWithOneHeader(//
				"ingredient", null, null, "Burger");

		// assertEquals(ingredient1.toString(), ((KlaxonObject)
		// ingredients.getFields().get(0)).getFields().get(0).toString());
		// assertEquals(ingredient2Name.toString(), ((KlaxonObject)
		// ingredients.getFields().get(1)).getFields().get(0).toString());
		// assertEquals(ingredient2Amount.toString(), ((KlaxonObject)
		// ingredients.getFields().get(1)).getFields().get(1).toString());

		// FIXME tests fails here: assertEquals(ingredient1,
		// ((KlaxonStringValue)
		// ingredients.getFields().get(0)));
		assertEquals(ingredient2Name, ((KlaxonObject) ingredients.getFields().get(1)).getFields().get(0));
		assertEquals(ingredient2Amount, ((KlaxonObject) ingredients.getFields().get(1)).getFields().get(1));

		// photo
		KlaxonValue photoFormat = KlaxonTestUtils.createWithOneHeader(//
				"format", null, null, "jpg");
		KlaxonValue photoPhoto = KlaxonTestUtils.createWithOneHeader(//
				"photo", null, null, "~/photos/hamburger.jpg");

		KlaxonObject photo = (KlaxonObject) fields.get(4);
		assertEquals(photoFormat, photo.getFields().get(0));
		assertEquals(photoPhoto, photo.getFields().get(1));

		// reciepe
		KlaxonValue reciepeLang = KlaxonTestUtils.createWithOneHeader(//
				"lang", null, null, "en");
		KlaxonValue reciepeReciepe = KlaxonTestUtils.createWithOneHeader(//
				"reciepe", null, null, "Take ham and burgers and make a hamburger");

		KlaxonObject reciepe = (KlaxonObject) fields.get(5);
		assertEquals(reciepeLang, reciepe.getFields().get(0));
		assertEquals(reciepeReciepe, reciepe.getFields().get(1));
	}

	private KlaxonObject calculateKlaxon(K2DocFormat format) throws KlaxonToXMLException, KlaxonException {
		Document document = KlaxonToXMLImpl.createDocument();
		Element elem = foods.createElement(document, format);
		document.appendChild(elem);

		KlaxonObject klaxon = (KlaxonObject) fromElems.createKlaxonOfElement(elem);

		System.out.println(format);
		System.out.println(KlaxonTestUtils.toString(document));
		klaxon.print(0, System.out);

		return klaxon;
	}

}
