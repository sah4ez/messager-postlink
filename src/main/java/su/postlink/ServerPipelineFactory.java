package su.postlink;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * Created by aleksandr on 17.12.16.
 */
public class ServerPipelineFactory implements ChannelPipelineFactory {
    @Override
    public ChannelPipeline getPipeline() throws Exception {
        MessagerHandler message = new MessagerHandler();
        LoginHandler login = new LoginHandler();
        RegisterHandler register = new RegisterHandler();

        return Channels.pipeline(message);
    }
}
