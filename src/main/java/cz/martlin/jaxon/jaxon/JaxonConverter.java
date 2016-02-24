package cz.martlin.jaxon.jaxon;

import java.io.File;

import org.w3c.dom.Document;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.j2k.JackToKlaxonConverter;
import cz.martlin.jaxon.jack.JackConverter;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.k2xml.KlaxonToXMLConverter;
import cz.martlin.jaxon.klaxon.KlaxonConverter;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;

public class JaxonConverter {

	private final JackConverter jack;
	private final JackToKlaxonConverter j2k;
	private final KlaxonConverter klaxon;
	private final KlaxonToXMLConverter k2x;

	public JaxonConverter(Config config) {
		jack = new JackConverter(config);
		j2k = new JackToKlaxonConverter(config);
		klaxon = new KlaxonConverter(config);
		k2x = new KlaxonToXMLConverter(config);
	}

	private Document objectToDocument(Object object) throws JaxonException {
		JackObject jackObject = jack.toJack(object);
		KlaxonAbstractElement klaxonEntry = j2k.jackToKlaxon(jackObject);
		Document document = klaxon.toDocument(klaxonEntry);
		return document;
	}

	private Object documentToObject(Document document) throws JaxonException {
		KlaxonAbstractElement klaxonEntry = klaxon.toKlaxon(document);
		JackObject jackObject = j2k.klaxonToJack(klaxonEntry);
		Object object = jack.toObject(jackObject);
		return object;
	}

	/**
	 * Converts object to XML string
	 * 
	 * @param object
	 * @return
	 * @throws JaxonException
	 */
	public String objectToString(Object object) throws JaxonException {
		Document document = objectToDocument(object);
		return k2x.export(document);
	}

	/**
	 * Converts object into XML file
	 * 
	 * @param object
	 * @param file
	 * @throws JaxonException
	 */
	public void objectToFile(Object object, File file) throws JaxonException {
		Document document = objectToDocument(object);
		k2x.export(document, file);
	}

	/**
	 * Parses object from XML string
	 * 
	 * @param string
	 * @return
	 * @throws JaxonException
	 */
	public Object objectFromString(String string) throws JaxonException {
		Document document = k2x.parse(string);
		return documentToObject(document);
	}

	/**
	 * Parses object from XML file.
	 * 
	 * @param file
	 * @return
	 * @throws JaxonException
	 */
	public Object objectFromFile(File file) throws JaxonException {
		Document document = k2x.parse(file);
		return documentToObject(document);
	}

}
