package cz.martlin.jaxon.k2xml;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.w3c.dom.Document;

/**
 * Converts klaxon to xml in various "formats" (file, stream, string, ...).
 * 
 * @author martin
 *
 */
public class KlaxonToXMLConverter {

	private final KlaxonToXMLImpl impl;

	public KlaxonToXMLConverter(KlaxonToXMLConfig config) {
		impl = new KlaxonToXMLImpl(config);
	}

	/**
	 * Parses document from file.
	 * 
	 * @param file
	 * @return
	 * @throws KlaxonToXMLException
	 */
	public Document parse(File file) throws KlaxonToXMLException {
		return impl.parse(file);
	}

	/**
	 * Parses document from reader.
	 * 
	 * @param reader
	 * @return
	 * @throws KlaxonToXMLException
	 */
	public Document parse(Reader reader) throws KlaxonToXMLException {
		return impl.parse(reader);
	}

	/**
	 * Parses document from input stream.
	 * 
	 * @param ins
	 * @return
	 * @throws KlaxonToXMLException
	 */
	public Document parse(InputStream ins) throws KlaxonToXMLException {
		return impl.parse(ins);
	}

	/**
	 * Parses document from string.
	 * 
	 * @param string
	 * @return
	 * @throws KlaxonToXMLException
	 */
	public Document parse(String string) throws KlaxonToXMLException {
		return impl.parse(string);
	}

	/**
	 * Exports document to file.
	 * 
	 * @param document
	 * @param file
	 * @throws KlaxonToXMLException
	 */
	public void export(Document document, File file) throws KlaxonToXMLException {

		impl.export(document, file);
	}

	/**
	 * Exports document to writer.
	 * 
	 * @param document
	 * @param writer
	 * @throws KlaxonToXMLException
	 */
	public void export(Document document, Writer writer) throws KlaxonToXMLException {

		impl.export(document, writer);
	}

	/**
	 * Exports document to output stream.
	 * 
	 * @param document
	 * @param ous
	 * @throws KlaxonToXMLException
	 */
	public void export(Document document, OutputStream ous) throws KlaxonToXMLException {

		impl.export(document, ous);
	}

	/**
	 * Exports document to string.
	 * 
	 * @param document
	 * @return
	 * @throws KlaxonToXMLException
	 */
	public String export(Document document) throws KlaxonToXMLException {

		return impl.export(document);
	}

}
