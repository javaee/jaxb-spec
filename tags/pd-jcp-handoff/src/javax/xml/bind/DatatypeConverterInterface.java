/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.bind;

/**
 * <p>
 * The DatatypeConverterInterface is for JAXB provider use only. A 
 * JAXB provider must supply a class that implements this interface. 
 * JAXB Providers are required to call the 
 * {@link DatatypeConverter#setDatatypeConverter(DatatypeConverterInterface) 
 * DatatypeConverter.setDatatypeConverter} api at 
 * some point before the first marshal or unmarshal operation (perhaps during
 * the call to JAXBContext.newInstance).  This step is necessary to configure 
 * the converter that should be used to perform the print and parse 
 * functionality.  Calling this api repeatedly will have no effect - the 
 * DatatypeConverter instance passed into the first invocation is the one that 
 * will be used from then on.
 * </p>
 *
 * <p>
 * This interface defines the parse and print methods. There is one
 * parse and print method for each XML schema datatype specified in the
 * the default binding Table 5-1 in the JAXB specification. 
 * </p>
 *
 * <p>
 * The parse and print methods defined here are invoked by the static parse
 * and print methods defined in the {@link DatatypeConverter DatatypeConverter} 
 * class.
 * </p>
 *
 * <p>
 * A parse method for a XML schema datatype must be capable of converting any
 * lexical representation of the XML schema datatype ( specified by the
 * <a href="http://www.w3.org/TR/xmlschema-2/"> XML Schema Part2: Datatypes 
 * specification</a> into a value in the value space of the XML schema datatype. 
 * If an error is encountered during conversion, then a ParseConversionEvent 
 * must be generated.
 * </p>
 *
 * <p>
 * A print method for a XML schema datatype can output any lexical 
 * representation that is valid with respect to the XML schema datatype.
 * If an error is encountered during conversion, then a PrintConversionEvent
 * must be generated.
 * </p>
 *
 * The prefix xsd: is used to refer to XML schema datatypes
 * <a href="http://www.w3.org/TR/xmlschema-2/"> XML Schema Part2: Datatypes 
 * specification.</a>
 *  
 * <p>
 * @author <ul><li>Sekhar Vajjhala, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems Inc.</li><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Ryan Shoemaker,Sun Microsystems Inc.</li></ul>
 * @version $Revision: 1.2 $
 * @see DatatypeConverter
 * @see ParseConversionEvent
 * @see PrintConversionEvent
 * @since JAXB1.0
 */

public interface DatatypeConverterInterface {
    /**  
     * <p>
     * Convert the string argument into a string. 
     * @param lexicalXSDString
     *     A lexical representation of the XML Schema datatype xsd:string
     * @return
     *     A string that is the same as the input string.
     */ 
    public String parseString( String lexicalXSDString );

    /**
     * <p>
     * Convert the string argument into a BigInteger value.
     * @param
     *     lexicalXSDInteger A string containing a lexical representation of 
     *     xsd:integer.
     * @return
     *     A BigInteger value represented by the string argument.
     */ 
    public java.math.BigInteger parseInteger( String lexicalXSDInteger );

    /**
     * <p>
     * Convert the string argument into an int value.
     * @param
     *     lexicalXSDInt A string containing a lexical representation of 
     *     xsd:int.
     * @return
     *     An int value represented byte the string argument.
     */ 
    public int parseInt( String lexicalXSDInt );

    /**
     * <p>
     * Converts the string argument into a long value.
     * @param
     *     lexicalXSDLong A string containing lexical representation of 
     *     xsd:long.
     * @return
     *     A long value represented by the string argument.
     */ 
    public long parseLong( String lexicalXSDLong );

    /**
     * <p>
     * Converts the string argument into a short value.
     * @param
     *     lexicalXSDShort A string containing lexical representation of 
     *     xsd:short.
     * @return
     *     A short value represented by the string argument.
     */ 
    public short parseShort( String lexicalXSDShort );

    /**
     * <p>
     * Converts the string argument into a BigDecimal value.
     * @param
     *     lexicalXSDDecimal A string containing lexical representation of 
     *     xsd:decimal.
     * @return
     *     A BigDecimal value represented by the string argument.
     */ 
    public java.math.BigDecimal parseDecimal( String lexicalXSDDecimal );

    /**
     * <p>
     * Converts the string argument into a float value.
     * @param
     *     lexicalXSDFloat A string containing lexical representation of 
     *     xsd:float.
     * @return
     *     A float value represented by the string argument.
     */ 
    public float parseFloat( String lexicalXSDFloat );

    /**
     * <p>
     * Converts the string argument into a double value.
     * @param
     *     lexicalXSDDouble A string containing lexical representation of 
     *     xsd:double.
     * @return
     *     A double value represented by the string argument.
     */ 
    public double parseDouble( String lexicalXSDDouble );

    /**
     * <p>
     * Converts the string argument into a boolean value.
     * @param
     *     lexicalXSDBoolean A string containing lexical representation of 
     *     xsd:boolean.
     * @return
     *     A boolean value represented by the string argument.
     */ 
    public boolean parseBoolean( String lexicalXSDBoolean );

    /**
     * <p>
     * Converts the string argument into a byte value.
     * @param
     *     lexicalXSDByte A string containing lexical representation of 
     *     xsd:byte.
     * @return
     *     A byte value represented by the string argument.
     */ 
    public byte parseByte( String lexicalXSDByte );
    
    /**
     * <p>
     * Converts the string argument into a QName value.
     * @param lexicalXSDQName
     *     A string containing lexical representation of xsd:QName.
     * @param nsc
     *     A namespace context for interpreting a prefix within a QName.
     * @return
     *     A QName value represented by the string argument.
     */ 
    public javax.xml.namespace.QName parseQName( String lexicalXSDQName,
    				             javax.xml.namespace.NamespaceContext nsc);

    /**
     * <p>
     * Converts the string argument into a Calendar value.
     * @param
     *     lexicalXSDDateTime A string containing lexical representation of 
     *     xsd:datetime.
     * @return
     *     A Calendar object represented by the string argument.
     */ 
    public java.util.Calendar parseDateTime( String lexicalXSDDateTime );

    /**
     * <p>
     * Converts the string argument into an array of bytes.
     * @param
     *     lexicalXSDBase64Binary A string containing lexical representation
     *     of xsd:base64Binary.
     * @return
     *     An array of bytes represented by the string argument.
     */ 
    public byte[] parseBase64Binary( String lexicalXSDBase64Binary );

    /**
     * <p>
     * Converts the string argument into an array of bytes.
     * @param
     *     lexicalXSDHexBinary A string containing lexical representation of
     *     xsd:hexBinary.
     * @return
     *     An array of bytes represented by the string argument.
     */ 
    public byte[] parseHexBinary( String lexicalXSDHexBinary );

    /**
     * <p>
     * Converts the string argument into a long value.
     * @param
     *     lexicalXSDUnsignedInt A string containing lexical representation 
     *     of xsd:unsignedInt.
     * @return
     *     A long value represented by the string argument.
     */ 
    public long parseUnsignedInt( String lexicalXSDUnsignedInt );

    /**
     * <p>
     * Converts the string argument into an int value.
     * @param
     *     lexicalXSDUnsignedShort -A string containing lexical 
     *     representation of xsd:unsignedShort.
     * @return
     *     An int value represented by the string argument.
     */ 
    public int parseUnsignedShort( String lexicalXSDUnsignedShort );

    /**
     * <p>
     * Converts the string argument into a Calendar value.
     * @param
     *     lexicalXSDTime A string containing lexical representation of 
     *     xsd:time.
     * @return
     *     A Calendar value represented by the string argument.
     */ 
    public java.util.Calendar parseTime( String lexicalXSDTime );
    
    /**
     * <p>
     * Converts the string argument into a Calendar value.
     * @param
     *     lexicalXSDDate A string containing lexical representation of 
     *     xsd:Date.
     * @return
     *     A Calendar value represented by the string argument.
     */ 
    public java.util.Calendar parseDate( String lexicalXSDDate );

    /**
     * <p>
     * Return a string containing the lexical representation of the 
     * simple type.
     * @param
     *     lexicalXSDAnySimpleType A string containing lexical 
     *     representation of the simple type.
     * @return
     *     A string containing the lexical representation of the 
     *     simple type.
     */ 
    public String parseAnySimpleType( String lexicalXSDAnySimpleType );
    
    /**
     * <p>
     * Converts the string argument into a string.
     * @param val
     *     A string value.
     * @return
     *     A string containing a lexical representation of xsd:string
     */ 
    public String printString( String val );

    /**
     * <p>
     * Converts a BigInteger value into a string.
     * @param val
     *     A BigInteger value
     * @return
     *     A string containing a lexical representation of xsd:integer
     */ 
    public String printInteger( java.math.BigInteger val );

    /**
     * <p>
     * Converts an int value into a string.
     * @param val
     *     An int value
     * @return
     *     A string containing a lexical representation of xsd:int
     */ 
    public String printInt( int val );


    /**
     * <p>
     * Converts a long value into a string.
     * @param val
     *     A long value
     * @return
     *     A string containing a lexical representation of xsd:long
     */ 
    public String printLong( long val );

    /**
     * <p>
     * Converts a short value into a string.
     * @param val
     *     A short value
     * @return
     *     A string containing a lexical representation of xsd:short
     */ 
    public String printShort( short val );

    /**
     * <p>
     * Converts a BigDecimal value into a string.
     * @param val
     *     A BigDecimal value
     * @return
     *     A string containing a lexical representation of xsd:decimal
     */ 
    public String printDecimal( java.math.BigDecimal val );

    /**
     * <p>
     * Converts a float value into a string.
     * @param val
     *     A float value
     * @return
     *     A string containing a lexical representation of xsd:float
     */ 
    public String printFloat( float val );

    /**
     * <p>
     * Converts a double value into a string.
     * @param val
     *     A double value
     * @return
     *     A string containing a lexical representation of xsd:double
     */ 
    public String printDouble( double val );

    /**
     * <p>
     * Converts a boolean value into a string.
     * @param val
     *     A boolean value
     * @return
     *     A string containing a lexical representation of xsd:boolean
     */ 
    public String printBoolean( boolean val );

    /**
     * <p>
     * Converts a byte value into a string.
     * @param val
     *     A byte value
     * @return
     *     A string containing a lexical representation of xsd:byte
     */ 
    public String printByte( byte val );

    /**
     * <p>
     * Converts a QName instance into a string.
     * @param val
     *     A QName value
     * @param nsc
     *     A namespace context for interpreting a prefix within a QName.
     * @return
     *     A string containing a lexical representation of QName
     */ 
    public String printQName( javax.xml.namespace.QName val,
                              javax.xml.namespace.NamespaceContext nsc );

    /**
     * <p>
     * Converts a Calendar value into a string.
     * @param val
     *     A Calendar value
     * @return
     *     A string containing a lexical representation of xsd:dateTime
     */ 
    public String printDateTime( java.util.Calendar val );

    /**
     * <p>
     * Converts an array of bytes into a string.
     * @param val
     *     an array of bytes
     * @return
     *     A string containing a lexical representation of xsd:base64Binary
     */ 
    public String printBase64Binary( byte[] val );

    /**
     * <p>
     * Converts an array of bytes into a string.
     * @param val
     *     an array of bytes
     * @return
     *     A string containing a lexical representation of xsd:hexBinary
     */ 
    public String printHexBinary( byte[] val );

    /**
     * <p>
     * Converts a long value into a string.
     * @param val
     *     A long value
     * @return
     *     A string containing a lexical representation of xsd:unsignedInt
     */ 
    public String printUnsignedInt( long val );

    /**
     * <p>
     * Converts an int value into a string.
     * @param val
     *     An int value
     * @return
     *     A string containing a lexical representation of xsd:unsignedShort
     */ 
    public String printUnsignedShort( int val );

    /**
     * <p>
     * Converts a Calendar value into a string.
     * @param val
     *     A Calendar value
     * @return
     *     A string containing a lexical representation of xsd:time
     */ 
    public String printTime( java.util.Calendar val );

    /**
     * <p>
     * Converts a Calendar value into a string.
     * @param val
     *     A Calendar value
     * @return
     *     A string containing a lexical representation of xsd:date
     */ 
    public String printDate( java.util.Calendar val );

    /**
     * <p>
     * Converts a string value into a string.
     * @param val
     *     A string value
     * @return
     *     A string containing a lexical representation of xsd:AnySimpleType
     */ 
    public String printAnySimpleType( String val );
}
