/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind;

import javax.xml.namespace.QName;

/**
 * Representation of a named Xml Element.
 *
 * @author <ul><li>Ryan Shoemaker, Sun Microsystems, Inc.</li><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems, Inc.</li></ul> 
 * @version $Revision: 1.1 $
 * @since JAXB2.0
 */

public class XmlElement<T> implements IXmlElement<T>, java.io.Serializable {
    private QName   elementName;
    private T       elementValue;
    private boolean isNil;

    public QName getXmlElementName() {
	return elementName;
    }

    public void  setXmlElementName(QName tag) {
	elementName = tag;
    }

    public void  setXmlElementValue(T value){
	elementValue = value;
    }

    public T getXmlElementValue() {
	return elementValue;
    }

    /**
     * Return true iff this element is considered to have no content.
     *
     * Note that a element with nil content can still have attribute(s).
     */
    public boolean isXmlElementNil() { 
	return isNil; 
    }

    public void    setXmlELementNil(boolean value) { 
	isNil = value;
    }
}

