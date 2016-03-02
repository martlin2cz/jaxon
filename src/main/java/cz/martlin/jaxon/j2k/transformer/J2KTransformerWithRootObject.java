package cz.martlin.jaxon.j2k.transformer;

import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;

/**
 * Represents simple J2K transformer such that the object's metadata is stored
 * directly into root element representing object.
 * 
 * @author martin
 *
 */
public abstract class J2KTransformerWithRootObject implements J2KBaseTransformer {

	protected final JackObjectsTransformer transformer;

	public J2KTransformerWithRootObject(JackObjectsTransformer transformer) {
		super();
		this.transformer = transformer;
	}

	@Override
	public KlaxonObject jackToKlaxonRoot(JackObject jack) throws JackToKlaxonException {
		KlaxonObject klaxon = transformer.toKlaxon(jack);

		return addMetadata(klaxon, jack);
	}

	/**
	 * Adds somehow metadata (probably as header fields) of given jack to given
	 * klaxon (can create new).
	 * 
	 * @param klaxon
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected abstract KlaxonObject addMetadata(KlaxonObject klaxon, JackObject jack) throws JackToKlaxonException;

	@Override
	public JackObject klaxonRootToJack(KlaxonObject klaxonRoot) throws JackToKlaxonException {
		check(klaxonRoot);
		return parse(klaxonRoot);
	}

	/**
	 * Somehow check given root.
	 * 
	 * @param klaxonRoot
	 * @throws JackToKlaxonException
	 */
	protected abstract void check(KlaxonObject klaxonRoot) throws JackToKlaxonException;

	/**
	 * Parses given root element.
	 * 
	 * @param klaxonRoot
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected abstract JackObject parse(KlaxonObject klaxonRoot) throws JackToKlaxonException;

}
