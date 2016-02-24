package cz.martlin.jaxon.testings;

import cz.martlin.jaxon.jack.data.design.JackObjectDesign;
import cz.martlin.jaxon.jack.data.design.JackValueType;
import cz.martlin.jaxon.jack.data.values.JackObject;

public interface JackTestTuple<T> {

	public abstract JackValueType getType();
	
	public abstract T createObject();

	public abstract JackObject createJackObject();

	public abstract JackObjectDesign createJackDesign();

}