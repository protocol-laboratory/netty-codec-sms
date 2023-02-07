package io.github.protocol.codec.cmpp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CmppConnectCodecTest {

    private final CmppDecoder decoder = new CmppDecoder();

    @Test
    public void case1() {
        CmppHeader header = new CmppHeader(CmppConst.CONNECT_ID, 0);
        CmppConnectBody body = new CmppConnectBody("source", "password",
                (byte) 0, 1122334455);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf byteBuf = CmppEncoder.INSTANCE.doEncode(ctx, new CmppConnect(header, body));
        CmppConnect bindTransmitter = (CmppConnect) decoder.decode(byteBuf);
        Assertions.assertEquals("source", bindTransmitter.body().sourceAddr());
        Assertions.assertEquals("password", bindTransmitter.body().authenticatorSource());
        Assertions.assertEquals((byte) 0, bindTransmitter.body().version());
        Assertions.assertEquals(1122334455, bindTransmitter.body().timestamp());
    }
}
