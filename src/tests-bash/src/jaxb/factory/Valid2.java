package jaxb.factory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import java.util.Map;

/**
 * Created by miran on 31/10/14.
 */
public class Valid2 {

    public static JAXBContext createContext(String path, ClassLoader cl) {
        return new PropValidJAXBContext2();
    }

    public static JAXBContext createContext(Class[] classes, Map<String, Object> properties) throws JAXBException {
        return new PropValidJAXBContext2();
    }


    public static class PropValidJAXBContext2 extends JAXBContext {

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
