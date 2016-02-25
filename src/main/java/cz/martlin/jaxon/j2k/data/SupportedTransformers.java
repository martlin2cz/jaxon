package cz.martlin.jaxon.j2k.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cz.martlin.jaxon.j2k.transformer.J2KBaseTransformer;
import cz.martlin.jaxon.j2k.transformer.JackObjectsTransformer;
import cz.martlin.jaxon.j2k.transformers.FryJ2KTransformerImpl;
import cz.martlin.jaxon.j2k.transformers.HermesSimpleJ2KTransformerImpl;
import cz.martlin.jaxon.j2k.transformers.J2KTransformWithHeaderVerboseImpl;
import cz.martlin.jaxon.j2k.transformers.J2KTransformWithRootObjSimpleImpl;

/**
 * Lists currently implemented and ready-to-use combinations of
 * {@link J2KBaseTransformer}s and {@link JackObjectsTransformer}s.
 * 
 * @author martin
 *
 */
public class SupportedTransformers {

	public SupportedTransformers() {
	}

	/**
	 * For given names tries to find, create and return base transformer.
	 * 
	 * @param config
	 * @param baseTransformerName
	 * @param objectsTransformerName
	 * @return transformer or null if no such (base nor objects) not found
	 */
	public J2KBaseTransformer find(J2KConfig config, String baseTransformerName, String objectsTransformerName) {
		JackObjectsTransformer objects = find(config, objectsTransformerName);
		if (objects == null) {
			return null;
		}

		J2KBaseTransformer base = find(baseTransformerName, objects);
		if (base == null) {
			return null;
		}

		return base;
	}

	/**
	 * For given base transformer name and objects transformer tries to find and
	 * create one.
	 * 
	 * @param baseTransformerName
	 * @param objectsTransformer
	 * @return
	 */
	public J2KBaseTransformer find(String baseTransformerName, JackObjectsTransformer objectsTransformer) {
		Map<String, J2KBaseTransformer> bases = initBaseTransformers(objectsTransformer);
		J2KBaseTransformer base = bases.get(baseTransformerName);
		return base;
	}

	/**
	 * For given objects transformer name tries to find and create one.
	 * 
	 * @param config
	 * @param objectsTransformerName
	 * @return
	 */
	public JackObjectsTransformer find(J2KConfig config, String objectsTransformerName) {
		Map<String, JackObjectsTransformer> objects = initObjectsTransformers(config);
		JackObjectsTransformer transformer = objects.get(objectsTransformerName);
		return transformer;
	}

	/**
	 * Lists all supported names of objects transformers.
	 * 
	 * @return
	 */
	public Collection<String> listObjectsTransformers() {
		return initObjectsTransformers(null).keySet();
	}

	/**
	 * Lists all supported names of base transformers.
	 * 
	 * @return
	 */
	public Collection<String> listBaseTransformers() {
		return initBaseTransformers(null).keySet();
	}

	/**
	 * Initializes list of objects transformers.
	 * 
	 * @param config
	 * @return
	 */
	private Map<String, JackObjectsTransformer> initObjectsTransformers(J2KConfig config) {
		Map<String, JackObjectsTransformer> transformers = new HashMap<>();

		JackObjectsTransformer fry = new FryJ2KTransformerImpl(config);
		transformers.put(fry.getName(), fry);

		JackObjectsTransformer hermes = new HermesSimpleJ2KTransformerImpl(config);
		transformers.put(hermes.getName(), hermes);

		
		return transformers;
	}

	/**
	 * Initializes list of objects transformers.
	 * 
	 * @param transformer
	 * @return
	 */
	private Map<String, J2KBaseTransformer> initBaseTransformers(JackObjectsTransformer transformer) {
		Map<String, J2KBaseTransformer> transformers = new HashMap<>();

		J2KBaseTransformer wh = new J2KTransformWithHeaderVerboseImpl(transformer);
		transformers.put(wh.getName(), wh);

		J2KBaseTransformer wro = new J2KTransformWithRootObjSimpleImpl(transformer);
		transformers.put(wro.getName(), wro);

		return transformers;
	}

}
