package cz.martlin.jaxon.j2k.translator;

import java.util.List;

import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtomicValueFormat;
import cz.martlin.jaxon.j2k.atomics.format.AtomicValueFormatsProvider;
import cz.martlin.jaxon.j2k.data.JackToKlaxonException;
import cz.martlin.jaxon.j2k.serializer.AbstractToStringSerializer;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackAtomicValue;
import cz.martlin.jaxon.jack.data.values.JackNullValue;
import cz.martlin.jaxon.jack.data.values.JackValue;
import cz.martlin.jaxon.klaxon.data.KlaxonEntry;

public class SingleValuedTranslator<T> extends AtomicValueTranslator<T> {

	protected final AtomicValueFormat formatToKlaxon;
	protected final List<AtomicValueFormat> formatsFromKlaxon;

	protected final AbstractToStringSerializer<T> serializer;

	public SingleValuedTranslator(AbstractToStringSerializer<T> serializer,
			AtomicValueFormat formatToKlaxon,
			List<AtomicValueFormat> formatsFromKlaxon) {

		this.serializer = serializer;
		this.formatToKlaxon = formatToKlaxon;
		this.formatsFromKlaxon = formatsFromKlaxon;
	}

	public SingleValuedTranslator(AbstractToStringSerializer<T> serializer,
			AtmValFrmtToKlaxonStyle toKlaxonStyle,
			AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {

		this.serializer = serializer;

		this.formatToKlaxon = AtomicValueFormatsProvider
				.formatToKlaxon(toKlaxonStyle);

		this.formatsFromKlaxon = AtomicValueFormatsProvider.formatFromKlaxon(
				fromKlaxonStyle, formatToKlaxon);
	}

	@Override
	public Class<T> supportedType() {
		return serializer.supportedType();
	}

	public KlaxonEntry toKlaxon(String name, JackValueType type, JackValue jack)
			throws JackToKlaxonException {

		JackAtomicValue atomic = (JackAtomicValue) jack;
		Object val = atomic.getValue();

		String value;
		try {
			value = serializeObject(type, val);
		} catch (Exception e) {
			throw new JackToKlaxonException("Cannot serialize value", e);
		}

		return toValuedKlaxon(name, value);
	}

	@Override
	public JackValue toJack(JackValueType type, KlaxonEntry klaxon)
			throws JackToKlaxonException {

		String val = fromValuedKlaxon(klaxon);
		if (val == null) {
			return JackNullValue.INSTANCE;
		}

		Object value;
		try {
			value = parseObject(type, val);
		} catch (Exception e) {
			throw new JackToKlaxonException("Cannot parse value", e);
		}

		return new JackAtomicValue(value);
	}

	private KlaxonEntry toValuedKlaxon(String name, String value) {
		return formatToKlaxon.toKlaxon(name, value);
	}

	private String fromValuedKlaxon(KlaxonEntry klaxon) {

		String value = null;
		for (AtomicValueFormat format : formatsFromKlaxon) {
			value = format.fromKlaxon(klaxon);
			if (value != null) {
				return value;
			}
		}

		return null;
	}

	private Object parseObject(JackValueType type, String value)
			throws Exception {

		return serializer.parse(type, value);
	}

	protected String serializeObject(JackValueType type, Object value)
			throws Exception {

		@SuppressWarnings("unchecked")
		T val = (T) value;
		return serializer.serialize(type, val);
	}
}
