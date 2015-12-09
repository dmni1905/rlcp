package rlcp.generate;

import java.io.IOException;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import rlcp.*;
import rlcp.exception.BadRlcpBodyException;
import rlcp.exception.BadRlcpHeaderException;
import rlcp.exception.BadRlcpRequestException;
import rlcp.exception.BadRlcpResponseException;
import rlcp.util.DomHelper;
import rlcp.util.Util;

import static rlcp.util.Constants.*;

/**
 * RlcpParser implementation for Generate method.
 */
public class RlcpGenerateParser implements RlcpParser<RlcpGenerateRequest, RlcpGenerateResponse> {

    /**
     * Parse the content of the given RLCP-request and return instance {@code RlcpGenerateRequest}.
     *
     * @param rlcpRequestString string RLCP-request
     * @return {@code RlcpGenerateRequest} instance for specified method parsed from param String.
     * @throws BadRlcpRequestException if RLCP-request the is invalid.
     *                                 For example, RLCP-request has bad header or RLCP-request has bad body.
     * @see RlcpGenerateRequest
     */
    @Override
    public RlcpGenerateRequest parseRequest(String rlcpRequestString) throws BadRlcpRequestException {
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

        RlcpGenerateRequestBody parsedBody;
        try {
            parsedBody = parseRequestBody(bodyBuilder.toString());
        } catch (BadRlcpBodyException ex) {
            throw new BadRlcpRequestException(ex);
        }

        return new RlcpGenerateRequest(parsedHeader, parsedBody);

    }

    /**
     * Parse the content of the given RLCP-response and return a new object {@code RlcpGenerateResponse}.
     *
     * @param rlcpResponseString string RLCP-response
     * @return {@code RlcpGenerateResponse} implementation instance for specified method parsed from param String, or null.
     * @throws BadRlcpResponseException if RLCP-response the is invalid.
     *                                  For example, RLCP-response has bad header or RLCP-response has bad body.
     * @see RlcpGenerateResponse
     */
    @Override
    public RlcpGenerateResponse parseResponse(String rlcpResponseString) throws BadRlcpResponseException {
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

        RlcpGenerateResponseBody parsedBody = null;
        if (parsedHeader.isSuccessful()) {
            try {
                parsedBody = parseResponseBody(bodyBuilder.toString());
            } catch (BadRlcpBodyException ex) {
                throw new BadRlcpResponseException(ex);
            }
        }

        return new RlcpGenerateResponse(parsedHeader, parsedBody);
    }

    /**
     * Parse the content of the given RLCP-request body and return instance {@code RlcpGenerateRequestBody}.
     *
     * @param rlcpBodyString string RLCP-request body
     * @return {@code RlcpGenerateRequestBody} instance for specified method parsed from param String.
     * @throws BadRlcpBodyException if RLCP-request body the is invalid.
     * @see RlcpGenerateRequestBody
     */
    @Override
    public RlcpGenerateRequestBody parseRequestBody(String rlcpBodyString) throws BadRlcpBodyException {
        String condition;
        try {
            condition = DomHelper.getTextFromNodeByXpath(DomHelper.toXml(rlcpBodyString), xPath_selectConditionForGenerating);
        } catch (DocumentException ex) {
            throw new BadRlcpBodyException(ex);
        }
        return new RlcpGenerateRequestBody(condition);

    }

    /**
     * Parse the content of the given RLCP-response and return a new object {@code RlcpGenerateResponseBody}.
     *
     * @param rlcpBodyString string RLCP-response
     * @return {@code RlcpGenerateResponseBody} implementation instance for specified method parsed from param String, or null.
     * @throws BadRlcpBodyException if RLCP-response body the is invalid..
     * @see RlcpGenerateResponseBody
     */
    @Override
    public RlcpGenerateResponseBody parseResponseBody(String rlcpBodyString) throws BadRlcpBodyException {
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
}
