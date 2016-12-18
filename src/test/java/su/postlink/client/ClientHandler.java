package su.postlink.client;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import su.postlink.protoc.Message;

/**
 * Created by aleksandr on 17.12.16.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    Message.Body.Builder reg = new Message.Body.Builder();
    Message.Body.Builder login = new Message.Body.Builder();
    Message.Body.Builder msg = new Message.Body.Builder();

    /**
     * Creates a client-side handler.
     */
    public ClientHandler() {
        reg.setType(1).setNickName("nick");

        login.setType(2).setNickName("nick");

        msg.setType(3);
        msg.setNickNameFrom("nick");
        msg.setNickNameTo("nick");
        msg.setDate(1231231);
        msg.setBody("list");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(reg.build());
        ctx.writeAndFlush(login.build());
        ctx.writeAndFlush(msg.build());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(msg);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
