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

import java.util.ArrayList;
import java.util.List;

public class SmgpSubmitTest {

    @Test
    public void case1() {
        SmgpHeader header = new SmgpHeader(SmgpConst.SUBMIT_ID, 13);
        ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);
        Mockito.when(ctx.alloc()).thenReturn(ByteBufAllocator.DEFAULT);
        List<String> destTermIds = new ArrayList<>();
        destTermIds.add("qwer");
        SmgpSubmitBody submitBody = new SmgpSubmitBody((byte) 1, (byte) 2, (byte) 3, "serviceId", "feeType", "feeCode",
                "msgFormat", "validTime", "atTime", "srcTermId",
                "chargeTermId", (byte) 1, destTermIds, (byte) 2, "as", "adsd");
        ByteBuf buf = SmgpEncoder.INSTANCE.doEncode(ctx, new SmgpSubmit(header, submitBody));
        SmgpSubmit message = (SmgpSubmit) new SmgpDecoder().decode(buf);
        Assertions.assertEquals("qwer", message.body().destTermId().get(0));
        Assertions.assertEquals("srcTermId", message.body().srcTermId());
        Assertions.assertEquals(0, buf.readableBytes());
    }
}
