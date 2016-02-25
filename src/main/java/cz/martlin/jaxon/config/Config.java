package cz.martlin.jaxon.config;

import java.text.SimpleDateFormat;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.jack.data.misc.JackConfig;
import cz.martlin.jaxon.k2xml.KlaxonToXMLConfig;
import cz.martlin.jaxon.klaxon.config.KlaxonConfig;

public class Config implements JackConfig, KlaxonConfig, J2KConfig, KlaxonToXMLConfig {

	private boolean ignoringFinalFields = true;
	private AtmValFrmtToKlaxonStyle aVFStyleToKlaxon = AtmValFrmtToKlaxonStyle.CHILD_WITH_TEXT_VALUE;
	private AtmValFrmtFromKlaxonStyle aVFStyleFromKlaxon = AtmValFrmtFromKlaxonStyle.SAME_AS_TO_KLAXON;
	private boolean indent = true;
	private int indentSize = 2;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

	public Config() {
	}

	@Override
	public boolean isIgnoringFinalFields() {
		return ignoringFinalFields;
	}

	@Override
	public AtmValFrmtToKlaxonStyle getAVFStyleToKlaxon() {
		return aVFStyleToKlaxon;
	}

	@Override
	public AtmValFrmtFromKlaxonStyle getAVFStyleFromKlaxon() {
		return aVFStyleFromKlaxon;
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

}
