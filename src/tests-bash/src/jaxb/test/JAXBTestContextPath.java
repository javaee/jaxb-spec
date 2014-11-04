package jaxb.test;

import javax.xml.bind.JAXBContext;

/**
 * Created by miran on 29/10/14.
 */
public class JAXBTestContextPath extends JAXBTest {

    protected JAXBContext createContext() throws Throwable {
        try {
            JAXBContext ctx = JAXBContext.newInstance("jaxb.test.usr");
            System.out.println("     TEST: context class = [" + ctx.getClass().getName() + "]\n");
            return ctx;
        } catch (Throwable t) {
            System.out.println("     TEST: Throwable [" + t.getClass().getName() + "] thrown.\n");
            throw t;
        }
    }

    public static void main(String[] args) {
        new JAXBTestContextPath().test(args);
    }

}
