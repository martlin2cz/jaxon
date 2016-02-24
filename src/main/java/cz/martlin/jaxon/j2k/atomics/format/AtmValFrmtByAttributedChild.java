package cz.martlin.jaxon.j2k.atomics.format;

import java.util.HashSet;
import java.util.Set;

import cz.martlin.jaxon.klaxon.data.KlaxonAttribute;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithChildren;
import cz.martlin.jaxon.klaxon.data.KlaxonEntry;

public class AtmValFrmtByAttributedChild implements AtomicValueFormat {

	private final String ATTR_NAME = "value";

	public AtmValFrmtByAttributedChild() {
	}

	@Override
	public KlaxonEntry toKlaxon(String name, String value) {

		Set<KlaxonAttribute> attributes = new HashSet<>();
		KlaxonAttribute attr = new KlaxonAttribute(ATTR_NAME, value);
		attributes.add(attr);

		return new KlaxonElemWithChildren(name, attributes);
	}

	@Override
	public String fromKlaxon(KlaxonEntry klaxon) {
		if (!(klaxon instanceof KlaxonElemWithChildren)) {
			return null;
		}
		
		KlaxonElemWithChildren wc = (KlaxonElemWithChildren) klaxon;

		if (!wc.isEmpty()) {
			throw new IllegalArgumentException("Element " + klaxon
					+ " has both value attribute and children.");
		}

		KlaxonAttribute attr = wc.getAttribute(ATTR_NAME);
		if (attr == null) {
			return null;
		} else {
			return attr.getValue();
		}
	}
}
