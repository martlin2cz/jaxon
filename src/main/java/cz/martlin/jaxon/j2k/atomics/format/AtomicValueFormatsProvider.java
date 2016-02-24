package cz.martlin.jaxon.j2k.atomics.format;

import java.util.ArrayList;
import java.util.List;

public class AtomicValueFormatsProvider {

	private static final AtomicValueFormat attributed = new AtmValFrmtByAttribute();
	private static final AtomicValueFormat childElem = new AtmValFrmtByValuedChild();
	private static final AtomicValueFormat childWithAttr = new AtmValFrmtByAttributedChild();
	private static final List<AtomicValueFormat> allFormats = initializeAll();

	private static List<AtomicValueFormat> initializeAll() {
		List<AtomicValueFormat> alls = new ArrayList<>();

		alls.add(attributed);
		alls.add(childElem);
		alls.add(childWithAttr);

		return alls;
	}

	public static List<AtomicValueFormat> allSupportedFormats() {
		return allFormats;
	}

	public static AtomicValueFormat formatToKlaxon(AtmValFrmtToKlaxonStyle style) {

		switch (style) {
		case ATTRIBUTE:
			return attributed;
		case CHILD_WITH_ATTRIBUTE:
			return childWithAttr;
		case CHILD_WITH_TEXT_VALUE:
			return childElem;
		default:
			throw new IllegalArgumentException("Unsupported style " + style);
		}
	}

	public static List<AtomicValueFormat> formatFromKlaxon(
			AtmValFrmtFromKlaxonStyle style, AtomicValueFormat toKlaxonFormat) {

		switch (style) {
		case SAME_AS_TO_KLAXON:
			return asList(toKlaxonFormat);
		case TRY_ALL_AVAIBLE:
			return allFormats;
		default:
			throw new IllegalArgumentException("Unsupported style " + style);
		}
	}

	private static List<AtomicValueFormat> asList(
			AtomicValueFormat toKlaxonFormat) {

		List<AtomicValueFormat> list = new ArrayList<>();
		list.add(toKlaxonFormat);
		return list;
	}
}
