package rlcp.server.logger;

import java.io.PrintStream;

/**
 * Simple logging class. Supposed to be rewrited with log4j logger.
 * 
 * @author Eugene Efimchick
 */
public class Logger {

    private static PrintStream defaultOut = System.out;

    private Logger() {
    }//to prevent instantiation

    public static void log(Exception exc, PrintStream out) {
        out.println(new java.util.Date() + " - Exception: " + exc.getMessage());
        exc.printStackTrace(out);
    }

    public static void log(Exception exc) {
        log(exc, defaultOut);
    }

    public static void log(String msg, PrintStream out) {
        out.println(new java.util.Date() + " - Message: " + msg);
    }

    public static void log(String msg) {
        log(msg, defaultOut);
    }
}
