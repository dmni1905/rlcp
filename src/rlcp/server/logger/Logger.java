package rlcp.server.logger;

import java.io.PrintStream;

/**
 * Simple logging class. Supposed to be rewrited with log4j logger.
 */
public class Logger {

    private static PrintStream stream = System.out;

    private Logger() {
    }//to prevent instantiation

    /**
     * Prints exception in stream log
     * @param exc exception
     * @param out log
     */
    public static void log(Exception exc, PrintStream out) {
        out.println(new java.util.Date() + " - Exception: " + exc.getMessage());
        exc.printStackTrace(out);
    }

    /**
     * Prints exception in log
     * @param exc exception
     */
    public static void log(Exception exc) {
        log(exc, stream);
    }

    /**
     * Prints message in stream log
     * @param msg message
     * @param out log
     */
    public static void log(String msg, PrintStream out) {
        out.println(new java.util.Date() + " - Message: " + msg);
    }

    /**
     * Prints message in log
     * @param msg message
     */
    public static void log(String msg) {
        log(msg, stream);
    }
}
