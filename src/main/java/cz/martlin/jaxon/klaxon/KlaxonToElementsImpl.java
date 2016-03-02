package cz.martlin.jaxon.klaxon;

import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import cz.martlin.jaxon.klaxon.config.K2DocFormat;
import cz.martlin.jaxon.klaxon.config.KlaxonConfig;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;

/**
 * Implements conversion from klaxon objects to XML DOM elements.
 * 
 * @author martin
 *
 */
public class KlaxonToElementsImpl {
	private final KlaxonTools tools = new KlaxonTools();
	private final KlaxonConfig config;

	public KlaxonToElementsImpl(KlaxonConfig config) {
		this.config = config;
	}

	/**
	 * For given klaxon object creates (with given document) corresponding
	 * element.
	 * 
	 * @param document
	 * @param klaxon
	 * @return
	 */
	public Element createElementOfKlaxon(Document document, KlaxonObject klaxon) throws KlaxonException {
		return createElement(document, klaxon);
	}

	/**
	 * Converts klaxon object klaxon with owner owner, into new node with parent
	 * parent. If preferAttr is true, will use attribute if possible.
	 * 
	 * @param document
	 * @param klaxon
	 * @param owner
	 * @param parent
	 * @param preferAttr
	 * @return
	 * @throws KlaxonException
	 */
	protected Node klaxonToNode(Document document, KlaxonValue klaxon, KlaxonValue owner, Element parent,
			boolean preferAttr) throws KlaxonException {

		if (tools.isToCreateAttribute(config.getFormat(), klaxon, owner, preferAttr)) {
			return createAttribute(document, klaxon);
		} else {
			return createElement(document, klaxon);
		}
	}

	/**
	 * Creates attribute of given klaxon. Klaxon must be
	 * {@link KlaxonStringValue}.
	 * 
	 * @param document
	 * @param klaxon
	 * @return
	 */
	private Attr createAttribute(Document document, KlaxonValue klaxon) {
		KlaxonStringValue string = (KlaxonStringValue) klaxon;

		String name = string.getName();
		String value = string.getValue();

		Attr attr = document.createAttribute(name);
		attr.setValue(value);

		return attr;
	}

	/**
	 * Creates element of given klaxon.
	 * 
	 * @param document
	 * @param klaxon
	 * @return
	 * @throws KlaxonException
	 */
	private Element createElement(Document document, KlaxonValue klaxon) throws KlaxonException {

		String name = klaxon.getName();
		Element elem = document.createElement(name);

		addHeaders(document, klaxon, elem);

		if (klaxon instanceof KlaxonObject) {
			KlaxonObject object = (KlaxonObject) klaxon;
			addFields(document, object, elem);
		} else if (klaxon instanceof KlaxonStringValue) {
			KlaxonStringValue string = (KlaxonStringValue) klaxon;
			addContent(document, string, elem);
		} else {
			Exception e = new IllegalArgumentException("Unkown klaxon object tyoe");
			throw new KlaxonException("Unknown type", e);
		}

		return elem;
	}

	/**
	 * Adds text content of given klaxon string value into given elem.
	 * 
	 * @param document
	 * @param string
	 * @param elem
	 * @throws KlaxonException
	 */
	private void addContent(Document document, KlaxonStringValue string, Element elem) throws KlaxonException {
		String value = string.getValue();
		K2DocFormat format = config.getFormat();

		if (tools.isToCreateTextNode(format, string, elem)) {
			Text text = document.createTextNode(value);
			elem.appendChild(text);

		} else if (tools.isToCreateValuedAttribute(format, string, elem)) {
			String name = tools.configOrName(config.getFormat().valueAttrName(), string);
			elem.setAttribute(name, value);

		} else if (tools.isToCreateTextChild(format, string, elem)) {
			addChildText(document, string, elem);

		} else {
			Exception e = new IllegalArgumentException("Unspefied what to do with string value");
			throw new KlaxonException("Unspecified technique", e);
		}
	}

	/**
	 * Creates and adds new child node with text of given klaxon string value.
	 * The node is appended to elem. Use when element with text content must
	 * have more children, so is necessary to wrap text by another element.
	 * 
	 * @param document
	 * @param string
	 * @param elem
	 * @throws KlaxonException
	 */
	private void addChildText(Document document, KlaxonStringValue string, Element elem) throws KlaxonException {
		String name = tools.configOrName(config.getFormat().valueElemName(), string);
		Element child = document.createElement(name);
		elem.appendChild(child);

		addContent(document, string, child);
	}

	/**
	 * Adds headers of given klaxon object to given element.
	 * 
	 * @param document
	 * @param klaxon
	 * @param elem
	 * @throws KlaxonException
	 */
	private void addHeaders(Document document, KlaxonValue klaxon, Element elem) throws KlaxonException {
		boolean preferAttrs = config.getFormat().headerAsAtrr();
		List<KlaxonValue> headers = klaxon.getHeaders();

		add(document, preferAttrs, headers, klaxon, elem);
	}

	/**
	 * Adds fields of given klaxon object to given element.
	 * 
	 * @param document
	 * @param klaxon
	 * @param elem
	 * @throws KlaxonException
	 */
	private void addFields(Document document, KlaxonObject klaxon, Element elem) throws KlaxonException {
		boolean preferAttrs = config.getFormat().fieldAsAtrr();
		List<KlaxonValue> fields = klaxon.getFields();

		add(document, preferAttrs, fields, klaxon, elem);
	}

	/**
	 * Adds given items owned by owner into elem. If preferAttr is true, will
	 * each item append as attribute rather then child (if possible).
	 * 
	 * @param document
	 * @param preferAttr
	 * @param items
	 * @param owner
	 * @param elem
	 * @throws KlaxonException
	 */
	private void add(Document document, boolean preferAttr, List<KlaxonValue> items, KlaxonValue owner, Element elem)
			throws KlaxonException {

		for (KlaxonValue value : items) {
			Node child = klaxonToNode(document, value, owner, elem, preferAttr);
			tools.appendNodeTo(elem, child);
		}
	}

	////////////////////////////////////////////////////////////////////////////////

}
