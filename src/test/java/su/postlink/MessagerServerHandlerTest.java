package su.postlink;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import su.postlink.protoc.Message;

/**
 * Created by aleksandr on 15.12.16.
 */
public class MessagerServerHandlerTest extends Assert {
    MessagerHandler handler;
    Message.Body.Builder msg;

    User user = new User(2, "nick2");
    ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);

    @Before
    public void setUp() throws Exception {
        handler = new MessagerHandler();
        msg = new Message.Body.Builder();
        msg.setIdTo(1).setNickNameTo("nick1");
        msg.setIdFrom(user.getId()).setNickNameFrom(user.getNickName());
        msg.setDate(System.currentTimeMillis()).setBody("Hello World");

    }

    private ChannelBuffer message() {
        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        channelBuffer.writeBytes(msg.build().toByteArray());
        return channelBuffer;
    }

    @Test
    public void channelRead() throws Exception {
        Object o = handler.decode(ctx, null, message(), null);
        assertEquals(msg.build(), o);
    }

}