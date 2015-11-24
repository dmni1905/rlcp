package rlcp.check;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;

/**
 *
 * @author Jerome
 */
public class RlcpCheckRequestBodyTest {
    
    public RlcpCheckRequestBodyTest() {
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
    public void test() throws Exception {
       List<ConditionForChecking> list = new ArrayList<ConditionForChecking>();
       list.add(new ConditionForChecking(1, 1111, "", ""));
       //RlcpCheckRequestBody.parse(new RlcpCheckRequestBody(list, "instr", new PreGenerated("", "", "")).toString()).getDocument();
    }
}
