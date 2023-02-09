package io.github.protocol.codec.cmpp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CmppActiveTestTest {

    private final CmppDecoder decoder = new CmppDecoder();

    @Test
    public void case1() {
        CmppHeader header = new CmppHeader(CmppConst.ACTIVE_TEST_ID, 0);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf byteBuf = CmppEncoder.INSTANCE.doEncode(ctx, new CmppActiveTest(header));
        CmppActiveTest cmppActiveTest = (CmppActiveTest) decoder.decode(byteBuf);
        Assertions.assertEquals(0, byteBuf.readableBytes());
    }
}
