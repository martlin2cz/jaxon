package cz.martlin.jaxon.j2k.translators;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.j2k.translator.AtomicValueTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackAtomicValue;
import cz.martlin.jaxon.jack.data.values.JackNullValue;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;

/**
 * Represents translator for translating {@link Point} instances.
 * 
 * @author martin
 *
 */
public class PointTranslator extends AtomicValueTranslator<Point> {

	private static final String X_ATTR_NAME = "x";
	private static final String Y_ATTR_NAME = "y";

	public PointTranslator() {
	}

	@Override
	public Class<Point> supportedType() {
		return Point.class;
	}

	@Override
	public KlaxonObject toKlaxon(String name, JackValueType type, JackValue jack) throws JackToKlaxonException {
		if (jack instanceof JackNullValue) {
			return null;
		}

		if (!(jack instanceof JackAtomicValue)) {
			Exception e = new IllegalArgumentException("Not an atomic value");
			throw new JackToKlaxonException("Bad format", e);
		}

		JackAtomicValue atomic = (JackAtomicValue) jack;
		Point point = (Point) atomic.getValue();

		List<KlaxonValue> attributes = new ArrayList<>();

		String xStr = Double.toString(point.getX());
		KlaxonStringValue xAtrr = new KlaxonStringValue(X_ATTR_NAME, xStr);
		attributes.add(xAtrr);

		String yStr = Double.toString(point.getX());
		KlaxonStringValue yAtrr = new KlaxonStringValue(Y_ATTR_NAME, yStr);
		attributes.add(yAtrr);

		return new KlaxonObject(name, attributes);
	}

	@Override
	public JackValue toJack(JackValueType type, KlaxonValue klaxon) throws JackToKlaxonException {

		if (!(klaxon instanceof KlaxonObject)) {
			Exception e = new IllegalArgumentException("Not an klaxon object");
			throw new JackToKlaxonException("Bad format", e);
		}

		KlaxonObject object = (KlaxonObject) klaxon;

		int xVal = parseIntFrom(object, X_ATTR_NAME);
		int yVal = parseIntFrom(object, Y_ATTR_NAME);

		Point point = new Point(xVal, yVal);

		return new JackAtomicValue(point);

	}

	/**
	 * Tries to parse int value from given klaxon's field of given name.
	 * 
	 * @param klaxon
	 * @param name
	 * @return
	 * @throws JackToKlaxonException
	 */
	// TODO move to SomeJ2KUtils class
	private int parseIntFrom(KlaxonObject klaxon, String name) throws JackToKlaxonException {
		try {
			KlaxonValue attr = klaxon.findFirst(name);

			if (attr == null) {
				throw new IllegalArgumentException("Missing attribute " + name);
			}

			if (!(attr instanceof KlaxonStringValue)) {
				throw new IllegalArgumentException("Bad attribute format");
			}

			KlaxonStringValue string = (KlaxonStringValue) attr;
			String value = string.getValue();

			return Integer.parseInt(value);
		} catch (Exception e) {
			throw new JackToKlaxonException("Cannot parse value of " + name, e);
		}
	}

}
