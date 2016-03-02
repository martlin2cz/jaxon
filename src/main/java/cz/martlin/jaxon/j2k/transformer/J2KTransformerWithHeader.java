package cz.martlin.jaxon.j2k.transformer;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.jack.data.values.JackObject;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;

/**
 * Implements {@link J2KBaseTransformer} such that each root contains some
 * header and body element. Header should contain some metadata about object
 * (type, name, specification of used format and version ...) and the body
 * element the real data. This abstract class predefines various methods to
 * implement it.
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
	public KlaxonObject jackToKlaxonRoot(JackObject jack) throws JackToKlaxonException {

		KlaxonObject header = createHeaderElement(jack);
		KlaxonObject body = createBodyElement(jack);

		return createRootElement(header, body);

	}

	/**
	 * Somehow creates header klaxon for given jack. Header should contain
	 * metadata of object and serialization.
	 * 
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected abstract KlaxonObject createHeaderElement(JackObject jack) throws JackToKlaxonException;

	/**
	 * Creates body element. In default implentation simply uses klaxon created
	 * by {@link #transformer}.
	 * 
	 * @param jack
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected KlaxonObject createBodyElement(JackObject jack) throws JackToKlaxonException {

		try {
			return transformer.toKlaxon(jack);
		} catch (JackToKlaxonException e) {
			throw new JackToKlaxonException("Cannot create body element");
		}
	}

	/**
	 * Creates whole XML root klaxon. Has header in header, body in fields and
	 * name {@link #getRootElemName()}.
	 * 
	 * @param header
	 * @param body
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected KlaxonObject createRootElement(KlaxonObject header, KlaxonObject body) throws JackToKlaxonException {

		List<KlaxonValue> headers = new ArrayList<>();
		List<KlaxonValue> fields = new ArrayList<>();

		headers.add(header);
		fields.add(body);

		String name = getRootElemName();
		return new KlaxonObject(name, headers, fields);
	}

	/**
	 * Returns name of root element.
	 * 
	 * @return
	 */
	protected abstract String getRootElemName();

	@Override
	public JackObject klaxonRootToJack(KlaxonObject klaxonRoot) throws JackToKlaxonException {

		checkRoot(klaxonRoot);
		KlaxonObject header = getHeader(klaxonRoot);
		KlaxonObject body = getBody(klaxonRoot);

		return parseRoot(klaxonRoot, header, body);
	}

	/**
	 * Checks root element and throws exception if is somehow wrong. If ignores
	 * error, process should fail unexpectally later.
	 * 
	 * @param klaxonRoot
	 * @throws JackToKlaxonException
	 */
	protected abstract void checkRoot(KlaxonObject klaxonRoot) throws JackToKlaxonException;

	/**
	 * Returns header element of given root.
	 * 
	 * @param klaxonRoot
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected KlaxonObject getHeader(KlaxonObject klaxonRoot) throws JackToKlaxonException {
		List<KlaxonValue> children = klaxonRoot.list();
		return (KlaxonObject) children.get(0);
	}

	/**
	 * Returns body element of given root.
	 * 
	 * @param klaxonRoot
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected KlaxonObject getBody(KlaxonObject klaxonRoot) throws JackToKlaxonException {
		List<KlaxonValue> children = klaxonRoot.list();
		return (KlaxonObject) children.get(1);
	}

	/**
	 * Parses root element of given root with given header. In default impl
	 * simply uses {@link #transformer} to parse body.
	 * 
	 * @param klaxonRoot
	 * @param header
	 * @param body
	 * @return
	 * @throws JackToKlaxonException
	 */
	protected JackObject parseRoot(KlaxonObject klaxonRoot, KlaxonObject header, KlaxonObject body)
			throws JackToKlaxonException {

		try {
			return transformer.toJack(body);
		} catch (JackToKlaxonException e) {
			throw new JackToKlaxonException("Cannot parse body");
		}
	}
}
