package io.github.protocol.codec.smpp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SmppSubmitSmRespCodecTest {

    private final SmppDecoder decoder = new SmppDecoder();

    @Test
    public void case1() throws Exception {
        SmppHeader header = new SmppHeader(SmppConst.SUBMIT_SM_RESP_ID, 0);
        SmppSubmitSmRespBody body = new SmppSubmitSmRespBody("messageId");
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf byteBuf = SmppEncoder.INSTANCE.doEncode(ctx, new SmppSubmitSmResp(header, body));
        SmppSubmitSmResp submitSmResp = (SmppSubmitSmResp) decoder.decode(byteBuf);
        Assertions.assertEquals("messageId", submitSmResp.body().messageId());
        Assertions.assertEquals(0, byteBuf.readableBytes());
    }
}
