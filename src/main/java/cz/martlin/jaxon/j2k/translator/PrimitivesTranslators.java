package cz.martlin.jaxon.j2k.translator;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jaxon.Primitives;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtFromKlaxonStyle;
import cz.martlin.jaxon.j2k.atomics.format.AtmValFrmtToKlaxonStyle;
import cz.martlin.jaxon.j2k.serializer.PrimitiveTypeSerializer;
import cz.martlin.jaxon.j2k.translators.PrimitiveTypeTranslator;

public class PrimitivesTranslators {

	public PrimitivesTranslators() {
		// TODO Auto-generated constructor stub
	}

	public static List<SingleValuedTranslator<?>> getTranslators(
			AtmValFrmtToKlaxonStyle toKlaxonStyle,
			AtmValFrmtFromKlaxonStyle fromKlaxonStyle) {

		List<PrimitiveTypeSerializer<?>> serializers = Primitives
				.getSerializers();

		List<SingleValuedTranslator<?>> translators = new ArrayList<>(
				serializers.size());

		for (PrimitiveTypeSerializer<?> serializer : serializers) {
			
			PrimitiveTypeTranslator<?> translator = new PrimitiveTypeTranslator<>(
					serializer, toKlaxonStyle, fromKlaxonStyle);

			translators.add(translator);
		}

		return translators;
	}
}
