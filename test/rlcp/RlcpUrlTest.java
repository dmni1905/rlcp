package rlcp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.*;
import rlcp.exception.BadRlcpUrlException;


public class RlcpUrlTest {
    
    public RlcpUrlTest() {
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
        System.out.println("RlcpUrl");
        String raw = "flow://vera:Rt5612@127.0.0.1:1732";
        RlcpUrl url = RlcpUrl.parse(raw);
        assertEquals(url.getHost(),"127.0.0.1");
        assertEquals(url.getPort(),"1732");
        assertEquals(url.getLogin(),"vera");
        assertEquals(url.getPassword(),"Rt5612");
        assertEquals(raw, url.toString());
    }
    
    @Test
    public void test2() throws Exception {
        System.out.println("RlcpUrl2");
        String raw = "flow://127.0.0.1:1732";
        RlcpUrl url = RlcpUrl.parse(raw);
        assertEquals(url.getHost(),"127.0.0.1");
        assertEquals(url.getPort(),"1732");
        assertNull(url.getLogin());
        assertNull(url.getPassword());
        assertEquals(raw, url.toString());
    }
    
    @Test(expected=BadRlcpUrlException.class)
    public void test3() throws Exception {
        System.out.println("RlcpUrl2");
        String raw = "badraw";
        RlcpUrl url = RlcpUrl.parse(raw);
    }
    
    @Test(expected=BadRlcpUrlException.class)
    public void test4() throws Exception {
        System.out.println("RlcpUrl2");
        String raw = "flow://badraw";
        RlcpUrl url = RlcpUrl.parse(raw);
    }

}
