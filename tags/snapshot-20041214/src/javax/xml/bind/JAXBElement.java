/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind;

import javax.xml.namespace.QName;

/**
 * JAXB representation of an Xml Element instance.
 *
 * @author Kohsuke Kawaguchi, Joe Fialli
 * @since JAXB 2.0
 */

public class JAXBElement<T> {

    /** xml element tag name */
    final private QName name;

    /** Java datatype binding for xml element declaration's type. */
    final private Class<T> declaredType;

    /** Scope of xml element declaration representing this xml element instance.
     *  Can be one of the following values:
     *  - {@link #GlobalScope} for global xml element declaration.
     *  - local element declaration has a scope set to the Java class 
     *     representation of complex type defintion containing
     *     xml element declaration. 
     */
    final private Class scope;

    /** xml element value. 
        Represents content model and attributes of an xml element instance. */
    private T value;

    /** true iff the xml element instance has xsi:nil="true". */
    private boolean nil = false;

    /**
     * Designates global scope for an xml element.
     */
    public static final class GlobalScope {}

    /**
     * <p>Construct an xml element instance.</p>
     * 
     * @param name          Java binding of xml element tag name
     * @param declaredType  Java binding of xml element declaration's type
     * @param scope         Java binding of scope of xml element declaration
     * @param value         Java instance representing xml element's 
     *                      content model and/or attribute(s).
     * @see #getScope()
     * @see #isTypeSubstituted()
     */
    public JAXBElement(QName name, 
		       Class<T> declaredType, 
		       Class<T> scope, 
		       T value) {
        this.value = value;
        this.declaredType = declaredType;
	this.scope = scope;
        this.name = name;
    }

    /** 
     * Returns the Java binding of the xml element declaration's type attribute.
     */
    public Class<T> getDeclaredType() {
        return declaredType;
    }

    /**
     * Returns the xml element tag name.
     */
    public QName getName() {
        return name;
    }

    /**
     * <p>Set the content model and attributes of this xml element.</p>
     *
     * Note that this value can be non-null even when 
     * {@link #isNil()} is <code>true<\code> since a nil element
     * can have attributes.
     *
     * @see #isTypeSubstituted()
     */
    public void setValue(T t) {
        this.value = t;
    }

    /**
     * Return the content model and attribute values for this element.
     *
     * Note that this value can be non-null even when 
     * {@link #isNil()} is <code>true<\code> since a nil element
     * can have attributes.
     */
    public T getValue() {
        return value;
    }

    /**
     * Returns scope of xml element declaration.
     *
     * @see #isGlobalScope()
     */
    public Class getScope() {
        return scope;
    }
    
    /**
     * <p>Returns <code>true</code> iff this element instance content model 
     * is nil.</p>
     */
    public boolean isNil() {
        return nil;
    }

    /**
     * <p>Set whether this element has nil content.</p>
     * <p>Default value for this property is false.</p>
     */
    public void setNil(boolean value) {
        this.nil = value;
    }
    
    /* Convenience methods  
     * (Not necessary but they do unambiguously conceptualize 
     *  the rationale behind this class' fields.)
     */

    /**
     * Returns true iff this xml element declaration is global.
     */
    public boolean isGlobalScope() {
	return this.scope == GlobalScope.class;
    }

    /**
     * Returns true iff this xml element instance's value has a different
     * type than xml element declaration's declared type.
     */
    public boolean isTypeSubstituted() {
	return this.value.getClass() != this.declaredType;
    }
}
