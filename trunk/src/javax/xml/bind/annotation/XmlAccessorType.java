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
 * <p> The XmlAccessorType can be used with the following
 * program elements:</p>
 * 
 * <ul> 
 *   <li> package</li>
 *   <li> a top level value class </li>
 * </ul>
 *
 * Although the description below describes only top level value
 * class, the description applies to nested classes as well, since
 * nested classes are like top level classes.
 *
 * <p> See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <p>This annotation can be used to specify whether a a field or
 * property is serialized by default. This allows a class designer to
 * select the default serialization of a class to be either fields or
 * getters/setters.
 * 
 * <p>The annotation <tt> XmlAccessorType </tt> on a package applies to
 * all types in the package. But the following rules inheritance
 * semantics apply to the <tt> XmlAccessorType </tt> on a class:
 *
 * <ul>
 *   <li> If there is an annotation on the top level value class, then
 *        it is used. </li>
 *   <li> Otherwise, if a <tt> XmlAccessorType </tt> exists on one of
 *        its super classes, then it is inherited.
 *   <li> Otherwise, the <tt> XmlAccessorType </tt> on a package is
 *        inherited.
 * </ul>
 * <p> <b> Defaulting Rules: </b> </p>
 *
 * <p>By default, if <tt> XmlAccessorType </tt> on a package is absent,
 * then the following package level annotation is assumed.</p>
 *
 *   XmlAccessorType(AccessType.PUBLIC_MEMBER)
 *
 * <p> By default, if XmlAccessorType on a top level value
 * class is absent and none of super classes is annotated with <tt>
 * XmlAccessorType </tt>, then the following default is assumed: </p>
 *
 *   XmlAccessorType(AccessType.PUBLIC_MEMBER)
 *
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @since JAXB2.0
 * @version  $Revision: 1.3 $
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
