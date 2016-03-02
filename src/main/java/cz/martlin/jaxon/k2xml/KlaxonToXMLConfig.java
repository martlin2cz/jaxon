package cz.martlin.jaxon.k2xml;

/**
 * Confugration of klaxon-to-xml process.
 * @author martin
 *
 */
public interface KlaxonToXMLConfig {
	
	/**
	 * Should be resulting generated xml indented?
	 * @return
	 */
	public boolean isIndent();

	/**
	 * Returns size of indent of resulting xml.
	 * @return
	 */
	public int getIndentSize();

}
