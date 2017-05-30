/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015-2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.bind.test;

import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNull;

/**
 * regression test for
 * JDK-8145112: newInstance(String, ClassLoader): java.lang.JAXBException should not be wrapped as expected
 * according to spec
 */
public class JAXBContextWrapExceptionTest {

    public static class Factory {

        public static JAXBContext createContext(Class[] classesToBeBound, Map<String, ?> properties) throws JAXBException {
            throw new JAXBException("test");
        }

        public static JAXBContext createContext(String contextPath, ClassLoader classLoader, Map<String, ?> properties)
                throws JAXBException {
            throw new JAXBException("test");
        }
    }

    @Test
    public void testContextPath() {
        try {
            JAXBContext.newInstance("whatever", ClassLoader.getSystemClassLoader());
        } catch (Throwable t) {
            assertEquals("test", t.getMessage());
            assertNull("Root cause must be null", t.getCause());
        }
    }

    @Test
    public void testClasses() {
        try {
            JAXBContext.newInstance(new Class[0]);
            assertTrue("This should fail", false);
        } catch (Throwable t) {
            assertEquals("test", t.getMessage());
            assertNull("Root cause must be null", t.getCause());
        }
    }

    @Before
    public void setup() {
        System.setProperty("javax.xml.bind.JAXBContextFactory", "javax.xml.bind.test.JAXBContextWrapExceptionTest$Factory");
    }

    public static void main(String[] args) throws JAXBException {
        new JAXBContextWrapExceptionTest().testContextPath();
        new JAXBContextWrapExceptionTest().testClasses();
    }

}
