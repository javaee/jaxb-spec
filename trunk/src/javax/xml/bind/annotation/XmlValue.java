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
 * <p>
 * Enables mapping a class to a  XML Schema complex type with a
 * simpleContent or a XML Schema simple type. 
 * </p>
 *
 * <p>
 * <b> Usage: </b>
 * <p>
 * The <tt>@XmlValue</tt> annotation can be used with the following program
 * elements: 
 * <ul> 
 *   <li> a JavaBean property.</li>
 *   <li> field.</li>
 * </ul>
 * 
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * The usage is subject to the following usage constraints:
 * <ul>
 *   <li>At most one JAXB field or property can be annotated with the
 *       <tt>@XmlValue</tt> annotation. </li> 
 *
 *   <li><tt>@XmlValue</tt> must be the only mapping annotation on the
 *       JavaBean property.</li> 
 *
 *   <li>The type of the JavaBean property must be mapped to
 *       a XML Schema simple type.</li> 
 *
 *   <li>If the containing class extends another class, then the
 *       superclass and the containing class must both be mapped to
 *       XML Schema types in the same XML Schema type hierarchy. </li>
 *
 *   <li>If the JavaBean property is a read/write property, then
 *       <tt>@XmlValue</tt> can be used to annotate either the getter 
 *       or setter method but not both.</li> 
 *
 * </ul>
 * </p>
 * <p>
 * If the annotated JavaBean property is the sole class member being
 * mapped to XML Schema construct, then the class is mapped to a
 * simple type. 
 *
 * If there are additional JavaBean properties (other than the
 * JavaBean property annotated with <tt>@XmlValue</tt> annotation)
 * that are mapped to XML attributes, then the class is mapped to a
 * complex type with simpleContent.
 * </p>
 *
 * <p> <b> Example 1: </b> Map a class to XML Schema simpleType</p>
 *
 *   <pre>
 * 
 *     // Example 1: Code fragment
 *     public class USPrice {
 *         &#64;XmlValue
 *         public java.math.BigDecimal price;
 *     }
 *  
 *     &lt;!-- Example 1: XML Schema fragment -->
 *     &lt;xs:simpleType name="USPrice">
 *       &lt;xs:restriction base="xs:decimal"/>
 *     &lt;/xs:simpleType>
 *
 *   </pre>
 * 
 * <p><b> Example 2: </b> Map a class to XML Schema complexType with
 *        with simpleContent.</p>
 * 
 *   <pre>
 *
 *   // Example 2: Code fragment
 *   public class InternationalPrice {
 *       &#64;XmlValue
 *       public java.math.BigDecimal price;
 * 
 *       &#64;XmlAttribute
 *       public String currency;
 *   }
 *  
 *   &lt;!-- Example 2: XML Schema fragment -->
 *   &lt;xs:complexType name="InternationalPrice">
 *     &lt;xs:simpleContent>
 *       &lt;xs:extension base="xs:decimal">
 *         &lt;xs:attribute name="currency" type="xs:string"/>
 *       &lt;/xs:extension>
 *     &lt;/xs:simpleContent>
 *   &lt;/xs:complexType> 
 *
 *   </pre>
 * </p>
 *
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @see XmlType
 * @since JAXB2.0
 * @version $Revision: 1.5 $
 */

@Retention(RUNTIME) @Target({FIELD, METHOD})
public @interface XmlValue {}
