#!/bin/sh


#
# Last SPEC's version javadoc:
# http://docs.oracle.com/javase/8/docs/api/javax/xml/bind/JAXBContext.html
# -------------------------------------------------------------------------------
#    Discovery of JAXB implementation
#
#    When one of the newInstance methods is called, a JAXB implementation is discovered by the following steps.
#
#    1) For each package/class explicitly passed in to the newInstance(java.lang.String) method, in the order they are specified, jaxb.properties file is looked up in its package, by using the associated classloader — this is the owner class loader for a Class argument, and for a package the specified ClassLoader.
#    If such a file is discovered, it is loaded as a property file, and the value of the JAXB_CONTEXT_FACTORY key will be assumed to be the provider factory class. This class is then loaded by the associated classloader discussed above.
#
#    This phase of the look up allows some packages to force the use of a certain JAXB implementation. (For example, perhaps the schema compiler has generated some vendor extension in the code.)
#
#    2) If the system property JAXB_CONTEXT_FACTORY exists, then its value is assumed to be the provider factory class. This phase of the look up enables per-JVM override of the JAXB implementation.
#    3) Look for /META-INF/services/javax.xml.bind.JAXBContext file in the associated classloader. This file follows the standard service descriptor convention, and if such a file exists, its content is assumed to be the provider factory class. This phase of the look up is for automatic discovery. It allows users to just put a JAXB implementation in a classpath and use it without any furhter configuration.
#    4) Finally, if all the steps above fail, then the rest of the look up is unspecified. That said, the recommended behavior is to simply look for some hard-coded platform default JAXB implementation. This phase of the look up is so that JavaSE can have its own JAXB implementation as the last resort.
#    Once the provider factory class is discovered, its public static JAXBContext createContext(String,ClassLoader,Map) method (see newInstance(String, ClassLoader, Map) for the parameter semantics.) or public static JAXBContext createContet(Class[],Map) method (see newInstance(Class[], Map) for the parameter semantics) are invoked to create a JAXBContext.
# -------------------------------------------------------------------------------
# in short:
#
# 1) jaxb.properties in context package(s) - keyd by JAXB_CONTEXT_FACTORY
# 2) SystemProperty: JAXB_CONTEXT_FACTORY
#  * OSGi (non-SPEC)
# 3) ServiceLoader: /META-INF/services/javax.xml.bind.JAXBContext
# 4) default provider


#    testcases:
#
#        1) property file: ok
#        2) property file: missing property
#        3) property file: non-existing class property
#        4) property file: invalid class property
#
#        # system property: javax.xml.bind.context.factory
#        5) system property: ok
#        6) system property: ClassNotFound
#        7) system property: incorrect class

#        # ServiceLoader:
#        8) ok
#        9) ok - no new line
#        10) ClassNotFound
#        11) incorrect class
#
#        # default
#        12) no setup
#
#        # priorities:
#        13) prop.file sys.property ServiceLoader > prop.file
#        14) - sys.property + ServiceLoader > sys.prop
#        15) - - ServiceLoader > ServiceLoader
#

javac -version

compile() {
    javac -XDignore.symbol.file  $1
}

#
# Each test call tests 3 different cases:
#  1) ContextFactory.createContext(Class[] classes, Map<String,Object> properties )
#  2) ContextFactory.createContext( String path )
#  3) ContextFactory.createContext( String path ) + setting TCCL
#
test() {
    JVM_OPTS=$3
#    export D=$DEBUG
    export D=
    echo - JAXBTestContextPath ---
    java $JVM_OPTS $D jaxb.test.JAXBTestContextPath $1 $2

    echo - JAXBTestClasses ---
    java $JVM_OPTS $D jaxb.test.JAXBTestClasses $1 $2

    echo - JAXBTestContextPath+ClassLoader ---
    java $JVM_OPTS -cp ../classes $D jaxb.test.JAXBTestContextPath $1 $2 WithClassLoader

# parametrized classloader not applicable for method with classes:
#    echo JAXBTestClasses+ClassLoader
#    java -cp ../classes $D jaxb.test.JAXBTestClasses $1 $2 WithClassLoader

}

clean() {
    rm -rf META-INF
    # TODO: rm -rf $JAVA_HOME/jre/lib/jaxws.properties
}

#
# Sets up:
#  1) jaxb.properties file
#  2) META-INF/services/javax.xml.bind.context.factory file
#
prepare() {
    PROPS=$1
    SVC=$2

    echo ""
    echo "- prepare/clean -------------------------------------------"
    clean
    if [ "$SVC" != "-" ]; then
        mkdir -p META-INF/services
        echo "$SVC" > META-INF/services/javax.xml.bind.JAXBContext
    else
        rm -rf META-INF
    fi

    echo META-INF: $SVC
    if [ -f META-INF/services/javax.xml.bind.JAXBContext ]; then
      echo "   "`ls -al META-INF/services/javax.xml.bind.JAXBContext`
      echo "   "`cat META-INF/services/javax.xml.bind.JAXBContext`
      echo ""
    fi

    if [ "$PROPS" != "-" ]; then
        echo $PROPS > jaxb/test/usr/jaxb.properties
        else rm -rf jaxb/test/usr/jaxb.properties
    fi

    echo properties: $PROPS
    if [ -f jaxb/test/usr/jaxb.properties ]; then
      echo "   "`ls -al jaxb/test/usr/jaxb.properties`
      echo "   "`cat jaxb/test/usr/jaxb.properties`
      echo ""
    fi

}

echo "- compilation ---------------------------------------------"
find . -name '*.class' -delete

compile 'jaxb/factory/*.java'
compile 'jaxb/test/usr/*.java'
compile 'jaxb/test/*.java'

ls -al ../classes/
mkdir -p ../classes/jaxb/test
ls -al
cp jaxb/test/*.class ../classes/jaxb/test


export DEFAULT=com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl

##prepare props svc

# 1
prepare javax.xml.bind.context.factory=jaxb.factory.Valid -
test jaxb.factory.Valid\$PropValidJAXBContext -

# 2
prepare something=AnotherThing -
test - javax.xml.bind.JAXBException

# 3
prepare javax.xml.bind.context.factory=non.existing.FactoryClass -
test - javax.xml.bind.JAXBException

# 4
prepare javax.xml.bind.context.factory=jaxb.factory.Invalid -
test - javax.xml.bind.JAXBException

# 5
prepare - -
test jaxb.factory.Valid\$PropValidJAXBContext - -Djavax.xml.bind.context.factory=jaxb.factory.Valid

# 6
prepare - -
test - javax.xml.bind.JAXBException -Djavax.xml.bind.context.factory=jaxb.factory.NonExisting

# 7
prepare - -
test - javax.xml.bind.JAXBException -Djavax.xml.bind.context.factory=jaxb.factory.Invalid

# 8
prepare - $'jaxb.factory.Valid\n'
test jaxb.factory.Valid\$PropValidJAXBContext  -

# 9
prepare - jaxb.factory.Valid
test jaxb.factory.Valid\$PropValidJAXBContext -

# 10
prepare - jaxb.factory.NonExisting
test - javax.xml.bind.JAXBException

# 11
prepare - jaxb.factory.Invalid
test - javax.xml.bind.JAXBException

# 12
prepare - -
test $DEFAULT -

# 13
prepare javax.xml.bind.context.factory=jaxb.factory.Valid jaxb.factory.Valid2
test jaxb.factory.Valid\$PropValidJAXBContext - -Djavax.xml.bind.context.factory=jaxb.factory.Valid3

# 14
prepare - jaxb.factory.Valid2
test jaxb.factory.Valid\$PropValidJAXBContext - -Djavax.xml.bind.context.factory=jaxb.factory.Valid

# 15
prepare - jaxb.factory.Valid2
test jaxb.factory.Valid2\$PropValidJAXBContext2 -


