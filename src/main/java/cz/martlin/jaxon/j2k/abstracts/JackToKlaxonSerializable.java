package cz.martlin.jaxon.j2k.abstracts;

/**
 * Represents object which can be processed by j2k.
 * 
 * @author martin
 *
 */
public interface JackToKlaxonSerializable {

	/**
	 * Returns some description of current object. Optional, can return null.
	 * 
	 * @return
	 */
	public abstract String jaxonDescription();
}
