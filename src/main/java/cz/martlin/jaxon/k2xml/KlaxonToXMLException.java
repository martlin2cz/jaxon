package cz.martlin.jaxon.k2xml;

import cz.martlin.jaxon.jaxon.JaxonException;

/**
 * Exception thrown during the klaxon-to-xml process.
 * 
 * @author martin
 *
 */
public class KlaxonToXMLException extends JaxonException {

	private static final long serialVersionUID = -2269026680689770221L;

	public KlaxonToXMLException() {
	}

	public KlaxonToXMLException(String message) {
		super(message);
	}

	public KlaxonToXMLException(Throwable cause) {
		super(cause);
	}

	public KlaxonToXMLException(String message, Throwable cause) {
		super(message, cause);
	}

}
