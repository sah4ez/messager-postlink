package su.postlink;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import su.postlink.protoc.Command;
import su.postlink.protoc.Login;
import su.postlink.protoc.Message;
import su.postlink.protoc.Registration;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by aleksandr on 17.12.16.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    final Server server;
    private ExecutorService es = Executors.newFixedThreadPool(4);
    private Command lastCommand = null;
    private Future future = null;

    public ServerHandler(Server server) {
        this.server = server;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof Registration.Body) {
            future = es.submit(register(((Registration.Body) msg)));
            ctx.channel().closeFuture();
            return;
        }

        if (msg instanceof Login.Body) {
            future = es.submit(login(((Login.Body) msg), ctx.channel()));
            return;
        }

        if (msg instanceof Message.Body) {
            future = es.submit(message(((Message.Body) msg)));
            return;
        }
        ctx.channel().close();

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    private Runnable register(Registration.Body reg) {
        return () -> {
            User user = new User(-1, reg.getNickName());
            server.registration(user);
            lastCommand = Command.REG;
        };
    }

    private Runnable login(Login.Body login, Channel channel) {
        return () -> {
            User user = new User(-1, login.getNickName());
            server.login(user, channel);
            lastCommand = Command.LOGIN;
        };
    }

    private Runnable message(Message.Body msg) {
        return () -> {
            if (msg.getNickNameTo().equals(msg.getNickNameFrom())) {
                server.sendListUser(msg);
                lastCommand = Command.LIST;
            } else {
                server.send(msg);
                lastCommand = Command.MSG;
            }
        };
    }

    public Command getLastCommand() {
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return lastCommand;
    }
}
