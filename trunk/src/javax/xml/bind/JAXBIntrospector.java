/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind;
import  javax.xml.namespace.QName;

/**
 * Provide access to JAXB xml binding data for a JAXB object.
 *
 * <p>
 * WORK IN PROGRESS:
 * Intially, the intent of this class is to just conceptualize how 
 * a JAXB application developer can access xml binding information, 
 * independent if binding model is java to schema or schema to java.
 * Since accessing the XML element name related to a JAXB element is
 * a highly requested feature, demonstrate access to this
 * binding information.
 *
 * The factory method to get a <code>JAXBIntrospector</code> instance is 
 * {@link JAXBContext#createJAXBIntrospector()}.
 *
 * @see JAXBContext#createJAXBIntrospector()
 */
public abstract class JAXBIntrospector {

    /** 
     * <p>Return true iff <code>object</code> represents a JAXB xml element.</p>
     * <p>Parameter <code>object</code> is a JAXB element for following cases:
     * <ol>
     *   <li>It is an instance of <code>javax.xml.bind.JAXBElement</code>.</li>
     *   <li>The class of <code>object</code> is annotated with 
     *       <code>@XmlRootElement</code>.
     *   </li>
     * </ol>
     *
     * @see #getElementName(Object)
     */
    public abstract boolean isElement(Object object);

    /**
     * <p>Get xml element qname for <code>jaxbElement</code>.</p>
     *
     * @param jaxbElement is an object that {@link #isElement(Object)} returned true.
     *                    
     * @return xml element qname associated with jaxbElement;
     *         null if <code>jaxbElement</code> is not a JAXB Element.
     */
    public abstract QName getElementName(Object jaxbElement);
}
