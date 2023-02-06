package io.github.protocol.codec.smpp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;

public class SmppSubmitSmCodecTest {

    private final SmppDecoder decoder = new SmppDecoder();

    @Test
    public void case1() throws Exception {
        SmppHeader header = new SmppHeader(SmppConst.SUBMIT_SM_ID, 0);
        byte[] shortMessage = "hello".getBytes(StandardCharsets.UTF_8);
        SmppSubmitSmBody body = new SmppSubmitSmBody("type",
                (byte) 1, (byte) 2, "src",
                (byte) 3, (byte) 4, "dst",
                (byte) 4, (byte) 3, (byte) 2, "time",
                "period", (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) shortMessage.length,
                shortMessage);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf byteBuf = SmppEncoder.INSTANCE.doEncode(ctx, new SmppSubmitSm(header, body));
        SmppSubmitSm submitSm = (SmppSubmitSm) decoder.decode(byteBuf);
        Assertions.assertEquals("type", submitSm.body().serviceType());
        Assertions.assertEquals((byte) 1, submitSm.body().sourceAddrTon());
        Assertions.assertEquals((byte) 2, submitSm.body().sourceAddrNpi());
        Assertions.assertEquals("src", submitSm.body().sourceAddr());
        Assertions.assertEquals((byte) 3, submitSm.body().destAddrTon());
        Assertions.assertEquals((byte) 4, submitSm.body().destAddrNpi());
        Assertions.assertEquals("dst", submitSm.body().destinationAddr());
        Assertions.assertEquals((byte) 4, submitSm.body().esmClass());
        Assertions.assertEquals((byte) 3, submitSm.body().protocolId());
        Assertions.assertEquals((byte) 2, submitSm.body().priorityFlag());
        Assertions.assertEquals("time", submitSm.body().scheduleDeliveryTime());
        Assertions.assertEquals("period", submitSm.body().validityPeriod());
        Assertions.assertEquals((byte) 1, submitSm.body().registeredDelivery());
        Assertions.assertEquals((byte) 2, submitSm.body().replaceIfPresentFlag());
        Assertions.assertEquals((byte) 3, submitSm.body().dataCoding());
        Assertions.assertEquals((byte) 4, submitSm.body().smDefaultMsgId());
        Assertions.assertEquals((byte) shortMessage.length, submitSm.body().smLength());
    }

}
