/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind;

/**
 * <p>
 * The <tt>JAXBContext</tt> class provides the client's entry point to the 
 * JAXB API. It provides an abstraction for managing the XML/Java binding 
 * information necessary to implement the JAXB binding framework operations: 
 * unmarshal, marshal and validate.  A client application obtains new instances 
 * of this class via the {@link #newInstance(String) newInstance(contextPath)}
 * method.
 *
 * <pre>
 *     JAXBContext jc = JAXBContext.newInstance( "com.acme.foo:com.acme.bar" );
 * </pre>
 *
 * <p>
 * The <tt>contextPath</tt> contains a list of Java package names that contain
 * schema derived interfaces.  The value of this parameter initializes the 
 * <tt>JAXBContext</tt> object so that it is capable of managing the
 * schema derived interfaces.
 *
 * <p>
 * <blockquote>
 * <i><B>SPEC REQUIREMENT:</B> the provider must supply an implementation
 * class containing a method with the following signature:</i>
 *
 * <pre>
 *        public static JAXBContext createContext( String contextPath, ClassLoader classLoader )
 *            throws JAXBException;
 * </pre>
 *
 * <p><i>
 * JAXB Providers must generate a <tt>jaxb.properties</tt> file in each package
 * containing schema derived classes.  The property file must contain a
 * property named <tt>javax.xml.bind.context.factory</tt> whose value is
 * the name of the class that implements the <tt>createContext</tt> API.</i>
 * 
 * <p><i>
 * The class supplied by the provider does not have to be assignable to 
 * <tt>javax.xml.bind.JAXBContext</tt>, it simply has to provide a class that
 * implements the <tt>createContext</tt> API.</i>
 * 
 * <p><i>
 * In addition, the provider must call the 
 * {@link DatatypeConverter#setDatatypeConverter(DatatypeConverterInterface) 
 * DatatypeConverter.setDatatypeConverter} api prior to any client 
 * invocations of the marshal and unmarshal methods.  This is necessary to 
 * configure the datatype converter that will be used during these operations.</i>
 * </blockquote>
 *
 * <p>
 * <b>Unmarshalling</b>
 * <p>
 * <blockquote>
 * The {@link Unmarshaller} class provides the client application the ability
 * to convert XML data into a tree of Java content objects.
 * The unmarshal method for a schema (within a namespace) allows for
 * any global XML element declared in the schema to be unmarshalled as
 * the root of an instance document. The <tt>JAXBContext</tt> object 
 * allows the merging of global elements across a set of schemas (listed
 * in the <tt>contextPath</tt>). Since each schema in the schema set can belong
 * to distinct namespaces, the unification of schemas to an unmarshalling 
 * context should be namespace independent.  This means that a client 
 * application is able to unmarshal XML documents that are instances of
 * any of the schemas listed in the <tt>contextPath</tt>.  For example:
 *
 * <pre>
 *        JAXBContext jc = JAXBContext.newInstance( "com.acme.foo:com.acme.bar" );
 *        Unmarshaller u = jc.createUnmarshaller();
 *        FooObject fooObj = (FooObject)u.unmarshal( new File( "foo.xml" ) ); // ok
 *        BarObject barObj = (BarObject)u.unmarshal( new File( "bar.xml" ) ); // ok
 *        BazObject bazObj = (BazObject)u.unmarshal( new File( "baz.xml" ) ); // error, "com.acme.baz" not in contextPath
 * </pre>
 *
 * <p>
 * The client application may also generate Java content trees explicitly rather
 * than unmarshalling existing XML data.  To do so, the application needs to 
 * have access and knowledge about each of the schema derived <tt>
 * ObjectFactory</tt> classes that exist in each of java packages contained 
 * in the <tt>contextPath</tt>.  For each schema derived java class, there will 
 * be a static factory method that produces objects of that type.  For example, 
 * assume that after compiling a schema, you have a package <tt>com.acme.foo</tt> 
 * that contains a schema derived interface named <tt>PurchaseOrder</tt>.  In 
 * order to create objects of that type, the client application would use the 
 * factory method like this:
 *
 * <pre>
 *       com.acme.foo.PurchaseOrder po = 
 *           com.acme.foo.ObjectFactory.createPurchaseOrder();
 * </pre>
 *
 * <p>
 * Once the client application has an instance of the the schema derived object,
 * it can use the mutator methods to set content on it.
 *
 * <p>
 * For more information on the generated <tt>ObjectFactory</tt> classes, see
 * Section 4.2 <i>Java Package</i> of the specification.
 *
 * <p>
 * <i><B>SPEC REQUIREMENT:</B> the provider must generate a class in each
 * package that contains all of the necessary object factory methods for that 
 * package named ObjectFactory as well as the static 
 * <tt>newInstance( javaContentInterface )</tt> method</i>  
 * </blockquote>
 *
 * <p>
 * <b>Marshalling</b>
 * <p>
 * <blockquote>
 * The {@link Marshaller} class provides the client application the ability
 * to convert a Java content tree back into XML data.  There is no difference
 * between marshalling a content tree that is created manually using the factory
 * methods and marshalling a content tree that is the result an <tt>unmarshal
 * </tt> operation.  Clients can marshal a java content tree back to XML data
 * to a <tt>java.io.OutputStream</tt> or a <tt>java.io.Writer</tt>.  The 
 * marshalling process can alternatively produce SAX2 event streams to a 
 * registered <tt>ContentHandler</tt> or produce a DOM Node object.  
 * <!-- don't expose fragment support yet 
 * Client applications 
 * have control over the output encoding as well as whether or not to marshal 
 * the XML data as a complete document or as a fragment.
 * -->
 *
 * <p>
 * Here is a simple example that unmarshals an XML document and then marshals
 * it back out:
 *
 * <pre>
 *        JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *
 *        // unmarshal from foo.xml
 *        Unmarshaller u = jc.createUnmarshaller();
 *        FooObject fooObj = (FooObject)u.unmarshal( new File( "foo.xml" ) );
 *
 *        // marshal to System.out
 *        Marshaller m = jc.createMarshaller();
 *        m.marshal( fooObj, System.out );
 * </pre>
 * </blockquote>
 *
 * <p>
 * <b>Validation</b>
 * <p>
 * <blockquote>
 * There are three varieties of validation available to JAXB client applications:
 * <blockquote>
 * <dl>
 *   <dt>Unmarshal-time Validation</dt>
 *   <dd>Validation performed on the XML data as it is being unmarshalled into
 *       a Java content tree</dd>
 *   <dt>On-Demand Validation</dt>
 *   <dd>Validation performed on the Java content tree in memory</dd>
 *   <dt>Fail-Fast Validation</dt>
 *   <dd>Validation of Java property constraints at runtime when client
 *       applications invoke the setter methods of the generated classes</dd>
 * </dl>
 * </blockquote>
 *
 * <p>
 * See: <a href="Validator.html#validationtypes">Validator javadocs</a> for a 
 * more detailed definition of the different type of validation.
 *
 * <p>
 * Although unmarshal-time validation and on-demand validation are very similar, 
 * they are completely orthogonal operations with no dependencies on each other.  
 * Client applications are free to use one, both, or neither types of validation.
 *
 * <p>
 * Validation errors and warnings encountered during the unmarshal and validate 
 * operations are reported to the client application via a callback error handler 
 * interface ({@link ValidationEventHandler ValidationEventHandler}) that receives
 * {@link ValidationEvent ValidationEvent} objects.  The <tt>ValidationEvent</tt>
 * objects contain information about the error or warning encountered.  JAXB 
 * allows a few different methods of handling validation events which are described 
 * in more detail in the 
 * <a href="Validator.html#handlingerrors">Validator javadoc</a>.
 * </blockquote>
 *
 * <p>
 * <b>JAXB Runtime Binding Framework Compatibility</b><br>
 * <blockquote>
 * Since the JAXB Specification does not define a common runtime system, a JAXB 
 * client application must not attempt to mix runtime objects (<tt>JAXBContext,
 * Marshaller</tt>, etc. ) from different providers.  This does not 
 * mean that the client application isn't portable, it simply means that a 
 * client has to use a runtime system provided by the same provider that was 
 * used to compile the schema.
 * </blockquote>
 *
 * @author <ul><li>Ryan Shoemaker, Sun Microsystems, Inc.</li><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems, Inc.</li></ul>
 * @version $Revision: 1.2 $ $Date: 2004-06-14 21:23:03 $
 * @see Marshaller
 * @see Unmarshaller
 * @see Validator
 * @since JAXB1.0
 */
public abstract class JAXBContext {
    
    /**
     * The name of the property that contains the name of the class capable
     * of creating new <tt>JAXBContext</tt> objects.
     */
    public static final String JAXB_CONTEXT_FACTORY = 
        "javax.xml.bind.context.factory";
       

    protected JAXBContext() {
    }

    
    /**
     * <p>
     * Obtain a new instance of a <tt>JAXBContext</tt> class.
     *
     * <p>
     * This is a convenience method for the 
     * {@link #newInstance(String,ClassLoader) newInstance} method.  It uses
     * the context class loader of the current thread.  To specify the use of
     * a different class loader, either set it via the 
     * <tt>Thread.setContextClassLoader()</tt> api or use the 
     * {@link #newInstance(String,ClassLoader) newInstance} method.
     */
    public static JAXBContext newInstance( String contextPath ) 
        throws JAXBException {
            
        //return newInstance( contextPath, JAXBContext.class.getClassLoader() );
        return newInstance( contextPath, Thread.currentThread().getContextClassLoader() );
    }
    
    
    
    /**
     * <p>
     * Obtain a new instance of a <tt>JAXBContext</tt> class.
     *
     * <p>
     * The client application must supply a context path which is a list of 
     * colon (':', \u005Cu003A) separated java package names that contain schema 
     * derived classes.
     *
     * The JAXB provider will ensure that each package on the context path
     * has a <tt>jaxb.properties</tt> file which contains a value for the 
     * <tt>javax.xml.bind.context.factory</tt> property and that all values
     * resolve to the same provider.
     *
     * <p>
     * If there are any global XML element name collisions across the various 
     * packages listed on the <tt>contextPath</tt>, a <tt>JAXBException</tt> 
     * will be thrown.  Mixing generated classes from multiple JAXB Providers 
     * in the same context path will also result in a <tt>JAXBException</tt> 
     * being thrown.
     *  
     * @param contextPath list of java package names that contain schema 
     *                     derived classes
     * @param classLoader
     *      This class loader will be used to locate the implementation
     *      classes.
     *
     * @return a new instance of a <tt>JAXBContext</tt>
     * @throws JAXBException if an error was encountered while creating the
     *                       <tt>JAXBContext</tt>, such as an ambiguity among
     *                       global elements contained in the contextPath,
     *                       failure to locate a value for the context factory
     *                       property, or mixing schema derived packages from
     *                       different providers on the same contextPath.
     */
    public static JAXBContext newInstance( String contextPath, ClassLoader classLoader ) 
        throws JAXBException {
            
        return (JAXBContext) ContextFinder.find(
                /* The default property name according to the JAXB spec */
                JAXB_CONTEXT_FACTORY,
                
                /* the context path supplied by the client app */
                contextPath,
                
                /* class loader to be used */
                classLoader );
    }
    
    
    /** 
     * Create an <tt>Unmarshaller</tt> object that can be used to convert XML
     * data into a java content tree.
     *
     * @return an <tt>Unmarshaller</tt> object
     *
     * @throws JAXBException if an error was encountered while creating the
     *                       <tt>Unmarshaller</tt> object
     */    
    public abstract Unmarshaller createUnmarshaller() throws JAXBException;
    
    
    /** 
     * Create a <tt>Marshaller</tt> object that can be used to convert a 
     * java content tree into XML data.
     *
     * @return a <tt>Marshaller</tt> object
     *
     * @throws JAXBException if an error was encountered while creating the
     *                       <tt>Marshaller</tt> object
     */    
    public abstract Marshaller createMarshaller() throws JAXBException;
    
    
    /** 
     * Create a <tt>Validator</tt> object that can be used to validate a
     * java content tree against its source schema.
     *
     * @return a <tt>Validator</tt> object
     *
     * @throws JAXBException if an error was encountered while creating the
     *                       <tt>Validator</tt> object
     */    
    public abstract Validator createValidator() throws JAXBException;
    
}
