#prepare props svc

scenario 1
prepare $FACTORY_ID=jaxb.factory.Valid -
test jaxb.factory.Valid\$JAXBContext1 -

scenario 2
prepare something=AnotherThing -
test - javax.xml.bind.JAXBException

scenario 3
prepare $FACTORY_ID=non.existing.FactoryClass -
test - javax.xml.bind.JAXBException

scenario 4
prepare $FACTORY_ID=jaxb.factory.Invalid -
test - javax.xml.bind.JAXBException

scenario 5
prepare - -
test jaxb.factory.Valid\$JAXBContext1 - -D$FACTORY_ID=jaxb.factory.Valid

scenario 6
prepare - -
test - javax.xml.bind.JAXBException -D$FACTORY_ID=jaxb.factory.NonExisting

scenario 7
prepare - -
test - javax.xml.bind.JAXBException -D$FACTORY_ID=jaxb.factory.Invalid

scenario 8
prepare - ${FACTORY_IMPL_PREFIX}$'Valid\n'
test ${FACTORY_IMPL_PREFIX}Valid\$JAXBContext1  -

scenario 9
prepare - ${FACTORY_IMPL_PREFIX}Valid
test ${FACTORY_IMPL_PREFIX}Valid\$JAXBContext1 -

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
prepare $FACTORY_ID=jaxb.factory.Valid jaxb.factory.Valid2
test jaxb.factory.Valid\$JAXBContext1 - -D$FACTORY_ID=jaxb.factory.Valid3

scenario 14
prepare - jaxb.factory.Valid2
test jaxb.factory.Valid\$JAXBContext1 - -D$FACTORY_ID=jaxb.factory.Valid

scenario 15
prepare - ${FACTORY_IMPL_PREFIX}Valid
test ${FACTORY_IMPL_PREFIX}Valid\$JAXBContext1 -

#    # system props: currently checked both:
#    $FACTORY_ID > will change to (?) > javax.xml.bind.JAXBContextFactory
#    javax.xml.binf.JAXBContext
