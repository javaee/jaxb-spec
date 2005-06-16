/*
 * Copyright 2005 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Inherited;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * <p> Controls whether fields or Javabean properties are serialized by default. </p>
 * 
 * <p> <b> Usage </b> </p>
 *
 * <p> <tt>@XmlAccessorType</tt> annotation can be used with the following program elements:</p>
 * 
 * <ul> 
 *   <li> package</li>
 *   <li> a top level class </li>
 * </ul>
 *
 * <p> See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <p>This annotation provides control over the default serialization
 * of properties and fields in a class.
 * 
 * <p>The annotation <tt> @XmlAccessorType </tt> on a package applies to
 * all classes in the package. The following inheritance
 * semantics apply:
 *
 * <ul>
 *   <li> If there is a <tt>@XmlAccessorType</tt> on a class, then it
 *        is used. </li>  
 *   <li> Otherwise, if a <tt>@XmlAccessorType</tt> exists on one of
 *        its super classes, then it is inherited.
 *   <li> Otherwise, the <tt>@XmlAccessorType </tt> on a package is
 *        inherited.
 * </ul>
 * <p> <b> Defaulting Rules: </b> </p>
 *
 * <p>By default, if <tt>@XmlAccessorType </tt> on a package is absent,
 * then the following package level annotation is assumed.</p>
 * <pre>
 *   &#64;XmlAccessorType(AccessType.PUBLIC_MEMBER)
 * </pre>
 * <p> By default, if <tt>@XmlAccessorType</tt> on a class is absent,
 * and none of its super classes is annotated with
 * <tt>@XmlAccessorType</tt>, then the following default on the class
 * is assumed: </p>
 * <pre>
 *   &#64;XmlAccessorType(AccessType.PUBLIC_MEMBER)
 * </pre>
 *
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @since JAXB2.0
 * @see AccessType
 * @version  $Revision: 1.4 $
 */

@Inherited @Retention(RUNTIME) @Target({PACKAGE, TYPE})
public @interface XmlAccessorType {

    /**
     * Specifies whether fields or properties are serialized. 
     * 
     * @see AccessType
     */
    AccessType value() default AccessType.PUBLIC_MEMBER;
}
