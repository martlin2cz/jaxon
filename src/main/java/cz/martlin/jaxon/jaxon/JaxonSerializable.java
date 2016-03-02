package cz.martlin.jaxon.jaxon;

import cz.martlin.jaxon.j2k.abstracts.JackToKlaxonSerializable;
import cz.martlin.jaxon.jack.abstracts.JackSerializable;

/**
 * Represents (non-atomic, composite) object processable by jaxon.
 * 
 * @author martin
 *
 */
public interface JaxonSerializable extends JackSerializable, JackToKlaxonSerializable {

}
