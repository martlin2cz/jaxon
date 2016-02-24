package cz.martlin.jaxon.klaxon;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import cz.martlin.jaxon.klaxon.config.KlaxonConfig;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.klaxon.data.KlaxonAttribute;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithChildren;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithValue;

public class KlaxonImplementation {

	public KlaxonImplementation(KlaxonConfig config) {
	}

	/**
	 * 
	 * @param document
	 * @param klaxon
	 * @return
	 */
	public Element createElementOfKlaxon(Document document,
			KlaxonAbstractElement klaxon) throws KlaxonException {

		String name = klaxon.getName();
		Element elem = document.createElement(name);

		addAttributes(klaxon, elem);

		if (klaxon instanceof KlaxonElemWithChildren) {
			KlaxonElemWithChildren withChildren = (KlaxonElemWithChildren) klaxon;
			addChildren(document, withChildren, elem);
		} else if (klaxon instanceof KlaxonElemWithValue) {
			KlaxonElemWithValue withValue = (KlaxonElemWithValue) klaxon;
			addValue(document, withValue, elem);
		}

		return elem;
	}

	private void addAttributes(KlaxonAbstractElement klaxon, Element elem) {
		for (KlaxonAttribute attr : klaxon.getAttributes()) {
			String attrName = attr.getName();
			String attrValue = attr.getValue();
			elem.setAttribute(attrName, attrValue);	//TODO if attrValue == null do not set?
		}
	}

	private void addChildren(Document document, KlaxonElemWithChildren klaxon,
			Element elem) throws KlaxonException {

		for (KlaxonAbstractElement child : klaxon.getChildren()) {
			Element childElem = createElementOfKlaxon(document, child);
			elem.appendChild(childElem);
		}
	}

	private void addValue(Document document, KlaxonElemWithValue klaxon,
			Element elem) throws KlaxonException {

		String value = klaxon.getValue();
		Node text = document.createTextNode(value);
		elem.appendChild(text);
	}

	public KlaxonAbstractElement parseKlaxonFromElement(Document document,
			Element elem) throws KlaxonException {

		String name = elem.getNodeName();

		Set<KlaxonAttribute> attributes = parseAttributes(elem);

		if (isWithValueNode(elem)) {
			String value = parseValue(document, elem);
			return new KlaxonElemWithValue(name, attributes, value);
		} else if (isWithChildrenNode(elem)) {
			List<KlaxonAbstractElement> children = parseChildren(document, elem);
			return new KlaxonElemWithChildren(name, attributes, children);
		} else {
			Exception e = new IllegalArgumentException(
					"Unknown element type of " + name);
			throw new KlaxonException("Illegal element", e);
		}
	}

	private boolean isWithValueNode(Element elem) {
		NodeList children = elem.getChildNodes();

		// TODO a co komentáře?
		if (children.getLength() != 1) { // TODO fakt to takhle bude fungovat?
			return false;
		}

		Node child = children.item(0);

		return (child instanceof Text);
	}

	private boolean isWithChildrenNode(Element elem) {
		return true;
	}

	private Set<KlaxonAttribute> parseAttributes(Element element)
			throws KlaxonException {

		NamedNodeMap attributes = element.getAttributes();
		TreeSet<KlaxonAttribute> klaxons = new TreeSet<>();

		for (int i = 0; i < attributes.getLength(); i++) {
			Attr attribute = (Attr) attributes.item(i);
			String name = attribute.getName();
			String value = attribute.getValue();

			KlaxonAttribute klaxon = new KlaxonAttribute(name, value);
			klaxons.add(klaxon);
		}

		return klaxons;
	}

	private List<KlaxonAbstractElement> parseChildren(Document document,
			Element element) throws KlaxonException {

		NodeList children = element.getChildNodes();
		List<KlaxonAbstractElement> klaxons = new ArrayList<>(
				children.getLength());

		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node instanceof Element) {
				Element child = (Element) node;

				KlaxonAbstractElement klaxon = parseKlaxonFromElement(document,
						child);
				klaxons.add(klaxon);
			}
		}

		return klaxons;
	}

	private String parseValue(Document document, Element element)
			throws KlaxonException {

		Node child = element.getFirstChild();
		Text text = (Text) child;

		return text.getData();
	}

}
