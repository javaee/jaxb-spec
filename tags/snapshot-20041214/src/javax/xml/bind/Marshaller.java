/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind;

/**
 * <p>
 * The <tt>Marshaller</tt> class is responsible for governing the process
 * of serializing Java content trees back into XML data.  It provides the basic
 * marshalling methods:
 *
 * <p>
 * <i>Assume the following setup code in all following code fragments:</i>
 * <blockquote>
 *    <pre>
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *       FooObject obj = (FooObject)u.unmarshal( new File( "foo.xml" ) );
 *       Marshaller m = jc.createMarshaller();
 *    </pre>
 * </blockquote>
 * 
 * <p>
 * Marshalling to a File:
 * <blockquote>
 *    <pre>
 *       OutputStream os = new FileOutputStream( "nosferatu.xml" );
 *       m.marshal( obj, os );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a SAX ContentHandler:
 * <blockquote>
 *    <pre>
 *       // assume MyContentHandler instanceof ContentHandler
 *       m.marshal( obj, new MyContentHandler() );  
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a DOM Node:
 * <blockquote>
 *    <pre>
 *       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
 *       dbf.setNamespaceAware(true);
 *       DocumentBuilder db = dbf.newDocumentBuilder();
 *       Document doc = db.newDocument();
 *
 *       m.marshal( obj, doc );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a java.io.OutputStream:
 * <blockquote>
 *    <pre>
 *       m.marshal( obj, System.out );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a java.io.Writer:
 * <blockquote>
 *    <pre>
 *       m.marshal( obj, new PrintWriter( System.out ) );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a javax.xml.transform.SAXResult:
 * <blockquote>
 *    <pre>
 *       // assume MyContentHandler instanceof ContentHandler
 *       SAXResult result = new SAXResult( new MyContentHandler() );
 *
 *       m.marshal( obj, result );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a javax.xml.transform.DOMResult:
 * <blockquote>
 *    <pre>
 *       DOMResult result = new DOMResult();
 *       
 *       m.marshal( obj, result );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a javax.xml.transform.StreamResult:
 * <blockquote>
 *    <pre>
 *       StreamResult result = new StreamResult( System.out );
 * 
 *       m.marshal( obj, result );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a javax.xml.stream.XMLStreamWriter:
 * <blockquote>
 *    <pre>
 *       XMLStreamWriter xmlStreamWriter = 
 *           XMLOutputFactory.newInstance().createXMLStreamWriter( ... );
 * 
 *       m.marshal( obj, xmlStreamWriter );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a javax.xml.stream.XMLEventWriter:
 * <blockquote>
 *    <pre>
 *       XMLEventWriter xmlEventWriter = 
 *           XMLOutputFactory.newInstance().createXMLEventWriter( ... );
 * 
 *       m.marshal( obj, xmlEventWriter );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * <a name="objMarshalling"></a>
 * <b>Marshalling <tt>java.lang.Object</tt> Objects</b><br>
 * <blockquote>
 * Although each of the marshal methods accepts a <tt>java.lang.Object</tt> as 
 * its first parameter, JAXB Providers are not required to be able to marshal
 * <b>any</b> arbitrary <tt>java.lang.Object</tt>.  If the <tt>JAXBContext</tt> 
 * object that was used to create this <tt>Marshaller</tt> does not have enough 
 * information to know how to marshal the object parameter (or any objects 
 * reachable from it), then the marshal operation will throw a 
 * {@link MarshalException MarshalException}.  Even though JAXB Providers are not 
 * required to be able to marshal arbitrary <tt>java.lang.Object</tt> objects, 
 * some providers may allow it.
 * </blockquote>
 *
 * <p>
 * <b>Encoding</b><br>
 * <blockquote>
 * By default, the Marshaller will use UTF-8 encoding when generating XML data
 * to a <tt>java.io.OutputStream</tt>, or a <tt>java.io.Writer</tt>.  Use the 
 * {@link #setProperty(String,Object) setProperty} API to change the output 
 * encoding used during these marshal operations.  Client applications are
 * expected to supply a valid character encoding name as defined in the
 * <a href="http://www.w3.org/TR/2000/REC-xml-20001006#charencoding">W3C XML 1.0
 * Recommendation</a> and supported by your 
 * <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/package-summary.html#charenc">
 * Java Platform</a>.
 * </blockquote>
 * 
 * <p>
 * <b>Validation and Well-Formedness</b><br>
 * <blockquote>
 * <p>
 * Client applications are not required to validate the Java content tree prior
 * to calling any of the marshal API's.  Furthermore, there is no requirement 
 * that the Java content tree be valid with respect to its original schema in
 * order to marshal it back into XML data.  Different JAXB Providers will 
 * support marshalling invalid Java content trees at varying levels, however
 * all JAXB Providers must be able to marshal a valid content tree back to 
 * XML data.  A JAXB Provider must throw a <tt>MarshalException</tt> when it
 * is unable to complete the marshal operation due to invalid content.  Some
 * JAXB Providers will fully allow marshalling invalid content, others will fail
 * on the first validation error.
 * <p>
 * Although there is no way to enable validation during the marshal operation,
 * it is possible that certain types of validation events will be detected 
 * during the operation.  These events will be reported to the registered
 * event handler.  If the client application has not registered an event handler
 * prior to invoking one of the marshal API's, then events will be delivered to
 * the default event handler which will terminate the marshal operation after
 * encountering the first error or fatal error.
 * </blockquote>
 *
 * <p>
 * <a name="supportedProps"></a>
 * <b>Supported Properties</b><br>
 * <blockquote>
 * <p>
 * All JAXB Providers are required to support the following set of properties.
 * Some providers may support additional properties.
 * <dl>
 *   <dt><tt>jaxb.encoding</tt> - value must be a java.lang.String</dd>
 *   <dd>The output encoding to use when marshalling the XML data.  The
 * 		 Marshaller will use "UTF-8" by default if this property is not
 *  	 specified.</dd>
 *   <dt><tt>jaxb.formatted.output</tt> - value must be a java.lang.Boolean</dd>
 *   <dd>This property controls whether or not the Marshaller will format
 * 	 the resulting XML data with line breaks and indentation.  A
 *       true value for this property indicates human readable indented 
 *       xml data, while a false value indicates unformatted xml data.
 *       The Marshaller will default to false (unformatted) if this 
 *       property is not specified.</dd>
 *   <dt><tt>jaxb.schemaLocation</tt> - value must be a java.lang.String</dd>
 *   <dd>This property allows the client application to specify an
 *       xsi:schemaLocation attribute in the generated XML data.  The format of 
 *       the schemaLocation attribute value is discussed in an easy to 
 *       understand, non-normative form in 
 *       <a href="http://www.w3.org/TR/xmlschema-0/#schemaLocation">Section 5.6 
 *       of the W3C XML Schema Part 0: Primer</a> and specified in 
 *       <a href="http://www.w3.org/TR/xmlschema-1/#Instance_Document_Constructions">
 *       Section 2.6 of the W3C XML Schema Part 1: Structures</a>.</dd>
 *   <dt><tt>jaxb.noNamespaceSchemaLocation</tt> - value must be a java.lang.String</dd>
 *   <dd>This property allows the client application to specify an
 *       xsi:noNamespaceSchemaLocation attribute in the generated XML 
 *       data.  The format of the schemaLocation attribute value is discussed in 
 *       an easy to understand, non-normative form in 
 *       <a href="http://www.w3.org/TR/xmlschema-0/#schemaLocation">Section 5.6 
 *       of the W3C XML Schema Part 0: Primer</a> and specified in 
 *       <a href="http://www.w3.org/TR/xmlschema-1/#Instance_Document_Constructions">
 *       Section 2.6 of the W3C XML Schema Part 1: Structures</a>.</dd>
 *   <dt><tt>jaxb.fragment</tt> - value must be a java.lang.Boolean</dd>
 *   <dd>This property determines whether or not document level events will be
 *       generated by the Marshaller.  This has different implications depending
 *       on which marshal api you are using - when this property is set to true:<br>
 *       <ul>
 *         <li>{@link #marshal(Object,org.xml.sax.ContentHandler) marshal(Object,ContentHandler)} - the Marshaller won't
 *             invoke {@link org.xml.sax.ContentHandler#startDocument()} and
 *             {@link org.xml.sax.ContentHandler#endDocument()}.</li>
 *         <li>{@link #marshal(Object,org.w3c.dom.Node) marshal(Object,Node)} - the property has no effect on this
 *             API.</li>
 *         <li>{@link #marshal(Object,java.io.OutputStream) marshal(Object,OutputStream)} - the Marshaller won't
 *             generate an xml declaration.</li>
 *         <li>{@link #marshal(Object,java.io.Writer) marshal(Object,Writer)} - the Marshaller won't
 *             generate an xml declaration.</li>
 *         <li>{@link #marshal(Object,javax.xml.transform.Result) marshal(Object,Result)} - depends on the kind of
 *             Result object, see semantics for Node, ContentHandler, and Stream APIs</li>
 *         <li>{@link #marshal(Object,javax.xml.stream.XMLEventWriter) marshal(Object,XMLEventWriter)} - the
 *             Marshaller will not generate {@link javax.xml.stream.events.XMLEvent#START_DOCUMENT} and
 *             {@link javax.xml.stream.events.XMLEvent#END_DOCUMENT} events.</li>
 *         <li>{@link #marshal(Object,javax.xml.stream.XMLStreamWriter) marshal(Object,XMLStreamWriter)} - the
 *             Marshaller will not generate {@link javax.xml.stream.events.XMLEvent#START_DOCUMENT} and
 *             {@link javax.xml.stream.events.XMLEvent#END_DOCUMENT} events.</li>
 *       </ul>
 *   </dd>
 * </dl>
 * </blockquote>
 * 
 * @author <ul><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Ryan Shoemaker, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems, Inc.</li></ul>
 * @version $Revision: 1.4 $ $Date: 2004-07-28 20:26:01 $
 * @see JAXBContext
 * @see Validator
 * @see Unmarshaller
 * @since JAXB1.0
 */
public interface Marshaller {
    
    /** 
     * The name of the property used to specify the output encoding in
     * the marshalled XML data.
     */
    public static final String JAXB_ENCODING = 
        "jaxb.encoding";

    /** 
     * The name of the property used to specify whether or not the marshalled 
     * XML data is formatted with linefeeds and indentation. 
     */
    public static final String JAXB_FORMATTED_OUTPUT = 
        "jaxb.formatted.output";
    
    /** 
     * The name of the property used to specify the xsi:schemaLocation
     * attribute value to place in the marshalled XML output.
     */
    public static final String JAXB_SCHEMA_LOCATION = 
        "jaxb.schemaLocation";
    
    /**
     * The name of the property used to specify the
     * xsi:noNamespaceSchemaLocation attribute value to place in the marshalled
     * XML output.
     */
    public static final String JAXB_NO_NAMESPACE_SCHEMA_LOCATION =
        "jaxb.noNamespaceSchemaLocation";

    /**
     * The name of the property used to specify whether or not the marshaller
     * will generate document level events (ie calling startDocument or endDocument).
     */
    public static final String JAXB_FRAGMENT =
        "jaxb.fragment";

    /**
     * Marshal the content tree rooted at obj into the specified 
     * <tt>javax.xml.transform.Result</tt>.
     * 
     * <p>
     * All JAXB Providers must at least support
     * {@link javax.xml.transform.dom.DOMResult},
     * {@link javax.xml.transform.sax.SAXResult}, and
     * {@link javax.xml.transform.stream.StreamResult}. It can 
     * support other derived classes of <tt>Result</tt> as well.
     * 
     * @param obj
     *      The content tree to be marshalled. 
     * @param result
     *      XML will be sent to this Result
     * 
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the 
     *      <tt>Marshaller</tt> is unable to marshal <tt>obj</tt> (or any 
     *      object reachable from <tt>obj</tt>).  See <a href="#objMarshalling">
     *      Marshalling objects</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     */
    public void marshal( Object obj, javax.xml.transform.Result result )
        throws JAXBException;
     
    /**
     * Marshal the content tree rooted at obj into an output stream.
     * 
     * @param obj
     *      The content tree to be marshalled. 
     * @param os
     *      XML will be added to this stream.
     * 
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the 
     *      <tt>Marshaller</tt> is unable to marshal <tt>obj</tt> (or any 
     *      object reachable from <tt>obj</tt>).  See <a href="#objMarshalling">
     *      Marshalling objects</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     */
    public void marshal( Object obj, java.io.OutputStream os )
        throws JAXBException;
     
    /**
     * Marshal the content tree rooted at obj into a Writer.
     * 
     * @param obj
     *      The content tree to be marshalled. 
     * @param writer
     *      XML will be sent to this writer.
     * 
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the 
     *      <tt>Marshaller</tt> is unable to marshal <tt>obj</tt> (or any 
     *      object reachable from <tt>obj</tt>).  See <a href="#objMarshalling">
     *      Marshalling objects</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     */
    public void marshal( Object obj, java.io.Writer writer )
        throws JAXBException;
     
    /**
     * Marshal the content tree rooted at obj into SAX2 events.
     * 
     * @param obj
     *      The content tree to be marshalled. 
     * @param handler
     *      XML will be sent to this handler as SAX2 events.
     * 
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the 
     *      <tt>Marshaller</tt> is unable to marshal <tt>obj</tt> (or any 
     *      object reachable from <tt>obj</tt>).  See <a href="#objMarshalling">
     *      Marshalling objects</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     */
    public void marshal( Object obj, org.xml.sax.ContentHandler handler )
        throws JAXBException;
    
    /**
     * Marshal the content tree rooted at obj into a DOM tree.
     * 
     * @param obj
     *      The content tree to be marshalled. 
     * @param node
     *      DOM nodes will be added as children of this node.
     *      This parameter must be a Node that accepts children
     *      ({@link org.w3c.dom.Document},
     *      {@link  org.w3c.dom.DocumentFragment}, or
     *      {@link  org.w3c.dom.Element})
     * 
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the 
     *      <tt>Marshaller</tt> is unable to marshal <tt>obj</tt> (or any 
     *      object reachable from <tt>obj</tt>).  See <a href="#objMarshalling">
     *      Marshalling objects</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     */
    public void marshal( Object obj, org.w3c.dom.Node node )
        throws JAXBException;
    
    /**
     * Marshal the content tree rooted at obj into a
     * {@link javax.xml.stream.XMLStreamWriter}.
     * 
     * @param obj
     *      The content tree to be marshalled. 
     * @param writer
     *      XML will be sent to this writer.
     * 
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the 
     *      <tt>Marshaller</tt> is unable to marshal <tt>obj</tt> (or any 
     *      object reachable from <tt>obj</tt>).  See <a href="#objMarshalling">
     *      Marshalling objects</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     * @since JAXB 2.0
     */
    public void marshal( Object obj, javax.xml.stream.XMLStreamWriter writer )
        throws JAXBException;
    
    /**
     * Marshal the content tree rooted at obj into a
     * {@link javax.xml.stream.XMLEventWriter}.
     * 
     * @param obj
     *      The content tree to be marshalled. 
     * @param writer
     *      XML will be sent to this writer.
     * 
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the 
     *      <tt>Marshaller</tt> is unable to marshal <tt>obj</tt> (or any 
     *      object reachable from <tt>obj</tt>).  See <a href="#objMarshalling">
     *      Marshalling objects</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     * @since JAXB 2.0
     */
    public void marshal( Object obj, javax.xml.stream.XMLEventWriter writer )
        throws JAXBException;
    
    /**
     * Get a DOM tree view of the content tree(Optional).
     * 
     * If the returned DOM tree is updated, these changes are also 
     * visible in the content tree. 
     * Use {@link #marshal(Object, org.w3c.dom.Node)} to force
     * a deep copy of the content tree to a DOM representation.
     * 
     * @param contentTree - JAXB Java representation of XML content
     * 
     * @return the DOM tree view of the contentTree
     * 
     * @throws UnsupportedOperationException
     *      If the JAXB provider implementation does not support a
     *      DOM view of the content tree
     * 
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     *
     * @throws JAXBException
     *      If any unexpected problem occurs
     *
     */
    public org.w3c.dom.Node getNode( java.lang.Object contentTree )
        throws JAXBException;
    
    /**
     * Set the particular property in the underlying implementation of 
     * <tt>Marshaller</tt>.  This method can only be used to set one of
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
     * <tt>Marshaller</tt>.  This method can only be used to get one of
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
    
    /**
     * Allow an application to register a validation event handler.
     * <p>
     * The validation event handler will be called by the JAXB Provider if any
     * validation errors are encountered during calls to any of the marshal
     * API's.  If the client application does not register a validation event 
     * handler before invoking one of the marshal methods, then validation 
     * events will be handled by the default event handler which will terminate 
     * the marshal operation after the first error or fatal error is encountered.
     * <p>
     * Calling this method with a null parameter will cause the Marshaller
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
        
    
}



