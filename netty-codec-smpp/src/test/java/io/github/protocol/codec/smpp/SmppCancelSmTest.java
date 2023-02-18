package io.github.protocol.codec.smpp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SmppCancelSmTest {

    private final SmppDecoder decoder = new SmppDecoder();

    @Test
    public void case1() {
        SmppHeader header = new SmppHeader(SmppConst.CANCEL_SM_ID, 0);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        SmppCancelSmBody smBody = new SmppCancelSmBody("servicetype", "messageid", (byte) 1,
                (byte) 2, "sourceaddr", (byte) 3, (byte) 4, "destinationaddr");
        ByteBuf buf = SmppEncoder.INSTANCE.doEncode(ctx, new SmppCancelSm(header, smBody));
        SmppCancelSm smppCancelSm = (SmppCancelSm) decoder.decode(buf);
        Assertions.assertEquals("servicetype", smppCancelSm.body().serviceType());
        Assertions.assertEquals("destinationaddr", smppCancelSm.body().destinationAddr());
        Assertions.assertEquals(0, buf.readableBytes());
    }
}
