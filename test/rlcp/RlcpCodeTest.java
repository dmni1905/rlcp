package rlcp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.*;

public class RlcpCodeTest {
    
    public RlcpCodeTest() {
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
    public void testGetByCode() {
        System.out.println("getByCode");
        assertEquals(RlcpCode.Success, RlcpCode.getByCode("200"));
        assertNull(RlcpCode.getByCode("some"));
    }
}
