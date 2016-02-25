package cz.martlin.jaxon.j2k.transformer;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithChildren;

/**
 * Implements {@link J2KBaseTransformer} such that each root contains some header
 * and body element. Header should contain some metadata about object (type,
 * name, specification of used format and version ...) and the body element the
 * real data. This abstract class predefines various methods to implement it.
 * 
 * @author martin
 * 
 */
public abstract class J2KTransformerWithHeader implements J2KBaseTransformer {

	protected final JackObjectsTransformer transformer;

	public J2KTransformerWithHeader(JackObjectsTransformer transformer) {
		this.transformer = transformer;
	}

	@Override
	public KlaxonAbstractElement jackToKlaxonRoot(JackObject jack)
			throws JackToKlaxonException {

		KlaxonAbstractElement header = createHeaderElement(jack);
		KlaxonAbstractElement body = createBodyElement(jack);

		return createRootElement(header, body);

	}

	protected abstract KlaxonAbstractElement createHeaderElement(JackObject jack)
			throws JackToKlaxonException;

	protected KlaxonAbstractElement createBodyElement(JackObject jack)
			throws JackToKlaxonException {

		try {
			return transformer.toKlaxon(jack);
		} catch (JackToKlaxonException e) {
			throw new JackToKlaxonException("Cannot create body element");
		}
	}

	protected abstract String getBodyElemName(JackObject jack) throws JackToKlaxonException;

	protected KlaxonAbstractElement createRootElement(
			KlaxonAbstractElement header, KlaxonAbstractElement body)
			throws JackToKlaxonException {

		List<KlaxonAbstractElement> children = new ArrayList<>();

		children.add(header);
		children.add(body);

		String name = getRootElemName();
		return new KlaxonElemWithChildren(name, children);
	}

	protected abstract String getRootElemName();

	@Override
	public JackObject klaxonRootToJack(KlaxonAbstractElement klaxonRoot)
			throws JackToKlaxonException {

		checkRoot(klaxonRoot);
		KlaxonAbstractElement header = getHeader(klaxonRoot);
		KlaxonAbstractElement body = getBody(klaxonRoot);

		return parseRoot(klaxonRoot, header, body);
	}

	protected abstract void checkRoot(KlaxonAbstractElement klaxonRoot)
			throws JackToKlaxonException;

	protected KlaxonAbstractElement getHeader(KlaxonAbstractElement klaxonRoot)
			throws JackToKlaxonException {

		KlaxonElemWithChildren kewc = (KlaxonElemWithChildren) klaxonRoot;
		return kewc.getChildren().get(0);
	}

	protected KlaxonAbstractElement getBody(KlaxonAbstractElement klaxonRoot)
			throws JackToKlaxonException {

		KlaxonElemWithChildren kewc = (KlaxonElemWithChildren) klaxonRoot;
		return kewc.getChildren().get(1);
	}

	protected JackObject parseRoot(KlaxonAbstractElement klaxonRoot,
			KlaxonAbstractElement header, KlaxonAbstractElement body)
			throws JackToKlaxonException {

		try {
			return transformer.toJack(body);
		} catch (JackToKlaxonException e) {
			throw new JackToKlaxonException("Cannot parse body");
		}
	}
}
