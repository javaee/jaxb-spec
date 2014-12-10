#!/bin/sh

#
# Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
#
# This code is free software; you can redistribute it and/or modify it
# under the terms of the GNU General Public License version 2 only, as
# published by the Free Software Foundation.  Oracle designates this
# particular file as subject to the "Classpath" exception as provided
# by Oracle in the LICENSE file that accompanied this code.
#
# This code is distributed in the hope that it will be useful, but WITHOUT
# ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
# FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
# version 2 for more details (a copy is included in the LICENSE file that
# accompanied this code).
#
# You should have received a copy of the GNU General Public License version
# 2 along with this work; if not, write to the Free Software Foundation,
# Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
#
# Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
# or visit www.oracle.com if you need additional information or have any
# questions.
#


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

export ENDORSED_DIR="`pwd`/endorsed"
export ENDORSED="-Djava.endorsed.dirs=$ENDORSED_DIR"

echo "JAVA_HOME: " $JAVA_HOME
echo "endorsed dirs: " $ENDORSED
ls -al $ENDORSED_DIR
javac -version


scenario() {
    echo ""
    echo "================================================"
    echo " Scenario " $1
    echo "================================================"
}

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
    # export D=$DEBUG
    export D=
    echo - JAXBTestContextPath ---
    java $JVM_OPTS $D $ENDORSED jaxb.test.JAXBTestContextPath $1 $2

    echo - JAXBTestClasses ---
    java $JVM_OPTS $D $ENDORSED jaxb.test.JAXBTestClasses $1 $2

    echo - JAXBTestContextPath+ClassLoader ---
    java $JVM_OPTS $ENDORSED -cp ../classes $D jaxb.test.JAXBTestContextPath $1 $2 WithClassLoader

    # parametrized classloader not applicable for method with classes:
    #    echo JAXBTestClasses+ClassLoader
    #    java -cp ../classes $D jaxb.test.JAXBTestClasses $1 $2 WithClassLoader
}

clean() {
    rm -rf META-INF
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
    echo "- prepare/clean -"
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

cd src

echo "- compilation -"
find . -name '*.class' -delete

compile 'jaxb/factory/*.java'
compile 'jaxb/test/usr/*.java'
compile 'jaxb/test/*.java'

# preparation for testing TCCL method
rm -rf ../classes/
mkdir -p ../classes/jaxb/test
cp jaxb/test/*.class ../classes/jaxb/test
find ../classes/


export DEFAULT=com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl

source ../scenarios.sh