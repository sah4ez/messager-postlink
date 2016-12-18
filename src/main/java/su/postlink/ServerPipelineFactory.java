package su.postlink;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import su.postlink.protoc.*;

/**
 * Created by aleksandr on 17.12.16.
 */
public class ServerPipelineFactory extends ChannelInitializer {

    private final Server server;
    private ChannelPipeline pipeline;

    public ServerPipelineFactory(Server server){
        this.server = server;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        pipeline = channel.pipeline();

        pipeline.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
        pipeline.addLast("protobufDecoderRegistration", new ProtobufDecoder(Registration.Body.getDefaultInstance()));
        pipeline.addLast("protobufDecoderLogin", new ProtobufDecoder(Login.Body.getDefaultInstance()));
        pipeline.addLast("protobufDecoderMessage", new ProtobufDecoder(Message.Body.getDefaultInstance()));
        pipeline.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast("protobufEncoder", new ProtobufEncoder());
        pipeline.addLast("myHandler", new ServerHandler(this.server));
    }

    protected ChannelPipeline getPipeline(){
        return this.pipeline;
    }
}
