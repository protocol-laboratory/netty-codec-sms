package io.github.protocol.codec.smpp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;

public class SmppDeliverSmCodecTest {

    private final SmppDecoder decoder = new SmppDecoder();

    @Test
    public void caseLess127() {
        SmppHeader header = new SmppHeader(SmppConst.DELIVER_SM_ID, 0);
        byte[] shortMessage = "hello".getBytes(StandardCharsets.UTF_8);
        SmppDeliverSmBody body = new SmppDeliverSmBody("type",
                (byte) 1, (byte) 2, "src",
                (byte) 3, (byte) 4, "dst",
                (byte) 4, (byte) 3, (byte) 2, "time",
                "period", (byte) 1, (byte) 2, (byte) 3, (byte) 4, (short) shortMessage.length,
                shortMessage);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf byteBuf = SmppEncoder.INSTANCE.doEncode(ctx, new SmppDeliverSm(header, body));
        SmppDeliverSm smppDeliverSm = (SmppDeliverSm) decoder.decode(byteBuf);
        Assertions.assertEquals("type", smppDeliverSm.body().serviceType());
        Assertions.assertEquals((byte) 1, smppDeliverSm.body().sourceAddrTon());
        Assertions.assertEquals((byte) 2, smppDeliverSm.body().sourceAddrNpi());
        Assertions.assertEquals("src", smppDeliverSm.body().sourceAddr());
        Assertions.assertEquals((byte) 3, smppDeliverSm.body().destAddrTon());
        Assertions.assertEquals((byte) 4, smppDeliverSm.body().destAddrNpi());
        Assertions.assertEquals("dst", smppDeliverSm.body().destinationAddr());
        Assertions.assertEquals((byte) 4, smppDeliverSm.body().esmClass());
        Assertions.assertEquals((byte) 3, smppDeliverSm.body().protocolId());
        Assertions.assertEquals((byte) 2, smppDeliverSm.body().priorityFlag());
        Assertions.assertEquals("time", smppDeliverSm.body().scheduleDeliveryTime());
        Assertions.assertEquals("period", smppDeliverSm.body().validityPeriod());
        Assertions.assertEquals((byte) 1, smppDeliverSm.body().registeredDelivery());
        Assertions.assertEquals((byte) 2, smppDeliverSm.body().replaceIfPresentFlag());
        Assertions.assertEquals((byte) 3, smppDeliverSm.body().dataCoding());
        Assertions.assertEquals((byte) 4, smppDeliverSm.body().smDefaultMsgId());
        Assertions.assertEquals((short) shortMessage.length, smppDeliverSm.body().smLength());
        Assertions.assertArrayEquals(shortMessage, smppDeliverSm.body().shortMessage());
        Assertions.assertEquals(0, byteBuf.readableBytes());
    }

    @Test
    public void caseMessageLengthLessEquals254AndBiggerThan127() {
        SmppHeader header = new SmppHeader(SmppConst.DELIVER_SM_ID, 0);
        StringBuilder target = new StringBuilder();
        int length = 140 / 5;
        while (length-- > 0) {
            target.append("hello");
        }
        byte[] shortMessage = target.toString().getBytes(StandardCharsets.UTF_8);
        SmppDeliverSmBody body = new SmppDeliverSmBody("type",
                (byte) 1, (byte) 2, "src",
                (byte) 3, (byte) 4, "dst",
                (byte) 4, (byte) 3, (byte) 2, "time",
                "period", (byte) 1, (byte) 2, (byte) 3, (byte) 4, (short) shortMessage.length,
                shortMessage);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf byteBuf = SmppEncoder.INSTANCE.doEncode(ctx, new SmppDeliverSm(header, body));
        SmppDeliverSm smppDeliverSm = (SmppDeliverSm) decoder.decode(byteBuf);
        Assertions.assertEquals("type", smppDeliverSm.body().serviceType());
        Assertions.assertEquals((byte) 1, smppDeliverSm.body().sourceAddrTon());
        Assertions.assertEquals((byte) 2, smppDeliverSm.body().sourceAddrNpi());
        Assertions.assertEquals("src", smppDeliverSm.body().sourceAddr());
        Assertions.assertEquals((byte) 3, smppDeliverSm.body().destAddrTon());
        Assertions.assertEquals((byte) 4, smppDeliverSm.body().destAddrNpi());
        Assertions.assertEquals("dst", smppDeliverSm.body().destinationAddr());
        Assertions.assertEquals((byte) 4, smppDeliverSm.body().esmClass());
        Assertions.assertEquals((byte) 3, smppDeliverSm.body().protocolId());
        Assertions.assertEquals((byte) 2, smppDeliverSm.body().priorityFlag());
        Assertions.assertEquals("time", smppDeliverSm.body().scheduleDeliveryTime());
        Assertions.assertEquals("period", smppDeliverSm.body().validityPeriod());
        Assertions.assertEquals((byte) 1, smppDeliverSm.body().registeredDelivery());
        Assertions.assertEquals((byte) 2, smppDeliverSm.body().replaceIfPresentFlag());
        Assertions.assertEquals((byte) 3, smppDeliverSm.body().dataCoding());
        Assertions.assertEquals((byte) 4, smppDeliverSm.body().smDefaultMsgId());
        Assertions.assertEquals((short) shortMessage.length, smppDeliverSm.body().smLength());
        Assertions.assertArrayEquals(shortMessage, smppDeliverSm.body().shortMessage());
        Assertions.assertEquals(0, byteBuf.readableBytes());
    }

    @Test
    public void caseMessageLengthBiggerThan254() {
        StringBuilder target = new StringBuilder();
        int length = 255 / 5;
        while (length-- > 0) {
            target.append("hello");
        }
        byte[] shortMessage = target.toString().getBytes(StandardCharsets.UTF_8);
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SmppDeliverSmBody("type",
                (byte) 1, (byte) 2, "src",
                (byte) 3, (byte) 4, "dst",
                (byte) 4, (byte) 3, (byte) 2, "time",
                "period", (byte) 1, (byte) 2, (byte) 3, (byte) 4, (short) shortMessage.length,
                shortMessage));
    }

}
