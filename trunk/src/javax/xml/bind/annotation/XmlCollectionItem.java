/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * <p> Maps a collection item type to a XML Schema ELement</p>
 *
 * <p> <b>Usage</b> </p>
 * <p>
 * This annotation type can only be used within the XmlCollection
 * annotation. There can only be one program annotation on a program 
 * element. Therefore, XmlCollectionItem is defined as a separate
 * annotation to allow a collection of these to be returned in an
 * XmlCollection.
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <p>
 * The usage is subject to the following constraints:
 * <ul>
 *   <li> All usage constraints are described in XmlCollection </li>
 * </ul>
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @see XmlCollection
 * @since JAXB2.0
 */

@Retention(RUNTIME) @Target({})
public @interface XmlCollectionItem {
    /**
     * Specfies a collection item type. For primitives, the wrapper
     * class is used.
     */
   Class type();
 
    /**
     * Customizes the element name of the collection item type.
     */
    XmlElement value() default @XmlElement(name = "");
}
