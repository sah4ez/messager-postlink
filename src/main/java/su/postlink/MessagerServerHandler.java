package su.postlink;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sun.istack.internal.NotNull;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import su.postlink.protoc.Login;
import su.postlink.protoc.Message;
import su.postlink.protoc.Registration;

/**
 * Created by aleksandr on 15.12.16.
 */
public class MessagerServerHandler extends ChannelInboundHandlerAdapter {
    private Server server = null;

    public MessagerServerHandler(Server server) {
        this.server = server;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Write back as received
        message(((byte[]) msg));
        login(((byte[]) msg));
        registration(((byte[]) msg));
        ctx.write(msg);
    }

    private void message(byte[] msg) {
        try {
            Message.Body message = Message.Body.parseFrom(msg);
            if (server == null) return;
            server.send(message);
        } catch (InvalidProtocolBufferException e) {
        } catch (Exception ignored) {

        }
    }

    private void login(byte[] msg) {
        try {
            Login.Body message = Login.Body.parseFrom(msg);
        } catch (InvalidProtocolBufferException e) {
        } catch (Exception ignored) {

        }
    }

    private void registration(byte[] msg) {
        try {
            Registration.Body message = Registration.Body.parseFrom(msg);
        } catch (InvalidProtocolBufferException e) {
        } catch (Exception ignored) {

        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, @NotNull Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
