package io.github.protocol.codec.smpp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SmppReplaceSmTest {

    private final SmppDecoder decoder = new SmppDecoder();

    @Test
    public void case1() {
        SmppHeader header = new SmppHeader(SmppConst.REPLACE_SM_ID, 0);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        SmppReplaceSmBody smBody = new SmppReplaceSmBody("messageid", (byte) 1, (byte) 2,
                "sourceaddr", "", "", (byte) 3, (byte) 4, (byte) 5, "");
        ByteBuf buf = SmppEncoder.INSTANCE.doEncode(ctx, new SmppReplaceSm(header, smBody));
        SmppReplaceSm smppReplaceSm = (SmppReplaceSm) decoder.decode(buf);
        Assertions.assertEquals("messageid", smppReplaceSm.body().messageId());
        Assertions.assertEquals(3, smppReplaceSm.body().registeredDelivery());
        Assertions.assertEquals(0, buf.readableBytes());
    }
}
