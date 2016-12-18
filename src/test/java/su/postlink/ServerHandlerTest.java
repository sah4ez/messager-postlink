package su.postlink;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import su.postlink.mock.ServerMock;
import su.postlink.protoc.Command;
import su.postlink.protoc.Message;

/**
 * Created by aleksandr on 18.12.16.
 */
public class ServerHandlerTest extends Assert {
    ServerHandler handler;
    ServerMock mock = new ServerMock();
    Server server = mock.getMock();
    ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
    Channel channel = Mockito.mock(Channel.class);
    ChannelFuture future = Mockito.mock(ChannelFuture.class);

    @Before
    public void setUp() throws Exception {
        Mockito.when(ctx.channel()).thenReturn(channel);
        Mockito.when(channel.close()).thenReturn(future);
        handler = new ServerHandler(server);
    }

    @Test
    public void channelReadRegistration() throws Exception {
        Message.Body.Builder reg = new Message.Body.Builder();
        reg.setType(1);
        reg.setNickName("nick");
        assertTrue(reg.hasNickName());
        handler.channelRead(ctx, reg.build());
        assertEquals(Command.REG, handler.getLastCommand());
        assertEquals(1,server.countRegisterUsers());

        reg.setNickName("nick2");
        handler.channelRead(ctx, reg.build());
        assertEquals(Command.REG, handler.getLastCommand());
        assertEquals(2,server.countRegisterUsers());
    }

    @Test
    public void channelReadLogin() throws Exception {
        Message.Body.Builder login = new Message.Body.Builder();
        login.setType(2);
        login.setNickName("nick");
        assertTrue(login.hasNickName());
        handler.channelRead(ctx, login.build());
        assertEquals(Command.LOGIN, handler.getLastCommand());
        assertEquals(1, server.countLogginedUser());

        login.setNickName("nick2");
        handler.channelRead(ctx, login.build());
        assertEquals(Command.LOGIN, handler.getLastCommand());
        assertEquals(2, server.countLogginedUser());
    }

    @Test
    public void channelReadSendMessage() throws Exception{
        server = mock.getMockSend();
        assertTrue(mock.getMsg().hasBody());
        assertTrue(mock.getMsg().hasDate());
        assertTrue(mock.getMsg().hasNickNameFrom());
        assertTrue(mock.getMsg().hasNickNameTo());
        handler.channelRead(ctx, mock.getMsg());
        assertEquals(Command.MSG, handler.getLastCommand());
    }

    @Test
    public void channelReadListUserMessage() throws Exception{
        server = mock.getMockList();
        assertTrue(mock.getMsg().hasBody());
        assertTrue(mock.getMsg().hasDate());
        assertTrue(mock.getMsg().hasNickNameFrom());
        assertTrue(mock.getMsg().hasNickNameTo());
        handler.channelRead(ctx, mock.getMsg());
        assertEquals(Command.LIST, handler.getLastCommand());
    }
}