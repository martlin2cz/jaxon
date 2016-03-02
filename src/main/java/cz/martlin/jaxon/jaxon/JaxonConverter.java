package cz.martlin.jaxon.jaxon;

import java.io.File;

import org.w3c.dom.Document;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.j2k.JackToKlaxonConverter;
import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.data.SupportedTransformers;
import cz.martlin.jaxon.j2k.transformer.J2KBaseTransformer;
import cz.martlin.jaxon.jack.JackConverter;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.k2xml.KlaxonToXMLConverter;
import cz.martlin.jaxon.klaxon.KlaxonConverter;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;

/**
 * The Jaxon main entry. Implements conversion between objets and vairious xml
 * types.
 * 
 * @author martin
 *
 */
public class JaxonConverter {

	private final JackConverter jack;
	private final JackToKlaxonConverter j2k;
	private final KlaxonConverter klaxon;
	private final KlaxonToXMLConverter k2x;

	public JaxonConverter(Config config, J2KBaseTransformer transformer) {
		jack = new JackConverter(config);
		j2k = new JackToKlaxonConverter(config, transformer);
		klaxon = new KlaxonConverter(config);
		k2x = new KlaxonToXMLConverter(config);
	}

	public JaxonConverter(Config config) {
		this(config, findTransformer(config));
	}

	/**
	 * Converts given object to document.
	 * 
	 * @param object
	 * @return
	 * @throws JaxonException
	 */
	private Document objectToDocument(Object object) throws JaxonException {
		JackObject jackObject = jack.toJack(object);
		KlaxonObject klaxonEntry = j2k.jackToKlaxon(jackObject);
		Document document = klaxon.toDocument(klaxonEntry);
		return document;
	}

	/**
	 * Converts given document do object.
	 * 
	 * @param document
	 * @return
	 * @throws JaxonException
	 */
	private Object documentToObject(Document document) throws JaxonException {
		KlaxonObject klaxonEntry = klaxon.toKlaxon(document);
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

	private static J2KBaseTransformer findTransformer(J2KConfig config) {
		String baseTransformerName = config.getBaseTransformerName();
		String objectsTransformerName = config.getObjectsTransformerName();
		SupportedTransformers transformers = new SupportedTransformers();

		return transformers.find(config, baseTransformerName, objectsTransformerName);
	}
}
