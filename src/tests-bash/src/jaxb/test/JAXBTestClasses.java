package jaxb.test;

import jaxb.test.usr.A;

import javax.xml.bind.JAXBContext;

/**
 * Created by miran on 29/10/14.
 */
public class JAXBTestClasses extends JAXBTest {

    protected JAXBContext createContext() throws Throwable {
        try {
            JAXBContext ctx = JAXBContext.newInstance(A.class);
            System.out.println("     TEST: context class = [" + ctx.getClass().getName() + "]\n");
            return ctx;
        } catch (Throwable t) {
            System.out.println("     TEST: Throwable [" + t.getClass().getName() + "] thrown.\n");
            throw t;
        }
    }

    public static void main(String[] args) {
        new JAXBTestClasses().test(args);
    }

}
