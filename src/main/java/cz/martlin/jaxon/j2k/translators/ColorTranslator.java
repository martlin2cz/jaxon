package cz.martlin.jaxon.j2k.translators;

import java.awt.Color;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.serializer.AbstractToStringSerializer;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;

public class ColorTranslator extends SingleValuedTranslator<Color> {

	public ColorTranslator(AtmValFrmtToKlaxonStyle toKlaxonStyle, AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {
		super(new ColorToStringSerializer(), toKlaxonStyle, fromKlaxonStyle);
	}

	public static class ColorToStringSerializer implements AbstractToStringSerializer<Color> {

		@Override
		public Class<Color> supportedType() {
			return Color.class;
		}

		@Override
		public Color parse(JackValueType type, String value) throws Exception {
			if (value == null) {
				return null;
			} else {
				return colorFromRGBA(value);
			}
		}

		@Override
		public String serialize(JackValueType type, Color value) throws Exception {
			if (value == null) {
				return null;
			} else {
				return colorToRGBA(value);
			}
		}

		public static Color colorFromRGBA(String str) {
			try {
				if (str.charAt(0) == '#') {
					str = str.substring(1);
				}
				if (str.length() < 8) {
					str = str + "FF";
				}

				int red = Integer.valueOf(str.substring(0, 2), 16);
				int green = Integer.valueOf(str.substring(2, 4), 16);
				int blue = Integer.valueOf(str.substring(4, 6), 16);
				int alpha = Integer.valueOf(str.substring(6, 8), 16);

				return new Color(red, green, blue, alpha);
			} catch (Exception e) {
				throw new IllegalArgumentException("Cannot parse color");
			}
		}

		public static String colorToRGBA(Color color) {
			int red = color.getRed();
			int green = color.getGreen();
			int blue = color.getBlue();
			int alpha = color.getAlpha();

			StringBuilder stb = new StringBuilder("#");
			stb.append(String.format("%02X", red));
			stb.append(String.format("%02X", green));
			stb.append(String.format("%02X", blue));

			if (alpha != 255) {
				stb.append(String.format("%02X", alpha));
			}

			return stb.toString();
		}
	}

}
