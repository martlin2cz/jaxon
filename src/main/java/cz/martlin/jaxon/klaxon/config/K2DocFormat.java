package cz.martlin.jaxon.klaxon.config;

/**
 * Format of exporting of klaxon objects into xml document, elements and
 * attributes.
 * 
 * @author martin
 *
 */
public enum K2DocFormat {

	/**
	 * Uses attributes as much as it is possible.
	 */
	ATTRS_WHERE_POSSIBLE(true, true, true, null, K2DocFormat.VALUE_NAME), //
	/**
	 * Uses attributes only for headers fields.
	 */
	ATTRS_FOR_HEADERS(true, false, false, null, K2DocFormat.VALUE_NAME), //
	/**
	 * Uses children for the every field (header or not) of object.
	 */
	@Deprecated
	CHILDREN_EVERYWHERE(false, false, false, null, K2DocFormat.VALUE_NAME), //
	/**
	 * Uses children for the every field (header or not) of object.
	 */
	CHILDREN_WHERE_POSSIBLE(false, false, false, null, K2DocFormat.VALUE_NAME); //

	public static final String VALUE_NAME = "value";

	private final boolean headerAsAttr;
	private final boolean fieldAssAttr;
	private final boolean textAsAttr;

	private final String valueElemName;
	private final String valueAttrName;

	private K2DocFormat(boolean headerAsAttr, boolean fieldAssAttr, boolean textAsAttr, //
			String valueElemName, String valueAttrName) {
		
		this.headerAsAttr = headerAsAttr;
		this.fieldAssAttr = fieldAssAttr;
		this.textAsAttr = textAsAttr;
		this.valueElemName = valueElemName;
		this.valueAttrName = valueAttrName;
	}

	/**
	 * Should be header exported as attribute?
	 * 
	 * @return
	 */
	public boolean headerAsAtrr() {
		return headerAsAttr;
	}

	/**
	 * Should by field exported as attribute?
	 * 
	 * @return
	 */
	public boolean fieldAsAtrr() {
		return fieldAssAttr;
	}

	/**
	 * Should be string value exported as attribute? (if no should create child
	 * text node element).
	 * 
	 * @return
	 */
	public boolean textAsAttr() {
		return textAsAttr;
	}

	/**
	 * Name of value attibute for string value. If null, will be used same as
	 * the owning field's.
	 * 
	 * @return
	 */
	public String valueAttrName() {
		return valueAttrName;
	}

	/**
	 * Name of value element of string value. If null, will be used same as the
	 * owning field's.
	 * 
	 * @return
	 */
	public String valueElemName() {
		return valueElemName;
	}

}
