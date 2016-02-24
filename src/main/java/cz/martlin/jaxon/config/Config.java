package cz.martlin.jaxon.config;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.data.J2KConfig;
import cz.martlin.jaxon.jack.data.misc.JackConfig;
import cz.martlin.jaxon.k2xml.KlaxonToXMLConfig;
import cz.martlin.jaxon.klaxon.config.KlaxonConfig;

public class Config implements JackConfig, KlaxonConfig, J2KConfig,
		KlaxonToXMLConfig {

	@Override
	public boolean isIgnoringFinalFields() {
		return true;
	}

	@Override
	public AtmValFrmtToKlaxonStyle getAVFStyleToKlaxon() {
		return AtmValFrmtToKlaxonStyle.CHILD_WITH_TEXT_VALUE;
	}

	@Override
	public AtmValFrmtFromKlaxonStyle getAVFStyleFromKlaxon() {
		return AtmValFrmtFromKlaxonStyle.SAME_AS_TO_KLAXON;
	}

	@Override
	public boolean isIndent() {
		return true;
	}

	@Override
	public int getIndentSize() {
		return 2;
	}

}
