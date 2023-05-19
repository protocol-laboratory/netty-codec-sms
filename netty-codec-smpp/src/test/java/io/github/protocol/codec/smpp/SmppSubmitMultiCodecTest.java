package io.github.protocol.codec.smpp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SmppSubmitMultiCodecTest {

    private final SmppDecoder decoder = new SmppDecoder();

    @Test
    public void case1() throws Exception {
        SmppHeader header = new SmppHeader(SmppConst.SUBMIT_MULTI_ID, 0);
        byte[] shortMessage = "hello".getBytes(StandardCharsets.UTF_8);
        List<DestAddress> destAddresses = new ArrayList<>();
        destAddresses.add(new DestAddress((byte) 2, (byte) 3, "dest"));
        SmppSubmitMultiBody body = new SmppSubmitMultiBody("type",
                (byte) 1, (byte) 2, "src",
                (byte) 1, destAddresses, (byte) 4,
                (byte) 1, (byte) 2, "time", "period",
                (byte) 1, (byte) 2, (byte) 3, (byte) 4, (short) shortMessage.length,
                shortMessage);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf byteBuf = SmppEncoder.INSTANCE.doEncode(ctx, new SmppSubmitMulti(header, body));
        SmppSubmitMulti smppSubmitMuti = (SmppSubmitMulti) decoder.decode(byteBuf);
        Assertions.assertEquals("type", smppSubmitMuti.body().serviceType());
        Assertions.assertEquals((byte) 1, smppSubmitMuti.body().sourceAddrTon());
        Assertions.assertEquals((byte) 2, smppSubmitMuti.body().sourceAddrNpi());
        Assertions.assertEquals("src", smppSubmitMuti.body().sourceAddr());
        Assertions.assertEquals((byte) 1, smppSubmitMuti.body().numberOfDests());
        Assertions.assertEquals((byte) 2, smppSubmitMuti.body().destAddresses().get(0).destAddrTon());
        Assertions.assertEquals((byte) 3, smppSubmitMuti.body().destAddresses().get(0).destAddrNpi());
        Assertions.assertEquals("dest", smppSubmitMuti.body().destAddresses().get(0).destinationAddr());
        Assertions.assertEquals((byte) 4, smppSubmitMuti.body().esmClass());
        Assertions.assertEquals((byte) 1, smppSubmitMuti.body().protocolId());
        Assertions.assertEquals((byte) 2, smppSubmitMuti.body().priorityFlag());
        Assertions.assertEquals("time", smppSubmitMuti.body().scheduleDeliveryTime());
        Assertions.assertEquals("period", smppSubmitMuti.body().validityPeriod());
        Assertions.assertEquals((byte) 1, smppSubmitMuti.body().registeredDelivery());
        Assertions.assertEquals((byte) 2, smppSubmitMuti.body().replaceIfPresentFlag());
        Assertions.assertEquals((byte) 3, smppSubmitMuti.body().dataCoding());
        Assertions.assertEquals((byte) 4, smppSubmitMuti.body().smDefaultMsgId());
        Assertions.assertEquals((short) shortMessage.length, smppSubmitMuti.body().smLength());
        Assertions.assertArrayEquals(shortMessage, smppSubmitMuti.body().shortMessage());
        Assertions.assertEquals(0, byteBuf.readableBytes());
    }

    @Test
    public void caseMessageLengthLessEquals254AndBiggerThan127() {
        SmppHeader header = new SmppHeader(SmppConst.SUBMIT_MULTI_ID, 0);
        StringBuilder target = new StringBuilder();
        int length = 160 / 5;
        while (length-- > 0) {
            target.append("hello");
        }
        byte[] shortMessage = target.toString().getBytes(StandardCharsets.UTF_8);
        List<DestAddress> destAddresses = new ArrayList<>();
        destAddresses.add(new DestAddress((byte) 2, (byte) 3, "dest"));
        SmppSubmitMultiBody body = new SmppSubmitMultiBody("type",
                (byte) 1, (byte) 2, "src",
                (byte) 1, destAddresses, (byte) 4,
                (byte) 1, (byte) 2, "time", "period",
                (byte) 1, (byte) 2, (byte) 3, (byte) 4, (short) shortMessage.length,
                shortMessage);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf byteBuf = SmppEncoder.INSTANCE.doEncode(ctx, new SmppSubmitMulti(header, body));
        SmppSubmitMulti smppSubmitMuti = (SmppSubmitMulti) decoder.decode(byteBuf);
        Assertions.assertEquals("type", smppSubmitMuti.body().serviceType());
        Assertions.assertEquals((byte) 1, smppSubmitMuti.body().sourceAddrTon());
        Assertions.assertEquals((byte) 2, smppSubmitMuti.body().sourceAddrNpi());
        Assertions.assertEquals("src", smppSubmitMuti.body().sourceAddr());
        Assertions.assertEquals((byte) 1, smppSubmitMuti.body().numberOfDests());
        Assertions.assertEquals((byte) 2, smppSubmitMuti.body().destAddresses().get(0).destAddrTon());
        Assertions.assertEquals((byte) 3, smppSubmitMuti.body().destAddresses().get(0).destAddrNpi());
        Assertions.assertEquals("dest", smppSubmitMuti.body().destAddresses().get(0).destinationAddr());
        Assertions.assertEquals((byte) 4, smppSubmitMuti.body().esmClass());
        Assertions.assertEquals((byte) 1, smppSubmitMuti.body().protocolId());
        Assertions.assertEquals((byte) 2, smppSubmitMuti.body().priorityFlag());
        Assertions.assertEquals("time", smppSubmitMuti.body().scheduleDeliveryTime());
        Assertions.assertEquals("period", smppSubmitMuti.body().validityPeriod());
        Assertions.assertEquals((byte) 1, smppSubmitMuti.body().registeredDelivery());
        Assertions.assertEquals((byte) 2, smppSubmitMuti.body().replaceIfPresentFlag());
        Assertions.assertEquals((byte) 3, smppSubmitMuti.body().dataCoding());
        Assertions.assertEquals((byte) 4, smppSubmitMuti.body().smDefaultMsgId());
        Assertions.assertEquals((short) shortMessage.length, smppSubmitMuti.body().smLength());
        Assertions.assertArrayEquals(shortMessage, smppSubmitMuti.body().shortMessage());
        Assertions.assertEquals(0, byteBuf.readableBytes());
    }

    @Test
    public void caseMessageLengthBiggerThan254() {
        List<DestAddress> destAddresses = new ArrayList<>();
        destAddresses.add(new DestAddress((byte) 2, (byte) 3, "dest"));
        StringBuilder target = new StringBuilder();
        int length = 255 / 5;
        while (length-- > 0) {
            target.append("hello");
        }
        byte[] shortMessage = target.toString().getBytes(StandardCharsets.UTF_8);
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SmppSubmitMultiBody("type",
                (byte) 1, (byte) 2, "src",
                (byte) 1, destAddresses, (byte) 4,
                (byte) 1, (byte) 2, "time", "period",
                (byte) 1, (byte) 2, (byte) 3, (byte) 4, (short) shortMessage.length,
                shortMessage));
    }

}
