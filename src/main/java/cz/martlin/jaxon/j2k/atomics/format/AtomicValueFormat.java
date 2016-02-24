package cz.martlin.jaxon.j2k.atomics.format;

import cz.martlin.jaxon.klaxon.data.KlaxonEntry;

public interface AtomicValueFormat {

	public KlaxonEntry toKlaxon(String name, String value);

	public String fromKlaxon(KlaxonEntry klaxon);

}
