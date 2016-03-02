package cz.martlin.jaxon.j2k.translator;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jaxon.Primitives;
import cz.martlin.jaxon.j2k.serializer.PrimitiveTypeSerializer;
import cz.martlin.jaxon.j2k.translators.PrimitiveTypeTranslator;

/**
 * Lists primitives translators.
 * 
 * @author martin
 *
 */
public class PrimitivesTranslators {

	public PrimitivesTranslators() {
	}

	/**
	 * Lists all primitives translators.
	 * 
	 * @return
	 */
	public static List<SingleValuedTranslator<?>> getTranslators() {

		List<PrimitiveTypeSerializer<?>> serializers = Primitives.getSerializers();

		List<SingleValuedTranslator<?>> translators = new ArrayList<>(serializers.size());

		for (PrimitiveTypeSerializer<?> serializer : serializers) {

			PrimitiveTypeTranslator<?> translator = new PrimitiveTypeTranslator<>(serializer);
			translators.add(translator);
		}

		return translators;
	}
}
