package cz.martlin.jaxon.config;

import java.text.SimpleDateFormat;

import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.j2k.transformers.FryJ2KTransformerImpl;
import cz.martlin.jaxon.j2k.transformers.J2KTransformWithRootObjSimpleImpl;
import cz.martlin.jaxon.jack.data.misc.JackConfig;
import cz.martlin.jaxon.k2xml.KlaxonToXMLConfig;
import cz.martlin.jaxon.klaxon.config.K2DocFormat;
import cz.martlin.jaxon.klaxon.config.KlaxonConfig;

/**
 * Jaxon configuration. Just simply implements particular configuration
 * interfaces as a bean.
 * 
 * @see JackConfig
 * @see KlaxonConfig
 * @see J2KConfig
 * @see KlaxonToXMLConfig
 * 
 * @author martin
 *
 */
public class Config implements JackConfig, KlaxonConfig, J2KConfig, KlaxonToXMLConfig {

	private boolean ignoringFinalFields = true;
	private boolean indent = true;
	private int indentSize = 2;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	private K2DocFormat format = K2DocFormat.ATTRS_FOR_HEADERS;

	private String baseTransformerName = J2KTransformWithRootObjSimpleImpl.NAME;
	private String objectsTransformerName = FryJ2KTransformerImpl.NAME;

	public Config() {
	}

	@Override
	public boolean isIgnoringFinalFields() {
		return ignoringFinalFields;
	}

	@Override
	public boolean isIndent() {
		return indent;
	}

	@Override
	public int getIndentSize() {
		return indentSize;
	}

	@Override
	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	@Override
	public K2DocFormat getFormat() {
		return format;
	}

	public void setFormat(K2DocFormat format) {
		this.format = format;
	}

	@Override
	public String getBaseTransformerName() {
		return baseTransformerName;
	}

	public void setBaseTransformerName(String baseTransformerName) {
		this.baseTransformerName = baseTransformerName;
	}

	@Override
	public String getObjectsTransformerName() {
		return objectsTransformerName;
	}

	public void setObjectsTransformerName(String objectsTransformerName) {
		this.objectsTransformerName = objectsTransformerName;
	}

	@Override
	public String toString() {
		return "Config [ignoringFinalFields=" + ignoringFinalFields + ", indent=" + indent + ", indentSize="
				+ indentSize + ", dateFormat=" + dateFormat + ", format=" + format + ", baseTransformerName="
				+ baseTransformerName + ", objectsTransformerName=" + objectsTransformerName + "]";
	}

}
