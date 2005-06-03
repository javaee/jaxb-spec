/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind.annotation.adapters;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.PARAMETER;


/**
 * Use an adapter that implements {@link XmlAdapter} for custom marshaling.
 *
 * <p> <b> Usage: </b> </p>
 *
 * <hr>
 * <b> Note to Reviewers: </b> This annotation has been completely
 * redesigned since JAXB 2.0 ED, 0.40. See {@link XmlAdapter}. 
 * <hr>
 *
 * <p> This annotation allows the use of an adapter that implements
 * the {@link XmlAdapter} interface for custom marshaling.
 *
 * <b> Example: </b> See example in {@link XmlAdapter}
 *
 * @author <ul><li>Sekhar Vajjhala, Sun Microsystems Inc.</li> <li> Kohsuke Kawaguchi, Sun Microsystems Inc.</li></ul>
 * @since JAXB2.0
 * @see XmlAdapter
 * @version $Revision: 1.2 $
 */

@Retention(RUNTIME) @Target({FIELD,METHOD,TYPE,PARAMETER})        
public @interface XmlJavaTypeAdapter {
    /**
     * Points to the clsss that converts a value type to a bound type or vice versa.
     * See {@link XmlAdapter} for more details.
     */
    Class<? extends XmlAdapter> value();
}
