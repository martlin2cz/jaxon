package cz.martlin.jaxon.testings.tuples;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jaxon.klaxon.KlaxonException;
import cz.martlin.jaxon.klaxon.config.K2DocFormat;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;

/**
 * Represents tuple of klaxon test instances. Contains testing klaxon object and
 * its corresponding xml element. Also provides method to make from element
 * whole document.
 * 
 * @author martin
 *
 */
public interface KlaxonTestTuple {

	/**
	 * Creates testing klaxon object.
	 * 
	 * @return
	 */
	public abstract KlaxonObject createKlaxon();

	/**
	 * Creates xml element.
	 * 
	 * @param document
	 * @param format
	 * @return
	 */
	public abstract Element createElement(Document document, K2DocFormat format);

	/**
	 * With help of {@link #createElement(Document, K2DocFormat)} should create
	 * document.
	 * 
	 * @param format
	 * @return
	 * @throws KlaxonException
	 */
	public abstract Document createDocument(K2DocFormat format) throws KlaxonException;

}
