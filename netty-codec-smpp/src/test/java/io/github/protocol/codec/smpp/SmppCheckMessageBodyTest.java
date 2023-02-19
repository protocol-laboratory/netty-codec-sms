package io.github.protocol.codec.smpp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SmppCheckMessageBodyTest {

    private final SmppDecoder decoder = new SmppDecoder();

    @Test
    public void case1() {
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeInt(30);
        buf.writeInt(SmppConst.SUBMIT_SM_RESP_ID);
        buf.writeInt(1);
        buf.writeInt(123456);
        SmppSubmitSmResp submitSmResp = (SmppSubmitSmResp) decoder.decode(buf);
        Assertions.assertNull(submitSmResp.body());
    }

}
