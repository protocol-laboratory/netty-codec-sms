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

public class SmgpSubmitRespTest {

    @Test
    public void case1() {
        SmgpHeader header = new SmgpHeader(SmgpConst.SUBMIT_RESP_ID, 12);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        SmgpSubmitResp message = new SmgpSubmitResp(header, new SmgpSubmitRespBody("123456789", 3));
        ByteBuf buf = SmgpEncoder.INSTANCE.doEncode(ctx, message);
        SmgpSubmitResp smgpSubmitRespMessage = (SmgpSubmitResp) new SmgpDecoder().decode(buf);
        Assertions.assertEquals("123456789", smgpSubmitRespMessage.body().msgId());
        Assertions.assertEquals(3, smgpSubmitRespMessage.body().status());
        Assertions.assertEquals(0, buf.readableBytes());
    }
}
