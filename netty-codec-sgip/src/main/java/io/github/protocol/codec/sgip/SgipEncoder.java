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
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

@ChannelHandler.Sharable
public class SgipEncoder extends MessageToMessageEncoder<SgipMessage> {

    public static final SgipEncoder INSTANCE = new SgipEncoder();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SgipMessage sgipMessage,
                          List<Object> list) throws Exception {
        list.add(doEncode(channelHandlerContext, sgipMessage));
    }

    ByteBuf doEncode(ChannelHandlerContext ctx, SgipMessage message) {
        switch (message.header().commandId()) {
            case SgipConst.BIND_ID:
                return encodeBind(ctx, (SgipBind) message);
            case SgipConst.BIND_RESP_ID:
                return encodeBindResp(ctx, (SgipBindResp) message);
            default:
                throw new IllegalArgumentException("Unknown command id: " + message.header().commandId());
        }
    }

    private ByteBuf encodeBind(ChannelHandlerContext ctx, SgipBind message) {
        ByteBuf buf = ctx.alloc().buffer(SgipConst.LEN_BIND_MSG);
        writeHeader(buf, message.header(), SgipConst.LEN_BIND_MSG);
        buf.writeByte(message.body().loginType());
        writeString(buf, message.body().loginName(), SgipConst.LEN_LOGIN_NAME);
        writeString(buf, message.body().loginPassword(), SgipConst.LEN_LOGIN_PASSWORD);
        writeString(buf, message.body().reserve(), SgipConst.LEN_BIND_RESERVE);
        return buf;
    }

    private ByteBuf encodeBindResp(ChannelHandlerContext ctx, SgipBindResp message) {
        ByteBuf buf = ctx.alloc().buffer(SgipConst.LEN_BIND_RESP_MSG);
        writeHeader(buf, message.header(), SgipConst.LEN_BIND_MSG);
        buf.writeByte(message.body().result());
        writeString(buf, message.body().reserve(), SgipConst.LEN_BIND_RESP_RESERVE);
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

    private void writeHeader(ByteBuf buf, SgipHeader header, int size) {
        if (header.messageLength() == 0) {
            buf.writeInt(size);
        } else {
            buf.writeInt(header.messageLength());
        }
        buf.writeInt(header.commandId());
        buf.writeLong(header.sequenceNumber());
    }
}
