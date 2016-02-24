package cz.martlin.jaxon.klaxon.data;

import cz.martlin.jaxon.abstracts.HasName;
import cz.martlin.jaxon.abstracts.Printable;

public abstract class KlaxonEntry implements HasName, Printable,
		Comparable<KlaxonEntry> {

	private final String name;

	public KlaxonEntry(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(KlaxonEntry o) {
		int names = this.getName().compareTo(o.getName());
		if (names != 0) {
			return names;
		}

		return 0;
	}

}
