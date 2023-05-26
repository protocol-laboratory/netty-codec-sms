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

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class CmppSubmitTest {

    private final CmppDecoder decoder = new CmppDecoder();

    @Test
    public void case1() {
        CmppHeader header = new CmppHeader(CmppConst.SUBMIT_ID, 0);
        List<String> destTerminalId = Arrays.asList("123456789", "8888888");
        byte[] msgContent = "q".getBytes(StandardCharsets.UTF_8);
        CmppSubmitBody cmppSubmitBody = new CmppSubmitBody(12345678L, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                "1234567890", (byte) 0, "qwertyuiopasdfgh", (byte) 0, (byte) 0,
                (byte) 0, (byte) 0, "asdfgh", "12", "asdfgh", "asdfghjklqwertyui",
                "asdfghjkloiuytr", "asdfghjklzxcvbnmqwert", (byte) 2, destTerminalId,
                (byte) 0, (short) msgContent.length, msgContent, "asdfghjklqwertyuiopz");
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf buf = CmppEncoder.INSTANCE.doEncode(ctx, new CmppSubmit(header, cmppSubmitBody));
        CmppSubmit cmppSubmit = (CmppSubmit) decoder.decode(buf);
        Assertions.assertEquals("asdfghjklzxcvbnmqwert", cmppSubmit.body().srcId());
        Assertions.assertEquals((short) msgContent.length, cmppSubmit.body().msgLength());
        Assertions.assertArrayEquals(msgContent, cmppSubmit.body().msgContent());
        Assertions.assertEquals("asdfghjklqwertyuiopz", cmppSubmit.body().linkId());
        Assertions.assertEquals("123456789", cmppSubmit.body().destTerminalId().get(0));
        Assertions.assertEquals(0, buf.readableBytes());
    }
}
