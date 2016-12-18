package su.postlink;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import su.postlink.protoc.Command;
import su.postlink.protoc.Message;

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
        if (!(msg instanceof Message.Body)) return;

        Message.Body message = ((Message.Body) msg);
        if (!message.hasType()) return;
        switch (message.getType()) {
            case 1: {
                User user = new User(-1, message.getNickName());
                server.registration(user);
                lastCommand = Command.REG;
                break;
            }
            case 2: {
                User user = new User(-1, message.getNickName());
                server.login(user, ctx.channel());
                lastCommand = Command.LOGIN;
                break;
            }
            case 3: {
                if (message.getNickNameTo().equals(message.getNickNameFrom())) {
                    server.sendListUser(message);
                    lastCommand = Command.LIST;
                } else {
                    server.send(message);
                    lastCommand = Command.MSG;
                }
                break;
            }
            case 4: {
                break;
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    public Command getLastCommand() {
        return lastCommand;
    }
}
