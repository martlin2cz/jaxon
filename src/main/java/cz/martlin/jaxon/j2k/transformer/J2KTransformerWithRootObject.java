package cz.martlin.jaxon.j2k.transformer;

import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;

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
	public KlaxonAbstractElement jackToKlaxonRoot(JackObject jack) throws JackToKlaxonException {
		KlaxonAbstractElement klaxon = transformer.toKlaxon(jack);

		return addMetadata(klaxon, jack);
	}

	protected abstract KlaxonAbstractElement addMetadata(KlaxonAbstractElement klaxon, JackObject jack) throws JackToKlaxonException;

	@Override
	public JackObject klaxonRootToJack(KlaxonAbstractElement klaxonRoot) throws JackToKlaxonException {
		check(klaxonRoot);
		return parse(klaxonRoot);
	}

	protected abstract void check(KlaxonAbstractElement klaxonRoot) throws JackToKlaxonException;

	protected abstract JackObject parse(KlaxonAbstractElement klaxonRoot) throws JackToKlaxonException;

}
