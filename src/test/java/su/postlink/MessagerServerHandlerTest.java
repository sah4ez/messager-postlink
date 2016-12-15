package su.postlink;

import io.netty.channel.ChannelHandlerContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import su.postlink.protoc.Message;

/**
 * Created by aleksandr on 15.12.16.
 */
public class MessagerServerHandlerTest {
    MessagerServerHandler handler;
    Message.Body.Builder msg;

    User user = new User(2, "nick2");
    ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);

    @Before
    public void setUp() throws Exception {
        handler = new MessagerServerHandler(null);
        msg = new Message.Body.Builder();
        msg.setIdTo(1).setNickNameTo("nick1");
        msg.setIdFrom(user.getId()).setNickNameFrom(user.getNickName());
        msg.setDate(System.currentTimeMillis()).setBody("Hello World");
        Mockito.when(ctx.write(0)).thenReturn(null);
        Mockito.when(ctx.flush()).thenReturn(null);
        Mockito.when(ctx.close()).thenReturn(null);

    }

    private Object message(){
        return msg.build().toByteArray();
    }

    @Test
    public void channelRead() throws Exception {
        handler.channelRead(ctx, message());
    }

    @Test
    public void channelReadComplete() throws Exception {
        handler.channelReadComplete(ctx);
    }

    @Test
    public void exceptionCaught() throws Exception {
        handler.exceptionCaught(ctx, new Exception());
    }
}