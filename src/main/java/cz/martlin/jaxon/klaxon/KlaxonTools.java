package cz.martlin.jaxon.klaxon;

import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import cz.martlin.jaxon.abstracts.HasName;
import cz.martlin.jaxon.klaxon.config.K2DocFormat;
import cz.martlin.jaxon.klaxon.data.KlaxonObject;
import cz.martlin.jaxon.klaxon.data.KlaxonStringValue;
import cz.martlin.jaxon.klaxon.data.KlaxonValue;

/**
 * Contains some various (mostly querying) methods usefull in
 * {@link KlaxonToElementsImpl}.
 * 
 * @author martin
 *
 */
class KlaxonTools {

	public KlaxonTools() {
	}

	/**
	 * Returns true, whether given klaxon value owned by owner should be created
	 * as attribute or as child.
	 * 
	 * //(To create as attribute klaxon must be klaxon string value, must have
	 * no headers, must have unique name in owner and must be preffered creating
	 * of attributes).
	 * 
	 * @param klaxon
	 * @param owner
	 * @param prefferAttibute
	 * @return
	 */
	public boolean isToCreateAttribute(K2DocFormat format, KlaxonValue klaxon, KlaxonValue owner,
			boolean prefferAttibute) {

		if (!prefferAttibute) {
			return false;
		}

		if (klaxon instanceof KlaxonStringValue) {
			return klaxon.getHeaders().isEmpty();
		}
		
		

		String name = klaxon.getName();
		if (!isUniqueNameInOwnersAttrs(format, name, owner)) {
			return false;
		}

		return prefferAttibute;
	}

	/**
	 * Returns true if given klaxon string value should be in given elem created
	 * as Text child or not. (That means elem has to have no children and config
	 * should not prefer storing text as attr.)
	 * 
	 * @param format
	 * @param string
	 * @param elem
	 * @return
	 */
	public boolean isToCreateTextNode(K2DocFormat format, KlaxonStringValue string, Element elem) {
		if (elem.getChildNodes().getLength() > 0) {
			return false;
		}

		if (format.textAsAttr()) {
			return false;
		}

		return true;
	}

	/**
	 * Returns true, if for given klaxon string value should be in given element
	 * created valued attribute. (That means config prefers creating text as
	 * attributes).
	 * 
	 * @param string
	 * @param elem
	 * @return
	 */
	public boolean isToCreateValuedAttribute(K2DocFormat format, KlaxonStringValue string, Element elem) {

		if (format.textAsAttr()) {
			return true;
		}

		return false;
	}

	/**
	 * Returns true, if for given klaxon string value should create new given
	 * element's child wrapping text. (So when elem has some children.)
	 * 
	 * @param string
	 * @param elem
	 * @return
	 */
	public boolean isToCreateTextChild(K2DocFormat format, KlaxonStringValue string, Element elem) {

		if (elem.getChildNodes().getLength() > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Returns true if given name is unique over owner's header fields (and if
	 * it is klaxon object, over owners' data fields as well).
	 * 
	 * @param name
	 * @param owner
	 * @return
	 */
	public boolean isUniqueNameInOwnersAttrs(K2DocFormat format, String name, KlaxonValue owner) {
		boolean isUnique = true;

		if (format.headerAsAtrr()) {
			List<KlaxonValue> headers = owner.getHeaders();
			isUnique &= isUniqueIn(name, headers);
		}

		if (owner instanceof KlaxonObject && format.headerAsAtrr()) {
			KlaxonObject object = (KlaxonObject) owner;
			List<KlaxonValue> fields = object.getFields();
			isUnique &= isUniqueIn(name, fields);
		}

		return isUnique;
	}

	/**
	 * Returns true if given name occurs in given set of items at least once.
	 * 
	 * @param name
	 * @param items
	 * @return
	 */
	public boolean isUniqueIn(String name, List<KlaxonValue> items) {
		int count = 0;

		for (KlaxonValue value : items) {
			if (value.getName().equals(name)) {
				count++;
			}
			if (count > 1) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Appends given child to given elem. Works properly for elements as well as
	 * attributes.
	 * 
	 * @param elem
	 * @param child
	 */
	public void appendNodeTo(Element elem, Node child) {
		if (child instanceof Attr) {
			Attr attr = (Attr) child;
			elem.setAttributeNode(attr);
		} else {
			elem.appendChild(child);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns name of element of value (by format or of given object if format
	 * has not specified).
	 * 
	 * @param format
	 * @param of
	 * @return
	 */
	public String nameOfValueElem(K2DocFormat format, HasName of) {
		return firstNonNull(format.valueElemName(), of.getName());
	}

	/**
	 * Returns name of element of value (by format or of given object if format
	 * has not specified).
	 * 
	 * @param format
	 * @param of
	 * @return
	 */
	public String nameOfValueElem(K2DocFormat format, Element elem) {
		return firstNonNull(format.valueElemName(), elem.getTagName());
	}

	/**
	 * Returns name of attribute of value (by format or of given object if
	 * format has not specified).
	 * 
	 * @param format
	 * @param of
	 * @return
	 */
	public String nameOfAttrElem(K2DocFormat format, HasName of) {
		return firstNonNull(format.valueAttrName(), of.getName());
	}

	/**
	 * Returns name of attribute of value (by format or of given object if
	 * format has not specified).
	 * 
	 * @param format
	 * @param of
	 * @return
	 */
	public String nameOfAttrElem(K2DocFormat format, Element elem) {
		return firstNonNull(format.valueAttrName(), elem.getTagName());
	}

	/**
	 * Returns first non-null value from given two. If no non-null value,
	 * returns - obviously - null.
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	private String firstNonNull(String first, String second) {
		if (first != null) {
			return first;
		}

		if (second != null) {
			return second;
		}

		return null;
	}

	/**
	 * Tries to find child element of given elem with given tag name. Returs
	 * null if no such child found.
	 * 
	 * @param elem
	 * @param name
	 * @return
	 */
	protected Element findChild(Element element, String name) {
		NodeList children = element.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);

			if (node instanceof Element) {
				Element elem = (Element) node;

				if (name.equals(elem.getTagName())) {
					return elem;
				}
			}
		}

		return null;
	}

	public boolean isStringValued(K2DocFormat format, Element elem) {
		return isTextNode(format, elem) || isValuedAttribute(format, elem) || isTextChild(format, elem);
	}

	public boolean isTextNode(K2DocFormat format, Element elem) {
		if (format.textAsAttr()) {
			return false;
		}

		if (elem.getChildNodes().getLength() == 1 //
				&& elem.getFirstChild() instanceof Text) {
			return true;
		}

		return false;
	}

	public boolean isValuedAttribute(K2DocFormat format, Element elem) {
		if (!format.textAsAttr()) {
			return false;
		}

		String attrName = nameOfAttrElem(format, elem);
		if (elem.hasAttribute(attrName)) {
			return true;
		}

		return false;
	}

	public boolean isTextChild(K2DocFormat format, Element elem) {
		if (format.textAsAttr()) {
			return false;
		}

		if (elem.getChildNodes().getLength() > 1) {
			String childName = nameOfValueElem(format, elem);
			Element child = findChild(elem, childName);
			if (child != null && isTextChild(format, child)) {
				return true;
			}
		}

		return false;
	}

}
