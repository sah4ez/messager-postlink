package su.postlink;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import su.postlink.protoc.Login;

/**
 * Created by aleksandr on 17.12.16.
 */
public class LoginHandlerTest {
    LoginHandler handler;
    Login.Body.Builder msg;

    ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
    @Before
    public void setUp() throws Exception {
        handler = new LoginHandler();
        msg = new Login.Body.Builder();
        Host host = new Host("127.0.0.1", 1245);
        msg.setNickName("nickName").setHost(host.getHost()).setPort(host.getPort());
    }
    private ChannelBuffer login() {
        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        channelBuffer.writeBytes(msg.build().toByteArray());
        return channelBuffer;
    }

    @Test
    public void decode() throws Exception {
        Object o = handler.decode(ctx, null, login(), null);
        Assert.assertEquals(msg.build(), o);

    }

}