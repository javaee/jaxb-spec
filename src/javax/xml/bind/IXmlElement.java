/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind;

import javax.xml.namespace.QName;

/**
 * <p>JAXB Representation for an Xml Element.</p>
 *
 * <p>This interface represents three basic properties associated with an Xml Element:
 * the element's qualified name, the value of the element and whether the
 * content model of the element should be considered nil or not, i.e. xsi:nil="true".
 *
 * <p>Typically, JAXB binding statically associates the schema Xml Element name with a 
 * JAXB property. However, extensibility Xml Schema features such as element substitution
 * groups and wildcard content allow the Xml document author to introduce Xml Element names
 * that are not statically constrained by the schema. To enable JAXB manipulation to
 * support these Xml Schema extensibility features, the JAXB application requires
 * users to use these instances of this interface.
 *</p>
 *
 * <p> Note that this class implements the JAXB 1.0 concept for marking an instance
 * to represent an Xml element, javax.xml.bind.Element. 
 * </p>
 * 
 * @version $Revision: 1.2 $
 * @since JAXB2.0
 */


/*
 * Considerations for this API.
 *
 * Would it be worthwhile to introduce methods that represent schema concepts
 * such as type and substitution group. These methods would aid system level 
 * developers like JAX-RPC/JAXB integrators and to aid the portability
 * JAXB runtime implementations by having common means to acomplish 
 * unmarshal/marshal/validation of this element.
 * 
 * Possible Info
 * 
 * Type substitution aids:
 * What is the static schema type definition name of this element?
 * What is the dynamic type definition name of this element?
 * Has the element value of this element been type substituted?
 *
 * Group substitution aids:
 * Is group substitution blocked for this element?
 * is valid element substitution method that takes another element and
 * returns true iff the submitted element is valid substition for this element.
 */
public interface IXmlElement<T> extends Element, java.io.Serializable {

    /* 
     * Property names are purposely long to avoid collisions 
     * when a element declaration with an anonymous type definition is bound
     * to a schema-derived class that implements this interface and inlines
     * the properties representing the anonymous type definition into the
     * the JAXB Element class. Another means to avoid collisions between
     * the schema-derived properties and these defined properties could be to use
     * a different naming convention that the JavaBean property design pattern.
     * 
     * Example:
     * <xs:element name="aElement">
     *   <xs:complexType>
     *     <xs:element name="name" type="xs:string"/>
     * 
     * public interface AElement extends IXmlElement<AElement>, java.io.Serializable {
     *     // properties representing anonymous type definition 
     *     void setName();
     *     String getName();
     *
     *     // properties from this interface, IXmlElement<T>.
     *     QName getXmlElementName();
     *     ...
     * } 
     */

    /**
     * Returns the fully qualified name for element.
     */
    public QName   getXmlElementName();


    /**
     * Set the element value of this element.
     */
    public void    setXmlElementValue(T value);

    /**
     * <p>Return the element value of this element.</p>
     *
     * <p>If the returned value is a subtype of T, it indicates
     * that type substitution has been performed on the element.
     * </p>
     */
    public T       getXmlElementValue();

    /**
     * <p>Returns true iff the content model of element value should be considered nil.</p>
     *
     * <p>If this value is true, marshalling outputs the attribute xsi:nil="true".
     * Note that this value can be true and element value instance can have properties
     * that are set that correspond to attribute.
     * </p>
     */
    public boolean isXmlElementNil();

    /**
     * Set whether the content model of element value has 
     * <code>xsi:nil="true"</code> or <code>xsi:nil="false"</code>.
     */
    public void    setXmlElementNil(boolean value);
}
