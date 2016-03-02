package cz.martlin.jaxon.j2k;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.j2k.transformer.J2KBaseTransformer;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;

/**
 * Converts jack objects to klaxon object.s
 * 
 * @author martin
 *
 */
public class JackToKlaxonConverter {

	private final J2KBaseTransformer transformer;

	public JackToKlaxonConverter(J2KConfig config, J2KBaseTransformer transformer) {
		this.transformer = transformer;
	}

	/**
	 * Converts given klaxon to jack.
	 * 
	 * @param klaxon
	 * @return
	 * @throws JackToKlaxonException
	 */
	public JackObject klaxonToJack(KlaxonObject klaxon) throws JackToKlaxonException {

		try {
			return transformer.klaxonRootToJack(klaxon);
		} catch (JackToKlaxonException e) {
			throw new JackToKlaxonException("Cannot convert Klaxon to Jack", e);
		}
	}

	/**
	 * Converts given jack to klaxon.
	 * 
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	public KlaxonObject jackToKlaxon(JackObject jack) throws JackToKlaxonException {

		try {
			return transformer.jackToKlaxonRoot(jack);
		} catch (JackToKlaxonException e) {
			throw new JackToKlaxonException("Cannot convert Jack to Klaxon", e);
		}
	}

}
