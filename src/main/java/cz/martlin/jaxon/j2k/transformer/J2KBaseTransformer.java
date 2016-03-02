package cz.martlin.jaxon.j2k.transformer;

import cz.martlin.jaxon.abstracts.HasName;
import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;

/**
 * Base interface for all jack-to-klaxon transformers. Gives methods to convert
 * klaxon root element to jack object and back, nothing more specific.
 * 
 * @author martin
 * 
 */
public interface J2KBaseTransformer extends HasName {
	/**
	 * Returns (unique) name of given transformer (used for simple finding by
	 * configuration). Simple, but not user friendly, is to use class name.
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Converts given jack object to klaxon object.
	 * 
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	public KlaxonObject jackToKlaxonRoot(JackObject jack) throws JackToKlaxonException;

	/**
	 * Converts given klaxon object (representing root of XML file) to jack
	 * object.
	 * 
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	public JackObject klaxonRootToJack(KlaxonObject klaxon) throws JackToKlaxonException;

}
