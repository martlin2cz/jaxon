package cz.martlin.jaxon.j2k.atomics.format;

import cz.martlin.jaxon.klaxon.data.KlaxonElemWithValue;
import cz.martlin.jaxon.klaxon.data.KlaxonEntry;

public class AtmValFrmtByValuedChild implements AtomicValueFormat {

	public AtmValFrmtByValuedChild() {
	}

	@Override
	public KlaxonEntry toKlaxon(String name, String value) {

		return new KlaxonElemWithValue(name, value);
	}

	@Override
	public String fromKlaxon(KlaxonEntry klaxon) {
		if (!(klaxon instanceof KlaxonElemWithValue)) {
			return null;
		}

		KlaxonElemWithValue wv = (KlaxonElemWithValue) klaxon;
		return wv.getValue();
	}

}
