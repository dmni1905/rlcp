package rlcp.calculate;

import java.io.IOException;
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
import rlcp.util.DomHelper;
import rlcp.util.Util;

import static rlcp.util.Constants.*;

/**
 * RlcpParser implementation for Calculate method.
 */
public class RlcpCalculateParser implements RlcpParser<RlcpCalculateRequest, RlcpCalculateResponse> {

    /**
     * Parse the content of the given RLCP-request and return instance {@code RlcpCalculateRequest}.
     *
     * @param rlcpRequestString string RLCP-request
     * @return {@code RlcpCalculateRequest} instance for specified method parsed from param String.
     * @throws BadRlcpRequestException if RLCP-request the is invalid.
     *                                 For example, RLCP-request has bad header or RLCP-request has bad body.
     * @see RlcpCalculateRequest
     */
    @Override
    public RlcpCalculateRequest parseRequest(String rlcpRequestString) throws BadRlcpRequestException {
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

        RlcpCalculateRequestBody parsedBody;
        try {
            parsedBody = parseRequestBody(bodyBuilder.toString());
        } catch (BadRlcpBodyException ex) {
            throw new BadRlcpRequestException(ex);
        }

        return new RlcpCalculateRequest(parsedHeader, parsedBody);

    }

    /**
     * Parse the content of the given RLCP-response and return a new object {@code RlcpCalculateResponse}.
     *
     * @param rlcpResponseString string RLCP-response
     * @return {@code RlcpCalculateResponse} implementation instance for specified method parsed from param String, or null.
     * @throws BadRlcpResponseException if RLCP-response the is invalid.
     *                                  For example, RLCP-response has bad header or RLCP-response has bad body.
     * @see RlcpCalculateResponse
     */
    @Override
    public RlcpCalculateResponse parseResponse(String rlcpResponseString) throws BadRlcpResponseException {
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

        RlcpCalculateResponseBody parsedBody = null;
        if (parsedHeader.isSuccessful()) {
            try {
                parsedBody = parseResponseBody(bodyBuilder.toString());
            } catch (BadRlcpBodyException ex) {
                throw new BadRlcpResponseException(ex);
            }
        }

        return new RlcpCalculateResponse(parsedHeader, parsedBody);
    }

    /**
     * Parse the content of the given RLCP-request body and return instance {@code RlcpCalculateRequestBody}.
     *
     * @param rlcpBodyString string RLCP-request body
     * @return {@code RlcpCalculateRequestBody} instance for specified method parsed from param String.
     * @throws BadRlcpBodyException if RLCP-request body the is invalid.
     * @see RlcpCalculateRequestBody
     */
    @Override
    public RlcpCalculateRequestBody parseRequestBody(String rlcpBodyString) throws BadRlcpBodyException {
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


    /**
     * Parse the content of the given RLCP-response and return a new object {@code RlcpCalculateResponseBody}.
     *
     * @param rlcpBodyString string RLCP-response
     * @return {@code RlcpCalculateResponseBody} implementation instance for specified method parsed from param String, or null.
     * @throws BadRlcpBodyException if RLCP-response body the is invalid.
     * @see RlcpCalculateResponseBody
     */
    @Override
    public RlcpCalculateResponseBody parseResponseBody(String rlcpBodyString) throws BadRlcpBodyException {
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
