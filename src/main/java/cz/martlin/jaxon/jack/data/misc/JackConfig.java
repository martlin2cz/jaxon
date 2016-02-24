package cz.martlin.jaxon.jack.data.misc;

/**
 * Configuration of jack process.
 * 
 * @author martin
 * 
 */
public interface JackConfig {

	/**
	 * Should jack ignore java fields which are final?
	 * 
	 * @return
	 */
	public boolean isIgnoringFinalFields();
}
