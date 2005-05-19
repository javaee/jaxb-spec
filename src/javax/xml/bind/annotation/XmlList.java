package javax.xml.bind.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * Used to mark an element property with a list simple type.
 *
 * <p>
 * When a collection property is annotated just with @XmlElement,
 * it means each item in the collection will be wrapped by an element.
 * For example,
 *
 * <pre>
 * &#64;XmlRootElement
 * class Foo {
 *     &#64;XmlElement
 *     List&lt;String> data;
 * }
 * </pre>
 *
 * would produce XML like this:
 *
 * <pre><xmp>
 * <foo>
 *   <data>abc</data>
 *   <data>def</data>
 * </foo>
 * </xmp></pre>
 *
 * &#64;XmlList annotations allows you to put multiple values as
 * whitespace-separated tokens into a single element. For example,
 *
 * <pre>
 * &#64;XmlRootElement
 * class Foo {
 *     &#64;XmlElement
 *     &#64;XmlList
 *     List&lt;String> data;
 * }
 * </pre>
 *
 * the above code will produce XML like this:
 *
 * <pre><xmp>
 * <foo>
 *   <data>abc def</data>
 * </foo>
 * </xmp></pre>
 *
 *
 *
 * <h2>Interaction with other annotations</h2>
 * <p>
 * This annotation can be only used on a property/field that has either explicit
 * or implicit {@link XmlElement} annotation.
 *
 * IOW, this annotation cannot be used with {@link XmlAttribute}, {@link XmlValue},
 * nor {@link XmlElementRef}.
 *
 *
 * @author Kohsuke Kawaguchi
 */
@Retention(RUNTIME) @Target({FIELD,METHOD,PARAMETER})
public @interface XmlList {
}
