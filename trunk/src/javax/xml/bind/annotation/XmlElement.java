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
 * Maps a JavaBean property to a XML element.
 *
 * <hr>
 * <b> Note To reviewers: </b> JAXB 2.0 ED 0.40 allowed the use of
 * this annotation on both Javabean property and class. However, now
 * it can only be used with a Javabean property. &#64;XmlRootElement
 * is to be used for annotating the class. So examples and description
 * that applied to class have been moved into the &#64;XmlRootElement
 * annotation.
 * <hr>
 *
 * <p> <b>Usage</b> </p>
 * <p>
 * The &#64;XmlElement annotation can be used with the following program
 * elements: 
 * <ul> 
 *   <li> a JavaBean property </li>
 *   <li> a public non final, non static field </li>
 * </ul>
 * 
 * The usage is subject to the following constraints:
 * <ul> 
 *   <li> The only other mapping annotations allowed with 
 *        <tt>&#64;XmlElement</tt> are: <tt>&#64;XmlID</tt> and <tt>&#64;XMLIDREF</tt> .</li>
 * </ul>
 *
 * <p><b> TBD after Early Access Version 0.40 </b>
 *  The &#64;Target meta annotation permits the usage of  &#64;XmlType
 *  annotation on an interface and enum construct. The mapping
 *  of interface and enum construct will be addressed after the Early
 *  Access Version 0.40 and this Javadoc will be updated accordingly. 
 * </p>
 * <p>
 * A JavaBean property, when annotated with &#64XmlElement annotation
 * is mapped to a local element in the XML Schema complex type to
 * which the containing class is mapped.
 * 
 * <p>
 * <b>Example 1: </b> Map a public non static non final field to local
 * element
 * <pre>
 *     //Example: Code fragment
 *     public class USPrice {
 *         &#64;XmlElement(name="itemprice")
 *         public java.math.BigDecimal price;
 *     }
 *
 *     &lt;!-- Example: Local XML Schema element -->
 *     &lt;xs:complexType name="USPrice"/>
 *       &lt;xs:sequence>
 *         &lt;xs:element name="itemprice" type="xs:decimal"/>
 *       &lt;/sequence>
 *     &lt;/xs:complexType>
 *   </pre>
 * <p>
 * 
 * <p>
 * <b>Example 2: </b> Associate a global element with XML Schema type
 * to which the class is mapped.
 * <p>
 * <b> Note to Reviewers: </b> Moved to {@link XmlRootElement}
 * <p>
 * <b> Example 3: </b> Map a field to a nillable element.
 *   <pre>
 * 
 *     //Example: Code fragment
 *     public class USPrice {
 *         &#64;XmlElement(nillable=true)
 *         public java.math.BigDecimal price;
 *     }
 *
 *     &lt;!-- Example: Local XML Schema element -->
 *     &lt;xs:complexType name="USPrice">
 *       &lt;xs:sequence>
 *         &lt;xs:element name="price" type="xs:decimal" nillable="true"/>
 *       &lt;/sequence>
 *     &lt;/xs:complexType>
 *   </pre>
 * <p>
 * 
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @since JAXB2.0
 * @version $Revision: 1.6 $
 */

@Retention(RUNTIME) @Target({FIELD, METHOD, TYPE})
public @interface XmlElement {
    /**
     *
     * Name of the XML Schema element. 
     * <p> If the value is "", then element name is derived from the
     * Javabean property name. 
     */
    String name() default "";
 
    /**
     * Customize the element declaration to be nillable. 
     * <p>If nillable() is true, then the JavaBean property is
     * mapped to a XML Schema nillable element declaration. 
     * <p>If nillable() is false and the JavaBean property type is a
     * collection type, then the JavaBean property is mapped to
     * repeating occurance. 
     * <p> Otherwise, the JavaBean property is mapped to an an 
     * XML Schema element declaration with occurance range of 0..1.
     */
    boolean nillable() default false;

    /**
     * Specifies the XML target namespace of the XML Schema
     * element. The targetNamespace() must be a valid namespace URI.
     * <p>
     * It the value is "##default", then the namespace is the
     * namespace of the containing class.
     * <p>
     * <b>Note to Reviewers: </b> TBD. address in later version
     * if targetNamespace() is different from that of the containing
     * class. 
     */
    String targetNamespace() default "##default" ;
}


