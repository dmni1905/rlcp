package rlcp.util;

import java.io.*;
import java.net.Socket;

import rlcp.RlcpUrl;
import rlcp.exception.BadRlcpHeaderException;
import rlcp.exception.BadRlcpUrlException;

/**
 * Provides some useful methods.
 *
 * @author Eugene Efimchick
 */
public class Util {

    private Util() {
    }

    /**
     * system-dependent line separator string
     */
    public static final String nativeLineSeparator = System.getProperty("line.separator");
    /**
     * WINDOWS systems line separator string
     */
    public static final String winLineSeparator = "\r\n";
    /**
     * UNIX systems line separator string
     */
    public static final String unixLineSeparator = "\n";
    /**
     * MAC systems line separator string
     */
    public static final String macLineSeparator = "\r";

    /**
     * Defines a line separator is used.
     *
     * @param string text
     * @return line separator used in raw text
     */
    public static String getLineSeparatorFor(String string) {
        //not null not empty
        String lineSeparator;
        if (string.contains(winLineSeparator)) {
            lineSeparator = winLineSeparator;
        } else if (string.contains(unixLineSeparator)) {
            lineSeparator = unixLineSeparator;
        } else {
            lineSeparator = nativeLineSeparator;
        }
        return lineSeparator;
    }

    /**
     * Setting margins. In raw string replace tabs to spaces.
     *
     * @param source raw {@code String}
     * @return The resulting {@code String}
     */
    public static String omitLineSeparatorArtefacts(String source) {
        return source.replaceAll("\r", "");
    }

    /**
     * Gets value of header field name
     *
     * @param namedHeader the header string
     * @param name        the prefix
     * @return value of the field
     * @throws BadRlcpHeaderException raw header the is incorrect
     */
    public static String parseNamedHeaderField(String namedHeader, String name) throws BadRlcpHeaderException {
        String parsedHeader;
        if (namedHeader.startsWith(name)) {
            parsedHeader = namedHeader.substring(name.length(), namedHeader.length());
        } else {
            throw new BadRlcpHeaderException("Bad header with name - " + name);
        }
        return parsedHeader;
    }

    /**
     * Gets name of header field
     *
     * @param raw the header string
     * @return name of the field
     * @throws BadRlcpHeaderException raw header the is incorrect
     */
    public static String parseHeaderFieldName(String raw) throws BadRlcpHeaderException {
        if (raw.contains(":")) {
            String[] parts = raw.split(":");
            if (parts.length >= 2) {
                String name = parts[0];
                return name;
            } else {
                throw new BadRlcpHeaderException("Bad raw header parameter: " + raw);
            }
        } else {
            throw new BadRlcpHeaderException("Bad raw header parameter: " + raw);
        }
    }

    /**
     * Reads value "content-length" of a header
     *
     * @param header       line of the header containing a variable "content-length"
     * @param headerString full RLCP-header
     * @return content length
     * @throws BadRlcpHeaderException if variable "content-length" the is invalid.
     */
    public static int parseContentLengthHeaderField(String header, String headerString) throws BadRlcpHeaderException {
        int parsedContentLength;
        try {
            parsedContentLength = Integer.parseInt(Util.parseNamedHeaderField(Util.omitLineSeparatorArtefacts(header), "content-length:"));
        } catch (NumberFormatException ex) {
            throw new BadRlcpHeaderException("content-length is not a number in " + headerString, ex);
        }
        return parsedContentLength;
    }

    /**
     * Define request's name type. Return one of the three choices (Check, Generate or Calculate)
     *
     * @param header       line of the header containing RLCP-method's name
     * @param headerString full RLCP-header
     * @return RLCP-method's name
     * @throws BadRlcpHeaderException if method's name not found
     */
    public static String parseMethodHeaderField(String header, String headerString) throws BadRlcpHeaderException {
        String parsedMethod = Util.omitLineSeparatorArtefacts(header);
        if (!(parsedMethod.equalsIgnoreCase("check")
                || parsedMethod.equalsIgnoreCase("generate")
                || parsedMethod.equalsIgnoreCase("echo")
                || parsedMethod.equalsIgnoreCase("calculate"))) {
            throw new BadRlcpHeaderException("Wrong method called in " + headerString);
        }
        return parsedMethod;
    }

    /**
     * Reads value "url" of a header
     *
     * @param header       line of the header containing a variable "content-length"
     * @param headerString full RLCP-header
     * @return url string
     * @throws BadRlcpHeaderException if variable "url" the is invalid.
     */
    public static RlcpUrl parseUrlHeaderField(String header, String headerString) throws BadRlcpHeaderException {
        RlcpUrl parsedUrl;
        try {
            parsedUrl = RlcpUrl.parse(Util.parseNamedHeaderField(Util.omitLineSeparatorArtefacts(header), "url:"));
        } catch (BadRlcpUrlException ex) {
            throw new BadRlcpHeaderException("RlcpUrl parse failed in " + headerString, ex);
        }
        return parsedUrl;
    }

    /**
     * Raw string must exit (not null and not empty), otherwise return exception
     *
     * @param string raw String
     * @throws IllegalArgumentException if raw is null or empty
     */
    public static void checkStringNotNullNotEmpty(String string) throws IllegalArgumentException {
        if (string == null) {
            throw new IllegalArgumentException("null String");
        }
        if (string.isEmpty()) {
            throw new IllegalArgumentException("empty String");
        }
    }

    /**
     * Read {@code RlcpRequest} or {@code RlcpResponse} header and body from first param,
     * and write them in appropriate resources
     *
     * @param stringSource  raw String {@code RlcpRequest} or {@code RlcpResponse} representation
     * @param headerBuilder resource, witch is write header
     * @param bodyBuilder   resource, witch is write body
     * @throws IOException if an I/O error occurs
     * @see rlcp.RlcpRequest
     * @see rlcp.RlcpResponse
     */
    public static void readHeaderAndBody(String stringSource, StringBuilder headerBuilder, StringBuilder bodyBuilder) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(stringSource));
        boolean bodyStarted = false;
        while (br.ready()) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            if (line.isEmpty()) {
                bodyStarted = true;
                continue;
            }
            if (!bodyStarted) {
                headerBuilder.append(line).append(Util.nativeLineSeparator);
            } else {
                bodyBuilder.append(line).append(Util.nativeLineSeparator);
            }
        }
    }

    /**
     * Read data stream from socket and return them
     *
     * @param socket socket
     * @return string data from socket
     * @throws IOException
     */
    public static String readSocketInputUntilInputShutdown(Socket socket) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int nextByte;

        InputStream socketIS = socket.getInputStream();
        while (!socket.isInputShutdown()) {
            nextByte = socketIS.read();
            if (nextByte >= 0) {
                baos.write(nextByte);
            } else {
                break;
            }
        }

        return baos.toString();
    }

    /**
     * Check objects not null. If found null return exception
     *
     * @param message string exception
     * @param objects objects that will check
     * @throws IllegalArgumentException
     */
    public static void checkNotNull(String message, Object... objects) throws IllegalArgumentException {
        for (Object object : objects) {
            if (object == null) {
                throw new IllegalArgumentException(message);
            }
        }
    }
}
