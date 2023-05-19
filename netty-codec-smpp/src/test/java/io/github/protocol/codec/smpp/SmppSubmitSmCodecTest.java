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
    public void caseLess127() {
        SmppHeader header = new SmppHeader(SmppConst.SUBMIT_SM_ID, 0);
        byte[] shortMessage = "hello".getBytes(StandardCharsets.UTF_8);
        SmppSubmitSmBody body = new SmppSubmitSmBody("type",
                (byte) 1, (byte) 2, "src",
                (byte) 3, (byte) 4, "dst",
                (byte) 4, (byte) 3, (byte) 2, "time",
                "period", (byte) 1, (byte) 2, (byte) 3, (byte) 4, (short) shortMessage.length,
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
        Assertions.assertEquals((short) shortMessage.length, submitSm.body().smLength());
        Assertions.assertArrayEquals(shortMessage, submitSm.body().shortMessage());
        Assertions.assertEquals(0, byteBuf.readableBytes());
    }

    @Test
    public void caseMessageLengthLessEquals254AndBiggerThan127() {
        SmppHeader header = new SmppHeader(SmppConst.SUBMIT_SM_ID, 0);
        StringBuilder target = new StringBuilder();
        int length = 140 / 5;
        while (length-- > 0) {
            target.append("hello");
        }
        byte[] shortMessage = target.toString().getBytes(StandardCharsets.UTF_8);
        SmppSubmitSmBody body = new SmppSubmitSmBody("type",
                (byte) 1, (byte) 2, "src",
                (byte) 3, (byte) 4, "dst",
                (byte) 4, (byte) 3, (byte) 2, "time",
                "period", (byte) 1, (byte) 2, (byte) 3, (byte) 4, (short) shortMessage.length,
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
        Assertions.assertEquals((short) shortMessage.length, submitSm.body().smLength());
        Assertions.assertArrayEquals(shortMessage, submitSm.body().shortMessage());
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SmppSubmitSmBody("type",
                (byte) 1, (byte) 2, "src",
                (byte) 3, (byte) 4, "dst",
                (byte) 4, (byte) 3, (byte) 2, "time",
                "period", (byte) 1, (byte) 2, (byte) 3, (byte) 4, (short) shortMessage.length,
                shortMessage));
    }

    @Test
    public void caseUsePayLoad() {
        SmppHeader header = new SmppHeader(SmppConst.SUBMIT_SM_ID, 0);
        StringBuilder target = new StringBuilder();
        int length = 255 / 5;
        while (length-- > 0) {
            target.append("hello");
        }
        byte[] shortMessage = target.toString().getBytes(StandardCharsets.UTF_8);
        TagLengthValue messagePayLoad =
                new TagLengthValue(SmppConst.TAG_MESSAGE_PAYLOAD, shortMessage.length, shortMessage);
        SmppSubmitSmBody body = new SmppSubmitSmBody("type",
                (byte) 1, (byte) 2, "src",
                (byte) 3, (byte) 4, "dst",
                (byte) 4, (byte) 3, (byte) 2, "time",
                "period", (byte) 1, (byte) 2, (byte) 3, (byte) 4, messagePayLoad);
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
        Assertions.assertEquals(submitSm.body().messagePayload().value().length,
                submitSm.body().messagePayload().length());
        Assertions.assertArrayEquals(shortMessage, submitSm.body().messagePayload().value());
        Assertions.assertEquals(0, byteBuf.readableBytes());
    }

    @Test
    public void caseMessageLengthBiggerThan64k() {
        StringBuilder target = new StringBuilder();
        int length = SmppConst.UNSIGNED_SHORT_MAX / 5 + 1;
        while (length-- > 0) {
            target.append("hello");
        }
        byte[] shortMessage = target.toString().getBytes(StandardCharsets.UTF_8);
        TagLengthValue messagePayLoad =
                new TagLengthValue(SmppConst.TAG_MESSAGE_PAYLOAD, shortMessage.length, shortMessage);
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SmppSubmitSmBody("type",
                (byte) 1, (byte) 2, "src",
                (byte) 3, (byte) 4, "dst",
                (byte) 4, (byte) 3, (byte) 2, "time",
                "period", (byte) 1, (byte) 2, (byte) 3, (byte) 4, messagePayLoad));
    }

}
