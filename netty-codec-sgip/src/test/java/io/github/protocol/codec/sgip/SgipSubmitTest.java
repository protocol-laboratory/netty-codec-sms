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

public class SgipSubmitTest {

    private SgipDecoder decoder = new SgipDecoder();

    @Test
    public void case1() {
        SgipHeader header = new SgipHeader(SgipConst.SUBMIT_ID, 112233445566L);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        SgipSubmitBody submitBody = new SgipSubmitBody("spnumber", "chargenumber",
                "usernumber", "corpid", "servicetype", "feetype",
                "feevalue", "givenvalue", "agentflag", "morelatetomtflag",
                123L, "expiretime", "scheduletime", 345L, 678L,
                23L, 43L, 5678L, 12345L);
        ByteBuf buf = SgipEncoder.INSTANCE.doEncode(ctx, new SgipSubmit(header, submitBody));
        SgipSubmit sgipSubmit = (SgipSubmit) decoder.decode(buf);
        Assertions.assertEquals("spnumber", sgipSubmit.body().spNumber());
        Assertions.assertEquals("scheduletime", sgipSubmit.body().scheduleTime());
        Assertions.assertEquals(12345L, sgipSubmit.body().messageContent());
        Assertions.assertEquals(0, buf.readableBytes());
    }
}
