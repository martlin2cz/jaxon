package cz.martlin.jaxon.klaxon;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.k2xml.KlaxonToXMLConverter;
import cz.martlin.jaxon.k2xml.KlaxonToXMLException;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;

/**
 * Utilities for klaxon tests.
 * 
 * @author martin
 *
 */
public class KlaxonTestUtils {

	/**
	 * Creates klaxon object with given (optional, can be both null) attrName
	 * and attrValue and (optional too) content.
	 * 
	 * @param elemName
	 * @param attrName
	 * @param attrValue
	 * @param content
	 * @return
	 */
	public static KlaxonValue createWithOneHeader(String elemName, String attrName, String attrValue, String content) {

		List<KlaxonValue> headers = new ArrayList<>();
		List<KlaxonValue> fields = new ArrayList<>();

		if (attrName != null && attrValue != null) {
			KlaxonStringValue attr = new KlaxonStringValue(attrName, attrValue);
			headers.add(attr);
		}

		if (content != null) {
			return new KlaxonStringValue(elemName, headers, content);
		} else {
			return new KlaxonObject(elemName, headers, fields);
		}
	}

	/**
	 * Create klaxon element with exactly two header fields.
	 * 
	 * @param elemName
	 * @param attr1Name
	 * @param attr1Value
	 * @param attr2Name
	 * @param attr2Value
	 * @param content
	 * @return
	 */
	public static KlaxonValue createWithTwoHeaders(String elemName, String attr1Name, String attr1Value,
			String attr2Name, String attr2Value, String content) {

		List<KlaxonValue> headers = new ArrayList<>();
		List<KlaxonValue> fields = new ArrayList<>();

		KlaxonStringValue attr1 = new KlaxonStringValue(attr1Name, attr1Value);
		headers.add(attr1);
		KlaxonStringValue attr2 = new KlaxonStringValue(attr2Name, attr2Value);
		headers.add(attr2);

		if (content != null) {
			return new KlaxonStringValue(elemName, headers, content);
		} else {
			return new KlaxonObject(elemName, headers, fields);
		}
	}

	/**
	 * Creates node with given name appended by text node with content. If not
	 * null, adds given attribute.
	 * 
	 * @param doc
	 * @param elemName
	 * @param attrName
	 * @param attrValue
	 * @param content
	 * @return
	 */
	public static Element createTextNode(Document doc, String elemName, String attrName, String attrValue,
			String content) {

		Element element = doc.createElement(elemName);

		if (attrName != null && attrValue != null) {
			element.setAttribute(attrName, attrValue);
		}

		Text text = doc.createTextNode(content);
		element.appendChild(text);

		return element;
	}

	/**
	 * Simply converts document to string.
	 * 
	 * @param document
	 * @return
	 * @throws KlaxonException
	 */
	public static String toString(Document document) throws KlaxonException {
		try {
			Config config = new Config();
			KlaxonToXMLConverter k2xml = new KlaxonToXMLConverter(config);
			return k2xml.export(document);
		} catch (KlaxonToXMLException e) {
			throw new KlaxonException("Cannot to-string document", e);
		}
	}

}
