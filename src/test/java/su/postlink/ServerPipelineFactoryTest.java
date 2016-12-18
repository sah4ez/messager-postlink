package su.postlink;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import su.postlink.protoc.Message;

/**
 * Created by aleksandr on 18.12.16.
 */
public class ServerPipelineFactoryTest extends Assert {
    Server server = Mockito.mock(Server.class);
    ServerPipelineFactory pipelineFactory;
    Channel channel = Mockito.mock(Channel.class);
    ChannelPipeline pipeline = Mockito.mock(ChannelPipeline.class);

    @Before
    public void setUp() throws Exception {
        pipelineFactory = new ServerPipelineFactory(server);
        Mockito.when(channel.pipeline()).thenReturn(pipeline);
        Mockito.when(pipeline.get("frameDecoder")).thenReturn(new ProtobufVarint32FrameDecoder());
        Mockito.when(pipeline.get("protobufDecoderMessage")).
                thenReturn(new ProtobufDecoder(Message.Body.getDefaultInstance()));
        Mockito.when(pipeline.get("frameEncoder")).thenReturn(new ProtobufVarint32LengthFieldPrepender());
        Mockito.when(pipeline.get("protobufEncoder")).thenReturn(new ProtobufEncoder());
        Mockito.when(pipeline.get("myHandler")).thenReturn(new ServerHandler(null));
    }

    @Test
    public void initChannelTest() throws Exception {
        pipelineFactory.initChannel(channel);
        ChannelPipeline pipeline = pipelineFactory.getPipeline();
        assertEquals(ProtobufVarint32FrameDecoder.class, pipeline.get("frameDecoder").getClass());
        assertEquals(ProtobufDecoder.class, pipeline.get("protobufDecoderMessage").getClass());
        assertEquals(ProtobufVarint32LengthFieldPrepender.class, pipeline.get("frameEncoder").getClass());
        assertEquals(ProtobufEncoder.class, pipeline.get("protobufEncoder").getClass());
        assertEquals(ServerHandler.class, pipeline.get("myHandler").getClass());

    }

}