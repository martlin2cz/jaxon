package cz.martlin.jaxon.j2k.translators;

import java.net.URL;

import cz.martlin.jaxon.j2k.serializer.AbstractToStringSerializer;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;
/**
 * Implements translator of {@link URL} instances.
 * @author martin
 *
 */
public class URLTranslator extends SingleValuedTranslator<URL> {

	public URLTranslator() {
		super(new UrlToStringSeriliazer());
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
