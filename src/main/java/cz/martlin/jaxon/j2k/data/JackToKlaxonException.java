package cz.martlin.jaxon.j2k.data;

import cz.martlin.jaxon.jaxon.JaxonException;

/**
 * Exception thrown during jack-to-klaxon process.
 * 
 * @author martin
 *
 */
public class JackToKlaxonException extends JaxonException {
	private static final long serialVersionUID = -4087587952225591961L;

	public JackToKlaxonException(String message, Throwable cause) {
		super(message, cause);
	}

	public JackToKlaxonException(String message) {
		super(message);
	}

	public JackToKlaxonException(Throwable cause) {
		super(cause);
	}

}
