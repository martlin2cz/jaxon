package cz.martlin.jaxon.j2k.data;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.jack.data.misc.JackConfig;

public interface J2KConfig extends JackConfig {

	public AtmValFrmtToKlaxonStyle getAVFStyleToKlaxon();

	public AtmValFrmtFromKlaxonStyle getAVFStyleFromKlaxon();
}
