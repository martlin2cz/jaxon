package cz.martlin.jaxon.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cz.martlin.jaxon.exception.KlaxonException;
import cz.martlin.jaxon.object.KlaxonItem;
import cz.martlin.jaxon.object.KlaxonObject;

public class XMLUtils {

	private static DocumentBuilder createBuilder() throws KlaxonException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			return dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new KlaxonException("Cannot initialize document builder", e);
		}

	}

	public static Document createDocument() throws KlaxonException {
		DocumentBuilder builder;
		try {
			builder = createBuilder();
		} catch (KlaxonException e) {
			throw new KlaxonException("Cannot create document", e);
		}

		return builder.newDocument();
	}

	public static Document parse(InputStream is) throws KlaxonException {
		DocumentBuilder builder;
		try {
			builder = createBuilder();
			return parse(builder, is);
		} catch (KlaxonException e) {
			throw new KlaxonException("Cannot parse document", e);
		}
	}

	private static Document parse(DocumentBuilder builder, InputStream is)
			throws KlaxonException {

		try {
			return builder.parse(is);
		} catch (SAXException | IOException e) {
			throw new KlaxonException("Cannot parse document", e);
		}
	}

	public static Element createRootFromKlaxon(Document document,
			KlaxonObject klaxon) throws KlaxonException {

		Element root = createRoot(document, klaxon);

		addItems(document, klaxon.getItems(), root);

		return root;
	}

	private static void addItems(Document document, Set<KlaxonItem> items,
			Element root) {
		for (KlaxonItem item : items) {
			String name = item.getName();
			Map<String, String> attributes = item.getAttributes();

			Element element = document.createElement(name);

			for (Entry<String, String> entry : attributes.entrySet()) {
				String attrName = entry.getKey();
				String attrValue = entry.getValue();

				element.setAttribute(attrName, attrValue);
			}

			root.appendChild(element);
		}
	}

	protected static Element createRoot(Document document, KlaxonObject klaxon) {
		Element root = document.createElement(klaxon.getName());

		return root;
	}

	public static Element getRoot(Document document) {
		return document.getDocumentElement();
	}

	public static KlaxonObject createKlaxonFromRoot(Element root)
			throws KlaxonException {

		String name = root.getNodeName();
		Set<KlaxonItem> items = parseItems(root);

		return new KlaxonObject(name, items);
	}

	private static Set<KlaxonItem> parseItems(Element root) {
		Set<KlaxonItem> items = new LinkedHashSet<>();

		NodeList children = root.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);

			if (child instanceof Element) {
				Element element = (Element) child;
				KlaxonItem item = parseItem(element);
				items.add(item);
			}

		}

		return items;
	}

	private static KlaxonItem parseItem(Element element) {

		String name = element.getNodeName();
		Map<String, String> attributes = new HashMap<>();

		NamedNodeMap attrs = element.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Node node = attrs.item(i);
			if (node instanceof Attr) {
				Attr attr = (Attr) node;

				String attrName = attr.getNodeName();
				String attrValue = attr.getNodeValue();
				attributes.put(attrName, attrValue);
			}
		}

		return new KlaxonItem(name, attributes);
	}

	// TODO very simple
	public static String toString(Document document) throws KlaxonException {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(document), new StreamResult(
					writer));
			return writer.getBuffer().toString();
		} catch (Exception e) {
			throw new KlaxonException("Cannot to-string document", e);
		}
	}

}
