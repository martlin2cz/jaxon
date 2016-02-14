package cz.martlin.jaxon.exception;

public class KlaxonException extends JackException {

	private static final long serialVersionUID = 2152689722283040296L;

	public KlaxonException(String message, Throwable cause) {
		super(message, cause);
	}

	public KlaxonException(String message) {
		super(message);
	}

	public KlaxonException(Throwable cause) {
		super(cause);
	}

}
