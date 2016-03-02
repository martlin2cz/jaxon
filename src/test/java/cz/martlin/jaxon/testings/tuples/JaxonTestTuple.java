package cz.martlin.jaxon.testings.tuples;

import cz.martlin.jaxon.jaxon.JaxonSerializable;

/**
 * Cretes test instance for Jaxon.
 * 
 * @author martin
 *
 * @param <T>
 */
public interface JaxonTestTuple<T extends JaxonSerializable> {

	/**
	 * Create object to convert.
	 * 
	 * @return
	 */
	public T createObject();
}
