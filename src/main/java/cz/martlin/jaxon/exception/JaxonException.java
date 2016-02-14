package cz.martlin.jaxon.exception;

public class JaxonException extends Exception {

	private static final long serialVersionUID = 7407134272928615845L;

	public JaxonException(String message, Throwable cause) {
		super(message, cause);
	}

	public JaxonException(String message) {
		super(message);
	}

	public JaxonException(Throwable cause) {
		super(cause);
	}

}
