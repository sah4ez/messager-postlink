package su.postlink;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;
import su.postlink.protoc.Message;

import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.util.concurrent.*;


/**
 * Created by aleksandr on 15.12.16.
 */
public class Server {

    private ConcurrentSkipListSet<User> allUser = new ConcurrentSkipListSet<>();
    private ConcurrentHashMap<User, Host> connected = new ConcurrentHashMap<>();

    private ExecutorService innerExec;
    private ExecutorService bossExec;
    private ExecutorService ioExec;
    private ServerBootstrap networkServer;

    private MessagerHandler handler = new MessagerHandler();
    private Host current = new Host("127.0.0.1", 8000);
    private Channel channel;

    public Server(Host host) {
        current = host;
        bossExec = new OrderedMemoryAwareThreadPoolExecutor(1,
                400000000,
                2000000000,
                60,
                TimeUnit.SECONDS);
        ioExec = new OrderedMemoryAwareThreadPoolExecutor(4,
                400000000,
                2000000000,
                60,
                TimeUnit.SECONDS);
        networkServer = new ServerBootstrap(new NioServerSocketChannelFactory(
                bossExec, ioExec, 4));
        innerExec = Executors.newFixedThreadPool(4);
    }

    public void run() throws Exception {
        networkServer.setOption("backlog", 500);
        networkServer.setOption("connectTimeoutMillis", 10000);
        networkServer.setPipelineFactory(new ServerPipelineFactory());
        channel = networkServer.bind(new InetSocketAddress(current.getHost(), current.getPort()));
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

    public void loadRegisterUser(ResultSet rs) {
        Runnable task = () -> {
            User user = new User(rs);
            allUser.add(user);
        };
        try {
            innerExec.submit(task).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public void send(Message.Body message) {

    }

    public int countRegisterUsers() {
        return allUser.size();
    }
}
