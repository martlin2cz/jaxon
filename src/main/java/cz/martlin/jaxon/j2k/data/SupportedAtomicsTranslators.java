package cz.martlin.jaxon.j2k.data;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jaxon.j2k.translator.AtomicValueTranslator;
import cz.martlin.jaxon.j2k.translator.PrimitivesTranslators;
import cz.martlin.jaxon.j2k.translators.BigDecimalTranslator;
import cz.martlin.jaxon.j2k.translators.CalendarTranslator;
import cz.martlin.jaxon.j2k.translators.ColorTranslator;
import cz.martlin.jaxon.j2k.translators.DateTranslator;
import cz.martlin.jaxon.j2k.translators.EnumsTranslator;
import cz.martlin.jaxon.j2k.translators.FileTranslator;
import cz.martlin.jaxon.j2k.translators.PointTranslator;
import cz.martlin.jaxon.j2k.translators.SimpleDateFormatTranslator;
import cz.martlin.jaxon.j2k.translators.StringTranslator;
import cz.martlin.jaxon.j2k.translators.URLTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;

/**
 * Represents currently supported atomic translators.
 * 
 * @see AtomicValueTranslator
 * @author martin
 *
 */
public class SupportedAtomicsTranslators {

	private final J2KConfig config;

	private final List<AtomicValueTranslator<?>> translators;

	public SupportedAtomicsTranslators(J2KConfig config) {
		super();
		this.config = config;
		this.translators = list();
	}

	/**
	 * Returns currently supported translators.
	 * 
	 * @return
	 */
	public Iterable<AtomicValueTranslator<?>> getTranslators() {
		return translators;
	}

	/**
	 * Lists translators.
	 * 
	 * @return
	 */
	private List<AtomicValueTranslator<?>> list() {

		List<AtomicValueTranslator<?>> transformers = new ArrayList<>();

		addBaseTranslators(transformers);
		addCustomTranslators(transformers, config);

		return transformers;
	}

	/**
	 * Adds minimal base translators (for primitive types, string and enums).
	 * 
	 * @param transformers
	 * @param toKlaxonStyle
	 * @param fromKlaxonStyle
	 */
	private static void addBaseTranslators(List<AtomicValueTranslator<?>> transformers) {

		transformers.addAll(PrimitivesTranslators.getTranslators());

		transformers.add(new StringTranslator());
		transformers.add(new EnumsTranslator<>());

		// TODO anything?
	}

	/**
	 * Adds various others custom translators (i. e. for Date, BigDecimal, File,
	 * Color, URL, ...).
	 * 
	 * @param transformers
	 * @param toKlaxonStyle
	 * @param fromKlaxonStyle
	 * @param config
	 */
	private static void addCustomTranslators(List<AtomicValueTranslator<?>> transformers, J2KConfig config) {

		transformers.add(new BigDecimalTranslator());
		transformers.add(new CalendarTranslator(config));
		transformers.add(new ColorTranslator());
		transformers.add(new DateTranslator(config));
		transformers.add(new FileTranslator());
		transformers.add(new PointTranslator());
		transformers.add(new URLTranslator());
		transformers.add(new SimpleDateFormatTranslator());

		// TODO others - URI, Dimension, Rectangle, ... ? ...

	}

	/**
	 * Finds translator for given type. Throws excepton if no such translator is
	 * found for given type.
	 * 
	 * @param type
	 * @return
	 * @throws JackToKlaxonException
	 */
	public AtomicValueTranslator<?> find(JackValueType type) throws JackToKlaxonException {

		for (AtomicValueTranslator<?> transformer : translators) {
			if (transformer.isApplicableTo(type)) {
				return transformer;
			}
		}

		Exception e = new IllegalArgumentException("Unsupported atomic type " + type);
		throw new JackToKlaxonException("Unupported type", e);
	}

}
