package su.postlink;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import su.postlink.protoc.Registration;

/**
 * Created by aleksandr on 17.12.16.
 */
public class RegisterHandlerTest {
    RegisterHandler handler;
    Registration.Body.Builder msg;

    ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);

    @Before
    public void setUp() throws Exception {
        handler = new RegisterHandler();
        msg = new Registration.Body.Builder();
        msg.setNickName("nickName");
    }
    private ChannelBuffer reg() {
        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        channelBuffer.writeBytes(msg.build().toByteArray());
        return channelBuffer;
    }

    @Test
    public void testDecode() throws Exception {
        Object o = handler.decode(ctx,null, reg(), null);
        Assert.assertEquals(msg.build(), o);
    }

}