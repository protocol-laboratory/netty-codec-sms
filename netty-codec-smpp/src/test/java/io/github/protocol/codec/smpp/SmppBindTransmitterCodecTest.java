package io.github.protocol.codec.smpp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SmppBindTransmitterCodecTest {

    private final SmppDecoder decoder = new SmppDecoder();

    @Test
    public void case1() {
        SmppHeader header = new SmppHeader(SmppConst.BIND_TRANSMITTER_ID, 0);
        SmppBindTransmitterBody body = new SmppBindTransmitterBody("systemId", "password",
                "systemType", (byte) 1, (byte) 2, (byte) 3, "addressRange");
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf byteBuf = SmppEncoder.INSTANCE.doEncode(ctx, new SmppBindTransmitter(header, body));
        SmppBindTransmitter bindTransmitter = (SmppBindTransmitter) decoder.decode(byteBuf);
        Assertions.assertEquals("systemId", bindTransmitter.body().systemId());
        Assertions.assertEquals("password", bindTransmitter.body().password());
        Assertions.assertEquals("systemType", bindTransmitter.body().systemType());
        Assertions.assertEquals((byte) 1, bindTransmitter.body().interfaceVersion());
        Assertions.assertEquals((byte) 2, bindTransmitter.body().addrTon());
        Assertions.assertEquals((byte) 3, bindTransmitter.body().addrNpi());
        Assertions.assertEquals("addressRange", bindTransmitter.body().addressRange());
    }

}
