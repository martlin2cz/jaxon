package cz.martlin.jaxon.j2k.translators;

import java.awt.Point;
import java.util.LinkedHashSet;
import java.util.Set;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.j2k.translator.AtomicValueTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackAtomicValue;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.data.KlaxonAbstractElement;
import cz.martlin.jaxon.klaxon.data.KlaxonAttribute;
import cz.martlin.jaxon.klaxon.data.KlaxonElemWithChildren;
import cz.martlin.jaxon.klaxon.data.KlaxonEntry;

public class PointTranslator extends AtomicValueTranslator<Point> {

	private static final String X_ATTR_NAME = "x";
	private static final String Y_ATTR_NAME = "y";

	public PointTranslator(AtmValFrmtToKlaxonStyle toKlaxonStyle, AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {
		// TODO formatting
	}

	@Override
	public Class<Point> supportedType() {
		return Point.class;
	}

	@Override
	public KlaxonEntry toKlaxon(String name, JackValueType type, JackValue jack) throws JackToKlaxonException {
		if (!(jack instanceof JackAtomicValue)) {
			return null;
		}

		JackAtomicValue atomic = (JackAtomicValue) jack;
		Point point = (Point) atomic.getValue();

		Set<KlaxonAttribute> attributes = new LinkedHashSet<>();

		String xStr = Double.toString(point.getX());
		KlaxonAttribute xAtrr = new KlaxonAttribute(X_ATTR_NAME, xStr);
		attributes.add(xAtrr);

		String yStr = Double.toString(point.getX());
		KlaxonAttribute yAtrr = new KlaxonAttribute(Y_ATTR_NAME, yStr);
		attributes.add(yAtrr);

		return new KlaxonElemWithChildren(name, attributes);
	}

	@Override
	public JackValue toJack(JackValueType type, KlaxonEntry klaxon) throws JackToKlaxonException {
		try {
			KlaxonAbstractElement elem = (KlaxonAbstractElement) klaxon;

			KlaxonAttribute xAttr = elem.getAttribute(X_ATTR_NAME);
			KlaxonAttribute yAttr = elem.getAttribute(Y_ATTR_NAME);

			if (xAttr == null || yAttr == null) {
				throw new IllegalArgumentException("Missing attributes " + X_ATTR_NAME + " or " + Y_ATTR_NAME);
			}

			String xStr = xAttr.getValue();
			String yStr = yAttr.getValue();

			int xVal = Integer.parseInt(xStr);
			int yVal = Integer.parseInt(yStr);

			Point point = new Point(xVal, yVal);
			return new JackAtomicValue(point);

		} catch (Exception e) {
			throw new JackToKlaxonException("Cannot parse point", e);
		}
	}

}
