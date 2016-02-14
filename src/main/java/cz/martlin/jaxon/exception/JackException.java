package cz.martlin.jaxon.exception;

public class JackException extends JaxonException {

	private static final long serialVersionUID = 3920498300547722699L;

	public JackException(String message, Throwable cause) {
		super(message, cause);
	}

	public JackException(String message) {
		super(message);
	}

	public JackException(Throwable cause) {
		super(cause);
	}

}
