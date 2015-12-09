package rlcp.check;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import rlcp.generate.GeneratingResult;
import rlcp.RlcpParser;
import rlcp.RlcpRequestHeader;
import rlcp.RlcpResponseHeader;
import rlcp.exception.BadRlcpBodyException;
import rlcp.exception.BadRlcpHeaderException;
import rlcp.exception.BadRlcpRequestException;
import rlcp.exception.BadRlcpResponseException;
import rlcp.util.Constants;
import rlcp.util.DomHelper;
import rlcp.util.Util;

/**
 * RlcpParser implementation for Check method.
 */
public class RlcpCheckParser implements RlcpParser<RlcpCheckRequest, RlcpCheckResponse> {

    /**
     * Parse the content of the given RLCP-request and return instance {@code RlcpCheckRequest}.
     *
     * @param rlcpRequestString string RLCP-request
     * @return {@code RlcpCheckRequest} instance for specified method parsed from param String.
     * @throws BadRlcpRequestException if RLCP-request the is invalid.
     *                                 For example, RLCP-request has bad header or RLCP-request has bad body.
     * @see RlcpCheckRequest
     */
    @Override
    public RlcpCheckRequest parseRequest(String rlcpRequestString) throws BadRlcpRequestException {
        if (rlcpRequestString == null) {
            throw new IllegalArgumentException("rlcpRequestString is null");
        }
        if (rlcpRequestString.isEmpty()) {
            throw new IllegalArgumentException("rlcpRequestString is empty");
        }


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

        String body = bodyBuilder.toString();
        RlcpCheckRequestBody parsedBody;
        try {
            parsedBody = parseRequestBody(body);
        } catch (BadRlcpBodyException ex) {
            throw new BadRlcpRequestException(ex);
        }

        return new RlcpCheckRequest(parsedHeader, parsedBody);
    }

    /**
     * Parse the content of the given RLCP-response and return a new object {@code RlcpCheckResponse}.
     *
     * @param rlcpResponseString string RLCP-response
     * @return {@code RlcpCheckResponse} implementation instance for specified method parsed from param String, or null.
     * @throws BadRlcpResponseException if RLCP-response the is invalid.
     *                                  For example, RLCP-response has bad header or RLCP-response has bad body.
     * @see RlcpCheckResponse
     */
    @Override
    public RlcpCheckResponse parseResponse(String rlcpResponseString) throws BadRlcpResponseException {
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

        RlcpCheckResponseBody parsedBody = null;
        if (parsedHeader.isSuccessful()) {
            try {
                parsedBody = parseResponseBody(bodyBuilder.toString());
            } catch (BadRlcpBodyException ex) {
                throw new BadRlcpResponseException(ex);
            }
        }

        return new RlcpCheckResponse(parsedHeader, parsedBody);
    }

    /**
     * Parse the content of the given RLCP-request body and return instance {@code RlcpCheckRequestBody}.
     *
     * @param rlcpBodyString string RLCP-request body
     * @return {@code RlcpCheckRequestBody} instance for specified method parsed from param String.
     * @throws BadRlcpBodyException if RLCP-request body the is invalid.
     * @see RlcpCheckRequestBody
     */
    @Override
    public RlcpCheckRequestBody parseRequestBody(String rlcpBodyString) throws BadRlcpBodyException {
        Document rlcpRequestXml;
        try {
            rlcpRequestXml = DomHelper.toXml(rlcpBodyString);
        } catch (DocumentException ex) {
            throw new BadRlcpBodyException(ex);
        }
        List<ConditionForChecking> checkUnits = parseConditions(rlcpRequestXml);
        String solution = parseSolution(rlcpRequestXml);
        GeneratingResult generatingResult = DomHelper.parsePreGenearted(rlcpRequestXml);
        return new RlcpCheckRequestBody(checkUnits, solution, generatingResult);
    }

    private static List<ConditionForChecking> parseConditions(Document rlcpRequestXml) {
        @SuppressWarnings("unchecked")
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

    private static String parseSolution(Document rlcpRequestXml) {
        return DomHelper.getTextFromNodeByXpath(rlcpRequestXml, Constants.xPath_selectInstructions);
    }

    /**
     * Parse the content of the given RLCP-response and return a new object {@code RlcpCheckResponseBody}.
     *
     * @param rlcpBodyString string RLCP-response
     * @return {@code RlcpCheckResponseBody} implementation instance for specified method parsed from param String, or null.
     * @throws BadRlcpBodyException if RLCP-response body the is invalid..
     * @see RlcpCheckResponseBody
     */
    @Override
    public RlcpCheckResponseBody parseResponseBody(String rlcpBodyString) throws BadRlcpBodyException {
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
}
