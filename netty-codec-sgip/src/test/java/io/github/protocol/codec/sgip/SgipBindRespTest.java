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

package io.github.protocol.codec.sgip;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SgipBindRespTest {

    private SgipDecoder decoder = new SgipDecoder();

    @Test
    public void case1() {
        SgipHeader header = new SgipHeader(SgipConst.LEN_BIND_RESP_MSG, SgipConst.BIND_RESP_ID, 112345678L);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        SgipBindRespBody body = new SgipBindRespBody((byte) 1, "reserve");
        ByteBuf buf = SgipEncoder.INSTANCE.doEncode(ctx, new SgipBindResp(header, body));
        SgipBindResp message = (SgipBindResp) decoder.decode(buf);
        Assertions.assertEquals(1, message.body().result());
        Assertions.assertEquals("reserve", message.body().reserve());
        Assertions.assertEquals(0, buf.readableBytes());
    }
}
