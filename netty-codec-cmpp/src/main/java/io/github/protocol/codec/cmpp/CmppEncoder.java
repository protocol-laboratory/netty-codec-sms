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
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

@ChannelHandler.Sharable
public class CmppEncoder extends MessageToMessageEncoder<CmppMessage> {

    public static final CmppEncoder INSTANCE = new CmppEncoder();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CmppMessage cmppMessage,
                          List<Object> list) throws Exception {
        list.add(doEncode(channelHandlerContext, cmppMessage));
    }

    ByteBuf doEncode(ChannelHandlerContext ctx, CmppMessage message) {
        switch (message.header().commandId()) {
            case CmppConst.CONNECT_ID:
                return encodeConnect(ctx, (CmppConnect) message);
            default:
                throw new IllegalArgumentException("Unknown commandId: " + message.header().commandId());
        }
    }

    private ByteBuf encodeConnect(ChannelHandlerContext ctx, CmppConnect message) {
        ByteBuf buf = ctx.alloc().buffer();
        writeHeader(buf, message.header(), CmppConst.LEN_CONNECT_MSG);
        writeString(buf, message.body().sourceAddr(), CmppConst.LEN_SOURCE_ADDR);
        writeString(buf, message.body().authenticatorSource(), CmppConst.LEN_AUTHENTICATOR_SOURCE);
        buf.writeByte(message.body().version());
        buf.writeInt(message.body().timestamp());
        return buf;
    }

    private void writeString(ByteBuf buf, String str, int length) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        int len = Math.min(bytes.length, length);
        buf.writeBytes(bytes, 0, len);
        for (int i = len; i < length; i++) {
            buf.writeByte(0);
        }
    }

    private void writeHeader(ByteBuf buf, CmppHeader header, int size) {
        if (header.totalLength() == 0) {
            buf.writeInt(size);
        } else {
            buf.writeInt(header.totalLength());
        }
        buf.writeInt(header.commandId());
        buf.writeInt(header.sequenceId());
    }
}
