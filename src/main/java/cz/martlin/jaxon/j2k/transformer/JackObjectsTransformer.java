package cz.martlin.jaxon.j2k.transformer;

import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;

public interface JackObjectsTransformer {

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
	public JackObject toJack(KlaxonAbstractElement klaxon)
			throws JackToKlaxonException;

	/**
	 * Converts jack object to klaxon element.
	 * 
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	public KlaxonAbstractElement toKlaxon(JackObject jack)
			throws JackToKlaxonException;

}
