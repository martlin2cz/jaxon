package cz.martlin.jaxon.klaxon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jaxon.k2xml.KlaxonToXMLException;
import cz.martlin.jaxon.k2xml.KlaxonToXMLImpl;
import cz.martlin.jaxon.klaxon.config.KlaxonConfig;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;

public class KlaxonConverter {
	private final KlaxonImplementation impl;

	public KlaxonConverter(KlaxonConfig config) {
		this.impl = new KlaxonImplementation(config);

	}

	public Document toDocument(KlaxonAbstractElement klaxon)
			throws KlaxonException {
		Document document;
		
		try {
			document = KlaxonToXMLImpl.createDocument();
		} catch (KlaxonToXMLException e) {
			throw new KlaxonException("Cannot create document", e);
		}

		Element root;
		try {
			root = impl.createElementOfKlaxon(document, klaxon);
		} catch (KlaxonException e) {
			throw new KlaxonException("Cannot store to document", e);
		}
		document.appendChild(root);

		return document;
	}

	public KlaxonAbstractElement toKlaxon(Document document)
			throws KlaxonException {

		Element root = document.getDocumentElement();
		KlaxonAbstractElement klaxon;
		try {
			klaxon = impl.parseKlaxonFromElement(document, root);
		} catch (KlaxonException e) {
			throw new KlaxonException("Cannot load from document", e);
		}

		return klaxon;
	}
}
