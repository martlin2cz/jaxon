package cz.martlin.jaxon.process;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jaxon.exception.KlaxonException;
import cz.martlin.jaxon.object.KlaxonObject;
import cz.martlin.jaxon.utils.XMLUtils;

public class KlaxonConverter {

	public Document toDocument(KlaxonObject klaxon) throws KlaxonException {
		Document document = XMLUtils.createDocument();

		Element root;
		try {
			root = XMLUtils.createRootFromKlaxon(document, klaxon);
		} catch (KlaxonException e) {
			throw new KlaxonException("Cannot store to document", e);
		}
		document.appendChild(root);

		return document;
	}

	public KlaxonObject toKlaxon(Document document) throws KlaxonException {

		Element root = XMLUtils.getRoot(document);
		KlaxonObject klaxon;
		try {
			klaxon = XMLUtils.createKlaxonFromRoot(root);
		} catch (KlaxonException e) {
			throw new KlaxonException("Cannot load from document", e);
		}

		return klaxon;
	}
}
