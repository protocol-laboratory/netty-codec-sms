package io.github.protocol.codec.smpp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SmppUnbindTest {

    private final SmppDecoder decoder = new SmppDecoder();

    @Test
    public void case1() {
        SmppHeader header = new SmppHeader(SmppConst.UNBIND_ID, 0);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf buf = SmppEncoder.INSTANCE.doEncode(ctx, new SmppUnbind(header));
        SmppUnbind smppUnbind = (SmppUnbind) decoder.decode(buf);
        Assertions.assertEquals(0, smppUnbind.header().sequenceNumber());
        Assertions.assertEquals(0, buf.readableBytes());
    }
}
