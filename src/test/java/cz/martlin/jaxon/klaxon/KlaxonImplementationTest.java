package cz.martlin.jaxon.klaxon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.config.ImplProvider;
import cz.martlin.jaxon.k2xml.KlaxonToXMLException;
import cz.martlin.jaxon.k2xml.KlaxonToXMLImpl;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.klaxon.data.KlaxonAttribute;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithChildren;
import cz.martlin.jaxon.klaxon.data.KlaxonEntry;
import cz.martlin.jaxon.testings.klaxon.FoodKlaxonCreator;

public class KlaxonImplementationTest {

	private final Config config = ImplProvider.getTestingConfig();
	private final KlaxonImplementation impl = new KlaxonImplementation(config);

	private final FoodKlaxonCreator foods = FoodKlaxonCreator.createHamburger();

	@Test
	public void testCreateElementOfKlaxon() throws KlaxonException,
			KlaxonToXMLException {
		KlaxonAbstractElement klaxon = foods.createKlaxon();
		Document document = KlaxonToXMLImpl.createDocument();
		Element elem = impl.createElementOfKlaxon(document, klaxon);

		// check name
		assertEquals("food", elem.getNodeName());

		// check attributes
		assertEquals(3, elem.getAttributes().getLength());
		assertEquals("Hamburger", elem.getAttribute("name"));
		assertEquals("39.9", elem.getAttribute("cost"));

		// check children
		assertEquals(3, elem.getChildNodes().getLength());
		// ingredients
		assertEquals("ingredients", elem.getChildNodes().item(0).getNodeName());
		assertEquals(2, elem.getChildNodes().item(0).getChildNodes()
				.getLength());
		// photo
		assertEquals("photo", elem.getChildNodes().item(1).getNodeName());
		assertEquals("jpg", elem.getChildNodes().item(1).getAttributes()
				.getNamedItem("format").getNodeValue());

		assertEquals("reciepe", elem.getChildNodes().item(2).getNodeName());

	}

	@Test
	public void testParseKlaxonFromElement() throws KlaxonException,
			KlaxonToXMLException {
		Document document = KlaxonToXMLImpl.createDocument();
		Element elem = foods.createElement(document);

		KlaxonElemWithChildren klaxon = (KlaxonElemWithChildren) impl
				.parseKlaxonFromElement(document, elem);

		// klaxon.print(0, System.out);

		// check name
		assertEquals("food", klaxon.getName());

		// check attributes
		Set<KlaxonAttribute> attrs = new TreeSet<>(klaxon.getAttributes());
		assertEquals(3, attrs.size());

		KlaxonEntry name = new KlaxonAttribute("name", "Hamburger");
		assertTrue(attrs.contains(name));

		KlaxonEntry cost = new KlaxonAttribute("cost", "39");
		assertTrue(attrs.contains(cost));
		// ...

		// and children
		List<KlaxonAbstractElement> children = new ArrayList<>(
				klaxon.getChildren());
		assertEquals(3, children.size());

		KlaxonEntry photo = KlaxonTestUtils.createWithOneAttr(//
				"photo", "format", "jpg", "~/photos/hamburger.jpg");
		assertTrue(children.contains(photo));

		// ...
	}

}
