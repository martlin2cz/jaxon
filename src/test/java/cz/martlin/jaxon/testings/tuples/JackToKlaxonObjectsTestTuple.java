package cz.martlin.jaxon.testings.tuples;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;

/**
 * Creates tuple of test instances of jack-to-klaxon process.
 * 
 * @author martin
 *
 */
public interface JackToKlaxonObjectsTestTuple {

	/**
	 * Creates jack object.
	 * 
	 * @return
	 */
	public abstract JackObject createJack();

	/**
	 * Creates klaxon object by given transformer.
	 * 
	 * @param config
	 * @param transformer
	 * @return
	 */
	KlaxonObject createKlaxon(J2KConfig config, JackObjectsTransformer transformer);
}
