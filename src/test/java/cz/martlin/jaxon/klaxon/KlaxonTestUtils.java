package cz.martlin.jaxon.klaxon;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.klaxon.data.KlaxonAttribute;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithChildren;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithValue;

public class KlaxonTestUtils {

	public static KlaxonElemWithChildren createWithOneAttr(String elemName,
			String attrName, String attrValue) {

		Set<KlaxonAttribute> attributes = new HashSet<>();

		if (attrName != null && attrValue != null) {
			KlaxonAttribute attr = new KlaxonAttribute(attrName, attrValue);
			attributes.add(attr);
		}

		return new KlaxonElemWithChildren(elemName, attributes);
	}

	/**
	 * Creates new Klaxon element with given elem name. If content is provided,
	 * resulting elem is {@link KlaxonElemWithValue}, else
	 * {@link KlaxonElemWithChildren}. If attrName and attrValue are specified,
	 * adds attribute to created element.
	 * 
	 * @param elemName
	 * @param attrName
	 * @param attrValue
	 * @param content
	 * @return
	 */
	public static KlaxonAbstractElement createWithOneAttr(String elemName,
			String attrName, String attrValue, String content) {

		Set<KlaxonAttribute> attributes = new HashSet<>();

		if (attrName != null && attrValue != null) {
			KlaxonAttribute attr = new KlaxonAttribute(attrName, attrValue);
			attributes.add(attr);
		}
		if (content != null) {
			return new KlaxonElemWithValue(elemName, attributes, content);
		} else {
			return new KlaxonElemWithChildren(elemName, attributes);
		}
	}

	public static Element createTextNode(Document doc, String elemName,
			String attrName, String attrValue, String content) {

		Element element = doc.createElement(elemName);

		if (attrName != null && attrValue != null) {
			element.setAttribute(attrName, attrValue);
		}

		Text text = doc.createTextNode(content);
		element.appendChild(text);

		return element;
	}

	public static String toString(Document document) throws KlaxonException {
		// TODO pak použít ... Klaxon2XML
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
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
