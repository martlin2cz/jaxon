package cz.martlin.jaxon.jaxon;

/**
 * Base exception thrown during all the jaxon process.
 * @author martin
 *
 */
public class JaxonException extends Exception {

	private static final long serialVersionUID = 1558665841301012816L;

	public JaxonException() {
	}

	public JaxonException(String message) {
		super(message);
	}

	public JaxonException(Throwable cause) {
		super(cause);
	}

	public JaxonException(String message, Throwable cause) {
		super(message, cause);
	}

}
