#prepare props svc

scenario 1
prepare javax.xml.bind.context.factory=jaxb.factory.Valid -
test jaxb.factory.Valid\$PropValidJAXBContext -

scenario 2
prepare something=AnotherThing -
test - javax.xml.bind.JAXBException

scenario 3
prepare javax.xml.bind.context.factory=non.existing.FactoryClass -
test - javax.xml.bind.JAXBException

scenario 4
prepare javax.xml.bind.context.factory=jaxb.factory.Invalid -
test - javax.xml.bind.JAXBException

scenario 5
prepare - -
test jaxb.factory.Valid\$PropValidJAXBContext - -Djavax.xml.bind.context.factory=jaxb.factory.Valid

scenario 6
prepare - -
test - javax.xml.bind.JAXBException -Djavax.xml.bind.context.factory=jaxb.factory.NonExisting

scenario 7
prepare - -
test - javax.xml.bind.JAXBException -Djavax.xml.bind.context.factory=jaxb.factory.Invalid

scenario 8
prepare - $'jaxb.factory.Valid\n'
test jaxb.factory.Valid\$PropValidJAXBContext  -

scenario 9
prepare - jaxb.factory.Valid
test jaxb.factory.Valid\$PropValidJAXBContext -

scenario 10
prepare - jaxb.factory.NonExisting
test - javax.xml.bind.JAXBException

scenario 11
prepare - jaxb.factory.Invalid
test - javax.xml.bind.JAXBException

scenario 12
prepare - -
test $DEFAULT -

scenario 13
prepare javax.xml.bind.context.factory=jaxb.factory.Valid jaxb.factory.Valid2
test jaxb.factory.Valid\$PropValidJAXBContext - -Djavax.xml.bind.context.factory=jaxb.factory.Valid3

scenario 14
prepare - jaxb.factory.Valid2
test jaxb.factory.Valid\$PropValidJAXBContext - -Djavax.xml.bind.context.factory=jaxb.factory.Valid

scenario 15
prepare - jaxb.factory.Valid2
test jaxb.factory.Valid2\$PropValidJAXBContext2 -

scenario cleanup
prepare - -
rm -rf ../ctx-classloader-test
find . -name '*.class' -delete
