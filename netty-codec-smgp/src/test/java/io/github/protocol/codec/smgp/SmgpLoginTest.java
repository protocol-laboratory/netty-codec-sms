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

package io.github.protocol.codec.smgp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SmgpLoginTest {

    @Test
    public void case1() {
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        SmgpHeader header = new SmgpHeader(SmgpConst.LOGIN_ID, 1234);
        SmgpLogin body = new SmgpLogin(header, new SmgpLoginBody("clientId", "authenticator",
                (byte) 1, 12, (byte) 2));
        ByteBuf buf = SmgpEncoder.INSTANCE.doEncode(ctx, body);
        SmgpDecoder decoder = new SmgpDecoder();
        SmgpLogin message = (SmgpLogin) decoder.decode(buf);
        Assertions.assertEquals("clientId", message.body().clientId());
        Assertions.assertEquals(12, message.body().timestamp());
        Assertions.assertEquals(0, buf.readableBytes());
    }
}
