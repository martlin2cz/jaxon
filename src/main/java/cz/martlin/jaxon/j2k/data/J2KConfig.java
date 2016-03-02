package cz.martlin.jaxon.j2k.data;

import java.text.SimpleDateFormat;

import cz.martlin.jaxon.jack.data.misc.JackConfig;

/**
 * Jack-to-klaxon configuration.
 * 
 * @author martin
 *
 */
public interface J2KConfig extends JackConfig {

	/**
	 * Returns name of base transformer.
	 * 
	 * @return
	 */
	public String getBaseTransformerName();

	/**
	 * Returns name of objects transformer.
	 * 
	 * @return
	 */
	public String getObjectsTransformerName();

	/**
	 * Returns format of date to use.
	 * 
	 * @return
	 */
	public SimpleDateFormat getDateFormat();
}
