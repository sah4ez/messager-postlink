package su.postlink;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;
import su.postlink.protoc.Registration;

/**
 * Created by aleksandr on 17.12.16.
 */
public class RegisterHandler extends ReplayingDecoder {
    Registration.Body reg;

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendUpstream(e);
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendUpstream(e);
    }

    @Override
    protected Object decode(ChannelHandlerContext channelHandlerContext,
                            Channel channel,
                            ChannelBuffer buffer,
                            Enum anEnum) throws Exception {
        int length = buffer.writerIndex();
        byte[] buffArr = new byte[length];
        for (int i = 0; i < length; ++i)
            buffArr[i] = buffer.getByte(i);

        reg = Registration.Body.parseFrom(buffArr);
        return reg;
    }
}
