package cz.martlin.jaxon.j2k.transformer;

import cz.martlin.jaxon.abstracts.HasName;
import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;

/**
 * Represents abstract transformer between JackObjects and KlaxonObjects.
 * 
 * @author martin
 *
 */
public interface JackObjectsTransformer extends HasName {

	/**
	 * Returns name of this transforming technique (for check)
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Converts klaxon element to jack object
	 * 
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	public JackObject toJack(KlaxonObject klaxon) throws JackToKlaxonException;

	/**
	 * Converts jack object to klaxon element.
	 * 
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	public KlaxonObject toKlaxon(JackObject jack) throws JackToKlaxonException;

}
