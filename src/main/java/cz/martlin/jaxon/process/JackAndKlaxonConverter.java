package cz.martlin.jaxon.process;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import cz.martlin.jaxon.exception.JaxonException;
import cz.martlin.jaxon.jaxon.FieldsConverter;
import cz.martlin.jaxon.object.JackField;
import cz.martlin.jaxon.object.JackObject;
import cz.martlin.jaxon.object.KlaxonItem;
import cz.martlin.jaxon.object.KlaxonObject;
import cz.martlin.jaxon.utils.Utils;

public class JackAndKlaxonConverter {
	private static final String FORMAT_VERSION = "0.1";

	private final FieldsConverter converters = new FieldsConverter();

	public KlaxonObject toKlaxon(JackObject jack) throws JaxonException {
		String name = "jaxon-object";

		Set<KlaxonItem> items = new LinkedHashSet<>();
		Collection<JackField> fields = jack.getFields();

		for (JackField field : fields) {
			KlaxonItem item = converters.convert(field);
			items.add(item);
		}

		addMetadata(jack, items);

		return new KlaxonObject(name, items);
	}

	private void addMetadata(JackObject jack, Set<KlaxonItem> items) {
		Map<String, String> jocAttrs = new LinkedHashMap<>();
		jocAttrs.put("class-name", jack.getClazz().getName());
		KlaxonItem joci = new KlaxonItem("jaxon-object-class", jocAttrs);
		items.add(joci);

		Map<String, String> vofAttrs = new LinkedHashMap<>();
		vofAttrs.put("version", FORMAT_VERSION);
		KlaxonItem vofi = new KlaxonItem("version-of-format", vofAttrs);
		items.add(vofi);
	}

	public JackObject toJack(KlaxonObject klaxon) throws JaxonException {
		
		Set<JackField> fields = new LinkedHashSet<>();
		Set<KlaxonItem> items = klaxon.getItems();
		
		Class<?> clazz = checkAndParseClass(klaxon, items);

		

		for (KlaxonItem item : items) {
			JackField field = converters.convert(item);
			fields.add(field);
		}

		return new JackObject(clazz, fields);
	}

	private Class<?> checkAndParseClass(KlaxonObject klaxon, Set<KlaxonItem> items)
			throws JaxonException {
		String name = klaxon.getName();

		if (!"jaxon-object".equals(name)) {
			throw new JaxonException("Not a jaxon object, it is " + name);
		}

		KlaxonItem vofi = Utils.popItemWithName("version-of-format", items);
		String version = vofi.getAttributes().get("version");
		if (!FORMAT_VERSION.equals(version)) {
			throw new JaxonException("Wrong version, expected: "
					+ FORMAT_VERSION + ", but found " + version);
		}

		KlaxonItem joci = Utils.popItemWithName("jaxon-object-class", items);
		String className = joci.getAttributes().get("class-name");
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException | NullPointerException e) {
			throw new JaxonException("Missing or invalid class: " + className);
		}
		return clazz;
	}

}
