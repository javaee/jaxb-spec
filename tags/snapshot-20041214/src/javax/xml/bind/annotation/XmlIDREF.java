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
 * Maps a JavaBean property to XML Schema type <tt>xs:IDREF</tt>.
 * 
 * <p>
 * To preserve referential integrity of an object graph across XML
 * serialization followed by a XML deserialization, requires an object
 * reference to be marshalled by reference or containment
 * appropriately. Annotations <tt>&#64;XmlID</tt> and <tt>&#64;XmlIDREF</tt>
 * together allow a customized mapping of a JavaBean property's
 * type by containment or reference. 
 *
 * <p><b>Usage</b> </p>
 * The <tt>&#64;XmlIDREF</tt> annotation can be used with the following
 * program elements: 
 * <ul> 
 *   <li> a JavaBean property </li>
 *   <li> a public non final, non static field </li>
 * </ul>
 * 
 * <p> The usage is subject to the following constraints:
 * <ul>
 *   <li> JavaBean property's type must contain a JavaBean property
 *        annotated with <tt>&#64;XmlID</tt>.</li>
 *  
 *   <li> The only additional mapping annotations that can be used
 *        with <tt>@xs:IDREF</tt> are: <tt>@XmlElement</tt> and
 *        <tt>@XmlAttribute</tt>.</li>
 *   <li>If the JavaBean property is a read/write property, then the
 *       <tt>@XmlIDREF</tt> can be used to annotate either the getter
 *       or setter method but not both.</li> 
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * </ul>
 * <p><b>Example:</b> Map a JavaBean property to <tt>xs:IDREF</tt>
 *   (i.e. by reference rather than by containment)</p>
 * <pre>
 *
 *   //EXAMPLE: Code fragment
 *   public class Shipping {
 *       &#64;XmlIDREF public Customer getCustomer();
 *       public void setCustomer(Customer customer);
 *       ....
 *    }
 *
 *   &lt;!-- Example: XML Schema fragment -->
 *   &lt;xs:complexType name="Shipping">
 *     &lt;xs:complexContent>
 *       &lt;xs:sequence>
 *         &lt;xs:element name="customer" type="xs:IDREF"/>
 *         ....
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 *
 * </pre>
 *
 *
 * <p><b>Example 2: </b> The following is a complete example of
 * containment versus reference.
 * 
 * <pre>
 *    // By default, Customer maps to complex type <tt>xs:Customer</tt>
 *    public class Customer {
 *        
 *        // map JavaBean property type to <tt>xs:ID</tt>
 *        &#64;XmlID public String getCustomerID();
 *        public void setCustomerID(String id);
 *
 *        // .... other properties not shown 
 *    }
 *
 *
 *   // By default, Invoice maps to a complex type <tt>xs:Invoice</tt> 
 *   public class Invoice {
 *    
 *       // map by reference
 *       &#64;XmlIDREF public Customer getCustomer();       
 *       public void setCustomer(Customer customer);
 *
 *      // .... other properties not shown here
 *   }
 *
 *   // By default, Shipping maps to complex type <tt>xs:Shipping</tt>
 *   public class Shipping {
 *
 *       // map by reference
 *       &#64;XmlIDREF public Customer getCustomer();       
 *       public void setCustomer(Customer customer);
 *   }
 *
 *   // at least one class must reference Customer by containment;
 *   // Customer instances won't be marshalled.
 *   &#64;XmlElement(name="CustomerData")
 *   public class CustomerData {
 *       // map reference to Customer by containment by default.
 *       public Customer getCustomer();
 *
 *       // maps reference to Shipping by containment by default. 
 *       public Shipping getShipping();     
 *
 *       // maps reference to Invoice by containment by default. 
 *       public Invoice getInvoice();     
 *   }
 *
 *   &lt;!-- XML Schema mapping for above code frament -->
 *
 *   &lt;xs:complexType name="Invoice">
 *     &lt;xs:complexContent>
 *       &lt;xs:sequence>
 *         &lt;xs:element name="customer" type="xs:IDREF"/>
 *         ....
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 *
 *   &lt;xs:complexType name="Shipping">
 *     &lt;xs:complexContent>
 *       &lt;xs:sequence>
 *         &lt;xs:element name="customer" type="xs:IDREF"/>
 *         ....
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 *
 *   &lt;xs:complexType name="Customer">
 *     &lt;xs:complexContent>
 *       &lt;xs:sequence>
 *         ....
 *       &lt;/xs:sequence>
 *       &lt;xs:attribute name="CustomerID" type="xs:ID"/>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 *
 *   &lt;xs:complexType name="CustomerData">
 *     &lt;xs:complexContent>
 *       &lt;xs:sequence>
 *         &lt;xs:element name="customer" type="xs:Customer"/>
 *         &lt;xs:element name="shipping" type="xs:Shipping"/>
 *         &lt;xs:element name="invoice"  type="xs:Invoice"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 *
 *   &lt;xs:element name"customerData" type="xs:CustomerData"/>
 *
 *   &lt;!-- Instance document conforming to the above XML Schema -->
 *    &lt;customerData>
 *       &lt;customer customerID="Alice">
 *           ....
 *       &lt;/customer>
 *
 *       &lt;shipping customer="Alice">
 *           ....
 *       &lt;/shipping>
 *         
 *       &lt;invoice customer="Alice">
 *           ....
 *       &lt;/invoice>
 *   &lt;/customerData>
 *
 * </pre>
 *
 * @author Sekhar Vajjhala, Sun Microsystems, Inc. 
 * @see XmlID
 * @since JAXB2.0
 * @version $Revision: 1.3 $
 */

@Retention(RUNTIME) @Target({FIELD, METHOD})
public @interface XmlIDREF {}
