package cz.martlin.jaxon.testings;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jaxon.klaxon.KlaxonException;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;

public interface KlaxonTestTuple {

	public abstract KlaxonAbstractElement createKlaxon();

	public abstract Element createElement(Document document);

	public abstract Document createDocument() throws KlaxonException;

}
