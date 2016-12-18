package su.postlink.mock;

import io.netty.channel.Channel;
import org.mockito.Mockito;
import su.postlink.Server;
import su.postlink.User;
import su.postlink.protoc.Message;

/**
 * Created by aleksandr on 18.12.16.
 */
public class ServerMock {
    Server server = Mockito.mock(Server.class);
    User user = Mockito.mock(User.class);
    Channel channel = Mockito.mock(Channel.class);
    Message.Body msg = new Message.Body();
    boolean send = false;
    boolean list = false;

    public ServerMock(){

    }

    public Server getMock(){
        Mockito.when(server.registration(user)).thenReturn(1).thenReturn(2);
        Mockito.when(server.countRegisterUsers()).thenReturn(1).thenReturn(2);
        Mockito.when(server.login(user, channel)).thenReturn(true).thenReturn(true);
        Mockito.when(server.countLogginedUser()).thenReturn(1).thenReturn(2);
        return server;
    }

    public Server getMockSend(){
        msg = msg.toBuilder().
                setBody("hello").
                setDate(123123123).
                setNickNameFrom("nick").
                setNickNameTo("nick2").
                build();
        Mockito.when(server.send(msg)).thenReturn( send = !send);
        Mockito.when(server.sendListUser(msg)).thenReturn(list);
        return server;
    }

    public Server getMockList(){
        msg = msg.toBuilder().
                setBody("hello").
                setDate(123123123).
                setNickNameFrom("nick").
                setNickNameTo("nick").
                build();
        Mockito.when(server.send(msg)).thenReturn(send);
        Mockito.when(server.sendListUser(msg)).thenReturn(list = !list);
        return server;
    }

    public Message.Body getMsg() {
        return msg;
    }
}
