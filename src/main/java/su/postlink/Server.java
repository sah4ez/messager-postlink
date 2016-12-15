package su.postlink;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import su.postlink.protoc.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by aleksandr on 15.12.16.
 */
public class Server {

    private ConcurrentSkipListSet<User> allUser = new ConcurrentSkipListSet<>();
    private ConcurrentHashMap<User, Host> connected = new ConcurrentHashMap<>();
    private AtomicInteger countUsers = new AtomicInteger(0);
    private ForkJoinPool es = ForkJoinPool.commonPool();


    private MessagerServerHandler handler = new MessagerServerHandler(this);

    private Host current = new Host("127.0.0.1", 8000);

    private LocalAddress addr = new LocalAddress(current.getPort().toString());

    public Server(Host host){
        current = host;
    }

    public void run() throws Exception{
        EventLoopGroup serverGroup = new DefaultEventLoop();
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(serverGroup)
                    .channel(LocalServerChannel.class)
                    .handler(new ChannelInitializer<LocalServerChannel>() {
                        @Override
                        public void initChannel(LocalServerChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                        }
                    })
                    .childHandler(new ChannelInitializer<LocalChannel>() {
                        @Override
                        public void initChannel(LocalChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new LoggingHandler(LogLevel.INFO),
                                    handler);
                        }
                    });

            Bootstrap cb = new Bootstrap();
            cb.group(clientGroup)
                    .channel(LocalChannel.class)
                    .handler(new ChannelInitializer<LocalChannel>() {
                        @Override
                        public void initChannel(LocalChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new LoggingHandler(LogLevel.INFO),
                                    handler);
                        }
                    });

            // Start the server.
            sb.bind(addr).sync();

            // Start the client.
            Channel ch = cb.connect(addr).sync().channel();

            // Read commands from the stdin.
            System.out.println("Enter text (quit to end)");
            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                String line = in.readLine();
                if (line == null || "quit".equalsIgnoreCase(line)) {
                    break;
                }

                // Sends the received line to the server.
                lastWriteFuture = ch.writeAndFlush(line);
            }

            // Wait until all messages are flushed before closing the channel.
            if (lastWriteFuture != null) {
                lastWriteFuture.awaitUninterruptibly();
            }
        } finally {
            serverGroup.shutdownGracefully();
            clientGroup.shutdownGracefully();
        }
    }

    public boolean login() {

        return false;
    }

    public boolean register() {

        return false;
    }

    public ConcurrentSkipListSet<User> getAllUser() {
        return allUser;
    }

    public ConcurrentHashMap<User, Host> getConnected() {
        return connected;
    }

    public Host getCurrent() {
        return current;
    }

    public void loadRegisterUser(ResultSet rs){
        RecursiveAction task = new RecursiveAction() {
            @Override
            public void compute() {
                User user = new User(rs);
                allUser.add(user);
                countUsers.incrementAndGet();
            }
        };
        es.invoke(task);
        task.fork();
        task.join();
//        return task.join();
    }

    public void send(Message.Body message) {
        RecursiveAction task = new RecursiveAction() {
            @Override
            protected void compute() {

            }
        };

    }

    public int countUsers(){
        return countUsers.get();
    }
}
