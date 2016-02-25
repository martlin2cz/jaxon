package cz.martlin.jaxon.j2k.translators;

import java.net.URL;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.serializer.AbstractToStringSerializer;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;

public class URLTranslator extends SingleValuedTranslator<URL> {

	public URLTranslator(AtmValFrmtToKlaxonStyle toKlaxonStyle, AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {
		super(new UrlToStringSeriliazer(), toKlaxonStyle, fromKlaxonStyle);
	}

	public static class UrlToStringSeriliazer implements AbstractToStringSerializer<URL> {

		@Override
		public Class<URL> supportedType() {
			return URL.class;
		}

		@Override
		public URL parse(JackValueType type, String value) throws Exception {
			return new URL(value);
		}

		@Override
		public String serialize(JackValueType type, URL value) throws Exception {
			return value.toExternalForm();
		}

	}

}
