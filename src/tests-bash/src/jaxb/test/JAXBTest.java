package jaxb.test;

import javax.xml.bind.JAXBContext;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by miran on 29/10/14.
 */
public class JAXBTest {

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

    protected void test(String[] args) {
        if (args.length > 2) {
            setContextClassLoader();
        }

        try {
            JAXBContext ctx = createContext();
            assertTrue(ctx != null, "No Context found.");
            String ctxClassName = ctx.getClass().getName();
            assertTrue(args[0].equals(ctxClassName), "Incorrect provider: [" + ctxClassName + "], Expected: [" + args[0] + "]");
        } catch (Throwable throwable) {
            //throwable.printStackTrace();
            String expectedExceptionClass = args[1];
            String throwableClass = throwable.getClass().getName();
            boolean correctException = throwableClass.equals(expectedExceptionClass);
            assertTrue(correctException, "Got unexpected exception: [" +
                    throwableClass + "], expected: [" + expectedExceptionClass + "]");
            if (!correctException) {
                throwable.printStackTrace();
            }
        }
    }

    /**
     * UGLY HACK
     */
    private void setContextClassLoader() {
        try {
            String path = new File(".").getAbsoluteFile().getParent();
            System.out.println("    Creating URLClassLoader to load classes from: " + path);
            ClassLoader cl = new URLClassLoader(
                    new URL[]{
                            new URL("file://" + path + "/")
                    }, null
            );
            Thread.currentThread().setContextClassLoader(cl);
            System.out.println("    ...contextClassLoader set.");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static void assertTrue(boolean condition, String msg) {
        if (!condition) {
            log(" FAILED -  ERROR: " + msg); //throw new RuntimeException(msg);
        } else {
            log(" PASSED");
        }
    }

    private static void log(String msg) {
        System.out.println(msg);
    }

}
