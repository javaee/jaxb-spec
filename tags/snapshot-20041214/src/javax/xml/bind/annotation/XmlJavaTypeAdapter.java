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
 * Adapts the mapping of a Java type with no natural mapping to
 * a XML Schema representation.
 * <p>
 * Some Java types do not map naturally to XML Schema representation, for
 * example <tt>HashMap</tt>. For such types, a default mapping is defined. But
 * the default mapping can be overridden using <tt>&#64;XmlJavaTypeAdapter</tt>. The
 * <tt>&#64;XmlJavaTypeAdapter</tt> annotation allows the type of a JavaBean property to be adapted
 * using a custom value type. An custom value type models the schema to which
 * the type of a JavaBean property is to be mapped. Thus, it is the
 * custom value type rather than the static type of the JavaBean property
 * that is used in marshalling and unmarshalling.
 * <ul>
 *   <li> During marshalling, an instance of a type of a JavaBean property
 *        is converted to an instance of custom value type. To convert, JAXB
 *        binding framework invokes the method specified in
 *        printMethod annotation member. After conversion, the
 *        instance of custom value type is marshalled.  </li>
 *
 *   <li> During unmarshalling, a value is first unmarshalled into an
 *        instance of the custom value type. The custom value type is converted
 *        into an instance of the type of the JavaBean property. To
 *        convert, JAXB binding framework invokes the method specified
 *        in the parseMethod.</li> 
 * </ul>
 * 
 * <p><b>Usage</b></p>
 * Use of this annotation involves the following steps:
 * <ul>
 *   <li> Writing an custom value type. The custom value type is mapped using
 *        the Java to XML Schema mapping rules. </li>
 *   <li> Write the type conversion methods <tt>parseMethod</tt> and <tt>printMethod</tt>.
 *   <li> Specify the custom value type in <tt>&#64;XmlJavaTypeAdapter.javaType()</tt></li>
 *   <li> Specify the parseMethod in <tt>&#64;XmlJavaTypeAdapter.parseMethod()</tt></li>
 *   <li> Specify the printMethod in <tt>&#64;XmlJavaTypeAdapter.printMethod()</tt></li>
 * </ul>
 *
 * 
 * <p><b>Example:</b> Customized mapping of </tt>HashMap</tt></p>
 * <p>
 * The following example illustrates the use of <tt>&#64;XmlJavaTypeAdapter</tt>
 * annotation by using an custom value type to customize the mapping of a
 * <tt>HashMap</tt>.
 * </p>
 * 
 * <pre>
 *     // Example: Custom Value class for customized mapping of HashMap
 *     public class MyHashMapType {
 *
 *         // method specified in XmlJavaTypeAdapter.parseMethod
 *         // and invoked by JAXB binding framework during
 *         // unmarshalling.  
 *         public static HashMap createHashMap(MyHashMapType) {..};
 *
 *         // printMethod specified in XmlJavaTypeAdapter.printMethod and
 *         // invoked by JAXB Binding framework during marshalling. 
 *         public static MyHashMapType createMyHashMapType(HashMap ){..};
 *
 *         List <MyHashMapEntryType> entry;
 *     }
 *
 *     // Example: Customizations map class to a complex type with
 *     // simpleContent.   
 *     public class MyHashMapEntryType {
 *         &#64;XmlAttribute();
 *         public int key; 
 *
 *         &#64;XmlValue();
 *         public String value;
 *     }
 *
 *     // The custom value type is specified on a property
 *     &#64;XmlJTypeAdapter(javaType = MyHashMapType, 
 *                      parseMethod = MyHashMapType.createHashMap
 *                      printMethod = MyHashMapType.createMyHashMap
 *     HashMap hashmap;
 * 
 *     &lt;-- Example: XML Schema mapping for MyHashMapType -->
 *
 *     &lt;xs:complexType name="myHashMapType">
 *       &lt;xs:sequence>
 *         &lt;xs:element name="entry" type="myHashMapEntryType"
 *                        maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexType>
 *
 *     &lt;!-- XML Schema mapping for myHashMapType -->
 *     &lt;xs:complexType name="myHashMapEntryType">
 *       &lt;xs:simpleContent>
 *         &lt;xs:extension base="xs:string"/>
 *       &lt;/xs:simpleContent>
 *       &lt;xs:attribute name="key" type="xs:int"/>
 *     &lt;/xs:complexType>
 *
 *     &lt;xs:element name="hashmap" type="myHashMapType"/>
 *
 * </pre>
 * Thus, with the custom value type installed, the XML serialized form for
 * marshalling and umarshalling of HashMap is:
 * 
 * <pre>
 *
 *     &lt;hashmap>
 *         &lt;entry key="id123">this is a value&lt;/entry>
 *         &lt;entry key="id312">this is another value&lt;/entry>
 *         ...
 *       &lt;/hashmap>  
 * </pre>
 *
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @since JAXB2.0
 * @version $Revision: 1.3 $
 */

@Retention(RUNTIME) @Target({FIELD,METHOD,TYPE,PACKAGE})
public @interface XmlJavaTypeAdapter {
    /**
     * Name of the custom value type used in marshall and unmarshalling.
     */
    java.lang.Class javaType();

    /** 
     * Name of a static method that converts a JavaBean property type
     * to a custom value type.
     * 
     */
    String printMethod();

    /** 
     * Name of a static method that converts a custom value type to
     * JavaBean property type.
     *
    */
    String parseMethod();
}
