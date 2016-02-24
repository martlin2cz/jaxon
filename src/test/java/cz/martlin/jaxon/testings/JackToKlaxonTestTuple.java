package cz.martlin.jaxon.testings;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;

public interface JackToKlaxonTestTuple {

	public abstract JackObject createJack(J2KConfig config);

	// TODO create root
	// public abstract KlaxonAbstractElement createKlaxon(J2KConfig config,
	// XX_AbstractObjectTransformer transformer);

	KlaxonAbstractElement createKlaxon(J2KConfig config,
			JackObjectsTransformer transformer);
}
