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
 * <b> !!!! Note to Reviewers !!!! : </b>
 * {@link XmlCollection} and {@link XmlCollectionItem} are now
 * obsolete. See {@link XmlElements} {@link XmlElementRefs} and 
 * {@link XmlElementWrapper}.This annotation is retained for review
 * purposes  and will be removed at a later time.
 * <p>
 * 
 * Maps a collection type to a XML Schema complex type.
 *
 * <p><b>Usage</b></p>
 * <p>
 * The <tt>@XmlCollection</tt> annotation can be used with the
 * following program elements: 
 * <ul> 
 *   <li> a JavaBean property </li>
 *   <li> field </li>
 * </ul>
 * 
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <p>The mapping is designed to support two forms of seralization
 * shown below. 
 * <pre>
 *    //Example: code fragment
 *      int[] Names;
 *
 *    // XML Serialization Form 1 (Unwrapped collection)
 *    // Element name is derived from property or field name
 *    &lt;Names> ... &lt;/Names>
 *    &lt;Names> ... &lt;/Names>
 * 
 *    // XML Serialization Form 2 ( Wrapped collection )
 *    // Element name of wrapper is derived from JavaBean property
 *    // Element name of collection item is derived from its type name
 *    &lt;Names>
 *       &lt;int> value-of-item &lt;/int>
 *       &lt;int> value-of-item &lt;/int>
 *       ....
 *    &lt;/Names>
 *
 * </pre>
 *
 * <p> The two serialized XML forms allow a null collection to be
 * represented either by absence or presence of an element with a
 * nillable attribute. The <tt>@XmlElement</tt> annotation on the
 * JavaBean property is used to customize the schema corresponding to the above
 * XML serialization forms. 
 * 
 * The examples that follow illustrate ways to select wrapped and
 * unwrapped serialization forms as well as customizing them.
 *
 * <p>The usage is subject to the following constraints:
 * <ul>
 *   <li> type of the JavaBean property must be one of: indexed
 *        property, array, List, java.util.Set.
 *        <br> java.util.Map does not map naturally to XML Schema
 *        construct. Hence <tt>@XmlJavaTypeAdapter</tt> should be used
 *        to customize <tt>java.util.Map</tt>.</li>
 *
 *   <li> if <tt>@XmlElement.isNillable()</tt> is false, then for every
 *        <tt>@XmlCollectionItem</tt> in <tt>@XmlCollection.values()</tt>,
 *        <tt>@XmlCollectionItem.name()</tt> must be "". The element
 *        for the collection item is derived from the JavaBean
 *        property name. </li>
 *
 *   <li> if the type of JavaBean property is a collection type of
 *        array, an indexed property, or a parameterized list, then
 *        <tt>@XmlCollectionItem.type()</tt> must be null. The type is
 *        already known.</li>
 * </ul>
 * 
 * <p><b>Example 1:</b>Customizing both element and type for
 *                 collections items </p> 
 * <pre>
 *
 *     // Example 1: Customizing both element and type for collection items
 *     &#64;XmlElement(name="ItemsList", isNillable=true)
 *     &#64;XmlCollection(
 *             { &#64;XmlCollectionItem(type=java.lang.Integer.class,
 *                   value=&#64;XmlElement(name="A")), 
 *               &#64;XmlCollectionItem(type=java.lang.Float.class,
 *                   value=&#64;XmlElement(name="B")) 
 *             }
 *     public List items;
 *   
 *     &lt;!-- XML Schema fragment -->
 *        &lt;xs:element name="ItemsList" nillable="true">
 *          &lt;xs:complexType>
 *            &lt;xs:choice minOccurs="0" maxOccurs="unbounded">
 *              &lt;xs:element name="A" type="xs:int"/>
 *              &lt;xs:element name="B" type="xs:float"/>
 *            &lt;/xs:choice>
 *          &lt;/xs:complexType>
 *        &lt;/xs:element>
 * </pre>
 *
 * <p><b>Example 2:</b>Customizing only types for collection items.</p>
 * <pre>
 *  
 *     // Example 2: Customizing only types for collection items 
 *     // This is same as the above example except that for Float,
 *     // only the type is customized but not the element name.
 *       
 *     &#64;XmlElement(name="ItemsList", isNillable=true)
 *     &#64;XmlCollection(
 *             { &#64;XmlCollectionItem(type=Integer.class, value=&#64;XmlElement(name="A")),
 *               &#64;XmlCollectionItem(type=Float.class)
 *             }
 *     public List items;
 *   
 *     &lt;!-- XML Schema fragment -->
 *        &lt;xs:element name="ItemsList" nillable="true">
 *          &lt;xs:complexType>
 *            &lt;xs:choice minOccurs="0" maxOccurs="unbounded">
 *              &lt;xs:element name="A" type="xs:int"/>
 *              &lt;xs:element name="float" type="xs:float"/>
 *            &lt;/xs:choice>
 *          &lt;/xs:complexType>
 *        &lt;/xs:element>
 *   
 * </pre>
 *
 * <p><b>Example 3:</b>Customization of types and mapping of java.lang.Object to xs:anyType</p>
 * <pre>
 *     // Example 3 - customizing only types for collection items
 *     //             & mapping java.lang.Object to anyType.
 *       
 *     &#64;XmlElement(name="ItemsList", isNillable=true)
 *     &#64;XmlCollection(
 *             { &#64;XmlCollectionItem(type=Integer.class, value=&#64;XmlElement(name="A")),
 *               &#64;XmlCollectionItem(type=Float.class)
 *               &#64;XmlCollectionItem(type=Object.class)
 *             }
 *     public List items;
 *   
 *     &lt;!-- XML Schema fragment -->
 *       &lt;xs:element name="ItemsList" minOccurs="0" maxOccurs="1">
 *         &lt;xs:complexType>
 *           &lt;xs:choice minOccurs="0" maxOccurs="unbounded">
 *             &lt;xs:element name="A" type="xs:int"/>
 *             &lt;xs:element name="float" type="xs:float"/>
 *             &lt;xs:element name="Object" type="xs:anyType"/>
 *         &lt;/xs:choice>
 *       &lt;/xs:complexType>
 *     &lt;/xs:element> 
 * </pre>
 * 
 *
 * <p><b>Example 4:</b>Map to an unwrapped collection</p>
 *
 * <pre>       
 *
 *   public class PurchaseOrder {
 *     &#64;XmlElement(name="ItemsList", isNillable=false)
 *     &#64;XmlCollection(
 *             { &#64;XmlCollectionItem(type=Integer.class)}
 *     public List items;
 *   }
 *   
 *   &lt;!-- XML Schema fragment shown ->
 *
 *   &lt;xs:complexType name="PurchaseOrder">
 *     &lt;xs:choice minOccurs="0" maxOccurs="unbounded">
 *       &lt;xs:element name="ItemsList" type="xs:int"/>
 *     &lt;/xs:choice>
 *   &lt;/xs:complexType>
 * 
 * </pre>
 *
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @see XmlCollectionItem
 * @see XmlJavaTypeAdapter
 * @version $Revision: 1.4 $
 * @since JAXB2.0
 */

@Retention(RUNTIME) @Target({FIELD, METHOD})
public @interface XmlCollection {

    /**
     * Collection of annotations one per collection type.
     */
    XmlCollectionItem[] values() ;
}

 
