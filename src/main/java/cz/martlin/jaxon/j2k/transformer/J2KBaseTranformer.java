package cz.martlin.jaxon.j2k.transformer;

import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;

/**
 * Base interface for all jack-to-klaxon transformers. Gives methods to convert
 * klaxon root element to jack object and back, nothing more specific.
 * 
 * @author martin
 * 
 */
public interface J2KBaseTranformer {
	public KlaxonAbstractElement jackToKlaxonRoot(JackObject jack)
			throws JackToKlaxonException;

	public JackObject klaxonRootToJack(KlaxonAbstractElement klaxonRoot)
			throws JackToKlaxonException;

}
