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
 * Prevents the mapping of a JavaBean property to a XML Schema construct.
 * <p>
 * The <tt>@XmlTransient</tt> annotation is useful for resolving name
 * collisions between a JavaBean property name and a field name. A
 * name collision can occur when the uncapitalized JavaBean property name
 * and a field name are the same. If the JavaBean property refers to the
 * field, then the name collision can be resolved by mapping
 * preventing the mapping of either the field or the JavaBean
 * property using the <tt>@XmlTransient</tt> annotation.
 * <p><b>Usage</b></p>
 * <p> The <tt>@XmlTransient</tt> annotation can be used with the following
 *     program elements: 
 * <ul> 
 *   <li> a JavaBean property </li>
 *   <li> a public non final, non static field </li>
 * </ul>
 * 
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <p>
 * The usage is subject to the following usage constraints:
 * <ul>
 *   <li><tt>@XmlTransient</tt> must be the only mapping annotation on the
 *       JavaBean property</li> 
 *   <li>If the JavaBean property is a read/write property, then
 *       <tt>@XmlTransient</tt> can be used to annotate either the getter 
 *       or setter method but not both.</li> 
 *   </ul>
 * </p>
 * <p><b>Example:</b> Resolve name collision between JavaBean property and
 *     field name </p>
 * 
 * <pre>
 *   // Example: Code fragment
 *   public class USAddress {
 *
 *       // The field name "name" collides with the property name 
 *       // obtained by bean uncapitalization of getName() below
 *       &#64;XmlTransient public String name;
 *
 *       String getName() {..};
 *       String setName() {..};
 *   }
 *
 *    
 *   &lt;!-- Example: XML Schema fragment -->
 *   &lt;xs:complexType name="USAddress">
 *     &lt;xs:sequence>
 *       &lt;xs:element name="name" type="xs:string"/>
 *     &lt;/xs:sequence>
 *   &lt;/xs:complexType>
 * </pre>
 *
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @since JAXB2.0
 * @version $Revision: 1.2 $
 */

@Retention(RUNTIME) @Target({FIELD, METHOD})
public @interface XmlTransient {}
   
