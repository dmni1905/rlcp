package rlcp;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import rlcp.calculate.RlcpCalculateRequest;
import rlcp.calculate.RlcpCalculateRequestBody;
import rlcp.calculate.RlcpCalculateResponse;
import rlcp.calculate.RlcpCalculateResponseBody;
import rlcp.check.*;
import rlcp.exception.*;
import rlcp.generate.*;
import rlcp.method.RlcpMethod;
import rlcp.util.Constants;
import rlcp.util.DomHelper;
import rlcp.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        Util.checkStringNotNullNotEmpty(rlcpRequestString);
        StringBuilder headerBuilder = new StringBuilder();
        StringBuilder bodyBuilder = new StringBuilder();
        try {
            Util.readHeaderAndBody(rlcpRequestString, headerBuilder, bodyBuilder);
        } catch (IOException ex) {
            throw new BadRlcpRequestException(ex);
        }

        RlcpRequestHeader parsedHeader;
        try {
            parsedHeader = RlcpRequestHeader.parse(headerBuilder.toString());
        } catch (BadRlcpHeaderException ex) {
            throw new BadRlcpRequestException(ex);
        }
        String currentMethod = RlcpMethod.Recognizer.recognizeMethod(bodyBuilder.toString()).getName().toLowerCase();
        if (clazz.getName().toLowerCase().contains(currentMethod)) {
            try {
                switch (currentMethod) {
                    case "generate":
                        return clazz.cast(new RlcpGenerateRequest(parsedHeader, parseRequestBody(bodyBuilder.toString(), RlcpGenerateRequestBody.class)));
                    case "check":
                        return clazz.cast(new RlcpCheckRequest(parsedHeader, parseRequestBody(bodyBuilder.toString(), RlcpCheckRequestBody.class)));
                    case "calculate":
                        return clazz.cast(new RlcpCalculateRequest(parsedHeader, parseRequestBody(bodyBuilder.toString(), RlcpCalculateRequestBody.class)));
                    default:
                        throw new RlcpException("bad request");
                }
            } catch (BadRlcpBodyException ex) {
                throw new BadRlcpRequestException(ex);
            }
        } else {
            throw new BadRlcpRequestException(rlcpRequestString + " can not be cast to " + clazz.getCanonicalName());
        }
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
        String currentMethod = RlcpMethod.Recognizer.recognizeMethod(rlcpBodyString).getName().toLowerCase();
        if (clazz.getName().toLowerCase().contains(currentMethod)) {

            switch (RlcpMethod.Recognizer.recognizeMethod(rlcpBodyString).getName().toLowerCase()) {
                case "generate":
                    return clazz.cast(parseGenerateRequestBody(rlcpBodyString));
                case "check":
                    return clazz.cast(parseCheckRequestBody(rlcpBodyString));
                case "calculate":
                    return clazz.cast(parseCalculateRequestBody(rlcpBodyString));
                default:
                    throw new RlcpException("bad request body");
            }
        } else {
            throw new BadRlcpBodyException(rlcpBodyString + " can not be cast to " + clazz.getCanonicalName());
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
    public static <T extends RlcpResponse> T parseResponse(String rlcpResponseString, Class<T> clazz) throws BadRlcpResponseException {
        Util.checkStringNotNullNotEmpty(rlcpResponseString);

        StringBuilder headerBuilder = new StringBuilder();
        StringBuilder bodyBuilder = new StringBuilder();
        try {
            Util.readHeaderAndBody(rlcpResponseString, headerBuilder, bodyBuilder);
        } catch (IOException ex) {
            throw new BadRlcpResponseException(ex);
        }

        RlcpResponseHeader parsedHeader;
        try {
            parsedHeader = RlcpResponseHeader.parse(headerBuilder.toString());
        } catch (BadRlcpHeaderException ex) {
            throw new BadRlcpResponseException(ex);
        }
        String currentMethod = RlcpMethod.Recognizer.recognizeMethod(bodyBuilder.toString()).getName().toLowerCase();
        if (clazz.getName().toLowerCase().contains(currentMethod)) {
            try {
                switch (currentMethod) {
                    case "generate":
                        return clazz.cast(new RlcpGenerateResponse(parsedHeader, parseResponseBody(bodyBuilder.toString(), RlcpGenerateResponseBody.class)));
                    case "check":
                        return clazz.cast(new RlcpCheckResponse(parsedHeader, parseResponseBody(bodyBuilder.toString(), RlcpCheckResponseBody.class)));
                    case "calculate":
                        return clazz.cast(new RlcpCalculateResponse(parsedHeader, parseResponseBody(bodyBuilder.toString(), RlcpCalculateResponseBody.class)));
                    default:
                        throw new RlcpException("bad response");
                }
            } catch (BadRlcpBodyException ex) {
                throw new BadRlcpResponseException(ex);
            }
        } else {
            throw new BadRlcpResponseException(rlcpResponseString + " can not be cast to " + clazz.getCanonicalName());
        }
    }

    /**
     * Parse the content of the given RLCP-response body and return one instance of extends {@code RlcpResponseBody}:
     * {@code RlcpCalculateResponseBody}, {@code RlcpCheckResponseBody} or {@code RlcpGenerateResponseBody}.
     * <p>
     * <p>If the response body can not be converted to the required type, then return exception.</p>
     *
     * @param rlcpBodyString raw RlcpResponseBody representation
     * @param clazz          type RLCP-response class must match. This type of the class extends {@code RlcpResponseBody}.
     *                       For example, if the value is {@code RlcpGenerateResponseBody.class}, this method will return
     *                       instance {@code RlcpGenerateResponseBody}
     * @return {@code RlcpRequestBody} implementation instance for specified method parsed from param String,
     * or exception if response body doesn't match the class {@code RlcpResponseBody}.
     * @throws BadRlcpBodyException if RLCP-response body the is invalid.
     * @see RlcpResponseBody
     * @see RlcpCalculateResponseBody
     * @see RlcpCheckResponseBody
     * @see RlcpGenerateResponseBody
     */
    public static <T extends RlcpResponseBody> T parseResponseBody(String rlcpBodyString, Class<T> clazz) throws BadRlcpBodyException {
        String currentMethod = RlcpMethod.Recognizer.recognizeMethod(rlcpBodyString).getName().toLowerCase();
        if (clazz.getName().toLowerCase().contains(currentMethod)) {
            switch (RlcpMethod.Recognizer.recognizeMethod(rlcpBodyString).getName().toLowerCase()) {
                case "generate":
                    return clazz.cast(parseGenerateResponseBody(rlcpBodyString));
                case "check":
                    return clazz.cast(parseCheckResponseBody(rlcpBodyString));
                case "calculate":
                    return clazz.cast(parseCalculateResponseBody(rlcpBodyString));
                default:
                    throw new RlcpException("bad response body");
            }
        } else {
            throw new BadRlcpBodyException(rlcpBodyString + " can not be cast to " + clazz.getCanonicalName());
        }
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
        switch (RlcpMethod.Recognizer.recognizeMethod(rlcpRequestString).getName().toLowerCase()) {
            case "generate":
                return parseRequest(rlcpRequestString, RlcpGenerateRequest.class);
            case "check":
                return parseRequest(rlcpRequestString, RlcpCheckRequest.class);
            case "calculate":
                return parseRequest(rlcpRequestString, RlcpCalculateRequest.class);
            default:
                throw new RlcpException("bad request");
        }
    }

    /**
     * Parse the content of the given RLCP-request body and return a new object {@code RlcpRequestBody}.
     *
     * @param rlcpBodyString string RLCP-request body
     * @return {@code RlcpRequestBody} implementation instance for specified method parsed from param String, or exception.
     * @throws BadRlcpBodyException if RLCP-request body the is invalid.
     * @see RlcpRequestBody
     */
    public static RlcpRequestBody parseRequestBody(String rlcpBodyString) throws BadRlcpBodyException {
        switch (RlcpMethod.Recognizer.recognizeMethod(rlcpBodyString).getName().toLowerCase()) {
            case "generate":
                return parseRequestBody(rlcpBodyString, RlcpGenerateRequestBody.class);
            case "check":
                return parseRequestBody(rlcpBodyString, RlcpCheckRequestBody.class);
            case "calculate":
                return parseRequestBody(rlcpBodyString, RlcpCalculateRequestBody.class);
            default:
                throw new RlcpException("bad request body");
        }
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
    public static RlcpResponse parseResponse(String rlcpResponseString) throws BadRlcpResponseException {
        switch (RlcpMethod.Recognizer.recognizeMethod(rlcpResponseString).getName().toLowerCase()) {
            case "generate":
                return parseResponse(rlcpResponseString, RlcpGenerateResponse.class);
            case "check":
                return parseResponse(rlcpResponseString, RlcpCheckResponse.class);
            case "calculate":
                return parseResponse(rlcpResponseString, RlcpCalculateResponse.class);
            default:
                throw new RlcpException("bad response");
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
    public static RlcpResponseBody parseResponseBody(String rlcpBodyString) throws BadRlcpBodyException {
        switch (RlcpMethod.Recognizer.recognizeMethod(rlcpBodyString).getName().toLowerCase()) {
            case "generate":
                return parseResponseBody(rlcpBodyString, RlcpGenerateResponseBody.class);
            case "check":
                return parseResponseBody(rlcpBodyString, RlcpCheckResponseBody.class);
            case "calculate":
                return parseResponseBody(rlcpBodyString, RlcpCalculateResponseBody.class);
            default:
                throw new RlcpException("bad response body");
        }
    }

    private static RlcpGenerateRequestBody parseGenerateRequestBody(String rlcpBodyString) throws BadRlcpBodyException {
        String condition;
        try {
            condition = DomHelper.getTextFromNodeByXpath(DomHelper.toXml(rlcpBodyString), xPath_selectConditionForGenerating);
        } catch (DocumentException ex) {
            throw new BadRlcpBodyException(ex);
        }
        return new RlcpGenerateRequestBody(condition);
    }

    private static RlcpCheckRequestBody parseCheckRequestBody(String rlcpBodyString) throws BadRlcpBodyException {
        Document rlcpRequestXml;
        try {
            rlcpRequestXml = DomHelper.toXml(rlcpBodyString);
        } catch (DocumentException ex) {
            throw new BadRlcpBodyException(ex);
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

    private static RlcpCalculateRequestBody parseCalculateRequestBody(String rlcpBodyString) throws BadRlcpBodyException {
        String condition;
        String instructions;
        GeneratingResult generatingResult;
        try {
            Document requestDocument = DomHelper.toXml(rlcpBodyString);
            condition = DomHelper.getTextFromNodeByXpath(requestDocument, xPath_selectConditionForCalculating);
            instructions = DomHelper.getTextFromNodeByXpath(requestDocument, xPath_selectInstructions);
            generatingResult = DomHelper.parsePreGenearted(requestDocument);
        } catch (DocumentException ex) {
            throw new BadRlcpBodyException(ex);
        }
        return new RlcpCalculateRequestBody(condition, instructions, generatingResult);

    }

    private static RlcpGenerateResponseBody parseGenerateResponseBody(String rlcpBodyString) throws BadRlcpBodyException {
        Node resultNode;
        try {
            resultNode = DomHelper.toXml(rlcpBodyString).selectSingleNode(RESPONSE + "/" + GENERATING_RESULT);
        } catch (DocumentException ex) {
            throw new BadRlcpBodyException(ex);
        }
        return new RlcpGenerateResponseBody(
                DomHelper.getTextFromNodeByXpath(resultNode, PRE_GENERATED_TEXT + "/comment()"),
                DomHelper.getTextFromNodeByXpath(resultNode, PRE_GENERATED_CODE + "/comment()"),
                DomHelper.getTextFromNodeByXpath(resultNode, PRE_GENERATED_INSTRUCTIONS + "/comment()"));
    }

    private static RlcpCheckResponseBody parseCheckResponseBody(String rlcpBodyString) throws BadRlcpBodyException {
        Document doc;
        try {
            doc = DomHelper.toXml(rlcpBodyString);
        } catch (DocumentException ex) {
            throw new BadRlcpBodyException(ex);
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

    private static RlcpCalculateResponseBody parseCalculateResponseBody(String rlcpBodyString) throws BadRlcpBodyException {
        Node resultNode;
        try {
            resultNode = DomHelper.toXml(rlcpBodyString).selectSingleNode(RESPONSE + "/" + CALCULATING_RESULT);
        } catch (DocumentException ex) {
            throw new BadRlcpBodyException(ex);
        }
        return new RlcpCalculateResponseBody(
                DomHelper.getTextFromNodeByXpath(resultNode, PRE_GENERATED_TEXT + "/comment()"),
                DomHelper.getTextFromNodeByXpath(resultNode, PRE_GENERATED_CODE + "/comment()"));
    }
}
