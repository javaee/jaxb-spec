package javax.xml.bind.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Generates a wrapper element around XML representation.
 *
 * This is primarily intended to be used to produce a wrapper
 * XML element around collections. The annotation therefore supports
 * two forms of serialization shown below. 
 *
 * <pre>
 *    //Example: code fragment
 *      int[] Names;
 *
 *    // XML Serialization Form 1 (Unwrapped collection)
 *    &lt;Names> ... &lt;/Names>
 *    &lt;Names> ... &lt;/Names>
 * 
 *    // XML Serialization Form 2 ( Wrapped collection )
 *    &lt;Names>
 *       &lt;int> value-of-item &lt;/int>
 *       &lt;int> value-of-item &lt;/int>
 *       ....
 *    &lt;/Names>
 * </pre>
 *
 * <p> The two serialized XML forms allow a null collection to be
 * represented either by absence or presence of an element with a
 * nillable attribute.
 * 
 * <p> <b>Usage</b> </p>
 * <p>
 * The <tt>@XmlElementWrapper</tt> annotation can be used with the
 * following program elements: 
 * <ul> 
 *   <li> JavaBean property </li>
 *   <li> public non final, non static field </li>
 * </ul>
 *
 * It can be placed on a collection property along with 
 * along with either {@link XmlElement}, {@link XmlElements},
 * {@link XmlElementRef}, or   {@link XmlElementRefs}.
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * @author <ul><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Sekhar Vajjhala, Sun Microsystems, Inc.</li></ul>
 * @see XmlElement 
 * @see XmlElements
 * @see XmlElementRef
 * @see XmlElementRefs
 *
 */

@Retention(RUNTIME) @Target({FIELD, METHOD})
public @interface XmlElementWrapper {
    /**
     * Name of the XML wrapper element. By default, the XML wrapper
     * element name is derived from the JavaBean property name.
     */
    String name() default "##default";

    /**
     * XML target namespace of the XML wrapper element.
     * namespace() must be a valid namespace URI.
     *
     * <p>
     * The default value is determined as follows:
     * <ul>
     *   <li> If a top level class declared in a named package is
     *        annotated then the default target namespace is the
     *        namespace to which package name is mapped. </li>
     *   <li> TBD after Early Access version 0.40: Specify behavior if
     *        a top level class is declared in an unnamed package. </li>
     * </ul>
     */
    String namespace() default "##default" ;

    /**
     * If true, the absence of the collection is represented by
     * using <tt>xsi:nil='true'</tt>. Otherwise, it is represented by
     * the absence of the element.
     */
    boolean nillable() default false;
}
