package cz.martlin.jaxon.j2k;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.j2k.transformer.J2KTransformerWithHeader;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.j2k.transformers.FryJ2KTransformerImpl;
import cz.martlin.jaxon.j2k.transformers.J2KTransformWithHeaderVerboseImpl;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;

public class JackToKlaxonConverter {

	J2KTransformerWithHeader transformer;
	private JackObjectsTransformer objectsTransformer;

	public JackToKlaxonConverter(J2KConfig config) {
		objectsTransformer = new FryJ2KTransformerImpl(config);
		this.transformer = new J2KTransformWithHeaderVerboseImpl(
				objectsTransformer);
	}

	public J2KTransformerWithHeader getTransformer() {
		return transformer;
	}

	public JackObjectsTransformer getObjectsTransformer() {
		return objectsTransformer;
	}

	public JackObject klaxonToJack(KlaxonAbstractElement klaxon)
			throws JackToKlaxonException {

		try {
			return transformer.klaxonRootToJack(klaxon);
		} catch (JackToKlaxonException e) {
			throw new JackToKlaxonException("Cannot convert Klaxon to Jack", e);
		}
	}

	public KlaxonAbstractElement jackToKlaxon(JackObject jack)
			throws JackToKlaxonException {

		try {
			return transformer.jackToKlaxonRoot(jack);
		} catch (JackToKlaxonException e) {
			throw new JackToKlaxonException("Cannot convert Jack to Klaxon", e);
		}
	}

}
