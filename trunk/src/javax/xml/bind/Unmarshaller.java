/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind;

/**
 * The <tt>Unmarshaller</tt> class governs the process of deserializing XML 
 * data into newly created Java content trees, optionally validating the XML 
 * data as it is unmarshalled.  It provides the basic unmarshalling methods:
 *    
 * <p>
 * Unmarshalling from a File:
 * <blockquote>
 *    <pre>
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *       Object o = u.unmarshal( new File( "nosferatu.xml" ) );
 *    </pre>
 * </blockquote>
 *
 *  
 * <p>
 * Unmarshalling from an InputStream:
 * <blockquote>
 *    <pre>
 *       InputStream is = new FileInputStream( "nosferatu.xml" );
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *       Object o = u.unmarshal( is );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Unmarshalling from a URL:
 * <blockquote>
 *    <pre>
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *       URL url = new URL( "http://beaker.east/nosferatu.xml" );
 *       Object o = u.unmarshal( url );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Unmarshalling from a StringBuffer using a 
 * <tt>javax.xml.transform.stream.StreamSource</tt>:
 * <blockquote>
 *    <pre>
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *       StringBuffer xmlStr = new StringBuffer( "&lt;?xml version=&quot;1.0&quot;?&gt;..." );
 *       Object o = u.unmarshal( new StreamSource( new StringReader( xmlStr.toString() ) ) );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Unmarshalling from a <tt>org.w3c.dom.Node</tt>:
 * <blockquote>
 *    <pre>
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 * 
 *       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
 *       dbf.setNamespaceAware(true);
 *       DocumentBuilder db = dbf.newDocumentBuilder();
 *       Document doc = db.parse(new File( "nosferatu.xml"));

 *       Object o = u.unmarshal( doc );
 *    </pre>
 * </blockquote>
 * 
 * <p>
 * Unmarshalling from a <tt>javax.xml.transform.sax.SAXSource</tt> using a
 * client specified validating SAX2.0 parser:
 * <blockquote>
 *    <pre>
 *       // configure a validating SAX2.0 parser (Xerces2)
 *       static final String JAXP_SCHEMA_LANGUAGE =
 *           "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
 *       static final String JAXP_SCHEMA_LOCATION =
 *           "http://java.sun.com/xml/jaxp/properties/schemaSource";
 *       static final String W3C_XML_SCHEMA =
 *           "http://www.w3.org/2001/XMLSchema";
 *
 *       System.setProperty( "javax.xml.parsers.SAXParserFactory",
 *                           "org.apache.xerces.jaxp.SAXParserFactoryImpl" );
 *
 *       SAXParserFactory spf = SAXParserFactory.newInstance();
 *       spf.setNamespaceAware(true);
 *       spf.setValidating(true);
 *       SAXParser saxParser = spf.newSAXParser();
 *       
 *       try {
 *           saxParser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
 *           saxParser.setProperty(JAXP_SCHEMA_LOCATION, "http://....");
 *       } catch (SAXNotRecognizedException x) {
 *           // exception handling omitted
 *       }
 *
 *       XMLReader xmlReader = saxParser.getXMLReader();
 *       SAXSource source = 
 *           new SAXSource( xmlReader, new InputSource( "http://..." ) );
 *
 *       // Setup JAXB to unmarshal
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *       ValidationEventCollector vec = new ValidationEventCollector();
 *       u.setEventHandler( vec );
 *       
 *       // turn off the JAXB provider's default validation mechanism to 
 *       // avoid duplicate validation
 *       u.setValidating( false )
 *
 *       // unmarshal
 *       Object o = u.unmarshal( source );
 *
 *       // check for events
 *       if( vec.hasEvents() ) {
 *          // iterate over events
 *       }
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Unmarshalling from a StAX XMLStreamReader:
 * <blockquote>
 *    <pre>
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 * 
 *       javax.xml.stream.XMLStreamReader xmlStreamReader = 
 *           javax.xml.stream.XMLInputFactory().newInstance().createXMLStreamReader( ... );
 * 
 *       Object o = u.unmarshal( xmlStreamReader );
 *    </pre>
 * </blockquote>
 *
 *  
 * <p>
 * Unmarshalling from a StAX XMLEventReader:
 * <blockquote>
 *    <pre>
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 * 
 *       javax.xml.stream.XMLEventReader xmlEventReader = 
 *           javax.xml.stream.XMLInputFactory().newInstance().createXMLEventReader( ... );
 * 
 *       Object o = u.unmarshal( xmlEventReader );
 *    </pre>
 * </blockquote>
 *
 *  
 * <p>
 * <a name="unmarshalEx"></a>
 * <b>Unmarshalling XML Data</b><br>
 * <blockquote>
 * The <tt>JAXBContext</tt> object used to create this <tt>Unmarshaller</tt>
 * was initialized with a <tt>contextPath</tt> which determines the schema
 * derived content available to the <tt>Marshaller</tt>, <tt>Unmarshaller</tt>,
 * and <tt>Validator</tt> objects it produces.  If the <tt>JAXBContext</tt> 
 * object that was used to create this <tt>Unmarshaller</tt> does not have 
 * enough information to know how to unmarshal the XML content from the 
 * specified source, then the unmarshal operation will abort immediately by 
 * throwing a {@link UnmarshalException UnmarshalException}.
 * </blockquote>
 *
 * <p>
 * <b>Support for SAX2.0 Compliant Parsers</b><br>
 * <blockquote>
 * A client application has the ability to select the SAX2.0 compliant parser
 * of their choice.  If a SAX parser is not selected, then the JAXB Provider's
 * default parser will be used.  Even though the JAXB Provider's default parser
 * is not required to be SAX2.0 compliant, all providers are required to allow
 * a client application to specify their own SAX2.0 parser.  Some providers may
 * require the client application to specify the SAX2.0 parser at schema compile
 * time. See {@link #unmarshal(javax.xml.transform.Source) unmarshal(Source)} 
 * for more detail.
 * </blockquote>
 *
 * <p>
 * <b>Validation and Well-Formedness</b><br>
 * <blockquote>
 * <p>
 * A client application can enable or disable the provider's default validation
 * mechanism via the <tt>setValidating</tt> API.  Sophisticated clients can 
 * specify their own validating SAX 2.0 compliant parser and bypass the 
 * provider's default validation mechanism using the 
 * {@link #unmarshal(javax.xml.transform.Source) unmarshal(Source)}  API.
 * 
 * <p>
 * For a more detailed definition of how validation errors and warnings are
 * handled, see the {@link Validator Validator} javadoc.
 * </blockquote>
 *
 * <p>
 * <a name="supportedProps"></a>
 * <b>Supported Properties</b><br>
 * <blockquote>
 * <p>
 * There currently are not any properties required to be supported by all 
 * JAXB Providers on Unmarshaller.  However, some providers may support 
 * their own set of provider specific properties.
 * </blockquote>
 * 
 * 
 * @author <ul><li>Ryan Shoemaker, Sun Microsystems, Inc.</li><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems, Inc.</li></ul>
 * @version $Revision: 1.2 $ $Date: 2004-06-14 21:23:05 $
 * @see JAXBContext
 * @see Marshaller
 * @see Validator
 * @since JAXB1.0
 */
public interface Unmarshaller {
    
    /**
     * Unmarshal XML data from the specified file and return the resulting
     * content tree.
     *
     * @param f the file to unmarshal XML data from
     * @return the newly created root object of the java content tree 
     *
     * @throws JAXBException 
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the 
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the file parameter is null
     */
    public Object unmarshal( java.io.File f ) throws JAXBException;
    
    /**
     * Unmarshal XML data from the specified InputStream and return the 
     * resulting content tree.  Validation event location information may
     * be incomplete when using this form of the unmarshal API.
     *
     * @param is the InputStream to unmarshal XML data from
     * @return the newly created root object of the java content tree 
     *
     * @throws JAXBException 
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the 
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the InputStream parameter is null
     */
    public Object unmarshal( java.io.InputStream is ) throws JAXBException;

    /**
     * Unmarshal XML data from the specified URL and return the resulting
     * content tree.
     *
     * @param url the url to unmarshal XML data from
     * @return the newly created root object of the java content tree 
     *
     * @throws JAXBException 
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the 
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the URL parameter is null
     */
    public Object unmarshal( java.net.URL url ) throws JAXBException;
    
    /**
     * Unmarshal XML data from the specified SAX InputSource and return the
     * resulting content tree.
     *
     * @param source the input source to unmarshal XML data from
     * @return the newly created root object of the java content tree 
     *
     * @throws JAXBException 
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the 
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the InputSource parameter is null
     */
    public Object unmarshal( org.xml.sax.InputSource source ) throws JAXBException;
    
    /**
     * Unmarshal XML data from the specified DOM tree and return the resulting
     * content tree.
     *
     * @param node
     *      the document/element to unmarshal XML data from.
     *      The caller must support at least Document and Element.
     * @return the newly created root object of the java content tree 
     *
     * @throws JAXBException 
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the 
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the Node parameter is null
     */
    public Object unmarshal( org.w3c.dom.Node node ) throws JAXBException;
    
    /**
     * Unmarshal XML data from the specified XML Source and return the 
     * resulting content tree.  
     * <p>
     * <b>SAX 2.0 Parser Pluggability</b>
     * <p>
     * A client application can choose not to use the default parser mechanism
     * supplied with their JAXB provider.  Any SAX 2.0 compliant parser can be
     * substituted for the JAXB provider's default mechanism.  To do so, the
     * client application must properly configure a <tt>SAXSource</tt> containing 
     * an <tt>XMLReader</tt> implemented by the SAX 2.0 parser provider.  If the
     * <tt>XMLReader</tt> has an <tt>org.xml.sax.ErrorHandler</tt> registered
     * on it, it will be replaced by the JAXB Provider so that validation errors
     * can be reported via the <tt>ValidationEventHandler</tt> mechanism of
     * JAXB.  If the <tt>SAXSource</tt> does not contain an <tt>XMLReader</tt>, 
     * then the JAXB provider's default parser mechanism will be used.
     * <p>
     * This parser replacement mechanism can also be used to replace the JAXB
     * provider's unmarshal-time validation engine.  The client application 
     * must properly configure their SAX 2.0 compliant parser to perform
     * validation (as shown in the example above).  Any <tt>SAXParserExceptions
     * </tt> encountered by the parser during the unmarshal operation will be 
     * processed by the JAXB provider and converted into JAXB 
     * <tt>ValidationEvent</tt> objects which will be reported back to the 
     * client via the <tt>ValidationEventHandler</tt> registered with the 
     * <tt>Unmarshaller</tt>.  <i>Note:</i> specifying a substitute validating 
     * SAX 2.0 parser for unmarshalling does not necessarily replace the 
     * validation engine used by the JAXB provider for performing on-demand 
     * validation.
     * <p>
     * The only way for a client application to specify an alternate parser
     * mechanism to be used during unmarshal is via the 
     * <tt>unmarshal(SAXSource)</tt> API.  All other forms of the unmarshal 
     * method (File, URL, Node, etc) will use the JAXB provider's default 
     * parser and validator mechanisms.
     *
     * @param source the XML Source to unmarshal XML data from (providers are
     *        only required to support SAXSource, DOMSource, and StreamSource)
     * @return the newly created root object of the java content tree
     *
     * @throws JAXBException 
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the 
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the Source parameter is null
     */
    public Object unmarshal( javax.xml.transform.Source source )
        throws JAXBException;
    
    /**
     * Unmarshal XML data from the specified pull parser and return the
     * resulting content tree.
     *
     * <p>
     * This method assumes that the parser is at a start element event,
     * and the unmarshalling will be done from this start element to the
     * corresponding end element.
     * If this method returns successfully, the <tt>reader</tt> will be
     * pointing at the token right after the end element. 
     * 
     * @param reader
     *      The parser to be read.
     * @return
     *      the newly created root object of the java content tree.
     *
     * @throws JAXBException 
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the 
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the <tt>reader</tt> parameter is null
     * @throws IllegalStateException
     *      If <tt>reader</tt> is not pointing to a start element event.
     */
    public Object unmarshal( javax.xml.stream.XMLStreamReader reader )
        throws JAXBException;
    
    /**
     * Unmarshal XML data from the specified pull parser and return the
     * resulting content tree.
     *
     *
     * <p>
     * This method assumes that the parser is at a start element event,
     * and the unmarshalling will be done from this start element to the
     * corresponding end element.
     * If this method returns successfully, the <tt>reader</tt> will be
     * pointing at the token right after the end element. 
     * 
     * @param reader
     *      The parser to be read.
     * @return
     *      the newly created root object of the java content tree.
     *
     * @throws JAXBException 
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the 
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the <tt>reader</tt> parameter is null
     * @throws IllegalStateException
     *      If <tt>reader</tt> is not pointing to a start element event.
     */
    public Object unmarshal( javax.xml.stream.XMLEventReader reader )
        throws JAXBException;
    
    /**
     * Get an unmarshaller handler object that can be used as a component in
     * an XML pipeline.
     * 
     * <p>
     * The JAXB Provider can return the same handler object for multiple 
     * invocations of this method. In other words, this method does not 
     * necessarily create a new instance of <tt>UnmarshallerHandler</tt>. If the 
     * application needs to use more than one <tt>UnmarshallerHandler</tt>, it 
     * should create more than one <tt>Unmarshaller</tt>.
     *
     * @return the unmarshaller handler object
     * @see UnmarshallerHandler
     */
    public UnmarshallerHandler getUnmarshallerHandler();
    
    /**
     * Specifies whether or not the default validation mechanism of the
     * <tt>Unmarshaller</tt> should validate during unmarshal operations.  
     * By default, the <tt>Unmarshaller</tt> does not validate.
     * <p>
     * This method may only be invoked before or after calling one of the
     * unmarshal methods.
     * <p>
     * This method only controls the JAXB Provider's default unmarshal-time
     * validation mechanism - it has no impact on clients that specify their 
     * own validating SAX 2.0 compliant parser.  Clients that specify their
     * own unmarshal-time validation mechanism may wish to turn off the JAXB
     * Provider's default validation mechanism via this API to avoid "double
     * validation".
     * 
     * @param validating true if the Unmarshaller should validate during 
     *        unmarshal, false otherwise
     * @throws JAXBException if an error occurred while enabling or disabling
               validation at unmarshal time
     */
    public void setValidating( boolean validating ) 
        throws JAXBException;
    
    /**
     * Indicates whether or not the <tt>Unmarshaller</tt> is configured to 
     * validate during unmarshal operations.
     *
     * <p>
     * This API returns the state of the JAXB Provider's default unmarshal-time
     * validation mechanism. 
     *
     * @return true if the Unmarshaller is configured to validate during 
     *         unmarshal operations, false otherwise
     * @throws JAXBException if an error occurs while retrieving the validating
     *         flag
     */
    public boolean isValidating() 
        throws JAXBException;
    
    /**
     * Allow an application to register a <tt>ValidationEventHandler</tt>.
     * <p>
     * The <tt>ValidationEventHandler</tt> will be called by the JAXB Provider 
     * if any validation errors are encountered during calls to any of the 
     * unmarshal methods.  If the client application does not register a 
     * <tt>ValidationEventHandler</tt> before invoking the unmarshal methods, 
     * then <tt>ValidationEvents</tt> will be handled by the default event 
     * handler which will terminate the unmarshal operation after the first 
     * error or fatal error is encountered.
     * <p>
     * Calling this method with a null parameter will cause the Unmarshaller
     * to revert back to the default default event handler.
     *
     * @param handler the validation event handler
     * @throws JAXBException if an error was encountered while setting the
     *         event handler
     */
    public void setEventHandler( ValidationEventHandler handler )
        throws JAXBException;

    /**
     * Return the current event handler or the default event handler if one
     * hasn't been set.
     *
     * @return the current ValidationEventHandler or the default event handler
     *         if it hasn't been set
     * @throws JAXBException if an error was encountered while getting the 
     *         current event handler
     */
    public ValidationEventHandler getEventHandler()
        throws JAXBException;

    /**
     * Set the particular property in the underlying implementation of 
     * <tt>Unmarshaller</tt>.  This method can only be used to set one of
     * the standard JAXB defined properties above or a provider specific
     * property.  Attempting to set an undefined property will result in
     * a PropertyException being thrown.  See <a href="#supportedProps">
     * Supported Properties</a>.
     *
     * @param name the name of the property to be set. This value can either
     *              be specified using one of the constant fields or a user 
     *              supplied string.
     * @param value the value of the property to be set
     *
     * @throws PropertyException when there is an error processing the given
     *                            property or value
     * @throws IllegalArgumentException
     *      If the name parameter is null
     */
    public void setProperty( String name, Object value ) 
        throws PropertyException;
    
    /**
     * Get the particular property in the underlying implementation of 
     * <tt>Unmarshaller</tt>.  This method can only be used to get one of
     * the standard JAXB defined properties above or a provider specific
     * property.  Attempting to get an undefined property will result in
     * a PropertyException being thrown.  See <a href="#supportedProps">
     * Supported Properties</a>.
     *
     * @param name the name of the property to retrieve
     * @return the value of the requested property
     *
     * @throws PropertyException
     *      when there is an error retrieving the given property or value
     *      property name
     * @throws IllegalArgumentException
     *      If the name parameter is null
     */
    public Object getProperty( String name ) throws PropertyException;
    
        
}
