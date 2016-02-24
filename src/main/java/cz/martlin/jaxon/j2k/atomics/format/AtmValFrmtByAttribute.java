package cz.martlin.jaxon.j2k.atomics.format;

import cz.martlin.jaxon.klaxon.data.KlaxonAttribute;
import cz.martlin.jaxon.klaxon.data.KlaxonEntry;

public class AtmValFrmtByAttribute implements AtomicValueFormat {

	public AtmValFrmtByAttribute() {
	}

	@Override
	public KlaxonEntry toKlaxon(String name, String value) {
		return new KlaxonAttribute(name, value);
	}

	@Override
	public String fromKlaxon(KlaxonEntry klaxon) {
		if (!(klaxon instanceof KlaxonAttribute)) {
			return null;
		}

		KlaxonAttribute attr = (KlaxonAttribute) klaxon;
		return attr.getValue();
	}
}
