package cz.martlin.jaxon.j2k.translators;

import java.io.File;

import cz.martlin.jaxon.j2k.serializer.AbstractToStringSerializer;
import cz.martlin.jaxon.j2k.translator.SingleValuedTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;

/**
 * Represents translator of {@link File} instances.
 * 
 * @author martin
 *
 */
public class FileTranslator extends SingleValuedTranslator<File> {

	public FileTranslator() {
		super(new FileToStringSerializer());
	}

	public static class FileToStringSerializer implements AbstractToStringSerializer<File> {

		@Override
		public Class<File> supportedType() {
			return File.class;
		}

		@Override
		public File parse(JackValueType type, String value) throws Exception {
			if (value == null) {
				return null;
			} else {
				return new File(value);
			}
		}

		@Override
		public String serialize(JackValueType type, File value) throws Exception {
			if (value == null) {
				return null;
			} else {
				return value.getPath();
			}
		}

	}
}
