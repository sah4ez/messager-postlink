package su.postlink;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import su.postlink.mock.LoggedUserMock;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aleksandr on 15.12.16.
 */
public class ServerTest extends Assert {
    private Server server;
    private Host host = new Host("127.0.0.1", 8888);

    @Before
    public void setUp() throws Exception {
        server = new Server(host);
    }

    @Test
    public void login() throws Exception {
        assertFalse(server.login());
    }

    @Test
    public void register() throws Exception {
        assertFalse(server.register());
    }

    @Test
    public void getAllUser() throws Exception {
        assertNotNull(server.getAllUser());
    }

    @Test
    public void getConnected() throws Exception {
        assertNotNull(server.getConnected());
    }

    @Test
    public void getCurrent() throws Exception {
        assertEquals("127.0.0.1", server.getCurrent().getHost());
        assertEquals(8888, server.getCurrent().getPort().intValue());
    }

    @Test
    public void testLoadUser() {
        try {
            ResultSet rs = LoggedUserMock.resultSet();
            while (rs.next()) {
                server.loadRegisterUser(rs);
            }
        } catch (SQLException e) {
        }
        assertEquals(6, server.countRegisterUsers());
    }

}