package cz.martlin.jaxon.abstracts;

import java.io.PrintStream;

/**
 * Represents object which can be printed to some {@link PrintStream}.
 * 
 * @author martin
 *
 */
public interface Printable {

	/**
	 * Prints this object into out padded by given padding.
	 * 
	 * @param padding
	 * @param out
	 */
	public void print(int padding, PrintStream out);
}
