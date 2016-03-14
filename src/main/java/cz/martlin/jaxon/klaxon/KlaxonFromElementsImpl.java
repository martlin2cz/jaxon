package cz.martlin.jaxon.klaxon;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import cz.martlin.jaxon.klaxon.config.K2DocFormat;
import cz.martlin.jaxon.klaxon.config.KlaxonConfig;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;

/**
 * Implements conversion to klaxon from XML DOM elements.
 * 
 * @author martin
 *
 */
public class KlaxonFromElementsImpl {
	private final KlaxonTools tools = new KlaxonTools();
	private final KlaxonConfig config;

	public KlaxonFromElementsImpl(KlaxonConfig config) {
		super();
		this.config = config;
	}

	/**
	 * For given XML DOM element parses its klaxon object representation.
	 * 
	 * @param document
	 * @param klaxon
	 * @return
	 */
	public KlaxonObject createKlaxonOfElement(Element element) throws KlaxonException {
		return elemToKlaxon(element);
	}

	/**
	 * Parses given element to klaxon object.
	 * 
	 * @param element
	 * @return
	 * @throws KlaxonException
	 */
	protected KlaxonObject elemToKlaxon(Element element) throws KlaxonException {
		return childToKlaxonObject(element);
	}

	/**
	 * Parses attributes of given element.
	 * 
	 * @param element
	 * @param ignoreAttr
	 * @return
	 */
	private List<KlaxonValue> parseAttributes(Element element, String ignoreAttr) {
		NamedNodeMap attrs = element.getAttributes();

		List<KlaxonValue> attributes = new ArrayList<>(attrs.getLength());

		for (int i = 0; i < attrs.getLength(); i++) {
			Node node = attrs.item(i);
			if (node instanceof Attr) {
				Attr attr = (Attr) node;
				if (ignoreAttr != null && attr.getName().equals(ignoreAttr)) {
					continue;
				}

				KlaxonValue value = attributeToKlaxon(attr);
				attributes.add(value);
			}
		}

		return attributes;

	}

	/**
	 * Parses child items of given element.
	 * 
	 * @param element
	 * @param ignoreChild
	 * @return
	 * @throws KlaxonException
	 */
	private List<KlaxonValue> parseChildren(Element element, String ignoreChild) throws KlaxonException {
		NodeList children = element.getChildNodes();

		List<KlaxonValue> items = new ArrayList<>(children.getLength());

		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node instanceof Element) {
				Element child = (Element) node;
				if (ignoreChild != null && child.getTagName().equals(ignoreChild)) {
					continue;
				}

				KlaxonValue value = childToKlaxon(child);
				items.add(value);
			}
		}

		return items;
	}

	/**
	 * Converts XML DOM attribute to klaxon string value.
	 * 
	 * @param attr
	 * @return
	 */
	protected KlaxonStringValue attributeToKlaxon(Attr attr) {
		String name = attr.getName();
		String value = attr.getValue();
		return new KlaxonStringValue(name, value);
	}

	/**
	 * Converts element into klaxon string value or klaxon object depending on
	 * whether the element is string valued element (
	 * {@link #isStringValued(Element)}).
	 * 
	 * @param child
	 * @return
	 * @throws KlaxonException
	 */
	private KlaxonValue childToKlaxon(Element child) throws KlaxonException {
		K2DocFormat format = config.getFormat();

		if (tools.isStringValued(format, child)) {
			return childToKlaxonString(child);
		} else {
			return childToKlaxonObject(child);
		}
	}

	private KlaxonStringValue childToKlaxonString(Element child) throws KlaxonException {
		String name = child.getTagName();
		K2DocFormat format = config.getFormat();

		List<KlaxonValue> headers;
		String value;
		if (tools.isTextNode(format, child)) {
			headers = parseAttributes(child, null);
			value = valueOfTextNode(child);
		} else if (tools.isValuedAttribute(format, child)) {
			String attrName = tools.nameOfAttrElem(format, child);
			headers = parseAttributes(child, attrName);
			value = valueOfValuedAttribute(child);
		} else if (tools.isTextChild(format, child)) {
			String childName = tools.nameOfValueElem(format, child);
			headers = parseChildren(child, childName);
			value = valueOfChildedValued(child);

		} else {
			Exception e = new IllegalArgumentException("Unexpected (expected some text) child of name " + name);
			throw new KlaxonException("Unspecified child", e);
		}
		return new KlaxonStringValue(name, headers, value);
	}

	private String valueOfChildedValued(Element elem) {
		K2DocFormat format = config.getFormat();
		String childName = tools.nameOfValueElem(format, elem);
		Element child = tools.findChild(elem, childName);
		Text text = (Text) child.getFirstChild();
		return text.getTextContent();
	}

	private String valueOfValuedAttribute(Element elem) {
		K2DocFormat format = config.getFormat();
		String attrName = tools.nameOfAttrElem(format, elem);
		return elem.getAttribute(attrName);
	}

	private String valueOfTextNode(Element child) {
		Text text = (Text) child.getFirstChild();
		return text.getTextContent();
	}

	private KlaxonObject childToKlaxonObject(Element child) throws KlaxonException {
		String name = child.getTagName();

		List<KlaxonValue> headers = parseAttributes(child, null);
		List<KlaxonValue> fields = parseChildren(child, null);
		return new KlaxonObject(name, headers, fields);
	}

}
