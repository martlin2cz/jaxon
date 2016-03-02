package cz.martlin.jaxon.k2xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Implements storing and loading of xml documents into and from files/streams/strings etc..
 * @author martin
 *
 */
public class KlaxonToXMLImpl {

	private final KlaxonToXMLConfig config;

	public KlaxonToXMLImpl(KlaxonToXMLConfig config) {
		this.config = config;
	}

	/**
	 * Creates document builder.
	 * @return
	 * @throws KlaxonToXMLException
	 */
	private static DocumentBuilder createBuilder() throws KlaxonToXMLException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			return dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new KlaxonToXMLException(
					"Cannot initialize document builder", e);
		}

	}

	/**
	 * Creates documents transformer.
	 * @return
	 * @throws TransformerConfigurationException
	 * @throws TransformerFactoryConfigurationError
	 */
	private Transformer createTransformer()
			throws TransformerConfigurationException,
			TransformerFactoryConfigurationError {

		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();

		if (config.isIndent()) {
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount",
					Integer.toString(config.getIndentSize()));
		}
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");

		return transformer;
	}

	/**
	 * Creates empty document.
	 * 
	 * @return
	 * @throws KlaxonToXMLException
	 */
	public static Document createDocument() throws KlaxonToXMLException {
		DocumentBuilder builder;
		try {
			builder = createBuilder();
		} catch (KlaxonToXMLException e) {
			throw new KlaxonToXMLException("Cannot create document", e);
		}

		return builder.newDocument();
	}

	/**
	 * Parses document from xml file.
	 * 
	 * @param file
	 * @return
	 * @throws KlaxonToXMLException
	 */
	public Document parse(File file) throws KlaxonToXMLException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			return parse(fis);
		} catch (FileNotFoundException e) {
			throw new KlaxonToXMLException("File not found", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * Parses document from reader.
	 * 
	 * @param reader
	 * @return
	 * @throws KlaxonToXMLException
	 */
	public Document parse(Reader reader) throws KlaxonToXMLException {
		InputSource source = new InputSource(reader);
		return parseFromSource(source);
	}

	/**
	 * Parses document from input stream.
	 * 
	 * @param ins
	 * @return
	 * @throws KlaxonToXMLException
	 */
	public Document parse(InputStream ins) throws KlaxonToXMLException {
		InputSource source = new InputSource(ins);
		return parseFromSource(source);
	}

	/**
	 * Parses document from string.
	 * 
	 * @param string
	 * @return
	 * @throws KlaxonToXMLException
	 */
	public Document parse(String string) throws KlaxonToXMLException {
		StringReader reader = new StringReader(string);

		Document doc = parse(reader);

		reader.close();
		return doc;
	}

	/**
	 * Parses document from given source.
	 * 
	 * @param source
	 * @return
	 * @throws KlaxonToXMLException
	 */
	private Document parseFromSource(InputSource source)
			throws KlaxonToXMLException {
		DocumentBuilder builder;
		try {
			builder = createBuilder();

			return builder.parse(source);
		} catch (KlaxonToXMLException | SAXException | IOException e) {
			throw new KlaxonToXMLException("Cannot parse document", e);
		}
	}

	/**
	 * Exports document to file.
	 * 
	 * @param document
	 * @param file
	 * @throws KlaxonToXMLException
	 */
	public void export(Document document, File file)
			throws KlaxonToXMLException {
		StreamResult result = new StreamResult(file);
		exportToResult(document, result);
	}

	/**
	 * Exports document to writer.
	 * 
	 * @param document
	 * @param writer
	 * @throws KlaxonToXMLException
	 */
	public void export(Document document, Writer writer)
			throws KlaxonToXMLException {
		StreamResult result = new StreamResult(writer);
		exportToResult(document, result);
	}

	/**
	 * Exports document to output stream
	 * 
	 * @param document
	 * @param ous
	 * @throws KlaxonToXMLException
	 */
	public void export(Document document, OutputStream ous)
			throws KlaxonToXMLException {
		StreamResult result = new StreamResult(ous);
		exportToResult(document, result);
	}

	/**
	 * Exports document to string.
	 * 
	 * @param document
	 * @return
	 * @throws KlaxonToXMLException
	 */
	public String export(Document document) throws KlaxonToXMLException {
		StringWriter writer = new StringWriter();

		export(document, writer);

		return writer.getBuffer().toString();
	}

	/**
	 * Exportst document to given result.
	 * 
	 * @param document
	 * @param result
	 * @throws TransformerFactoryConfigurationError
	 * @throws KlaxonToXMLException
	 */
	private void exportToResult(Document document, StreamResult result)
			throws TransformerFactoryConfigurationError, KlaxonToXMLException {
		DOMSource source = new DOMSource(document);

		try {
			Transformer transformer = createTransformer();
			transformer.transform(source, result);
		} catch (TransformerException e) {
			throw new KlaxonToXMLException("Cannot export", e);
		}
	}

}
