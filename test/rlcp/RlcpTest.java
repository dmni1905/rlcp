package rlcp;

import org.junit.BeforeClass;
import org.junit.Test;
import rlcp.calculate.RlcpCalculateRequest;
import rlcp.calculate.RlcpCalculateRequestBody;
import rlcp.calculate.RlcpCalculateResponse;
import rlcp.calculate.RlcpCalculateResponseBody;
import rlcp.check.*;
import rlcp.exception.BadRlcpBodyException;
import rlcp.exception.BadRlcpRequestException;
import rlcp.exception.BadRlcpResponseException;
import rlcp.exception.RlcpException;
import rlcp.generate.*;
import rlcp.method.RlcpMethod;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static rlcp.method.RlcpMethod.*;

public class RlcpTest {
    private static String rlcpCalculateRequest;
    private static String rlcpCalculateRequestBody;

    private static String rlcpCalculateResponse;
    private static String rlcpCalculateResponseBody;

    private static String rlcpCheckRequest;
    private static String rlcpCheckRequestBody;

    private static String rlcpCheckResponse;
    private static String rlcpCheckResponseBody;

    private static String rlcpGenerateRequest;
    private static String rlcpGenerateRequestBody;

    private static String rlcpGenerateResponse;
    private static String rlcpGenerateResponseBody;

    @BeforeClass
    public static void init() {
        rlcpCalculateRequestBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE Request SYSTEM \"http://de.ifmo.ru/--DTD/Request.dtd\">\n" +
                "\n" +
                "<Request>\n" +
                "  <Conditions>\n" +
                "    <ConditionForCalculating>\n" +
                "      <Input>\n" +
                "        <!--тест-тест-тест-->\n" +
                "      </Input>\n" +
                "    </ConditionForCalculating>\n" +
                "  </Conditions>\n" +
                "  <Instructions>\n" +
                "    <!--тест-тест-тест2-->\n" +
                "  </Instructions>\n" +
                "  <PreGenerated>\n" +
                "    <Text>\n" +
                "      <!--тест Text-->\n" +
                "    </Text>\n" +
                "    <Code>\n" +
                "      <!--тест Code-->\n" +
                "    </Code>\n" +
                "    <Instructions>\n" +
                "      <!--тест Instructions-->\n" +
                "    </Instructions>\n" +
                "  </PreGenerated>\n" +
                "</Request>";
        ;
        rlcpCalculateRequest = "CALCULATE\n" +
                "url:rlcp://user:user@127.0.0.1:3000\n" +
                "content-length:554\n" +
                "\n" + rlcpCalculateRequestBody;
        rlcpCheckRequestBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE Request SYSTEM \"http://de.ifmo.ru/--DTD/Request.dtd\">\n" +
                "\n" +
                "<Request>\n" +
                "  <Conditions>\n" +
                "    <ConditionForChecking id=\"1\" Time=\"5\">\n" +
                "      <Input>\n" +
                "        <!--тест-Input-1-->\n" +
                "      </Input>\n" +
                "      <Output>\n" +
                "        <!--тест-Output-1-->\n" +
                "      </Output>\n" +
                "    </ConditionForChecking>\n" +
                "    <ConditionForChecking id=\"2\" Time=\"5\">\n" +
                "      <Input>\n" +
                "        <!--тест-Input-2-->\n" +
                "      </Input>\n" +
                "      <Output>\n" +
                "        <!--тест-Output-2-->\n" +
                "      </Output>\n" +
                "    </ConditionForChecking>\n" +
                "  </Conditions>\n" +
                "  <Instructions>\n" +
                "    <!--тест-->\n" +
                "  </Instructions>\n" +
                "  <PreGenerated>\n" +
                "    <Text>\n" +
                "      <!--text-->\n" +
                "    </Text>\n" +
                "    <Code>\n" +
                "      <!--code-->\n" +
                "    </Code>\n" +
                "    <Instructions>\n" +
                "      <!--instructions-->\n" +
                "    </Instructions>\n" +
                "  </PreGenerated>\n" +
                "</Request>";
        rlcpCheckRequest = "CHECK\n" +
                "url:rlcp://user:user@127.0.0.1:3000\n" +
                "content-length:784\n" +
                "\n" + rlcpCheckRequestBody;
        rlcpGenerateRequestBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE Request SYSTEM \"http://de.ifmo.ru/--DTD/Request.dtd\">\n" +
                "\n" +
                "<Request>\n" +
                "  <Conditions>\n" +
                "    <ConditionForGenerating>\n" +
                "      <Input>\n" +
                "        <!--generating cond-->\n" +
                "      </Input>\n" +
                "    </ConditionForGenerating>\n" +
                "  </Conditions>\n" +
                "</Request>";
        rlcpGenerateRequest = "GENERATE\n" +
                "url:rlcp://user:user@127.0.0.1:3000\n" +
                "content-length:274\n" +
                "\n" + rlcpGenerateRequestBody;
        rlcpGenerateResponseBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE Response SYSTEM \"http://de.ifmo.ru/--DTD/Response.dtd\">\n" +
                "\n" +
                "<Response>\n" +
                "  <GeneratingResult>\n" +
                "    <Text>\n" +
                "      <!--text-->\n" +
                "    </Text>\n" +
                "    <Code>\n" +
                "      <!--code-->\n" +
                "    </Code>\n" +
                "    <Instructions>\n" +
                "      <!--instructions-->\n" +
                "    </Instructions>\n" +
                "  </GeneratingResult>\n" +
                "</Response>";
        rlcpGenerateResponse = "200\n" +
                "content-length:319\n" +
                "\n" + rlcpGenerateResponseBody;
        rlcpCalculateResponseBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE Response SYSTEM \"http://de.ifmo.ru/--DTD/Response.dtd\">\n" +
                "\n" +
                "<Response>\n" +
                "  <CalculatingResult>\n" +
                "    <Text>\n" +
                "      <!--text-->\n" +
                "    </Text>\n" +
                "    <Code>\n" +
                "      <!--code-->\n" +
                "    </Code>\n" +
                "  </CalculatingResult>\n" +
                "</Response>";
        rlcpCalculateResponse = "200\n" +
                "content-length:256\n" +
                "\n" + rlcpCalculateResponseBody;
        rlcpCheckResponseBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE Response SYSTEM \"http://de.ifmo.ru/--DTD/Response.dtd\">\n" +
                "\n" +
                "<Response>\n" +
                "  <CheckingResult id=\"1\" Time=\"50\" Result=\"1.00\">\n" +
                "    <!--ok-->\n" +
                "  </CheckingResult>\n" +
                "  <CheckingResult id=\"2\" Time=\"50\" Result=\"1.00\">\n" +
                "    <!--ok-->\n" +
                "  </CheckingResult>\n" +
                "</Response>";
        rlcpCheckResponse = "200\n" +
                "content-length:297\n" +
                "\n" + rlcpCheckResponseBody;
    }

    //    ------------------------------------------ ParseRequest(String, Class) ------------------------------------------
    @Test
    public void testParseCalculateValidRequestCast() {
        RlcpCalculateRequest request = null;
        try {
            request = Rlcp.parseRequest(rlcpCalculateRequest, RlcpCalculateRequest.class);
        } catch (BadRlcpRequestException e) {
            e.printStackTrace();
        }
        assertEquals(request.getMethod().getName().toLowerCase(), "calculate");

        RlcpUrl url = new RlcpUrl("127.0.0.1", "3000", "user", "user");
        assertEquals(request.getHeader().getUrl().getLogin(), url.getLogin());
        assertEquals(request.getHeader().getUrl().getPassword(), url.getPassword());
        assertEquals(request.getHeader().getUrl().getHost(), url.getHost());
        assertEquals(request.getHeader().getUrl().getPort(), url.getPort());

        assertEquals(request.getHeader().getContentLength(), 554);
        assertEquals(request.getBody().getInstructions(), "тест-тест-тест2");
        GeneratingResult result = new GeneratingResult("тест Text", "тест Code", "тест Instructions");
        assertEquals(request.getBody().getPreGenerated().getText(), result.getText());
        assertEquals(request.getBody().getPreGenerated().getCode(), result.getCode());
        assertEquals(request.getBody().getPreGenerated().getInstructions(), result.getInstructions());
    }

    @Test
    public void testParseCheckValidRequestCast() {
        RlcpCheckRequest request = null;
        try {
            request = Rlcp.parseRequest(rlcpCheckRequest, RlcpCheckRequest.class);
        } catch (BadRlcpRequestException e) {
            e.printStackTrace();
        }
        assertEquals(request.getMethod().getName().toLowerCase(), "check");

        RlcpUrl url = new RlcpUrl("127.0.0.1", "3000", "user", "user");
        assertEquals(request.getHeader().getUrl().getLogin(), url.getLogin());
        assertEquals(request.getHeader().getUrl().getPassword(), url.getPassword());
        assertEquals(request.getHeader().getUrl().getHost(), url.getHost());
        assertEquals(request.getHeader().getUrl().getPort(), url.getPort());

        assertEquals(request.getHeader().getContentLength(), 784);
        GeneratingResult result = new GeneratingResult("text", "code", "instructions");
        assertEquals(request.getBody().getPreGenerated().getText(), result.getText());
        assertEquals(request.getBody().getPreGenerated().getCode(), result.getCode());
        assertEquals(request.getBody().getPreGenerated().getInstructions(), result.getInstructions());
        assertEquals(request.getBody().getInstructions(), "тест");
        assertEquals(request.getBody().getConditionsList().size(), 2);
    }

    @Test
    public void testParseGenerateValidRequestCast() {
        RlcpGenerateRequest request = null;
        try {
            request = Rlcp.parseRequest(rlcpGenerateRequest, RlcpGenerateRequest.class);
        } catch (BadRlcpRequestException e) {
            e.printStackTrace();
        }
        assertEquals(request.getMethod().getName().toLowerCase(), "generate");
        RlcpUrl url = new RlcpUrl("127.0.0.1", "3000", "user", "user");
        assertEquals(request.getHeader().getUrl().getLogin(), url.getLogin());
        assertEquals(request.getHeader().getUrl().getPassword(), url.getPassword());
        assertEquals(request.getHeader().getUrl().getHost(), url.getHost());
        assertEquals(request.getHeader().getUrl().getPort(), url.getPort());

        assertEquals(request.getHeader().getContentLength(), 274);
        assertEquals(request.getBody().getCondition(), "generating cond");
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckRequestCastExceptionIfGiveGenerateRequest() throws BadRlcpRequestException {
        RlcpCheckRequest request = Rlcp.parseRequest(rlcpGenerateRequest, RlcpCheckRequest.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckRequestCastExceptionIfGiveCalculateRequest() throws BadRlcpRequestException {
        RlcpCheckRequest request = Rlcp.parseRequest(rlcpCalculateRequest, RlcpCheckRequest.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateRequestCastExceptionIfGiveGenerateRequest() throws BadRlcpRequestException {
        RlcpCalculateRequest request = Rlcp.parseRequest(rlcpGenerateRequest, RlcpCalculateRequest.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateRequestCastExceptionIfGiveCheckRequest() throws BadRlcpRequestException {
        RlcpCalculateRequest request = Rlcp.parseRequest(rlcpCheckRequest, RlcpCalculateRequest.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateRequestCastExceptionIfGiveCheckRequest() throws BadRlcpRequestException {
        RlcpGenerateRequest request = Rlcp.parseRequest(rlcpCheckRequest, RlcpGenerateRequest.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateRequestCastExceptionIfGiveCalculateRequest() throws BadRlcpRequestException {
        RlcpGenerateRequest request = Rlcp.parseRequest(rlcpCalculateRequest, RlcpGenerateRequest.class);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseGenerateRequestCastExceptionBadHeader() throws BadRlcpRequestException {
        RlcpGenerateRequest request = Rlcp.parseRequest(rlcpGenerateRequest.replace("rlcp://", "flow://"), RlcpGenerateRequest.class);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseGenerateRequestCastExceptionBadBody() throws BadRlcpRequestException {
        RlcpGenerateRequest request = Rlcp.parseRequest(rlcpGenerateRequest.replace("<Request>", "<>"), RlcpGenerateRequest.class);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCalculateRequestCastBadHeader() throws BadRlcpRequestException {
        RlcpCalculateRequest request = Rlcp.parseRequest(rlcpCalculateRequest.replace("rlcp://", "flow://"), RlcpCalculateRequest.class);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCalculateRequestCastExceptionBadBody() throws BadRlcpRequestException {
        RlcpCalculateRequest request = Rlcp.parseRequest(rlcpCalculateRequest.replace("<Request>", "<>"), RlcpCalculateRequest.class);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCheckRequestCastExceptionBadHeader() throws BadRlcpRequestException {
        RlcpCheckRequest request = Rlcp.parseRequest(rlcpCheckRequest.replace("rlcp://", "flow://"), RlcpCheckRequest.class);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCheckRequestCastExceptionBadBody() throws BadRlcpRequestException {
        RlcpCheckRequest request = Rlcp.parseRequest(rlcpCheckRequest.replace("<Request>", "<>"), RlcpCheckRequest.class);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCheckRequestCastExceptionNoHeader() throws BadRlcpRequestException {
        RlcpCheckRequest request = Rlcp.parseRequest(rlcpCheckRequestBody, RlcpCheckRequest.class);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCalculateRequestCastExceptionNoHeader() throws BadRlcpRequestException {
        RlcpCalculateRequest request = Rlcp.parseRequest(rlcpCalculateRequestBody, RlcpCalculateRequest.class);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseGenerateRequestCastExceptionNoHeader() throws BadRlcpRequestException {
        RlcpGenerateRequest request = Rlcp.parseRequest(rlcpGenerateRequestBody, RlcpGenerateRequest.class);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseGenerateRequestCastExceptionNoBody() throws BadRlcpRequestException {
        RlcpGenerateRequest request = Rlcp.parseRequest(rlcpGenerateRequest.replace(rlcpGenerateRequestBody, ""), RlcpGenerateRequest.class);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCalculateRequestCastExceptionNoBody() throws BadRlcpRequestException {
        RlcpCalculateRequest request = Rlcp.parseRequest(rlcpCalculateRequest.replace(rlcpCalculateRequestBody, ""), RlcpCalculateRequest.class);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCheckRequestCastExceptionNoBody() throws BadRlcpRequestException {
        RlcpCheckRequest request = Rlcp.parseRequest(rlcpCheckRequest.replace(rlcpCheckRequestBody, ""), RlcpCheckRequest.class);
    }

    //    ------------------------------------------ ParseRequestBody(String, Class) ------------------------------------------
    @Test
    public void testParseCalculateValidRequestBodyCast() {
        RlcpCalculateRequestBody requestBody = null;
        try {
            requestBody = Rlcp.parseRequestBody(rlcpCalculateRequestBody, RlcpCalculateRequestBody.class);
        } catch (BadRlcpBodyException e) {
            e.printStackTrace();
        }
        assertEquals(requestBody.getCondition(), "тест-тест-тест");
        GeneratingResult result = new GeneratingResult("тест Text", "тест Code", "тест Instructions");
        assertEquals(requestBody.getPreGenerated().getText(), result.getText());
        assertEquals(requestBody.getPreGenerated().getCode(), result.getCode());
        assertEquals(requestBody.getPreGenerated().getInstructions(), result.getInstructions());
    }

    @Test
    public void testParseCheckValidRequestBodyCast() {
        RlcpCheckRequestBody requestBody = null;
        try {
            requestBody = Rlcp.parseRequestBody(rlcpCheckRequestBody, RlcpCheckRequestBody.class);
        } catch (BadRlcpBodyException e) {
            e.printStackTrace();
        }
        assertEquals(requestBody.getMethod().getName().toLowerCase(), "check");

        GeneratingResult result = new GeneratingResult("text", "code", "instructions");
        assertEquals(requestBody.getPreGenerated().getText(), result.getText());
        assertEquals(requestBody.getPreGenerated().getCode(), result.getCode());
        assertEquals(requestBody.getPreGenerated().getInstructions(), result.getInstructions());
        assertEquals(requestBody.getInstructions(), "тест");
        assertEquals(requestBody.getConditionsList().size(), 2);
    }

    @Test
    public void testParseGenerateValidRequestBodyCast() {
        RlcpGenerateRequestBody requestBody = null;
        try {
            requestBody = Rlcp.parseRequestBody(rlcpGenerateRequestBody, RlcpGenerateRequestBody.class);
        } catch (BadRlcpBodyException e) {
            e.printStackTrace();
        }
        assertEquals(requestBody.getMethod().getName().toLowerCase(), "generate");

        assertEquals(requestBody.getCondition(), "generating cond");
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckRequestBodyCastExceptionIfGiveGenerateRequest() throws BadRlcpBodyException {
        RlcpCheckRequestBody requestBody = Rlcp.parseRequestBody(rlcpGenerateRequestBody, RlcpCheckRequestBody.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckRequestBodyCastExceptionIfGiveCalculateRequest() throws BadRlcpBodyException {
        RlcpCheckRequestBody requestBody = Rlcp.parseRequestBody(rlcpCalculateRequestBody, RlcpCheckRequestBody.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateRequestBodyCastExceptionIfGiveCheckRequest() throws BadRlcpBodyException {
        RlcpCalculateRequestBody requestBody = Rlcp.parseRequestBody(rlcpCheckRequestBody, RlcpCalculateRequestBody.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateRequestBodyCastExceptionIfGiveGenerateRequest() throws BadRlcpBodyException {
        RlcpCalculateRequestBody requestBody = Rlcp.parseRequestBody(rlcpGenerateRequestBody, RlcpCalculateRequestBody.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateRequestBodyCastExceptionIfGiveCalculateRequest() throws BadRlcpBodyException {
        RlcpGenerateRequestBody requestBody = Rlcp.parseRequestBody(rlcpCalculateRequestBody, RlcpGenerateRequestBody.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateRequestBodyCastExceptionIfGiveCheckRequest() throws BadRlcpBodyException {
        RlcpGenerateRequestBody requestBody = Rlcp.parseRequestBody(rlcpCheckRequestBody, RlcpGenerateRequestBody.class);
    }

    @Test(expected = RlcpException.class)
    public void testParseGenerateBodyRequestCastExceptionBadHeader() throws BadRlcpBodyException {
        RlcpGenerateRequestBody requestBody = Rlcp.parseRequestBody(rlcpGenerateRequestBody.replace("<Request>", "<>"), RlcpGenerateRequestBody.class);
    }

    @Test(expected = RlcpException.class)
    public void testParseCheckBodyRequestCastExceptionBadHeader() throws BadRlcpBodyException {
        RlcpCheckRequestBody requestBody = Rlcp.parseRequestBody(rlcpCheckRequestBody.replace("<Request>", "<>"), RlcpCheckRequestBody.class);
    }

    @Test(expected = RlcpException.class)
    public void testParseCalculateBodyRequestCastExceptionBadHeader() throws BadRlcpBodyException {
        RlcpCalculateRequestBody requestBody = Rlcp.parseRequestBody(rlcpCalculateRequestBody.replace("<Request>", "<>"), RlcpCalculateRequestBody.class);
    }

    @Test(expected = NullPointerException.class)
    public void testParseCheckRequestBodyCastExceptionNoBody() throws BadRlcpBodyException {
        RlcpCheckRequestBody request = Rlcp.parseRequestBody("", RlcpCheckRequestBody.class);
    }

    @Test(expected = NullPointerException.class)
    public void testParseCalculateRequestBodyCastExceptionNoBody() throws BadRlcpBodyException {
        RlcpCalculateRequestBody request = Rlcp.parseRequestBody("", RlcpCalculateRequestBody.class);
    }

    @Test(expected = NullPointerException.class)
    public void testParseGenerateRequestBodyCastExceptionNoBody() throws BadRlcpBodyException {
        RlcpGenerateRequestBody request = Rlcp.parseRequestBody("", RlcpGenerateRequestBody.class);
    }

    //    ------------------------------------------ ParseResponse(String, Class) ------------------------------------------
    @Test
    public void testParseCalculateValidResponseCast() {
        RlcpCalculateResponse response = Rlcp.parseResponse(rlcpCalculateResponse, RlcpCalculateResponse.class);

        assertEquals(response.getMethod().getName().toLowerCase(), "calculate");
        assertEquals(response.getHeader().getContentLength(), 256);

        assertTrue(response.getBody().getDocument().asXML().contains("<Text><!--text--></Text>"));
        assertTrue(response.getBody().getDocument().asXML().contains("<Code><!--code--></Code>"));
    }

    @Test
    public void testParseCheckValidResponseCast() {
        RlcpCheckResponse response = Rlcp.parseResponse(rlcpCheckResponse, RlcpCheckResponse.class);
        assertEquals(response.getMethod().getName().toLowerCase(), "check");
        assertEquals(response.getHeader().getContentLength(), 297);

        assertTrue(response.getBody().getDocument().asXML().contains("<CheckingResult id=\"1\" Time=\"50\" Result=\"1.00\"><!--ok--></CheckingResult>"));
        assertTrue(response.getBody().getDocument().asXML().contains("<CheckingResult id=\"2\" Time=\"50\" Result=\"1.00\"><!--ok--></CheckingResult>"));
    }

    @Test
    public void testParseGenerateValidResponseCast() {
        RlcpGenerateResponse response = Rlcp.parseResponse(rlcpGenerateResponse, RlcpGenerateResponse.class);

        assertEquals(response.getMethod().getName().toLowerCase(), "generate");
        assertEquals(response.getHeader().getContentLength(), 319);
        assertEquals(response.getHeader().getResponseCode(), "200");

        assertTrue(response.getBody().getDocument().asXML().contains("<Text><!--text--></Text>"));
        assertTrue(response.getBody().getDocument().asXML().contains("<Code><!--code--></Code>"));
        assertTrue(response.getBody().getDocument().asXML().contains("<Instructions><!--instructions--></Instructions>"));
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckResponseCastExceptionIfGiveGenerateResponse() throws BadRlcpResponseException {
        RlcpCheckResponse response = Rlcp.parseResponse(rlcpGenerateResponse, RlcpCheckResponse.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckResponseCastExceptionIfGiveCalculateResponse() throws BadRlcpResponseException {
        RlcpCheckResponse response = Rlcp.parseResponse(rlcpCalculateResponse, RlcpCheckResponse.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateResponseCastExceptionIfGiveGenerateResponse() throws BadRlcpResponseException {
        RlcpCalculateResponse response = Rlcp.parseResponse(rlcpGenerateResponse, RlcpCalculateResponse.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateResponseCastExceptionIfGiveCheckResponse() throws BadRlcpResponseException {
        RlcpCalculateResponse response = Rlcp.parseResponse(rlcpCheckResponse, RlcpCalculateResponse.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateResponseCastExceptionIfGiveCheckResponse() throws BadRlcpResponseException {
        RlcpGenerateResponse response = Rlcp.parseResponse(rlcpCheckResponse, RlcpGenerateResponse.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateResponseCastExceptionIfGiveCalculateResponse() throws BadRlcpResponseException {
        RlcpGenerateResponse response = Rlcp.parseResponse(rlcpCalculateResponse, RlcpGenerateResponse.class);
    }

    @Test(expected = RlcpException.class)
    public void testParseGenerateResponseCastExceptionBadBody() throws BadRlcpResponseException {
        RlcpGenerateResponse response = Rlcp.parseResponse(rlcpGenerateResponse.replace("<Response>", "<>"), RlcpGenerateResponse.class);
    }

    @Test(expected = RlcpException.class)
    public void testParseCheckResponseCastExceptionBadBody() throws BadRlcpResponseException {
        RlcpCheckResponse response = Rlcp.parseResponse(rlcpCheckResponse.replace("<Response>", "<>"), RlcpCheckResponse.class);
    }

    @Test(expected = RlcpException.class)
    public void testParseCalculateResponseCastExceptionBadBody() throws BadRlcpResponseException {
        RlcpCalculateResponse response = Rlcp.parseResponse(rlcpCalculateResponse.replace("<Response>", "<>"), RlcpCalculateResponse.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseCheckRsponseCastExceptionNoHeader() throws BadRlcpResponseException {
        RlcpCheckResponse response = Rlcp.parseResponse(rlcpCheckResponseBody, RlcpCheckResponse.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseCalculateRsponseCastExceptionNoHeader() throws BadRlcpResponseException {
        RlcpCalculateResponse response = Rlcp.parseResponse(rlcpCalculateResponseBody, RlcpCalculateResponse.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseGenerateRsponseCastExceptionNoHeader() throws BadRlcpResponseException {
        RlcpGenerateResponse response = Rlcp.parseResponse(rlcpGenerateResponseBody, RlcpGenerateResponse.class);
    }

    @Test(expected = NullPointerException.class)
    public void testParseCheckResponseCastExceptionNoBody() throws BadRlcpResponseException {
        RlcpCheckResponse response = Rlcp.parseResponse(rlcpCheckResponse.replace(rlcpCheckResponseBody, ""), RlcpCheckResponse.class);
    }

    @Test(expected = NullPointerException.class)
    public void testParseGenerateResponseCastExceptionNoBody() throws BadRlcpResponseException {
        RlcpGenerateResponse response = Rlcp.parseResponse(rlcpGenerateResponse.replace(rlcpGenerateResponseBody, ""), RlcpGenerateResponse.class);
    }

    @Test(expected = NullPointerException.class)
    public void testParseCalculateResponseCastExceptionNoBody() throws BadRlcpResponseException {
        RlcpCalculateResponse response = Rlcp.parseResponse(rlcpCalculateResponse.replace(rlcpCalculateResponseBody, ""), RlcpCalculateResponse.class);
    }

    //    ------------------------------------------ ParseResponseBody(String, Class) ------------------------------------------
    @Test
    public void testParseCalculateValidResponseBodyCast() {
        RlcpCalculateResponseBody responseBody = Rlcp.parseResponseBody(rlcpCalculateResponseBody, RlcpCalculateResponseBody.class);
        assertEquals(responseBody.getMethod().getName().toLowerCase(), "calculate");
        assertEquals(responseBody.getContentLength(), 256);

        assertTrue(responseBody.getDocument().asXML().contains("<Text><!--text--></Text>"));
        assertTrue(responseBody.getDocument().asXML().contains("<Code><!--code--></Code>"));
    }

    @Test
    public void testParseCheckValidResponseBodyCast() {
        RlcpCheckResponseBody responseBody = Rlcp.parseResponseBody(rlcpCheckResponseBody, RlcpCheckResponseBody.class);
        assertEquals(responseBody.getMethod().getName().toLowerCase(), "check");
        assertEquals(responseBody.getContentLength(), 297);

        assertTrue(responseBody.getDocument().asXML().contains("<CheckingResult id=\"1\" Time=\"50\" Result=\"1.00\"><!--ok--></CheckingResult>"));
        assertTrue(responseBody.getDocument().asXML().contains("<CheckingResult id=\"2\" Time=\"50\" Result=\"1.00\"><!--ok--></CheckingResult>"));
    }

    @Test
    public void testParseGenerateValidResponseBodyCast() {
        RlcpGenerateResponseBody responseBody = Rlcp.parseResponseBody(rlcpGenerateResponseBody, RlcpGenerateResponseBody.class);
        assertEquals(responseBody.getMethod().getName().toLowerCase(), "generate");
        assertEquals(responseBody.getContentLength(), 319);

        assertTrue(responseBody.getDocument().asXML().contains("<Text><!--text--></Text>"));
        assertTrue(responseBody.getDocument().asXML().contains("<Code><!--code--></Code>"));
        assertTrue(responseBody.getDocument().asXML().contains("<Instructions><!--instructions--></Instructions>"));
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckResponseBodyCastExceptionIfGiveGenerateResponseBody() throws BadRlcpBodyException {
        RlcpCheckResponseBody responseBody = Rlcp.parseResponseBody(rlcpGenerateResponseBody, RlcpCheckResponseBody.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckResponseBodyCastExceptionIfGiveCalculateResponseBody() throws BadRlcpBodyException {
        RlcpCheckResponseBody responseBody = Rlcp.parseResponseBody(rlcpCalculateResponseBody, RlcpCheckResponseBody.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateResponseBodyCastExceptionIfGiveGenerateResponseBody() throws BadRlcpBodyException {
        RlcpCalculateResponseBody responseBody = Rlcp.parseResponseBody(rlcpGenerateResponseBody, RlcpCalculateResponseBody.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateResponseBodyCastExceptionIfGiveCheckResponseBody() throws BadRlcpBodyException {
        RlcpCalculateResponseBody responseBody = Rlcp.parseResponseBody(rlcpCheckResponseBody, RlcpCalculateResponseBody.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateResponseBodyCastExceptionIfGiveCheckResponseBody() throws BadRlcpBodyException {
        RlcpGenerateResponseBody responseBody = Rlcp.parseResponseBody(rlcpCheckResponseBody, RlcpGenerateResponseBody.class);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateResponseBodyCastExceptionIfGiveCalculateResponseBody() throws BadRlcpBodyException {
        RlcpGenerateResponseBody responseBody = Rlcp.parseResponseBody(rlcpCalculateResponseBody, RlcpGenerateResponseBody.class);
    }

    @Test(expected = RlcpException.class)
    public void testParseGenerateResponseBodyCastExceptionBadBody() throws BadRlcpBodyException {
        RlcpGenerateResponseBody responseBody = Rlcp.parseResponseBody(rlcpGenerateResponseBody.replace("<Response>", "<>"), RlcpGenerateResponseBody.class);
    }

    @Test(expected = RlcpException.class)
    public void testParseCheckResponseBodyCastExceptionBadBody() throws BadRlcpBodyException {
        RlcpCheckResponseBody responseBody = Rlcp.parseResponseBody(rlcpCheckResponseBody.replace("<Response>", "<>"), RlcpCheckResponseBody.class);
    }

    @Test(expected = RlcpException.class)
    public void testParseCalculateResponseBodyCastExceptionBadBody() throws BadRlcpBodyException {
        RlcpCalculateResponseBody responseBody = Rlcp.parseResponseBody(rlcpCalculateResponseBody.replace("<Response>", "<>"), RlcpCalculateResponseBody.class);
    }

    @Test(expected = NullPointerException.class)
    public void testParseCheckResponseBodyCastExceptionNoBody() throws BadRlcpBodyException {
        RlcpCheckResponseBody responseBody = Rlcp.parseResponseBody("", RlcpCheckResponseBody.class);
    }

    @Test(expected = NullPointerException.class)
    public void testParseCalculateResponseBodyCastExceptionNoBody() throws BadRlcpBodyException {
        RlcpCalculateResponseBody responseBody = Rlcp.parseResponseBody("", RlcpCalculateResponseBody.class);
    }

    @Test(expected = NullPointerException.class)
    public void testParseGenerateResponseBodyCastExceptionNoBody() throws BadRlcpBodyException {
        RlcpGenerateResponseBody responseBody = Rlcp.parseResponseBody("", RlcpGenerateResponseBody.class);
    }

    //    ------------------------------------------ ParseRequest(String) ------------------------------------------
    @Test
    public void testParseCalculateValidRequest() {
        RlcpCalculateRequest request = null;
        try {
            request = (RlcpCalculateRequest) Rlcp.parseRequest(rlcpCalculateRequest);
        } catch (BadRlcpRequestException e) {
            e.printStackTrace();
        }
        assertEquals(request.getMethod().getName().toLowerCase(), "calculate");

        RlcpUrl url = new RlcpUrl("127.0.0.1", "3000", "user", "user");
        assertEquals(request.getHeader().getUrl().getLogin(), url.getLogin());
        assertEquals(request.getHeader().getUrl().getPassword(), url.getPassword());
        assertEquals(request.getHeader().getUrl().getHost(), url.getHost());
        assertEquals(request.getHeader().getUrl().getPort(), url.getPort());

        assertEquals(request.getHeader().getContentLength(), 554);
        assertEquals(request.getBody().getInstructions(), "тест-тест-тест2");
        GeneratingResult result = new GeneratingResult("тест Text", "тест Code", "тест Instructions");
        assertEquals(request.getBody().getPreGenerated().getText(), result.getText());
        assertEquals(request.getBody().getPreGenerated().getCode(), result.getCode());
        assertEquals(request.getBody().getPreGenerated().getInstructions(), result.getInstructions());
    }

    @Test
    public void testParseCheckValidRequest() {
        RlcpCheckRequest request = null;
        try {
            request = (RlcpCheckRequest) Rlcp.parseRequest(rlcpCheckRequest);
        } catch (BadRlcpRequestException e) {
            e.printStackTrace();
        }
        assertEquals(request.getMethod().getName().toLowerCase(), "check");

        RlcpUrl url = new RlcpUrl("127.0.0.1", "3000", "user", "user");
        assertEquals(request.getHeader().getUrl().getLogin(), url.getLogin());
        assertEquals(request.getHeader().getUrl().getPassword(), url.getPassword());
        assertEquals(request.getHeader().getUrl().getHost(), url.getHost());
        assertEquals(request.getHeader().getUrl().getPort(), url.getPort());

        assertEquals(request.getHeader().getContentLength(), 784);
        GeneratingResult result = new GeneratingResult("text", "code", "instructions");
        assertEquals(request.getBody().getPreGenerated().getText(), result.getText());
        assertEquals(request.getBody().getPreGenerated().getCode(), result.getCode());
        assertEquals(request.getBody().getPreGenerated().getInstructions(), result.getInstructions());
        assertEquals(request.getBody().getInstructions(), "тест");
        assertEquals(request.getBody().getConditionsList().size(), 2);
    }

    @Test
    public void testParseGenerateValidRequest() {
        RlcpGenerateRequest request = null;
        try {
            request = (RlcpGenerateRequest) Rlcp.parseRequest(rlcpGenerateRequest);
        } catch (BadRlcpRequestException e) {
            e.printStackTrace();
        }
        assertEquals(request.getMethod().getName().toLowerCase(), "generate");

        RlcpUrl url = new RlcpUrl("127.0.0.1", "3000", "user", "user");
        assertEquals(request.getHeader().getUrl().getLogin(), url.getLogin());
        assertEquals(request.getHeader().getUrl().getPassword(), url.getPassword());
        assertEquals(request.getHeader().getUrl().getHost(), url.getHost());
        assertEquals(request.getHeader().getUrl().getPort(), url.getPort());

        assertEquals(request.getHeader().getContentLength(), 274);
        assertEquals(request.getBody().getCondition(), "generating cond");
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckRequestExceptionIfGiveGenerateRequest() throws BadRlcpRequestException {
        RlcpCheckRequest request = (RlcpCheckRequest) Rlcp.parseRequest(rlcpGenerateRequest);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckRequestExceptionIfGiveCalculateRequest() throws BadRlcpRequestException {
        RlcpCheckRequest request = (RlcpCheckRequest) Rlcp.parseRequest(rlcpCalculateRequest);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateRequestExceptionIfGiveGenerateRequest() throws BadRlcpRequestException {
        RlcpCalculateRequest request = (RlcpCalculateRequest) Rlcp.parseRequest(rlcpGenerateRequest);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateRequestExceptionIfGiveCheckRequest() throws BadRlcpRequestException {
        RlcpCalculateRequest request = (RlcpCalculateRequest) Rlcp.parseRequest(rlcpCheckRequest);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateRequestExceptionIfGiveCheckRequest() throws BadRlcpRequestException {
        RlcpGenerateRequest request = (RlcpGenerateRequest) Rlcp.parseRequest(rlcpCheckRequest);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateRequestExceptionIfGiveCalculateRequest() throws BadRlcpRequestException {
        RlcpGenerateRequest request = (RlcpGenerateRequest) Rlcp.parseRequest(rlcpCalculateRequest);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseGenerateRequestExceptionBadHeader() throws BadRlcpRequestException {
        RlcpGenerateRequest request = (RlcpGenerateRequest) Rlcp.parseRequest(rlcpGenerateRequest.replace("rlcp://", "flow://"));
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseGenerateRequestExceptionBadBody() throws BadRlcpRequestException {
        RlcpGenerateRequest request = (RlcpGenerateRequest) Rlcp.parseRequest(rlcpGenerateRequest.replace("<Request>", "<>"));
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCalculateRequestBadHeader() throws BadRlcpRequestException {
        RlcpCalculateRequest request = (RlcpCalculateRequest) Rlcp.parseRequest(rlcpCalculateRequest.replace("rlcp://", "flow://"));
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCalculateRequestExceptionBadBody() throws BadRlcpRequestException {
        RlcpCalculateRequest request = (RlcpCalculateRequest) Rlcp.parseRequest(rlcpCalculateRequest.replace("<Request>", "<>"));
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCheckRequestExceptionBadHeader() throws BadRlcpRequestException {
        RlcpCheckRequest request = (RlcpCheckRequest) Rlcp.parseRequest(rlcpCheckRequest.replace("rlcp://", "flow://"));
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCheckRequestExceptionBadBody() throws BadRlcpRequestException {
        RlcpCheckRequest request = (RlcpCheckRequest) Rlcp.parseRequest(rlcpCheckRequest.replace("<Request>", "<>"));
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCheckRequestExceptionNoHeader() throws BadRlcpRequestException {
        RlcpCheckRequest request = (RlcpCheckRequest) Rlcp.parseRequest(rlcpCheckRequestBody);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCalculateRequestExceptionNoHeader() throws BadRlcpRequestException {
        RlcpCalculateRequest request = (RlcpCalculateRequest) Rlcp.parseRequest(rlcpCalculateRequestBody);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseGenerateRequestExceptionNoHeader() throws BadRlcpRequestException {
        RlcpGenerateRequest request = (RlcpGenerateRequest) Rlcp.parseRequest(rlcpGenerateRequestBody);
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseGenerateRequestExceptionNoBody() throws BadRlcpRequestException {
        RlcpGenerateRequest request = (RlcpGenerateRequest) Rlcp.parseRequest(rlcpGenerateRequest.replace(rlcpGenerateRequestBody, ""));
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCalculateRequestExceptionNoBody() throws BadRlcpRequestException {
        RlcpCalculateRequest request = (RlcpCalculateRequest) Rlcp.parseRequest(rlcpCalculateRequest.replace(rlcpCalculateRequestBody, ""));
    }

    @Test(expected = BadRlcpRequestException.class)
    public void testParseCрeckRequestExceptionNoBody() throws BadRlcpRequestException {
        RlcpCheckRequest request = (RlcpCheckRequest) Rlcp.parseRequest(rlcpCheckRequest.replace(rlcpCheckRequestBody, ""));
    }

    //    ------------------------------------------ ParseRequestBody(String) ------------------------------------------
    @Test
    public void testParseCalculateValidRequestBody() {
        RlcpCalculateRequestBody requestBody = null;
        try {
            requestBody = (RlcpCalculateRequestBody) Rlcp.parseRequestBody(rlcpCalculateRequestBody);
        } catch (BadRlcpBodyException e) {
            e.printStackTrace();
        }
        assertEquals(requestBody.getCondition(), "тест-тест-тест");
        GeneratingResult result = new GeneratingResult("тест Text", "тест Code", "тест Instructions");
        assertEquals(requestBody.getPreGenerated().getText(), result.getText());
        assertEquals(requestBody.getPreGenerated().getCode(), result.getCode());
        assertEquals(requestBody.getPreGenerated().getInstructions(), result.getInstructions());
    }

    @Test
    public void testParseCheckValidRequestBody() {
        RlcpCheckRequestBody requestBody = null;
        try {
            requestBody = (RlcpCheckRequestBody) Rlcp.parseRequestBody(rlcpCheckRequestBody);
        } catch (BadRlcpBodyException e) {
            e.printStackTrace();
        }
        assertEquals(requestBody.getMethod().getName().toLowerCase(), "check");

        GeneratingResult result = new GeneratingResult("text", "code", "instructions");
        assertEquals(requestBody.getPreGenerated().getText(), result.getText());
        assertEquals(requestBody.getPreGenerated().getCode(), result.getCode());
        assertEquals(requestBody.getPreGenerated().getInstructions(), result.getInstructions());
        assertEquals(requestBody.getInstructions(), "тест");
        assertEquals(requestBody.getConditionsList().size(), 2);
    }

    @Test
    public void testParseGenerateValidRequestBody() {
        RlcpGenerateRequestBody requestBody = null;
        try {
            requestBody = (RlcpGenerateRequestBody) Rlcp.parseRequestBody(rlcpGenerateRequestBody);
        } catch (BadRlcpBodyException e) {
            e.printStackTrace();
        }
        assertEquals(requestBody.getMethod().getName().toLowerCase(), "generate");

        assertEquals(requestBody.getCondition(), "generating cond");
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckRequestBodyExceptionIfGiveGenerateRequest() throws BadRlcpBodyException {
        RlcpCheckRequestBody requestBody = (RlcpCheckRequestBody) Rlcp.parseRequestBody(rlcpGenerateRequestBody);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckRequestBodyExceptionIfGiveCalculateRequest() throws BadRlcpBodyException {
        RlcpCheckRequestBody requestBody = (RlcpCheckRequestBody) Rlcp.parseRequestBody(rlcpCalculateRequestBody);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateRequestBodyExceptionIfGiveCheckRequest() throws BadRlcpBodyException {
        RlcpCalculateRequestBody requestBody = (RlcpCalculateRequestBody) Rlcp.parseRequestBody(rlcpCheckRequestBody);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateRequestBodyExceptionIfGiveGenerateRequest() throws BadRlcpBodyException {
        RlcpCalculateRequestBody requestBody = (RlcpCalculateRequestBody) Rlcp.parseRequestBody(rlcpGenerateRequestBody);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateRequestBodyExceptionIfGiveCalculateRequest() throws BadRlcpBodyException {
        RlcpGenerateRequestBody requestBody = (RlcpGenerateRequestBody) Rlcp.parseRequestBody(rlcpCalculateRequestBody);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateRequestBodyExceptionIfGiveCheckRequest() throws BadRlcpBodyException {
        RlcpGenerateRequestBody requestBody = (RlcpGenerateRequestBody) Rlcp.parseRequestBody(rlcpCheckRequestBody);
    }

    @Test(expected = RlcpException.class)
    public void testParseGenerateBodyRequestExceptionBadHeader() throws BadRlcpBodyException {
        RlcpGenerateRequestBody requestBody = (RlcpGenerateRequestBody) Rlcp.parseRequestBody(rlcpGenerateRequestBody.replace("<Request>", "<>"));
    }

    @Test(expected = RlcpException.class)
    public void testParseCheckBodyRequestExceptionBadHeader() throws BadRlcpBodyException {
        RlcpCheckRequestBody requestBody = (RlcpCheckRequestBody) Rlcp.parseRequestBody(rlcpCheckRequestBody.replace("<Request>", "<>"));
    }

    @Test(expected = RlcpException.class)
    public void testParseCalculateBodyRequestExceptionBadHeader() throws BadRlcpBodyException {
        RlcpCalculateRequestBody requestBody = (RlcpCalculateRequestBody) Rlcp.parseRequestBody(rlcpCalculateRequestBody.replace("<Request>", "<>"));
    }

    @Test(expected = NullPointerException.class)
    public void testParseCheckRequestBodyExceptionNoBody() throws BadRlcpBodyException {
        RlcpCheckRequestBody request = (RlcpCheckRequestBody) Rlcp.parseRequestBody("");
    }

    @Test(expected = NullPointerException.class)
    public void testParseCalculateRequestBodyExceptionNoBody() throws BadRlcpBodyException {
        RlcpCalculateRequestBody request = (RlcpCalculateRequestBody) Rlcp.parseRequestBody("");
    }

    @Test(expected = NullPointerException.class)
    public void testParseGenerateRequestBodyExceptionNoBody() throws BadRlcpBodyException {
        RlcpGenerateRequestBody request = (RlcpGenerateRequestBody) Rlcp.parseRequestBody("");
    }

    //    ------------------------------------------ ParseResponse(String) ------------------------------------------
    @Test
    public void testParseCalculateValidResponse() {
        RlcpCalculateResponse response = null;
        try {
            response = (RlcpCalculateResponse) Rlcp.parseResponse(rlcpCalculateResponse);
        } catch (RlcpException e) {
            e.printStackTrace();
        }
        assertEquals(response.getMethod().getName().toLowerCase(), "calculate");
        assertEquals(response.getHeader().getContentLength(), 256);

        assertTrue(response.getBody().getDocument().asXML().contains("<Text><!--text--></Text>"));
        assertTrue(response.getBody().getDocument().asXML().contains("<Code><!--code--></Code>"));
    }

    @Test
    public void testParseCheckValidResponse() {
        RlcpCheckResponse response = null;
        try {
            response = (RlcpCheckResponse) Rlcp.parseResponse(rlcpCheckResponse);
        } catch (RlcpException e) {
            e.printStackTrace();
        }
        assertEquals(response.getMethod().getName().toLowerCase(), "check");
        assertEquals(response.getHeader().getContentLength(), 297);

        assertTrue(response.getBody().getDocument().asXML().contains("<CheckingResult id=\"1\" Time=\"50\" Result=\"1.00\"><!--ok--></CheckingResult>"));
        assertTrue(response.getBody().getDocument().asXML().contains("<CheckingResult id=\"2\" Time=\"50\" Result=\"1.00\"><!--ok--></CheckingResult>"));
    }

    @Test
    public void testParseGenerateValidResponse() {
        RlcpGenerateResponse response = null;
        try {
            response = (RlcpGenerateResponse) Rlcp.parseResponse(rlcpGenerateResponse);
        } catch (RlcpException e) {
            e.printStackTrace();
        }
        assertEquals(response.getMethod().getName().toLowerCase(), "generate");
        assertEquals(response.getHeader().getContentLength(), 319);
        assertEquals(response.getHeader().getResponseCode(), "200");

        assertTrue(response.getBody().getDocument().asXML().contains("<Text><!--text--></Text>"));
        assertTrue(response.getBody().getDocument().asXML().contains("<Code><!--code--></Code>"));
        assertTrue(response.getBody().getDocument().asXML().contains("<Instructions><!--instructions--></Instructions>"));
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckResponseExceptionIfGiveGenerateResponse() throws BadRlcpResponseException {
        RlcpCheckResponse response = (RlcpCheckResponse) Rlcp.parseResponse(rlcpGenerateResponse);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckResponseExceptionIfGiveCalculateResponse() throws BadRlcpResponseException {
        RlcpCheckResponse response = (RlcpCheckResponse) Rlcp.parseResponse(rlcpCalculateResponse);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateResponseExceptionIfGiveGenerateResponse() throws BadRlcpResponseException {
        RlcpCalculateResponse response = (RlcpCalculateResponse) Rlcp.parseResponse(rlcpGenerateResponse);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateResponseExceptionIfGiveCheckResponse() throws BadRlcpResponseException {
        RlcpCalculateResponse response = (RlcpCalculateResponse) Rlcp.parseResponse(rlcpCheckResponse);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateResponseExceptionIfGiveCheckResponse() throws BadRlcpResponseException {
        RlcpGenerateResponse response = (RlcpGenerateResponse) Rlcp.parseResponse(rlcpCheckResponse);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateResponseExceptionIfGiveCalculateResponse() throws BadRlcpResponseException {
        RlcpGenerateResponse response = (RlcpGenerateResponse) Rlcp.parseResponse(rlcpCalculateResponse);
    }

    @Test(expected = RlcpException.class)
    public void testParseGenerateResponseExceptionBadBody() throws BadRlcpResponseException {
        RlcpGenerateResponse response = (RlcpGenerateResponse) Rlcp.parseResponse(rlcpGenerateResponse.replace("<Response>", "<>"));
    }

    @Test(expected = RlcpException.class)
    public void testParseCheckResponseExceptionBadBody() throws BadRlcpResponseException {
        RlcpCheckResponse response = (RlcpCheckResponse) Rlcp.parseResponse(rlcpCheckResponse.replace("<Response>", "<>"));
    }

    @Test(expected = RlcpException.class)
    public void testParseCalculateResponseExceptionBadBody() throws BadRlcpResponseException {
        RlcpCalculateResponse response = (RlcpCalculateResponse) Rlcp.parseResponse(rlcpCalculateResponse.replace("<Response>", "<>"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseCheckRsponseExceptionNoHeader() throws BadRlcpResponseException {
        RlcpCheckResponse response = (RlcpCheckResponse) Rlcp.parseResponse(rlcpCheckResponseBody);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseCalculateRsponseExceptionNoHeader() throws BadRlcpResponseException {
        RlcpCalculateResponse response = (RlcpCalculateResponse) Rlcp.parseResponse(rlcpCalculateResponseBody);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseGenerateRsponseExceptionNoHeader() throws BadRlcpResponseException {
        RlcpGenerateResponse response = (RlcpGenerateResponse) Rlcp.parseResponse(rlcpGenerateResponseBody);
    }

    @Test(expected = NullPointerException.class)
    public void testParseCheckResponseExceptionNoBody() throws BadRlcpResponseException {
        RlcpCheckResponse response = (RlcpCheckResponse) Rlcp.parseResponse(rlcpCheckResponse.replace(rlcpCheckResponseBody, ""));
    }

    @Test(expected = NullPointerException.class)
    public void testParseGenerateResponseExceptionNoBody() throws BadRlcpResponseException {
        RlcpGenerateResponse response = (RlcpGenerateResponse) Rlcp.parseResponse(rlcpGenerateResponse.replace(rlcpGenerateResponseBody, ""));
    }

    @Test(expected = NullPointerException.class)
    public void testParseCalculateResponseExceptionNoBody() throws BadRlcpResponseException {
        RlcpCalculateResponse response = (RlcpCalculateResponse) Rlcp.parseResponse(rlcpCalculateResponse.replace(rlcpCalculateResponseBody, ""));
    }

    //    ------------------------------------------ ParseResponseBody(String) ------------------------------------------
    @Test
    public void testParseCalculateValidResponseBody() {
        RlcpCalculateResponseBody responseBody = null;
        try {
            responseBody = (RlcpCalculateResponseBody) Rlcp.parseResponseBody(rlcpCalculateResponseBody);
        } catch (RlcpException e) {
            e.printStackTrace();
        }
        assertEquals(responseBody.getMethod().getName().toLowerCase(), "calculate");
        assertEquals(responseBody.getContentLength(), 256);

        assertTrue(responseBody.getDocument().asXML().contains("<Text><!--text--></Text>"));
        assertTrue(responseBody.getDocument().asXML().contains("<Code><!--code--></Code>"));
    }

    @Test
    public void testParseCheckValidResponseBody() {
        RlcpCheckResponseBody responseBody = null;
        try {
            responseBody = (RlcpCheckResponseBody) Rlcp.parseResponseBody(rlcpCheckResponseBody);
        } catch (RlcpException e) {
            e.printStackTrace();
        }
        assertEquals(responseBody.getMethod().getName().toLowerCase(), "check");
        assertEquals(responseBody.getContentLength(), 297);

        assertTrue(responseBody.getDocument().asXML().contains("<CheckingResult id=\"1\" Time=\"50\" Result=\"1.00\"><!--ok--></CheckingResult>"));
        assertTrue(responseBody.getDocument().asXML().contains("<CheckingResult id=\"2\" Time=\"50\" Result=\"1.00\"><!--ok--></CheckingResult>"));
    }

    @Test
    public void testParseGenerateValidResponseBody() {
        RlcpGenerateResponseBody responseBody = null;
        try {
            responseBody = (RlcpGenerateResponseBody) Rlcp.parseResponseBody(rlcpGenerateResponseBody);
        } catch (RlcpException e) {
            e.printStackTrace();
        }
        assertEquals(responseBody.getMethod().getName().toLowerCase(), "generate");
        assertEquals(responseBody.getContentLength(), 319);

        assertTrue(responseBody.getDocument().asXML().contains("<Text><!--text--></Text>"));
        assertTrue(responseBody.getDocument().asXML().contains("<Code><!--code--></Code>"));
        assertTrue(responseBody.getDocument().asXML().contains("<Instructions><!--instructions--></Instructions>"));
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckResponseBodyExceptionIfGiveGenerateResponseBody() throws BadRlcpBodyException {
        RlcpCheckResponseBody responseBody = (RlcpCheckResponseBody) Rlcp.parseResponseBody(rlcpGenerateResponseBody);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCheckResponseBodyExceptionIfGiveCalculateResponseBody() throws BadRlcpBodyException {
        RlcpCheckResponseBody responseBody = (RlcpCheckResponseBody) Rlcp.parseResponseBody(rlcpCalculateResponseBody);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateResponseBodyExceptionIfGiveGenerateResponseBody() throws BadRlcpBodyException {
        RlcpCalculateResponseBody responseBody = (RlcpCalculateResponseBody) Rlcp.parseResponseBody(rlcpGenerateResponseBody);
    }

    @Test(expected = ClassCastException.class)
    public void testParseCalculateResponseBodyExceptionIfGiveCheckResponseBody() throws BadRlcpBodyException {
        RlcpCalculateResponseBody responseBody = (RlcpCalculateResponseBody) Rlcp.parseResponseBody(rlcpCheckResponseBody);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateResponseBodyExceptionIfGiveCheckResponseBody() throws BadRlcpBodyException {
        RlcpGenerateResponseBody responseBody = (RlcpGenerateResponseBody) Rlcp.parseResponseBody(rlcpCheckResponseBody);
    }

    @Test(expected = ClassCastException.class)
    public void testParseGenerateResponseBodyExceptionIfGiveCalculateResponseBody() throws BadRlcpBodyException {
        RlcpGenerateResponseBody responseBody = (RlcpGenerateResponseBody) Rlcp.parseResponseBody(rlcpCalculateResponseBody);
    }

    @Test(expected = RlcpException.class)
    public void testParseGenerateResponseBodyExceptionBadBody() throws BadRlcpBodyException {
        RlcpGenerateResponseBody responseBody = (RlcpGenerateResponseBody) Rlcp.parseResponseBody(rlcpGenerateResponseBody.replace("<Response>", "<>"));
    }

    @Test(expected = RlcpException.class)
    public void testParseCheckResponseBodyExceptionBadBody() throws BadRlcpBodyException {
        RlcpCheckResponseBody responseBody = (RlcpCheckResponseBody) Rlcp.parseResponseBody(rlcpCheckResponseBody.replace("<Response>", "<>"));
    }

    @Test(expected = RlcpException.class)
    public void testParseCalculateResponseBodyExceptionBadBody() throws BadRlcpBodyException {
        RlcpCalculateResponseBody responseBody = (RlcpCalculateResponseBody) Rlcp.parseResponseBody(rlcpCalculateResponseBody.replace("<Response>", "<>"));
    }

    @Test(expected = NullPointerException.class)
    public void testParseCheckResponseBodyExceptionNoBody() throws BadRlcpBodyException {
        RlcpCheckResponseBody responseBody = (RlcpCheckResponseBody) Rlcp.parseResponseBody("");
    }

    @Test(expected = NullPointerException.class)
    public void testParseCalculateResponseBodyExceptionNoBody() throws BadRlcpBodyException {
        RlcpCalculateResponseBody responseBody = (RlcpCalculateResponseBody) Rlcp.parseResponseBody("");
    }

    @Test(expected = NullPointerException.class)
    public void testParseGenerateResponseBodyExceptionNoBody() throws BadRlcpBodyException {
        RlcpGenerateResponseBody responseBody = (RlcpGenerateResponseBody) Rlcp.parseResponseBody("");
    }
}
