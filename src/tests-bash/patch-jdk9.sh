#!/bin/bash -ex
DIR=`pwd`
SRC=/Users/miran/dev/jigsaw/APIs/LATEST/jaxb-api/src/main/java
JDK9=/Users/miran/dev/jdk-forests/jdk9/jdk9u-dev/jaxws/src/java.xml.bind/share/classes/javax/xml/bind

cp -v \
   $SRC/javax/xml/bind/ContextFinder.java \
   $SRC/javax/xml/bind/JAXBContext.java \
   $SRC/javax/xml/bind/JAXBContextFactory.java \
   $SRC/javax/xml/bind/ServiceLoaderUtil.java \
 $JDK9/

cd /Users/miran/dev/jdk-forests/
ant -f build-patch-jdk9-dev.xml patch

cd $DIR
. endorsed.sh
