package io.github.protocol.codec.smpp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SmppBindTransmitterRespCodecTest {

    private final SmppDecoder decoder = new SmppDecoder();

    @Test
    public void case1() {
        SmppHeader header = new SmppHeader(SmppConst.BIND_TRANSMITTER_RESP_ID, 0);
        SmppBindTransmitterRespBody body = new SmppBindTransmitterRespBody("systemId");
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf byteBuf = SmppEncoder.INSTANCE.doEncode(ctx, new SmppBindTransmitterResp(header, body));
        SmppBindTransmitterResp bindTransmitterResp = (SmppBindTransmitterResp) decoder.decode(byteBuf);
        Assertions.assertEquals("systemId", bindTransmitterResp.body().systemId());
    }
}
