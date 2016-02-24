package cz.martlin.jaxon.k2xml;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.w3c.dom.Document;

public class KlaxonToXMLConverter {

	private final KlaxonToXMLImpl impl;

	public KlaxonToXMLConverter(KlaxonToXMLConfig config) {
		impl = new KlaxonToXMLImpl(config);
	}

	public Document parse(File file) throws KlaxonToXMLException {
		return impl.parse(file);
	}

	public Document parse(Reader reader) throws KlaxonToXMLException {
		return impl.parse(reader);
	}

	public Document parse(InputStream ins) throws KlaxonToXMLException {
		return impl.parse(ins);
	}

	public Document parse(String string) throws KlaxonToXMLException {
		return impl.parse(string);
	}

	public void export(Document document, File file)
			throws KlaxonToXMLException {

		impl.export(document, file);
	}

	public void export(Document document, Writer writer)
			throws KlaxonToXMLException {

		impl.export(document, writer);
	}

	public void export(Document document, OutputStream ous)
			throws KlaxonToXMLException {

		impl.export(document, ous);
	}

	public String export(Document document) throws KlaxonToXMLException {

		return impl.export(document);
	}

}
