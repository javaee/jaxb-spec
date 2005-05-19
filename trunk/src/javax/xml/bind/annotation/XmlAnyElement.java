/*
 * Copyright 2005 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind.annotation;

import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Maps a JAXB property to XML infoset representation and/or {@link JAXBElement).
 *
 * Such as:
 * <pre>
 * &#64;XmlAnyElement
 * public {@link Element}[] others;
 * 
 * // Collection of {@link Element} or {@link JAXBElement}.
 * &#64;XmlAnyElement(lax="true")
 * public {@link Object}[] others;
 *
 * &#64;XmlAnyElement
 * private List&lt;{@link Element}> nodes;
 *
 * &#64;XmlAnyElement
 * private {@link Element} node;
 * </pre>
 *
 * <p>
 * This annotation is mutually exclusive with
 * {@link XmlElement}, {@link XmlAttribute}, {@link XmlValue}, {@link XmlElementRef},
 * {@link XmlElements}, {@link XmlElementRefs}, {@link XmlID}, and {@link XmlIDREF}.
 *
 * <p>
 * This annotation can be used with {@link XmlJavaTypeAdapter}, so that users
 * can map their own data structure to DOM, which in turn be composed into XML.
 *
 * <p>
 * This annotation can be used with {@link XmlMixed} like this:
 * <pre>
 * // List of java.lang.String or {@link Element}
 * &#64;XmlAnyElement &#64;XmlMixed
 * List&lt;Object> others;
 * </pre>
 *
 * <p>
 * This annotation serves as a "catch-all" property for the unmarshaller.
 * Each element that does not match a static &#64;XmlElement annotation for
 * a JavaBean property on the class, is added to this element collection.
 * There can not be two {@link XmlAnyElement} annotations on the same class
 * (or between a class and its base class.)
 *
 * <p>
 * Design decision: simplify design by not representing wildcard namespace 
 * constraints.
 *
 * <h2>Schema To Java example</h2>
 *
 * The following schema would produce the following Java class:
 * <pre><xmp>
 * <xs:complexType name="foo">
 *   <xs:sequence>
 *     <xs:element name="a" type="xs:int" />
 *     <xs:element name="b" type="xs:int" />
 *     <xs:any namespace="##other" processContent="lax" minOccurs="0" maxOccurs="unbounded" />
 *   </xs:sequence>
 * </xs:complexType>
 * </xmp></pre>
 *
 * <pre>
 * class Foo {
 *   int a;
 *   int b;
 *   &#64;{@link XmlAnyElement}
 *   List&lt;Element> others;   // default property name is to be determined by the spec.
 * }
 * </pre>
 *
 * It can unmarshal instances like
 *
 * <pre><xmp>
 * <foo xmlns:e="extra">
 *   <a>1</a>
 *   <e:other />  // this will be bound to DOM, because unmarshalling is orderless
 *   <b>3</b>
 *   <e:other />
 *   <c>5</c>     // this will be bound to DOM, because the annotation doesn't remember namespaces.
 * </foo>
 * </xmp></pre>
 *
 *
 *
 * The following schema would produce the following Java class:
 * <pre><xmp>
 * <xs:complexType name="bar">
 *   <xs:complexContent>
 *   <xs:extension base="foo">
 *     <xs:sequence>
 *       <xs:element name="c" type="xs:int" />
 *       <xs:any namespace="##other" processContent="lax" minOccurs="0" maxOccurs="unbounded" />
 *     </xs:sequence>
 *   </xs:extension>
 * </xs:complexType>
 * </xmp></pre>
 *
 * <pre><xmp>
 * class Bar extends Foo {
 *   int c;
 *   // note that you won't get the 2nd wildcard.
 * }
 * </xmp></pre>
 *
 *
 * It can unmarshal instances like
 *
 * <pre><xmp>
 * <bar xmlns:e="extra">
 *   <a>1</a>
 *   <e:other />  // this will be bound to DOM, because unmarshalling is orderless
 *   <b>3</b>
 *   <e:other />
 *   <c>5</c>     // this now goes to Bar.c
 *   <e:other />  // this will go to Foo.others
 * </bar>
 * </xmp></pre>
 *
 *
 *
 *
 * <h2>Using {@link XmlAnyElement} with {@link XmlElementRef}</h2>
 * <p>
 * The {@link XmlAnyElement} annotation can be used with {@link XmlElementRef}s to
 * designate additional elements that can participate in the content tree.
 *
 * <p>
 * The following schema would produce the following Java class:
 * <pre><xmp>
 * <xs:complexType name="foo">
 *   <xs:choice maxOccurs="unbounded" minOccurs="0">
 *     <xs:element name="a" type="xs:int" />
 *     <xs:element name="b" type="xs:int" />
 *     <xs:any namespace="##other" processContent="lax" />
 *   </xs:sequence>
 * </xs:complexType>
 * </xmp></pre>
 *
 * <pre>
 * class Foo {
 *   &#64;{@link XmlAnyElement}
 *   &#64;{@link XmlElementRefs}({
 *     &#64;{@link XmlElementRef}(name="a")
 *     &#64;{@link XmlElementRef}(name="b")
 *   })
 *   {@link List}&lt;{@link Element}|{@link JAXBElement}> others;
 * }
 *
 * class ObjectFactory {
 *   ...
 *   {@link JAXBElement}&lt;Integer> createFooA( Integer i ) { ... }
 *   {@link JAXBElement}&lt;Integer> createFooB( Integer i ) { ... }
 * </pre>
 *
 * It can unmarshal instances like
 *
 * <pre><xmp>
 * <foo xmlns:e="extra">
 *   <a>1</a>     // this will unmarshal to a {@link JAXBElement} instance whose value is 1.
 *   <e:other />  // this will unmarshal to a DOM {@link Element}.
 *   <b>3</b>     // this will unmarshal to a {@link JAXBElement} instance whose value is 1.
 * </foo>
 * </xmp></pre>
 *
 *
 *
 *
 * <h2>W3C XML Schema "lax" wildcard emulation</h2>
 * The lax element of the annotation enables the emulation of the "lax" wildcard semantics.
 * For example, when the Java source code is annotated like this:
 * <pre>
 * &#64;{@link XmlRootElement}
 * class Foo {
 *   &#64;XmlAnyElement(lax=true)
 *   public {@link Object}[] others;
 * }
 * </pre>
 * then the following document will unmarshal like this:
 * <pre><xmp>
 * <foo>
 *   <unknown />
 *   <foo />
 * </foo>
 *
 * Foo foo = unmarshal();
 * // 1 for 'unknown', another for 'foo'
 * assert foo.others.length==2;
 * // 'unknown' unmarshals to a DOM element
 * assert foo.others[0] instanceof Element;
 * // because of lax=true, the 'foo' element eagerly
 * // unmarshals to a Foo object.
 * assert foo.others[1] instanceof Foo;
 * </xmp></pre>
 *
 *
 * @author Kohsuke Kawaguchi
 */
@Retention(RUNTIME)
@Target({FIELD,METHOD})
public @interface XmlAnyElement {

    /**
     * Controls the unmarshaller behavior when it sees elements
     * known to the current {@link JAXBContext}.
     *
     * <h3>When false</h3>
     * <p>
     * If false, all the elements that match the property will be unmarshalled
     * to DOM, and the property will only contain DOM elements.
     *
     * <h3>When true</h3>
     * <p>
     * If true, when an element matches a property marked with {@link XmlAnyElement}
     * is known to {@link JAXBContext} (for example, there's a class with
     * {@link XmlRootElement} that has the same tag name, or there's
     * {@link XmlElementDecl} that has the same tag name),
     * the unmarshaller will eagerly unmarshal this element to the JAXB object,
     * instead of unmarshalling it to DOM. Additionally, if the element is
     * unknown but it has a known xsi:type, the unmarshaller eagerly unmarshals
     * the element to a {@link JAXBElement}, with the unknown element name and
     * the JAXBElement value is set to an instance of the JAXB mapping of the 
     * known xsi:type.
     *
     * <p>
     * As a result, after the unmarshalling, the property can become heterogeneous;
     * it can have both DOM nodes and some JAXB objects at the same time.
     *
     * <p>
     * This can be used to emulate the "lax" wildcard semantics of the W3C XML Schema.
     */
    boolean lax() default false;

    /**
     * Specifies the {@link DomHandler} which is responsible for actually
     * converting XML from/to a DOM-like data structure.
     */
    Class<? extends DomHandler> value() default W3CDomHandler.class;
}
