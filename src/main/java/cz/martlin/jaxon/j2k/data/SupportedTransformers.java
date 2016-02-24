package cz.martlin.jaxon.j2k.data;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.translator.AtomicValueTranslator;
import cz.martlin.jaxon.j2k.translator.PrimitivesTranslators;
import cz.martlin.jaxon.j2k.translators.BigDecimalTranslator;
import cz.martlin.jaxon.j2k.translators.EnumsTranslator;
import cz.martlin.jaxon.j2k.translators.StringTranslator;
import cz.martlin.jaxon.jack.data.design.JackValueType;

public class SupportedTransformers {

	private final AtmValFrmtToKlaxonStyle toKlaxonStyle;
	private final AtmValFrmtFromKlaxonStyle fromKlaxonStyle;
	private final List<AtomicValueTranslator<?>> transformers;

	public SupportedTransformers(J2KConfig config) {
		super();
		this.toKlaxonStyle = config.getAVFStyleToKlaxon();
		this.fromKlaxonStyle = config.getAVFStyleFromKlaxon();
		this.transformers = list();
	}

	public SupportedTransformers(AtmValFrmtToKlaxonStyle toKlaxonStyle,
			AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {
		super();
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
		addCustomTranslators(transformers, toKlaxonStyle, fromKlaxonStyle);

		return transformers;
	}

	/**
	 * Adds minimal base translators (for primitive types, string and enums).
	 * 
	 * @param transformers
	 * @param toKlaxonStyle
	 * @param fromKlaxonStyle
	 */
	private static void addBaseTranslators(
			List<AtomicValueTranslator<?>> transformers,
			AtmValFrmtToKlaxonStyle toKlaxonStyle,
			AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {

		transformers.addAll(PrimitivesTranslators.getTranslators(//
				toKlaxonStyle, fromKlaxonStyle));

		transformers.add(new StringTranslator(toKlaxonStyle, fromKlaxonStyle));
		transformers.add(new EnumsTranslator<>(toKlaxonStyle, fromKlaxonStyle));

		// TODO anything?
	}

	/**
	 * Adds various others custom translators (i. e. for Date, BigDecimal, File,
	 * Color, URL, URI, ...).
	 * 
	 * @param transformers
	 * @param toKlaxonStyle
	 * @param fromKlaxonStyle
	 */
	private static void addCustomTranslators(
			List<AtomicValueTranslator<?>> transformers,
			AtmValFrmtToKlaxonStyle toKlaxonStyle,
			AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {

		transformers.add(new BigDecimalTranslator(toKlaxonStyle,
				fromKlaxonStyle));
		// TODO others - Date, file, Color, URL, URI, ...

	}

	public AtomicValueTranslator<?> find(JackValueType type)
			throws JackToKlaxonException {

		for (AtomicValueTranslator<?> transformer : transformers) {
			if (transformer.isApplicableTo(type)) {
				return transformer;
			}
		}

		Exception e = new IllegalArgumentException("Unsupported atomic type "
				+ type);
		throw new JackToKlaxonException("Unupported type", e);
	}

}
