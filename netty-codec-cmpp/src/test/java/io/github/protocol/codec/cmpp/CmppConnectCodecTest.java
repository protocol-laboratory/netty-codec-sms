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

public class CmppConnectCodecTest {

    private final CmppDecoder decoder = new CmppDecoder();

    @Test
    public void case1() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new CmppConnectBody("source", "password".getBytes(StandardCharsets.UTF_8),
                        (byte) 0, 1122334455));
    }

    @Test
    public void case2() {
        byte[] authenticatorSource = new byte[16];
        Arrays.fill(authenticatorSource, (byte) 'a');
        CmppHeader header = new CmppHeader(CmppConst.CONNECT_ID, 0);
        CmppConnectBody body = new CmppConnectBody("source", authenticatorSource,
                (byte) 0, 1122334455);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        ByteBuf byteBuf = CmppEncoder.INSTANCE.doEncode(ctx, new CmppConnect(header, body));
        CmppConnect bindTransmitter = (CmppConnect) decoder.decode(byteBuf);
        Assertions.assertEquals("source", bindTransmitter.body().sourceAddr());
        Assertions.assertArrayEquals(authenticatorSource, bindTransmitter.body().authenticatorSource());
        Assertions.assertEquals((byte) 0, bindTransmitter.body().version());
        Assertions.assertEquals(1122334455, bindTransmitter.body().timestamp());
        Assertions.assertEquals(0, byteBuf.readableBytes());
    }
}
