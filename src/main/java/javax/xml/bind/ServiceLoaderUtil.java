/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Shared ServiceLoader/FactoryFinder Utils shared among SAAJ, JAXB and JAXWS
 * - this class must be duplicated to all those projects, but it's
 * basically generic code and we want to have it everywhere same.
 *
 * @author Miroslav.Kos@oracle.com
 */
class ServiceLoaderUtil {

    private static final String OSGI_SERVICE_LOADER_CLASS_NAME = "org.glassfish.hk2.osgiresourcelocator.ServiceLoader";
    private static final String OSGI_SERVICE_LOADER_METHOD_NAME = "lookupProviderClasses";

    private static Logger logger;

    public static void setLogger(Logger l) {
        logger = l;
    }

    static <P> P firstByServiceLoader(Class<P> spiClass, ExceptionHandler handler) {
        // service discovery
        ServiceLoader<P> serviceLoader = ServiceLoader.load(spiClass);
        for (P impl : serviceLoader) {
            logger.fine("ServiceProvider loading Facility used; returning object [" + impl.getClass().getName() + "]");
            return impl;
        }
        return null;
    }

    static boolean isOsgi(ExceptionHandler handler) {
        try {
            Class.forName(OSGI_SERVICE_LOADER_CLASS_NAME);
            return true;
        } catch (ClassNotFoundException ignored) {
            logger.log(Level.FINE, "OSGi classes not found, OSGi not available.", ignored);
        }
        return false;
    }

    static Object lookupUsingOSGiServiceLoader(String factoryId, ExceptionHandler handler) {
        try {
            // Use reflection to avoid having any dependendcy on ServiceLoader class
            Class serviceClass = Class.forName(factoryId);
            Class target = Class.forName(OSGI_SERVICE_LOADER_CLASS_NAME);
            Method m = target.getMethod(OSGI_SERVICE_LOADER_METHOD_NAME, Class.class);
            Iterator iter = ((Iterable) m.invoke(null, serviceClass)).iterator();
            if (iter.hasNext()) {
                Object next = iter.next();
                logger.fine("Found implementation using OSGi facility; returning object [" + next.getClass().getName() + "].");
                return next;
            } else {
                return null;
            }
        } catch (Exception ignored) {
            logger.log(Level.FINE, "Unable to find from OSGi: [" + factoryId + "]", ignored);
            return null;
        }
    }

    static String propertyFileLookup(final String configFullPath, final String factoryId) throws IOException {
        File f = new File(configFullPath);
        String factoryClassName = null;
        if (f.exists()) {
            Properties props = new Properties();
            FileInputStream stream = new FileInputStream(f);
            props.load(stream);
            factoryClassName = props.getProperty(factoryId);
            try {
                stream.close();
            } catch (IOException ignored) {
            }
        }
        return factoryClassName;
    }

    static void checkPackageAccess(String className) {
        // make sure that the current thread has an access to the package of the given name.
        SecurityManager s = System.getSecurityManager();
        if (s != null) {
            int i = className.lastIndexOf('.');
            if (i != -1) {
                s.checkPackageAccess(className.substring(0, i));
            }
        }
    }

    static Class nullSafeLoadClass(String className, ClassLoader classLoader, ExceptionHandler handler) throws ClassNotFoundException {
        if (classLoader == null) {
            return Class.forName(className);
        } else {
            return classLoader.loadClass(className);
        }
    }

    /**
     * Returns instance of required class. It checks package access (security) unless it is defaultClassname. It means if you
     * are trying to instantiate default implementation (fallback), pass the class name to both first and second parameter.
     *
     * @param className          class to be instantiated
     * @param isDefaultClassname says whether default implementation class
     * @param handler            exception handler - necessary for wrapping exceptions and logging
     * @param <T>                Type of exception being thrown (necessary to distinguish between Runtime and checked exceptions)
     * @return instantiated object or throws Runtime/checked exception, depending on ExceptionHandler's type
     * @throws T
     */
    static <T extends Exception> Object newInstance(String className, boolean isDefaultClassname, final ExceptionHandler<T> handler) throws T {
        try {
            return safeLoadClass(className, isDefaultClassname, contextClassLoader(handler), handler).newInstance();
        } catch (ClassNotFoundException x) {
            throw handler.createException(x, "Provider " + className + " not found");
        } catch (Exception x) {
            throw handler.createException(x, "Provider " + className + " could not be instantiated: " + x);
        }
    }

    static Class safeLoadClass(String className, boolean isDefaultImplemenation, ClassLoader classLoader, ExceptionHandler handler) throws ClassNotFoundException {
        try {
            checkPackageAccess(className);
        } catch (SecurityException se) {
            // anyone can access the platform default factory class without permission
            if (isDefaultImplemenation) {
                return Class.forName(className);
            }
            // not platform default implementation ...
            throw se;
        }
        return nullSafeLoadClass(className, classLoader, handler);
    }

    static String getJavaHomeLibConfigPath(String filename) {
        String javah = AccessController.doPrivileged(new PrivilegedAction<String>() {
            @Override
            public String run() {
                return System.getProperty("java.home");
            }
        });
        return javah + File.separator + "lib" + File.separator + filename;
    }

    static ClassLoader contextClassLoader(ExceptionHandler exceptionHandler) throws Exception {
        try {
            return Thread.currentThread().getContextClassLoader();
        } catch (Exception x) {
            throw exceptionHandler.createException(x, x.toString());
        }
    }

    static abstract class ExceptionHandler<T extends Exception> {

        public abstract T createException(Throwable throwable, String message);

    }

}
