#!/bin/bash -ex

BDIR=`pwd`
cd ../..

mvn javadoc:javadoc

rm -rf _SPEC_/2.2.13-LATEST*
mv target/site/apidocs _SPEC_/2.2.13-LATEST

specdiff _SPEC_/2.2.13-SNAPSHOT _SPEC_/2.2.13-LATEST

open _SPEC_/2.2.13-LATEST-specdiff/index.html

cd $BDIR