package su.postlink;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import su.postlink.mock.LoggedUserMock;
import su.postlink.protoc.Message;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aleksandr on 15.12.16.
 */
public class ServerTest extends Assert {
    private Server server;
    Channel ch1 = Mockito.mock(Channel.class);
    Channel ch2 = Mockito.mock(Channel.class);
    ChannelFuture channelFuture = Mockito.mock(ChannelFuture.class);
    User user1 = new User(1, "name1");
    User user2 = new User(2, "name2");


    @Before
    public void setUp() throws Exception {
        server = new Server("127.0.0.1", 8888);
        Mockito.when(ch1.isOpen()).thenReturn(true).thenReturn(false);
        Mockito.when(ch1.write(new Object())).thenReturn(channelFuture);
        Mockito.when(ch2.isOpen()).thenReturn(true).thenReturn(false);
        Mockito.when(ch2.write(new Object())).thenReturn(channelFuture);
        server.registration(user1);
        server.registration(user2);

        server.login(user1, ch1);
        server.login(user2, ch2);
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
    public void testLoadUser() {
        try {
            ResultSet rs = LoggedUserMock.resultSet();
            while (rs.next()) {
                server.loadRegisterUser(rs);
            }
        } catch (SQLException e) {
        }
        assertEquals(6, server.countRegisterUsers());
        assertEquals(-1, server.getRegisterUser("nickName6").getId().intValue());
        assertEquals(6, server.getRegisterUser("name6").getId().intValue());
    }

    @Test
    public void testSendMessage() {
        Message.Body.Builder msg = new Message.Body.Builder();
        msg.setDate(System.currentTimeMillis());
        msg.setBody("hello");
        msg.setNickNameFrom(user1.getNickName());
        msg.setNickNameTo(user2.getNickName());

        assertTrue(server.send(msg.build()));
        assertFalse(server.send(msg.build()));
    }

    @Test
    public void testSendListUser(){
        Message.Body.Builder msg = new Message.Body.Builder();
        msg.setDate(System.currentTimeMillis());
        msg.setBody("hello");
        msg.setNickNameFrom(user1.getNickName());
        msg.setNickNameTo(user1.getNickName());

        assertTrue(server.sendListUser(msg.build()));
        assertFalse(server.sendListUser(msg.build()));
    }

}