package rlcp.util;

import java.io.*;
import java.net.Socket;
import rlcp.RlcpUrl;
import rlcp.exception.BadRlcpHeaderException;
import rlcp.exception.BadRlcpUrlException;

/**
 * Provides some useful methods.
 * @author Eugene Efimchick
 */
public class Util {

    private Util() {
    }
    public static final String nativeLineSeparator = System.getProperty("line.separator");
    public static final String winLineSeparator = "\r\n";
    public static final String unixLineSeparator = "\n";
    public static final String macLineSeparator = "\r";

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

    public static String omitLineSeparatorArtefacts(String source) {
        return source.replaceAll("\r", "");
    }

    public static String parseNamedHeaderField(String namedHeader, String name) throws BadRlcpHeaderException {
        String parsedHeader;
        if (namedHeader.startsWith(name)) {
            parsedHeader = namedHeader.substring(name.length(), namedHeader.length());
        } else {
            throw new BadRlcpHeaderException("Bad header with name - " + name);
        }
        return parsedHeader;
    }
    
    public static String parseHeaderFieldName(String raw) throws BadRlcpHeaderException {
        if (raw.contains(":")){
            String[] parts = raw.split(":");
            if (parts.length >= 2){
                String name = parts[0];
                return name;
            } else {
                throw new BadRlcpHeaderException("Bad raw header parameter: " + raw);
            }
        } else {
            throw new BadRlcpHeaderException("Bad raw header parameter: " + raw);
        }
    }

    public static int parseContentLengthHeaderField(String header, String headerString) throws BadRlcpHeaderException {
        int parsedContentLength;
        try {
            parsedContentLength = Integer.parseInt(Util.parseNamedHeaderField(Util.omitLineSeparatorArtefacts(header), "content-length:"));
        } catch (NumberFormatException ex) {
            throw new BadRlcpHeaderException("content-length is not a number in " + headerString, ex);
        }
        return parsedContentLength;
    }

    public static String parseMethodHeaderField(String header, String headerString) throws BadRlcpHeaderException {
        String parsedMethod = Util.omitLineSeparatorArtefacts(header);
        if (!(parsedMethod.equalsIgnoreCase("check")
                || parsedMethod.equalsIgnoreCase("generate")
                || parsedMethod.equalsIgnoreCase("calculate"))) {
            throw new BadRlcpHeaderException("Wrong method called in " + headerString);
        }
        return parsedMethod;
    }

    public static RlcpUrl parseUrlHeaderField(String header, String headerString) throws BadRlcpHeaderException {
        RlcpUrl parsedUrl = null;
        try {
            parsedUrl = RlcpUrl.parse(Util.parseNamedHeaderField(Util.omitLineSeparatorArtefacts(header), "url:"));
        } catch (BadRlcpUrlException ex) {
            throw new BadRlcpHeaderException("RlcpUrl parse failed in " + headerString, ex);
        }
        return parsedUrl;
    }

    public static void checkStringNotNullNotEmpty(String string) throws IllegalArgumentException {
        if (string == null) {
            throw new IllegalArgumentException("null String");
        }
        if (string.isEmpty()) {
            throw new IllegalArgumentException("empty String");
        }
    }

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
    
    public static void checkNotNull(String message, Object... objects) throws IllegalArgumentException {
        for (Object object : objects) {
            if (object == null) {
                throw new IllegalArgumentException(message);
            }
        }
    }
}
