/*
 * Copyright 2005 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind.annotation;

import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;

/**
 * Used by XmlAccessorType to control serialization of fields or
 * properties. 
 *
 * <p> <b> NOTE: The name of this annotation was intentionally chosen to
 * match the one in EJB 3.0, pending investigation of whether this
 * might be candidate for moving to Common Annotations JSR since it is
 * shared with EJB 3.0. </b> </p>
 *
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @since JAXB2.0
 * @version $Revision: 1.1 $
 */

public enum AccessType {
    PROPERTY,
    FIELD
} 

