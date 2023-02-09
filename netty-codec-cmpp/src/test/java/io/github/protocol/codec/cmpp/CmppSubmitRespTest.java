/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.github.protocol.codec.cmpp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CmppSubmitRespTest {

    private final CmppDecoder decoder = new CmppDecoder();

    @Test
    public void case1() {
        CmppHeader header = new CmppHeader(CmppConst.SUBMIT_RESP_ID, 0);
        CmppSubmitRespBody body = new CmppSubmitRespBody(12345678L, 12);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf buf = CmppEncoder.INSTANCE.doEncode(ctx, new CmppSubmitResp(header, body));
        CmppSubmitResp cmppSubmitResp = (CmppSubmitResp) decoder.decode(buf);
        Assertions.assertEquals(12345678L, cmppSubmitResp.body().msgId());
        Assertions.assertEquals(12, cmppSubmitResp.body().result());
        Assertions.assertEquals(0, buf.readableBytes());
    }
}
