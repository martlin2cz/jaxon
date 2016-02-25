package cz.martlin.jaxon.j2k.data;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
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

public class SupportedAtomicsTranslators {

	private final J2KConfig config;
	private final AtmValFrmtToKlaxonStyle toKlaxonStyle;
	private final AtmValFrmtFromKlaxonStyle fromKlaxonStyle;

	private final List<AtomicValueTranslator<?>> transformers;

	public SupportedAtomicsTranslators(J2KConfig config) {
		super();
		this.config = config;
		this.toKlaxonStyle = config.getAVFStyleToKlaxon();
		this.fromKlaxonStyle = config.getAVFStyleFromKlaxon();
		this.transformers = list();
	}

	public SupportedAtomicsTranslators(J2KConfig config, //
			AtmValFrmtToKlaxonStyle toKlaxonStyle, AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {
		super();
		this.config = config;
		this.toKlaxonStyle = toKlaxonStyle;
		this.fromKlaxonStyle = fromKlaxonStyle;
		this.transformers = list();
	}

	public Iterable<AtomicValueTranslator<?>> getTransformers() {
		return transformers;
	}

	private List<AtomicValueTranslator<?>> list() {

		List<AtomicValueTranslator<?>> transformers = new ArrayList<>();

		addBaseTranslators(transformers, toKlaxonStyle, fromKlaxonStyle);
		addCustomTranslators(transformers, toKlaxonStyle, fromKlaxonStyle, config);

		return transformers;
	}

	/**
	 * Adds minimal base translators (for primitive types, string and enums).
	 * 
	 * @param transformers
	 * @param toKlaxonStyle
	 * @param fromKlaxonStyle
	 */
	private static void addBaseTranslators(List<AtomicValueTranslator<?>> transformers,
			AtmValFrmtToKlaxonStyle toKlaxonStyle, AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {

		transformers.addAll(PrimitivesTranslators.getTranslators(//
				toKlaxonStyle, fromKlaxonStyle));

		transformers.add(new StringTranslator(toKlaxonStyle, fromKlaxonStyle));
		transformers.add(new EnumsTranslator<>(toKlaxonStyle, fromKlaxonStyle));

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
	private static void addCustomTranslators(List<AtomicValueTranslator<?>> transformers,
			AtmValFrmtToKlaxonStyle toKlaxonStyle, AtmValFrmtFromKlaxonStyle fromKlaxonStyle, J2KConfig config) {

		transformers.add(new BigDecimalTranslator(toKlaxonStyle, fromKlaxonStyle));
		transformers.add(new CalendarTranslator(config, toKlaxonStyle, fromKlaxonStyle));
		transformers.add(new ColorTranslator(toKlaxonStyle, fromKlaxonStyle));
		transformers.add(new DateTranslator(config, toKlaxonStyle, fromKlaxonStyle));
		transformers.add(new FileTranslator(toKlaxonStyle, fromKlaxonStyle));
		transformers.add(new PointTranslator(toKlaxonStyle, fromKlaxonStyle));
		transformers.add(new URLTranslator(toKlaxonStyle, fromKlaxonStyle));
		transformers.add(new SimpleDateFormatTranslator(toKlaxonStyle, fromKlaxonStyle));
		
		// TODO others -  URI, Rectangle, ... ? ...

	}

	public AtomicValueTranslator<?> find(JackValueType type) throws JackToKlaxonException {

		for (AtomicValueTranslator<?> transformer : transformers) {
			if (transformer.isApplicableTo(type)) {
				return transformer;
			}
		}

		Exception e = new IllegalArgumentException("Unsupported atomic type " + type);
		throw new JackToKlaxonException("Unupported type", e);
	}

}
