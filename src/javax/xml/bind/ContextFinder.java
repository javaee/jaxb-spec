/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//import java.lang.reflect.InvocationTargetException;

/**
 * This class is package private and therefore is not exposed as part of the 
 * JAXB API.
 *
 * This code is designed to implement the JAXB 1.0 spec pluggability feature
 *
 * @author <ul><li>Ryan Shoemaker, Sun Microsystems, Inc.</li></ul>
 * @version $Revision: 1.1 $
 * @see JAXBContext
 */
class ContextFinder {
    /** Temp debug code - this will be removed after we test everything
     */
    private static boolean debug = false;
    static {
        // Use try/catch block to support applets
        try {
            debug = System.getProperty("jaxb.debug") != null;
        } catch (Exception x) {
        }
    }

    private static void debugPrintln(String msg) {
        if (debug) {
            System.err.println("JAXB: " + msg);
        }
    }

    /**
     * Create an instance of a class using the specified ClassLoader
     */
    static Object newInstance( String contextPath, 
                               String className, 
                               ClassLoader classLoader )
        throws JAXBException
    {
        try {
            Class spiClass;
            if (classLoader == null) {
                spiClass = Class.forName(className);
            } else {
                spiClass = classLoader.loadClass(className);
            }
            
            /*
             * javax.xml.bind.context.factory points to a class which has a 
             * static method called 'createContext' that takes a single string 
             * argument and returns a javax.xml.JAXBContext.
             *
             * add exception handling
             *
             */
            Class paramTypes[] = {
                java.lang.String.class,
                ClassLoader.class
            };            
            Method m = spiClass.getMethod( "createContext", paramTypes );
            
            Object invocationParams[] = {
                contextPath,
                classLoader
            };
            
            // In the RI, this is equivalent to:
            // com.sun.xml.bind.ContextFactory.createContext( contextPath )
            return m.invoke( null, invocationParams );
        } catch (ClassNotFoundException x) {
            throw new JAXBException(
                Messages.format( Messages.PROVIDER_NOT_FOUND, className ),
                x);
        } catch (Exception x) {
            // can't catch JAXBException because the method is hidden behind
            // reflection.  Root element collisions detected in the call to
            // createContext() are reported as JAXBExceptions - just re-throw it
            if( x instanceof InvocationTargetException ) {
                Throwable t = ((InvocationTargetException)x).getTargetException();
                if( t != null ) {
                    if( t instanceof JAXBException ) {
                        // one of our exceptions, just re-throw
                        throw (JAXBException)t;
                    } else {
                        // some other exception, wrap the internal target exception
                        // with a JAXBException
                        throw new JAXBException(
                            Messages.format( Messages.COULD_NOT_INSTANTIATE, className, t ),
                            t);
                    }
                } else {
                    // target exception was null, so just wrap the entire exception
                    // with a JAXBException
                    throw new JAXBException(
                        Messages.format( Messages.COULD_NOT_INSTANTIATE, className, x ),
                        x);
                }
            } else {
                // some other type of exception - just wrap it
                throw new JAXBException(
                    Messages.format( Messages.COULD_NOT_INSTANTIATE, className, x ),
                    x);
            }
        }
    }

    /**
     * Finds the implementation Class object. Main entry point.
     * 
     * @return Class object of factory, never null
     *
     * @param factoryId Name of the factory to find, same as a property name
     * @param classLoader
     *      Used to locate class files and resource files.
     * 
     * @exception ContextFinder.ConfigurationError
     */
    static Object find(String factoryId, String contextPath, ClassLoader classLoader ) throws JAXBException
    {
        Object instance = null;
        
        instance = searchcontextPath( contextPath, factoryId, classLoader );
        if( instance == null ) {
            throw new JAXBException( 
                Messages.format( Messages.PROVIDER_NOT_FOUND, factoryId ) );
        }
        
        return instance;
    }

        
    /**
     * Walk the context path searching for jaxb.properties files containing
     * the javax.xml.bind.context.factory property.
     * 
     * @return Object 
     *      return an instace of the class or null if the property wasn't found.
     * @throws JAXBException 
     *      if there are any missing jaxb.property files on the context path
     *      or there are any differences in the values of the factory class
     *      names.
     */
    private static Object searchcontextPath( String contextPath,
                                             String factoryId,                                            
                                             ClassLoader classLoader )
        throws JAXBException {
            


        // String pathSep = File.pathSeparator;
        // since contextPath contains package names and not file paths, it
        // doesn't matter which pathSeparator the client uses.
        String pathSep = ":";  
        
        // don't use File.separatorChar because there is an issue with looking
        // up resources that are contained in jar files on win32.  If you try
        // to lookup "a\b\foo.properties" in a jar on the classpath, it will
        // fail, but if you lookup "a/b/foo.properties", it will succeed.
        //char fileSep = File.separatorChar;
        char fileSep = '/';

        String propFileName = null;
        String packageName = null;
        StringTokenizer tokens = new StringTokenizer( contextPath, pathSep );
        String factoryClassName = null;

        while( tokens.hasMoreTokens() ) {
            // com.acme.foo
            packageName = tokens.nextToken();
            
            // com/acme/foo/jaxb.properties
            propFileName = packageName.replace( '.', fileSep ).
                concat( fileSep + "jaxb.properties" );
            debugPrintln( "looking for " + propFileName );

            Properties props = loadJAXBProperties( classLoader, propFileName );
            if( props == null ) {
                throw new JAXBException( 
                    Messages.format( Messages.CANT_FIND_PROPERTIES_FILE, 
                                     packageName ) );
            } else {
                debugPrintln( "found it, looking for prop" );
                if( props.containsKey( factoryId ) ) {
                    debugPrintln( "found it, comparing" );
                    if( factoryClassName == null ) {
                        debugPrintln( "first occurrence" );
                        factoryClassName = props.getProperty( factoryId );
                    } else if( !factoryClassName.equals( props.getProperty( factoryId ) ) ) {
                        throw new JAXBException( 
                            Messages.format( Messages.CANT_MIX_PROVIDERS ) );
                    }
                } else {
                    throw new JAXBException( 
                        Messages.format( Messages.MISSING_PROPERTY,
                                         packageName,
                                         factoryId ) );
                }
            }
        }

        debugPrintln( "success, instantiating: " + factoryClassName );
        return newInstance( contextPath, factoryClassName, classLoader );
    }
    
    private static Properties loadJAXBProperties( ClassLoader classLoader,
                                                  String propFileName ) 
        throws JAXBException {
                                            
        Properties props = null;
                                                    
        try {
            InputStream is = 
                classLoader.getResourceAsStream( propFileName );

            if( is != null ) {
                if (debug) {
                    // extra debug flag check to avoid redundant getResource invocation
                    debugPrintln("loading props from "+classLoader.getResource(propFileName).toExternalForm() );
                }
                props = new Properties();
                props.load( is );
                is.close();
            } 
        } catch( IOException ioe ) {
            if( debug ) ioe.printStackTrace();
            throw new JAXBException( ioe.toString(), ioe );
        }
        
        return props;
    }
    
}
