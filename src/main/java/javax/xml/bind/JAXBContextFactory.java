/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015-2016 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
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

package javax.xml.bind;

import java.util.Map;

/**
 * <p>Factory that creates new <code>JAXBContext</code> instances.
 *
 * JAXBContextFactory can be located using {@link java.util.ServiceLoader#load(Class)}
 *
 * @since 9, JAXB 2.3
 */
public interface JAXBContextFactory {

    /**
     * <p>
     * Create a new instance of a {@code JAXBContext} class.
     *
     * <p>
     * For semantics see {@link javax.xml.bind.JAXBContext#newInstance(Class[], java.util.Map)}
     *
     * @param classesToBeBound
     *      List of java classes to be recognized by the new {@link JAXBContext}.
     *      Classes in {@code classesToBeBound} that are in named modules must be in a package
     *      that is {@linkplain java.lang.reflect.Module#isOpen open} to the {@code java.xml.bind} module.
     *      Can be empty, in which case a {@link JAXBContext} that only knows about
     *      spec-defined classes will be returned.
     * @param properties
     *      provider-specific properties. Can be null, which means the same thing as passing
     *      in an empty map.
     *
     * @return
     *      A new instance of a {@code JAXBContext}.
     *
     * @throws JAXBException
     *      if an error was encountered while creating the
     *      {@code JAXBContext}. See {@link JAXBContext#newInstance(Class[], Map)} for details.
     *
     * @throws IllegalArgumentException
     *      if the parameter contains {@code null} (i.e., {@code newInstance(null,someMap);})
     *
     * @since 9, JAXB 2.3
     */
    JAXBContext createContext(Class<?>[] classesToBeBound,
                              Map<String, ?> properties ) throws JAXBException;

    /**
     * <p>
     * Create a new instance of a {@code JAXBContext} class.
     *
     * <p>
     * For semantics see {@link javax.xml.bind.JAXBContext#newInstance(String, ClassLoader, java.util.Map)}
     *
     * <p>
     * The interpretation of properties is up to implementations. Implementations must
     * throw {@code JAXBException} if it finds properties that it doesn't understand.
     *
     * @param contextPath
     *      List of java package names that contain schema derived classes.
     *      Classes in {@code classesToBeBound} that are in named modules must be in a package
     *      that is {@linkplain java.lang.reflect.Module#isOpen open} to the {@code java.xml.bind} module.
     * @param classLoader
     *      This class loader will be used to locate the implementation classes.
     * @param properties
     *      provider-specific properties. Can be null, which means the same thing as passing
     *      in an empty map.
     *
     * @return a new instance of a {@code JAXBContext}
     * @throws JAXBException if an error was encountered while creating the
     *      {@code JAXBContext}. See {@link JAXBContext#newInstance(String, ClassLoader, Map)} for details.
     *
     * @since 9, JAXB 2.3
     */
    JAXBContext createContext(String contextPath,
                              ClassLoader classLoader,
                              Map<String, ?> properties ) throws JAXBException;

}
