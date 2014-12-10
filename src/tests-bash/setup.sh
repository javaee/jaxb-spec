#!/bin/sh

rm -rf endorsed
mkdir endorsed
cd ../..
mvn clean package
cp target/jaxb-api*.jar src/tests-bash/endorsed

echo "endorsed dir: "`pwd`/src/tests-bash/endorsed
ls -al src/tests-bash/endorsed