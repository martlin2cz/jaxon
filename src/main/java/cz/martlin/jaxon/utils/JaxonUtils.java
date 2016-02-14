package cz.martlin.jaxon.utils;

import java.util.Calendar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.exception.KlaxonException;
import cz.martlin.jaxon.object.KlaxonObject;

public class JaxonUtils {

	protected static Element createRoot(Document document, KlaxonObject klaxon) {
		Element root = document.createElement(Config.getRootElementNodeName());

		String version = Config.getVersion();
		root.setAttribute(Config.getVersionAttrName(), version);

		String clazz = "TODO classs";// TODO
		root.setAttribute(Config.getObjectTypeAttrName(), clazz);

		String when = Calendar.getInstance().getTime().toString();
		root.setAttribute(Config.getCreatedAtAttrName(), when);

		return root;
	}

	private static Class<?> parseObjectClass(Element root)
			throws KlaxonException {
		String className = root.getAttribute(Config.getObjectTypeAttrName());
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException | NullPointerException e) {
			throw new KlaxonException(
					"Invalid or missing object's class, root attribute "
							+ Config.getObjectTypeAttrName(), e);
		}
	}

	private static void checkVersion(Element root) throws KlaxonException {
		String requested = Config.getVersion();
		String current = root.getAttribute(Config.getVersionAttrName());

		if (requested.equals(current)) {
			throw new KlaxonException(
					"Missing or bad version, value of root attribute "
							+ Config.getObjectTypeAttrName() + ". Requested "
							+ requested + " but found " + current + ".");
		}

	}
}
