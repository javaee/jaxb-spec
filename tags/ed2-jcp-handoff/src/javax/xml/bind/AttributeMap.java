/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind;

import javax.xml.namespace.QName;
import java.util.Collection;

/**
 * Maps attribute names to their values.
 * 
 * @author <ul><li>Ryan Shoemaker, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems, Inc.</li></ul> 
 * @version $Revision: 1.2 $
 * @since JAXB2.0
 */
public abstract class AttributeMap {

    /**
     * <p>Return the object value of attribute specified by parameter 
     * <code>name</code>.</p>
     *
     * <p>If the attribute does not exist, return a null value.</p>
     *
     * <p>This method returns as strongly typed binding of the value of the
     * attribute as possible. For example,
     * for a global attribute declaration of type xs:boolean declared in the 
     * schema and introduced to an element via attribute wildcard having
     * processContents="strict" or "lax", 
     * a java.lang.boolean instance is returned by this method,
     * not the string value of the boolean. However, if the attribute name 
     * was not statically defined in the schema or when the wildcard attribute
     * processing model is "skip", the only possible return result is
     * a <code>java.lang.String</code> instance.</p>
     *
     * @param name a QName representing the name of the attribute
     *             to retrieve
     * @return the Object instance of the attribute's value
     *         if it exists, null otherwise     
     */
    abstract public Object getAttribute(QName name);

    /**
     * Set/Reset/Delete an attribute. 
     * 
     * <p>
     * If the attribute does not exist, then a new one
     * is created whose name is defined by the 'name' parameter
     * and value is defined by the 'value' parameter.
     * </p>
     *
     * <p>If the attribute already exists, then its current
     * value will be replaced by the 'value' parameter.  
     * </p>
     *
     * <p>Passing a null 'value' will cause an attribute wildcard
     * to be removed (if it exists) and it will not appear in 
     * the marshalled output.
     * </p>
     * 
     * <p>Structural validation of attribute values is not performed 
     * when this method is called.  This type of validation can 
     * be performed either during unmarshal or on-demand 
     * validation.  Prior to setting the property's value when 
     * TypeConstraint validation is enabled(fail-fast validation 
     * is optional), a non-null value is validated by applying the
     * property's predicate, but this can only occur for global 
     * attributes known at schema compile-time.  If 
     * TypeConstraintException is thrown, the attribute retains 
     * the value it had prior to the set method invocation.  
     * </p>
     * 
     * <p>Attribute value normalization occurs at marshal time according
     * to the XML 1.0 (2nd Ed.) Section 3.3.3 "Attribute-Value 
     * Normalization", not when this method is called.</p>
     *
     * @param name a QName representing the name of the attribute
     *             to retrieve
     * @param value value to be associated with the specified 
     *              attribute. 
     */
    abstract public void   setAttribute(QName name, Object value);

    /**
     * Returns a read-only collection of set of attribute
     * QName objects.
     *
     * <p>The attribute QNames are returned in no particular order.</p>
     *
     * @return a readonly collection over the set of attribute
     *         QName objects.    
     */
    abstract public Collection<QName> names();

    /**
     * Returns a read-only collection of set of attribute
     * QNames defined in <code>namespaceURI</code>.
     *
     * <p>The attribute QNames are returned in no particular order.</p>
     *
     * @return a readonly collection over the set of attribute
     *         QName objects.    
     */
    abstract public Collection<QName> names(String namespaceURI);
}

