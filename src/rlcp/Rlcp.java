package rlcp;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import rlcp.calculate.*;
import rlcp.echo.RlcpEchoRequest;
import rlcp.echo.RlcpEchoRequestBody;
import rlcp.echo.RlcpEchoResponse;
import rlcp.echo.RlcpEchoResponseBody;
import rlcp.check.*;
import rlcp.exception.*;
import rlcp.generate.*;
import rlcp.method.RlcpMethod;
import rlcp.util.Constants;
import rlcp.util.DomHelper;
import rlcp.util.Util;

import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static rlcp.method.RlcpMethod.*;
import static rlcp.method.RlcpMethod.Recognizer.*;
import static rlcp.util.Constants.*;

/**
 * Class for parser of raw representation of RLCP requests, responses and
 * their bodies, specified by RlcpRequest and RlcpResponse interfaces
 * implementations.
 */
public class Rlcp {

    /**
     * Parse the content of the given RLCP-request and return one instance of extends {@code RlcpRequest}:
     * {@code RlcpGenerateRequest}, {@code RlcpCalculateRequest} or {@code RlcpCheckRequest}.
     * <p>
     * <p>If the request can not be converted to the required type, then return exception.</p>
     *
     * @param rlcpRequestString raw RlcpRequest representation
     * @param clazz             type RLCP-request class must match. This type of the class extends {@code RlcpRequest}.
     *                          For example, if the value is {@code RlcpGenerateRequest.class}, this method will return
     *                          instance {@code RlcpGenerateRequest}
     * @return {@code RlcpRequest} implementation instance for specified method parsed from param String,
     * or exception if request doesn't match the class {@code RlcpRequest}.
     * @throws BadRlcpRequestException if RLCP-request the is invalid.
     *                                 For example, RLCP-request has bad header or RLCP-request has bad body.
     * @see RlcpRequest
     * @see RlcpGenerateRequest
     * @see RlcpCalculateRequest
     * @see RlcpCheckRequest
     */
    public static <T extends RlcpRequest> T parseRequest(String rlcpRequestString, Class<T> clazz) throws BadRlcpRequestException {
        return clazz.cast(parseRequest(rlcpRequestString, recognizeMethod(clazz)));
    }

    /**
     * Parse the content of the given RLCP-request and return a new object {@code RlcpRequest}.
     *
     * @param rlcpRequestString string RLCP-request
     * @return {@code RlcpRequest} implementation instance for specified method parsed from param String, or exception.
     * @throws BadRlcpRequestException if RLCP-request the is invalid.
     *                                 For example, RLCP-request has bad header or RLCP-request has bad body.
     * @see RlcpRequest
     */
    public static RlcpRequest parseRequest(String rlcpRequestString) throws BadRlcpRequestException {
        return parseRequest(rlcpRequestString, recognizeMethod(rlcpRequestString));
    }

    private static RlcpRequest parseRequest(String rlcpRequestString, RlcpMethod method) throws BadRlcpRequestException {
        Objects.requireNonNull(rlcpRequestString);
        Objects.requireNonNull(method);
        StringBuilder headerBuilder = new StringBuilder();
        StringBuilder bodyBuilder = new StringBuilder();
        try {
            Util.readHeaderAndBody(rlcpRequestString, headerBuilder, bodyBuilder);
        } catch (IOException ex) {
            throw new BadRlcpRequestException(ex);
        }

        RlcpRequestHeader parsedHeader;
        try {
            parsedHeader = RlcpRequestHeader.parse(headerBuilder.toString(), method);
        } catch (BadRlcpHeaderException ex) {
            throw new BadRlcpRequestException(ex);
        }

        try {
            switch (method.getName().toLowerCase()) {
                case "generate":
                    return new RlcpGenerateRequest(parsedHeader, parseGenerateRequestBody(bodyBuilder.toString()));
                case "check":
                    return new RlcpCheckRequest(parsedHeader, parseCheckRequestBody(bodyBuilder.toString()));
                case "calculate":
                    return new RlcpCalculateRequest(parsedHeader, parseCalculateRequestBody(bodyBuilder.toString()));
                case "echo":
                    return new RlcpEchoRequest(parsedHeader, parseEchoRequestBody());
                default:
                    throw new RlcpException("bad request");
            }
        } catch (RlcpException ex) {
            throw new BadRlcpRequestException(ex);
        }
    }

//    private static <T, A> T megaSwitch(A actual, Object[] params, Map<A, Function<Object[], T>> strategies) {
//        Function<Object[], T> strategy = strategies.get(actual);
//        Optional<Function<Object[], T>> optional = Optional.ofNullable(strategy);
//        optional.orElseThrow(() -> new RlcpException("bad"));
//        return optional.get().apply(params);

//        megaSwitch(method.getName().toLowerCase(), new Object[]{parsedHeader, bodyBuilder.toString()},
//                new HashMap<String, Function<Object[], RlcpRequest>>() {
//                    {
//                        put("generate", (p) -> new RlcpGenerateRequest((RlcpRequestHeader) p[0], parseGenerateRequestBody(String.valueOf(p[1]))));
//                        put("check", (p) -> new RlcpCheckRequest((RlcpRequestHeader) p[0], parseCheckRequestBody(String.valueOf(p[1]))));
//                        put("calculate", (p) -> new RlcpCalculateRequest((RlcpRequestHeader) p[0], parseCalculateRequestBody(String.valueOf(p[1]))));
//                        put("echo", (p) -> new RlcpEchoRequest((RlcpRequestHeader) p[0], new RlcpEchoRequestBody()));
//                    }
//                }
//        );
//    }


    /**
     * Parse the content of the given RLCP-request body and return a new object {@code RlcpRequestBody}.
     *
     * @param rlcpBodyString string RLCP-request body
     * @return {@code RlcpRequestBody} implementation instance for specified method parsed from param String, or exception.
     * @throws BadRlcpBodyException if RLCP-request body the is invalid.
     * @see RlcpRequestBody
     */
    public static RlcpRequestBody parseRequestBody(String rlcpBodyString) throws BadRlcpBodyException {
        return parseRequestBody(rlcpBodyString, recognizeMethod(rlcpBodyString));
    }

    /**
     * Parse the content of the given RLCP-request body and return one instance of extends {@code RlcpRequestBody}:
     * {@code RlcpCalculateRequestBody}, {@code RlcpCheckRequestBody} or {@code RlcpGenerateRequestBody}.
     * <p>
     * <p>If the request body can not be converted to the required type, then return exception.</p>
     *
     * @param rlcpBodyString raw RlcpRequestBody representation
     * @param clazz          type RLCP-request class must match. This type of the class extends {@code RlcpRequestBody}.
     *                       For example, if the value is {@code RlcpGenerateRequestBody.class}, this method will return
     *                       instance {@code RlcpGenerateRequestBody}
     * @return {@code RlcpRequestBody}implementation instance for specified method parsed from param String,
     * or exception if request body doesn't match the class {@code RlcpRequestBody}.
     * @throws BadRlcpBodyException if RLCP-request body the is invalid.
     * @see RlcpRequestBody
     * @see RlcpCalculateRequestBody
     * @see RlcpCheckRequestBody
     * @see RlcpGenerateRequestBody
     */
    public static <T extends RlcpRequestBody> T parseRequestBody(String rlcpBodyString, Class<T> clazz) throws BadRlcpBodyException {
        return clazz.cast(parseRequestBody(rlcpBodyString, recognizeMethod(clazz)));
    }

    private static RlcpRequestBody parseRequestBody(String rlcpBodyString, RlcpMethod method) throws BadRlcpBodyException {
        Objects.requireNonNull(method);
        Objects.requireNonNull(rlcpBodyString);

        switch (method.getName().toLowerCase()) {
            case "generate":
                return parseGenerateRequestBody(rlcpBodyString);
            case "check":
                return parseCheckRequestBody(rlcpBodyString);
            case "calculate":
                return parseCalculateRequestBody(rlcpBodyString);
            case "echo":
                return parseEchoRequestBody();
            default:
                throw new RlcpException("bad request body");
        }
    }

    /**
     * Parse the content of the given RLCP-response and return one instance of extends {@code RlcpResponse}:
     * {@code RlcpCalculateResponse}, {@code RlcpCheckResponse} or {@code RlcpGenerateResponse}.
     * <p>
     * <p>If the response can not be converted to the required type, then return exception.</p>
     *
     * @param rlcpResponseString raw RlcpResponse representation
     * @param clazz              type RLCP-response class must match. This type of the class extends {@code RlcpResponse}.
     *                           For example, if the value is {@code RlcpGenerateResponse.class}, this method will return
     *                           instance {@code RlcpGenerateResponse}
     * @return {@code RlcpResponse} implementation instance for specified method parsed from param String,
     * or exception if response doesn't match the class {@code RlcpResponse}.
     * @throws BadRlcpResponseException if RLCP-response the is invalid.
     *                                  For example, RLCP-response has bad header or RLCP-response has bad body.
     * @see RlcpResponse
     * @see RlcpCalculateResponse
     * @see RlcpCheckResponse
     * @see RlcpGenerateResponse
     */
    public static <T extends RlcpResponse> T parseResponse(String rlcpResponseString, Class<T> clazz){
        return clazz.cast(parseResponse(rlcpResponseString, recognizeMethod(clazz)));
    }

    /**
     * Parse the content of the given RLCP-response and return a new object {@code RlcpResponse}.
     *
     * @param rlcpResponseString string RLCP-response
     * @return {@code RlcpResponse} implementation instance for specified method parsed from param String, or exception.
     * @throws BadRlcpResponseException if RLCP-response the is invalid.
     *                                  For example, RLCP-response has bad header or RLCP-response has bad body.
     * @see RlcpResponse
     */
    public static RlcpResponse parseResponse(String rlcpResponseString){
        return parseResponse(rlcpResponseString, recognizeMethod(rlcpResponseString));
    }

    private static RlcpResponse parseResponse(String rlcpResponseString, RlcpMethod method) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(rlcpResponseString);

        StringBuilder headerBuilder = new StringBuilder();
        StringBuilder bodyBuilder = new StringBuilder();
        try {
            Util.readHeaderAndBody(rlcpResponseString, headerBuilder, bodyBuilder);
        } catch (IOException ex) {
            throw new RlcpException(ex);
        }

        RlcpResponseHeader parsedHeader;
        try {
            parsedHeader = RlcpResponseHeader.parse(headerBuilder.toString());
        } catch (BadRlcpHeaderException ex) {
            throw new RlcpException(ex);
        }

            switch (method.getName().toLowerCase()) {
                case "generate":
                    return new RlcpGenerateResponse(parsedHeader, parseGenerateResponseBody(bodyBuilder.toString()));
                case "check":
                    return new RlcpCheckResponse(parsedHeader, parseCheckResponseBody(bodyBuilder.toString()));
                case "calculate":
                    return new RlcpCalculateResponse(parsedHeader, parseCalculateResponseBody(bodyBuilder.toString()));
                case "echo":
                    return new RlcpEchoResponse(parsedHeader, new RlcpEchoResponseBody());
                default:
                    throw new RlcpException("bad request body");
            }
    }

    /**
     * Parse the content of the given RLCP-response body and return a new object {@code RlcpResponseBody}.
     *
     * @param rlcpBodyString string RLCP-response body
     * @return {@code RlcpResponseBody} implementation instance for specified method parsed from param String, or exception.
     * @throws BadRlcpBodyException if RLCP-response body the is invalid.
     * @see RlcpResponseBody
     */
    public static RlcpResponseBody parseResponseBody(String rlcpBodyString){
        return parseResponseBody(rlcpBodyString, recognizeMethod(rlcpBodyString));
    }

    public static <T extends RlcpResponseBody> T parseResponseBody(String rlcpBodyString, Class<T> clazz){
            return clazz.cast(parseResponseBody(rlcpBodyString, recognizeMethod(clazz)));

        }

    private static RlcpResponseBody parseResponseBody(String rlcpBodyString, RlcpMethod method){
        Objects.requireNonNull(method);
        Objects.requireNonNull(rlcpBodyString);

        switch (method.getName().toLowerCase()) {
            case "generate":
                return parseGenerateResponseBody(rlcpBodyString);
            case "check":
                return parseCheckResponseBody(rlcpBodyString);
            case "calculate":
                return parseCalculateResponseBody(rlcpBodyString);
            case "echo":
                return parseEchoResponseBody();
            default:
                throw new RlcpException("bad request body");
        }
    }

    private static RlcpGenerateRequestBody parseGenerateRequestBody(String rlcpBodyString) throws RlcpException {
        String condition;
        try {
            condition = DomHelper.getTextFromNodeByXpath(DomHelper.toXml(rlcpBodyString), xPath_selectConditionForGenerating);
        } catch (DocumentException ex) {
            throw new RlcpException(ex);
        }
        return new RlcpGenerateRequestBody(condition);
    }

    private static RlcpCheckRequestBody parseCheckRequestBody(String rlcpBodyString) {
        Document rlcpRequestXml;
        try {
            rlcpRequestXml = DomHelper.toXml(rlcpBodyString);
        } catch (DocumentException ex) {
            throw new RlcpException(ex);
        }
        List<ConditionForChecking> checkUnits = parseConditions(rlcpRequestXml);
        String solution = DomHelper.getTextFromNodeByXpath(rlcpRequestXml, Constants.xPath_selectInstructions);
        GeneratingResult generatingResult = DomHelper.parsePreGenearted(rlcpRequestXml);
        return new RlcpCheckRequestBody(checkUnits, solution, generatingResult);
    }

    private static List<ConditionForChecking> parseConditions(Document rlcpRequestXml) {
        List<Node> checkUnitsNodes = rlcpRequestXml.selectNodes(Constants.xPath_selectCheckUnitsNodes);
        List<ConditionForChecking> checkUnits = parseConditions(checkUnitsNodes);
        return checkUnits;
    }

    private static List<ConditionForChecking> parseConditions(List<Node> nodes) {
        List<ConditionForChecking> checkUnits = new ArrayList<ConditionForChecking>();
        for (Node node : nodes) {
            ConditionForChecking checkUnit = parseCondition(node);
            checkUnits.add(checkUnit);
        }
        return checkUnits;
    }

    private static ConditionForChecking parseCondition(Node node) {
        return new ConditionForChecking(parseId(node), parseTimeLimit(node), parseInputData(node), parseExpectedOutputData(node));
    }

    private static int parseId(Node node) {
        return Integer.parseInt(DomHelper.getTextFromNodeByXpath(node, Constants.xPath_selectCheckUnit_id));
    }

    private static long parseTimeLimit(Node node) {
        return Long.parseLong(DomHelper.getTextFromNodeByXpath(node, Constants.xPath_selectCheckUnit_timeLimit));
    }

    private static String parseInputData(Node node) {
        return DomHelper.getTextFromNodeByXpath(node, Constants.xPath_selectCheckUnit_inputData);
    }

    private static String parseExpectedOutputData(Node node) {
        return DomHelper.getTextFromNodeByXpath(node, Constants.xPath_selectCheckUnit_expectedOutputData);
    }

    private static RlcpCalculateRequestBody parseCalculateRequestBody(String rlcpBodyString) {
        String condition;
        String instructions;
        GeneratingResult generatingResult;
        try {
            Document requestDocument = DomHelper.toXml(rlcpBodyString);
            condition = DomHelper.getTextFromNodeByXpath(requestDocument, xPath_selectConditionForCalculating);
            instructions = DomHelper.getTextFromNodeByXpath(requestDocument, xPath_selectInstructions);
            generatingResult = DomHelper.parsePreGenearted(requestDocument);
        } catch (DocumentException ex) {
            throw new RlcpException(ex);
        }
        return new RlcpCalculateRequestBody(condition, instructions, generatingResult);

    }

    private static RlcpEchoRequestBody parseEchoRequestBody() {
        return new RlcpEchoRequestBody();
    }

    private static RlcpGenerateResponseBody parseGenerateResponseBody(String rlcpBodyString) {
        if (rlcpBodyString.trim().isEmpty()) {
            return new RlcpGenerateResponseBody("", "", "");
        }
        Node resultNode;
        try {
            resultNode = DomHelper.toXml(rlcpBodyString).selectSingleNode(RESPONSE + "/" + GENERATING_RESULT);
        } catch (DocumentException ex) {
            throw new RlcpException(ex);
        }
        return new RlcpGenerateResponseBody(
                DomHelper.getTextFromNodeByXpath(resultNode, PRE_GENERATED_TEXT + "/comment()"),
                DomHelper.getTextFromNodeByXpath(resultNode, PRE_GENERATED_CODE + "/comment()"),
                DomHelper.getTextFromNodeByXpath(resultNode, PRE_GENERATED_INSTRUCTIONS + "/comment()"));
    }

    private static RlcpCheckResponseBody parseCheckResponseBody(String rlcpBodyString) {
        if (rlcpBodyString.trim().isEmpty()) {
            return new RlcpCheckResponseBody(Collections.<CheckingResult>emptyList());
        }
        Document doc;
        try {
            doc = DomHelper.toXml(rlcpBodyString);
        } catch (DocumentException ex) {
            throw new RlcpException(ex);
        }
        List<CheckingResult> results = new ArrayList<CheckingResult>();
        for (Node node : (List<Node>) doc.selectNodes("//" + Constants.CHECKING_RESULT)) {
            CheckingResult result = parseResult(node);
            results.add(result);
        }
        return new RlcpCheckResponseBody(results);
    }

    private static CheckingResult parseResult(Node node) {
        return new CheckingResult(Integer.parseInt(DomHelper.getTextFromNodeByXpath(node, "@" + Constants.ID)),
                Long.parseLong(DomHelper.getTextFromNodeByXpath(node, "@" + Constants.TIME)),
                DomHelper.getTextFromNodeByXpath(node, "@" + Constants.RESULT),
                DomHelper.getTextFromNodeByXpath(node, "comment()"));
    }

    private static RlcpCalculateResponseBody parseCalculateResponseBody(String rlcpBodyString) {
        if (rlcpBodyString.trim().isEmpty()) {
            return new RlcpCalculateResponseBody(new CalculatingResult("", ""));
        }
        Node resultNode;
        try {
            resultNode = DomHelper.toXml(rlcpBodyString).selectSingleNode(RESPONSE + "/" + CALCULATING_RESULT);
        } catch (DocumentException ex) {
            throw new RlcpException(ex);
        }
        return new RlcpCalculateResponseBody(
                DomHelper.getTextFromNodeByXpath(resultNode, PRE_GENERATED_TEXT + "/comment()"),
                DomHelper.getTextFromNodeByXpath(resultNode, PRE_GENERATED_CODE + "/comment()"));
    }

    private static RlcpEchoResponseBody parseEchoResponseBody() {
        return new RlcpEchoResponseBody();
    }
}
