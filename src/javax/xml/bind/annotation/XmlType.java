/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * <p>
 * Maps a class to a XML Schema type.
 *
 * <p><b>Usage</b></p>
 * <p> The <tt>@XmlType</tt> annnotation can be used with the following program
 * elements:
 * <ul>
 *   <li> a top level value class </li>
 * </ul>
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 * 
 * <p>
 * A class maps naturally to XML Schema type. A value class is a data
 * container for values represented by properties and fields. A  
 * schema type is a data container for values represented by schema
 * components within a schema type's content model (e.g. model groups,
 * attributes etc).
 * <p>
 * A class maps to either a XML Schema complex type or a XML Schema simple
 * type. The XML Schema type is derived based on the 
 * mapping of JavaBean properties and fields contained within the
 * class. The schema type to which the class is mapped can either be
 * named or anonymous. A class can be mapped to an anonymous schema
 * type by annotating the class with <tt>&#64XmlType(name="")</tt>. 
 * <p>
 * Either a global element, local element or a local attribute can be
 * associated with an anoymous type as follows:
 * <ul>
 *   <li><b>global element: </b> A global element of an anonmyous
 *      type can be derived by annotating the class with @{@link
 *      XmlRootElement}. See Example 5 below. </li> 
 *
 *   <li><b>local element: </b> A JavaBean property that references
 *      the class annotated with @XmlType(name="") and mapped to an
 *      element is associated with an anonymous type. See Example 6
 *      below.</li> 
 *
 *   <li><b>attribute: </b> A JavaBean property that references
 *      the class annotated with @and is mapped to a attribute is
 *      associated with ean anonymous type. See Example 7 below. </li>
 * </ul>
 * <b> Mapping to XML Schema Complex Type </b>
 * <ul>
 *   <li>If class is annotated with <tt>@XmlType(name="") </tt>, then
 *   class is mapped to an anonymous type. Otherwise class name maps
 *   to a complex type name. The <tt>XmlName()</tt> annotation member
 *   can be used to customize the name.</li>  
 *
 *   <li> Properties and fields that are mapped to elements map to a
 *   content model within the complex type. The annotation member
 *   <tt>propOrder()</tt> can be used to customize the the content model to be
 *   <tt>xs:all</tt> or <tt>xs:sequence</tt> and also for specifying
 *   the order of XML elements in <tt>xs:sequence</tt>. </li> 
 *
 *   <li> Properties and fields mapped to attributes within the
 *        complex type.  </li>
 *
 *   <li> The targetnamespace of the XML Schema type can be customized
 *        using the annotation member <tt>namespace()</tt>. </li>
 * </ul>
 *
 * <p>
 * <b> Mapping class to XML Schema simple type </b>
 * <p>
 *  A class can be mapped to a XML Schema simple type using the
 * <tt>@XmlValue</tt> annotation. For additional details and examples,
 * see <tt>@XmlValue</tt> annotation type.
 * <p>
 * The following table shows the mapping of the class to a XML Schema
 * complex type or simple type. The notational symbols used in the table are:
 * <ul>
 *   <li> ->    : represents a mapping </li>
 *   <li> [x]+  : one or more occurances of x </li>
 *   <li> [ <tt>@XmlValue</tt> property ]: JavaBean property annotated with
 *         <tt>@XmlValue</tt></li>
 *   <li> X     : don't care
 * </ul>
 * <blockquote>
 *   <table border="1" cellpadding="4" cellspacing="3">
 *     <tbody>
 *       <tr>
 *         <td><b>Target</td>
 *         <td><b>propOrder</b></td>
 *         <td><b>ClassBody</b></td>
 *         <td><b>ComplexType</b></td>
 *         <td><b>SimpleType</b></td>
 *       </tr>
 * 
 *       <tr valign="top">
 *         <td>Class</td>
 *         <td>{}</td>
 *         <td>[property]+ -> elements</td>
 *         <td>complexcontent<br>xs:all</td>
 *         <td> </td>
 *       </tr>
 * 
 *       <tr valign="top">
 *         <td>Class</td>
 *         <td>non empty</td>
 *         <td>[property]+ -> elements</td>
 *         <td>complexcontent<br>xs:sequence</td>
 *         <td> </td>
 *       </tr>
 * 
 *       <tr valign="top">
 *         <td>Class</td>
 *         <td>X</td>
 *         <td>no property -> element</td>
 *         <td>complexcontent<br>empty sequence</td>
 *         <td> </td>
 *       </tr>
 * 
 *       <tr valign="top">
 *         <td>Class</td>
 *         <td>X</td>
 *         <td>1 [ <tt>@XmlValue</tt> property] && <br> [property]+
 *             ->attributes</td> 
 *         <td>simplecontent</td>
 *         <td> </td>
 *       </tr>
 * 
 *       <tr valign="top">
 *         <td>Class</td>
 *         <td>X</td>
 *         <td>1 [ <tt>@XmlValue</tt> property ]&& <br> no properties
 *         -> attribute</td> 
 *         <td> </td>
 *         <td>simpletype</td>
 *         <td> </td>
 *       </tr>
 *     </tbody>
 *   </table>
 * </blockquote>
 * 
 * <p> <b> Example 1: </b> Map a class to a complex type with
 *     xs:sequence </p>
 *
 * <pre>
 *   public class USAddress {
 *     String getName() {..};
 *     void setName(String) {..};
 * 
 *     String getStreet() {..};
 *     void setStreet(String) {..};
 *
 *     String getCity() {..}; 
 *     void setCity(String) {..};
 * 
 *     String getState() {..};
 *     void setState(String) {..};
 *
 *     java.math.BigDecimal getZip() {..};
 *     void setZip(java.math.BigDecimal) {..};
 * }
 *
 * &lt;!-- XML Schema mapping for USAddress -->
 * &lt;xs:complexType name="USAddress">
 *   &lt;xs:sequence>
 *     &lt;xs:element name="name" type="xs:string"/>
 *     &lt;xs:element name="street" type="xs:string"/>
 *     &lt;xs:element name="city" type="xs:string"/>
 *     &lt;xs:element name="state" type="xs:string"/>
 *     &lt;xs:element name="zip" type="xs:decimal"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 *
 * </pre>
 * <p>
 * <p> <b> Example 2: </b> Map a class to a complex type with
 *     xs:all </p>
 *
 * <pre>
 * &#64;XmlType(propOrder={})
 * public class USAddress { ...}
 * 
 * &lt;!-- XML Schema mapping for USAddress -->
 * &lt;xs:complexType name="USAddress">
 *   &lt;xs:all>
 *     &lt;xs:element name="name" type="xs:string"/>
 *     &lt;xs:element name="street" type="xs:string"/>
 *     &lt;xs:element name="city" type="xs:string"/>
 *     &lt;xs:element name="state" type="xs:string"/>
 *     &lt;xs:element name="zip" type="xs:decimal"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 *</pre>
 * <p> <b> Example 3: </b> Map a class to a complex type with
 *   xs:sequence with a customized ordering of JavaBean properties. 
 * </p>
 *
 * <pre>
 *   &#64;XmlType(propOrder={"street", "city" , "state", "zip", "name" })
 *   public class USAddress { ...}
 *
 *   &lt;!-- XML Schema mapping for USAddress -->
 *   &lt;xs:complexType name="USAddress">
 *     &lt;xs:all>
 *       &lt;xs:element name="name" type="xs:string"/>
 *       &lt;xs:element name="street" type="xs:string"/>
 *       &lt;xs:element name="city" type="xs:string"/>
 *       &lt;xs:element name="state" type="xs:string"/>
 *       &lt;xs:element name="zip" type="xs:decimal"/>
 *     &lt;/xs:all>
 *   &lt;/xs:complexType> 
 * </pre>
 *
 * <p> <b> Example 4: </b> Map a class to a complex type with
 *   xs:sequence with a customized ordering of JavaBean properties. 
 * </p>
 *
 * <pre>
 *   &#64;XmlType(propOrder={"street", "city" , "state", "zip", "name" })
 *   public class USAddress { ...}
 *
 *   &lt;!-- XML Schema mapping for USAddress -->
 *   &lt;xs:complexType name="USAddress">
 *     &lt;xs:all>
 *       &lt;xs:element name="name" type="xs:string"/>
 *       &lt;xs:element name="street" type="xs:string"/>
 *       &lt;xs:element name="city" type="xs:string"/>
 *       &lt;xs:element name="state" type="xs:string"/>
 *       &lt;xs:element name="zip" type="xs:decimal"/>
 *     &lt;/xs:all>
 *   &lt;/xs:complexType>
 * </pre>
 *
 * <p> <b> Example 5: </b> Map a class to a global element with an
 * anonymous type. 
 * </p>
 * <pre>
 *   &#64;XmlRootElement
 *   &#64;XmlType(name="")
 *   public class USAddress { ...}
 *
 *   &lt;!-- XML Schema mapping for USAddress -->
 *   &lt;xs:element name="USAddress">
 *     &lt;xs:complexType>
 *       &lt;xs:sequence>
 *         &lt;xs:element name="name" type="xs:string"/>
 *         &lt;xs:element name="street" type="xs:string"/>
 *         &lt;xs:element name="city" type="xs:string"/>
 *         &lt;xs:element name="state" type="xs:string"/>
 *         &lt;xs:element name="zip" type="xs:decimal"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexType>
 *   &lt;/xs:element>
 * </pre>
 *
 * <p> <b> Example 6: </b> Map a property to a local element with
 * anonmyous type. 
 * <pre>
 *   //Example: Code fragment
 *   public class Invoice {
 *       USAddress addr;
 *           ...
 *       }
 *
 *   &#64;XmlType(name="")
 *   public class USAddress { ... }
 *   } 
 *
 *   &lt;!-- XML Schema mapping for USAddress -->
 *   &lt;xs:complexType name="Invoice">
 *     &lt;xs:sequence>
 *       &lt;xs:element name="addr">
 *         &lt;xs:complexType>
 *           &lt;xs:element name="name", type="xs:string"/>
 *           &lt;xs:element name="city", type="xs:string"/>
 *           &lt;xs:element name="city" type="xs:string"/>
 *           &lt;xs:element name="state" type="xs:string"/>
 *           &lt;xs:element name="zip" type="xs:decimal"/>
 *         &lt;/xs:complexType>
 *       ...
 *     &lt;/xs:sequence>
 *   &lt;/xs:complexType> 
 * </pre>
 *
 * <p> <b> Example 7: </b> Map a property to an attribute with
 * anonmyous type.
 * 
 * <pre>
 *
 *     //Example: Code fragment
 *     public class Item {
 *         public String name;
 *         &#64;XmlAttribute 
 *         public USPrice price;
 *     }
 *    
 *     // map class to anonymous simple type. 
 *     &#64;XmlType(name="")
 *     public class USPrice { 
 *         &#64;XmlValue
 *         public java.math.BigDecimal price;
 *     }
 *
 *     &lt;!-- Example: XML Schema fragment -->
 *     &lt;xs:complexType name="Item">
 *       &lt;xs:sequence>
 *         &lt;xs:element name="name" type="xs:string"/>
 *         &lt;xs:attribute name="price">
 *           &lt;xs:simpleType>
 *             &lt;xs:restriction base="xs:decimal"/>
 *           &lt;/xs:simpleType>
 *         &lt;/xs:attribute>
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexType>
 * </pre>
 *
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @see XmlElement
 * @see XmlAttribute
 * @see XmlValue
 * @see XmlSchema
 * @since JAXB2.0
 * @version $Revision: 1.5 $
 */

@Retention(RUNTIME) @Target({TYPE})
public @interface XmlType {
    /**
     * Name of the XML Schema type which the class is mapped.
     */
    String name() default "##default" ;
 
    /**
     * Specifies the order for XML Schema elements when class is
     * mapped to a XML Schema complex type.
     * 
     * <p> Refer to the table for how the propOrder affects the
     * mapping of class </p>
     * 
     * <p> The propOrder is a list of names of JavaBean properties in
     *     the class. Each name in the list is the name of a Java
     *     identifier of the JavaBean property. The order in which
     *     JavaBean properties are listed is the order of XML Schema
     *     elements to which the JavaBean properties are mapped. </p>
     * <p> All of the JavaBean properties being mapped must be
     *     listed (i.e. if a JavaBean property mapping is prevented
     *     by <tt>@XmlTransient</tt>, then it does not have to be
     *     listed). Otherwise, it is an error.
     * <p> By default, the JavaBean properties are ordered using a
     *     default order specified in the JAXB 2.0 specification.
     */
    String[] propOrder() default {""};

    /**
     * Name of the target namespace of the XML Schema type. By
     * default, this is the target namespace to which the package
     * containing the class is mapped.
     */
    String namespace() default "##default" ;
}


