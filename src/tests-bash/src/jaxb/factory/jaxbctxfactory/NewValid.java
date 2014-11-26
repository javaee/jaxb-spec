package jaxb.factory.jaxbctxfactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBContextFactory;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import java.util.Map;

/**
 * Created by miran on 10/11/14.
 */
public class NewValid extends JAXBContextFactory {

    @Override
    public JAXBContext createContext(Class[] classes, Map<String, Object> properties) throws JAXBException {
        return new JAXBContext1();
    }

    @Override
    public JAXBContext createContext(String contextPath, ClassLoader classLoader, Map<String, Object> properties) throws JAXBException {
        return new JAXBContext1();
    }

    public static class JAXBContext1 extends JAXBContext {
        @Override
        public Unmarshaller createUnmarshaller() throws JAXBException {
            return null;
        }

        @Override
        public Marshaller createMarshaller() throws JAXBException {
            return null;
        }

        @Override
        public Validator createValidator() throws JAXBException {
            return null;
        }
    }

}
