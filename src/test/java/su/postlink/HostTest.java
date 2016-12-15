package su.postlink;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by aleksandr on 15.12.16.
 */
public class HostTest extends Assert {
    private  Host host;

    @Before
    public void setUp() throws Exception {
        host = new Host("192.168.1.1", 8888);
    }

    @Test
    public void getHost() throws Exception {
        assertEquals("192.168.1.1", host.getHost());
    }

    @Test
    public void setHost() throws Exception {
        host.setHost("127.0.0.1");
        assertEquals("127.0.0.1", host.getHost());
    }

    @Test
    public void getPort() throws Exception {
        assertEquals(8888, host.getPort().intValue());
    }

    @Test
    public void setPort() throws Exception {
        host.setPort(9999);
        assertEquals(9999, host.getPort().intValue());
    }

}