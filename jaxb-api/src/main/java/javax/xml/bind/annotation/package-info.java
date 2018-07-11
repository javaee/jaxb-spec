/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2004-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

/**
 * Defines annotations for customizing Java program elements to XML Schema mapping.
 * <p>
 * <h2>Package Specification</h2>
 * <p>The following table shows the JAXB mapping annotations
 * that can be associated with each program element. </p>
 * <p>
 * <table class="striped" summary="Annotations for customizing Java program elements to XML Schema mapping">
 *   <thead>
 *     <tr>
 *       <th scope="col">Program Element</th>
 *       <th scope="col">JAXB annotation</th>
 *     </tr>
 *   </thead>
 *   <tbody style="text-align:left">
 *     <tr>
 *       <th scope="row" style="vertical-align:top">Package</th>
 *       <td>
 *         <ul style="list-style-type:none">
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlAccessorOrder.html">XmlAccessorOrder</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlAccessorType.html">XmlAccessorType</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlSchema.html">XmlSchema</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlSchemaType.html">XmlSchemaType</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlSchemaTypes.html">XmlSchemaTypes</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/adapters/XmlJavaTypeAdapter.html">XmlJavaTypeAdapter</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/adapters/XmlJavaTypeAdapters.html">XmlJavaTypeAdapters</a></li>
 *         </ul>
 *       </td>
 *     </tr>
 *     <tr>
 *       <th scope="row" style="vertical-align:top">Class</th>
 *       <td>
 *         <ul style="list-style-type:none">
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlAccessorOrder.html">XmlAccessorOrder</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlAccessorType.html">XmlAccessorType</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlInlineBinaryData.html">XmlInlineBinaryData</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlRootElement.html">XmlRootElement</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlType.html">XmlType</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/adapters/XmlJavaTypeAdapter.html">XmlJavaTypeAdapter</a></li>
 *         </ul>
 *       </td>
 *     </tr>
 *     <tr>
 *       <th scope="row" style="vertical-align:top">Enum type</th>
 *       <td>
 *         <ul style="list-style-type:none">
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlEnum.html">XmlEnum</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlEnumValue.html">XmlEnumValue (enum constant only)</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlRootElement.html">XmlRootElement</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlType.html">XmlType</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/adapters/XmlJavaTypeAdapter.html">XmlJavaTypeAdapter</a></li>
 *         </ul>
 *       </td>
 *     </tr>
 *     <tr>
 *       <th scope="row" style="vertical-align:top">JavaBean Property/field</th>
 *       <td>
 *         <ul style="list-style-type:none">
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlElement.html">XmlElement</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlElements.html">XmlElements</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlElementRef.html">XmlElementRef</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlElementRefs.html">XmlElementRefs</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlElementWrapper.html">XmlElementWrapper</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlAnyElement.html">XmlAnyElement</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlAttribute.html">XmlAttribute</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlAnyAttribute.html">XmlAnyAttribute</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlTransient.html">XmlTransient</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlValue.html">XmlValue</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlID.html">XmlID</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlIDREF.html">XmlIDREF</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlList.html">XmlList</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlMixed.html">XmlMixed</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlMimeType.html">XmlMimeType</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlAttachmentRef.html">XmlAttachmentRef</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlInlineBinaryData.html">XmlInlineBinaryData</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlElementDecl.html">XmlElementDecl (only on method)</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/adapters/XmlJavaTypeAdapter.html">XmlJavaTypeAdapter</a></li>
 *         </ul>
 *       </td>
 *     </tr>
 *     <tr>
 *       <th scope="row" style="vertical-align:top">Parameter</th>
 *       <td>
 *         <ul style="list-style-type:none">
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlList.html">XmlList</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlAttachmentRef.html">XmlAttachmentRef</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/XmlMimeType.html">XmlMimeType</a></li>
 *             <li><a HREF="../../../../javax/xml/bind/annotation/adapters/XmlJavaTypeAdapter.html">XmlJavaTypeAdapter</a></li>
 *         </ul>
 *       </td>
 *     </tr>
 * </tbody>
 * </table>
 * <h3>Terminology</h3>
 * <p>
 * <b>JavaBean property and field:</b> For the purposes of
 * mapping, there is no semantic difference between a field and
 * a JavaBean property. Thus, an annotation that can be applied
 * to a JavaBean property can always be applied to a
 * field. Hence in the Javadoc documentation, for brevity, the
 * term JavaBean property or property is used to mean either JavaBean
 * property or a field. Where required, both are explicitly
 * mentioned.
 * <p>
 * <b>top level class:</b> For the purpose of mapping, there is
 * no semantic difference between a top level class and a
 * static nested class. Thus, an annotation that can be applied
 * to a top level class, can always be applied to a nested
 * static class. Hence in the Javadoc documentation, for
 * brevity, the term "top level class" or just class is used to
 * mean either a top level class or a nested static
 * class.
 * <p>
 * <b>mapping annotation:</b>A JAXB 2.0 defined program
 * annotation based on the JSR 175 programming annotation
 * facility.
 * <h3>Common Usage Constraints</h3>
 * <p>The following usage constraints are defined here since
 * they apply to more than annotation:
 * <ul>
 * <li> For a property, a given annotation can be applied to
 * either read or write property but not both. </li>
 * <li> A property name must be different from any other
 * property name in any of the super classes of the
 * class being mapped. </li>
 * <li> A mapped field name or the decapitalized name of a
 * mapped property must be unique within a class. </li>
 * </ul>
 * <h3>Notations</h3>
 * <b>Namespace prefixes</b>
 * <p>The following namespace prefixes are used in the XML Schema
 * fragments in this package.
 * <p>
 * <table class="striped" summary="XML Schema fragments namespace prefixes">
 *   <thead>
 *     <tr>
 *       <th scope="col">Prefix</th>
 *       <th scope="col">Namespace</th>
 *       <th scope="col">Notes</th>
 *     </tr>
 *   </thead>
 *   <tbody>
 *     <tr>
 *       <th scope="row">xs</th>
 *       <td>http://www.w3.org/2001/XMLSchema</td>
 *       <td>Namespace of XML Schema namespace</td>
 *     </tr>
 *     <tr>
 *       <th scope="row">ref</th>
 *       <td>http://ws-i.org/profiles/basic/1.1/xsd</td>
 *       <td>Namespace for swaref schema component</td>
 *     </tr>
 *     <tr>
 *       <th scope="row">xsi</th>
 *       <td>http://www.w3.org/2001/XMLSchema-instance</td>
 *       <td>XML Schema namespace for instances</td>
 *     </tr>
 *   </tbody>
 * </table>
 *
 * @since 1.6, JAXB 2.0
 */
package javax.xml.bind.annotation;
