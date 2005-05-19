package javax.xml.bind.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

/**
 * <p>
 * Modifies property mapping to support mixed content.
 *
 * <p>
 * Instances of java.lang.String represent XML text.
 * <p>
 * The usage is subject to the following constraints:
 * <ul>
 *   <li> can be used with &#64;XmlElementRef or &#64;XmlAnyElement</li>
 * </ul>
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 * @author Kohsuke Kawaguchi
 */
@Retention(RUNTIME)
@Target({FIELD,METHOD})
public @interface XmlMixed {
}
