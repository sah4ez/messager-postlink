package su.postlink;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import su.postlink.protoc.Message;

import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.util.concurrent.*;


/**
 * Created by aleksandr on 15.12.16.
 */
public class Server {

    private ConcurrentSkipListSet<User> allUser = new ConcurrentSkipListSet<>();
    private ConcurrentHashMap<User, Channel> connected = new ConcurrentHashMap<>();

    private ExecutorService innerExec = Executors.newFixedThreadPool(1);

    private InetSocketAddress host;

    {
        new InetSocketAddress("127.0.0.1", 8888);
    }

    public Server(String host, Integer port) {
        this.host = new InetSocketAddress(host, port);
    }

    public void run() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup(1);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ServerPipelineFactory(this));
            b.bind(new InetSocketAddress(host.getAddress(), host.getPort())).sync();
            System.out.println("Bind " + host.getAddress() + ":" + host.getPort());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ConcurrentSkipListSet<User> getAllUser() {
        return allUser;
    }

    public ConcurrentHashMap<User, Channel> getConnected() {
        return connected;
    }

    public void loadRegisterUser(ResultSet rs) {
        Runnable task = () -> {
            User user = new User(rs);
            allUser.add(user);
        };
        try {
            innerExec.submit(task).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public User getRegisterUser(String nickName) {
        return allUser.
                parallelStream().
                filter(user -> user.getNickName().equals(nickName)).
                findFirst().
                orElseGet(User::new);
    }

    public User getLoginUser(String nickName) {
        return connected.keySet().parallelStream().
                filter((user) -> user.getNickName().equals(nickName)).
                findFirst().orElseGet(User::new);
    }

    public int countRegisterUsers() {
        return allUser.size();
    }

    public int countLogginedUser() {
        return connected.size();
    }

    public int registration(User user) {
        if (user == null) return allUser.size();
        allUser.add(user);
        return allUser.size();
    }

    public boolean login(User user, Channel channel) {
        if (user == null || channel == null) return false;

        if (allUser.contains(user)) {
            connected.put(user, channel);
            return true;
        }
        return false;
    }

    public boolean send(Message.Body message) {
        if (!message.hasBody() ||
                !message.hasDate() ||
                !message.hasNickNameTo() ||
                !message.hasNickNameFrom()) return false;

        User toUser = getLoginUser(message.getNickNameTo());
        if ("".equals(toUser.getNickName())) return false;

        Channel channel = connected.get(toUser);

        if (channel.isOpen()) {
            synchronized (this) {
                channel.write(message);
            }
            return true;
        }
        return false;
    }

    public boolean sendListUser(Message.Body message) {
        if (!message.hasBody() ||
                !message.hasDate() ||
                !message.hasNickNameTo() ||
                !message.hasNickNameFrom()) return false;

        User fromUser = getLoginUser(message.getNickNameFrom());
        if ("".equals(fromUser.getNickName())) return false;

        Channel channel = connected.get(fromUser);

        if (channel.isOpen()) {
            allUser.forEach(user -> {
                Message.Body.Builder msg = new Message.Body.Builder();
                msg.setType(3);
                msg.setNickName(user.getNickName());
                synchronized (this) {
                    System.out.println(msg.build());
                    channel.write(msg.build());
                }
            });
            return true;
        }
        return false;
    }
}
