package rlcp.check;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import rlcp.RlcpParser;
import rlcp.exception.BadRlcpBodyException;
import rlcp.exception.BadRlcpRequestException;
import rlcp.exception.BadRlcpResponseException;
/**
 *
 * @author Jerome
 */
public class RlcpCheckResponseBodyTest {
    
    public RlcpCheckResponseBodyTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void test() throws BadRlcpBodyException {

        List<CheckingResult> list = new ArrayList<CheckingResult>();
        list.add(new CheckingResult(1, 2, "res", "out"));
       // RlcpCheckResponseBody.parse(new RlcpCheckResponseBody(list).toString());
    }
    
    @Test
    public void responseTest() throws BadRlcpBodyException, BadRlcpRequestException, BadRlcpResponseException {
        
        String src = "200\n"
                + "Content-Length:480\n"
                + "\n"
                + "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE Response SYSTEM \"http://de.ifmo.ru/--DTD/Response.dtd\"><Response><CheckingResult id=\"33251\" Time=\"0\" Result=\"1\"><!----></CheckingResult><CheckingResult id=\"33261\" Time=\"0\" Result=\"1\"><!----></CheckingResult><CheckingResult id=\"33271\" Time=\"0\" Result=\"0\"><!----></CheckingResult><CheckingResult id=\"33281\" Time=\"0\" Result=\"0\"><!----></CheckingResult><CheckingResult id=\"33291\" Time=\"0\" Result=\"0\"><!----></CheckingResult></Response>";

        new RlcpCheckParser().parseResponse(src);
        
        List<CheckingResult> list = new ArrayList<CheckingResult>();
        list.add(new CheckingResult(1, 2, "res", "out"));
       // RlcpCheckResponseBody.parse(new RlcpCheckResponseBody(list).toString());
    }
    
    
}
