package cz.martlin.jaxon.klaxon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jaxon.k2xml.KlaxonToXMLException;
import cz.martlin.jaxon.k2xml.KlaxonToXMLImpl;
import cz.martlin.jaxon.klaxon.config.KlaxonConfig;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;

/**
 * Implements converting of klaxon objects to xml documents and back.
 * 
 * @author martin
 *
 */
public class KlaxonConverter {
	private final KlaxonToElementsImpl toElems;
	private final KlaxonFromElementsImpl fromElems;

	public KlaxonConverter(KlaxonConfig config) {
		this.toElems = new KlaxonToElementsImpl(config);
		this.fromElems = new KlaxonFromElementsImpl(config);
	}

	/**
	 * Converts given klaxon object into document.
	 * 
	 * @param klaxon
	 * @return
	 * @throws KlaxonException
	 */
	public Document toDocument(KlaxonObject klaxon) throws KlaxonException {
		Document document;

		try {
			document = KlaxonToXMLImpl.createDocument();
		} catch (KlaxonToXMLException e) {
			throw new KlaxonException("Cannot create document", e);
		}

		Element root;
		try {
			root = toElems.createElementOfKlaxon(document, klaxon);
		} catch (KlaxonException e) {
			throw new KlaxonException("Cannot store to document", e);
		}
		document.appendChild(root);

		return document;
	}

	/**
	 * Converts given document to klaxon object.
	 * 
	 * @param document
	 * @return
	 * @throws KlaxonException
	 */
	public KlaxonObject toKlaxon(Document document) throws KlaxonException {

		Element root = document.getDocumentElement();
		KlaxonObject klaxon;
		try {
			klaxon = fromElems.createKlaxonOfElement(root);
		} catch (KlaxonException e) {
			throw new KlaxonException("Cannot load from document", e);
		}

		return klaxon;
	}
}
