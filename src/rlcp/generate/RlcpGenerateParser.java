package rlcp.generate;

import java.io.IOException;
import org.dom4j.DocumentException;
import org.dom4j.Node;
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

import static rlcp.util.Constants.*;

/**
 * RlcpParser implementation for Generate method.
 *
 * @author Eugene Efimchick
 */
public class RlcpGenerateParser implements RlcpParser<RlcpGenerateRequest, RlcpGenerateResponse> {

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
