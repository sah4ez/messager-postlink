package su.postlink.client;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import su.postlink.protoc.Registration;

/**
 * Created by aleksandr on 17.12.16.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

        Registration.Body.Builder msg = new Registration.Body.Builder();

        /**
         * Creates a client-side handler.
         */
        public ClientHandler() {
            msg.setNickName("nick");
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
//            for (int i = 0; i < 200; i++) {
//                msg.setNickName("nick" + i);
                ctx.writeAndFlush(msg.build());
//            }

        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
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
