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
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

@ChannelHandler.Sharable
public class SmgpEncoder extends MessageToMessageEncoder<SmgpMessage> {

    public static final SmgpEncoder INSTANCE = new SmgpEncoder();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SmgpMessage smgpMessage,
                          List<Object> list) throws Exception {
        list.add(doEncode(channelHandlerContext, smgpMessage));
    }

    ByteBuf doEncode(ChannelHandlerContext ctx, SmgpMessage message) {
        switch (message.header().requestID()) {
            case SmgpConst.LOGIN_ID:
                return encodeLogin(ctx, (SmgpLogin) message);
            case SmgpConst.LOGIN_RESP_ID:
                return encodeLoginResp(ctx, (SmgpLoginResp) message);
            case SmgpConst.SUBMIT_ID:
                return encodeSubmit(ctx, (SmgpSubmit) message);
            case SmgpConst.SUBMIT_RESP_ID:
                return encodeSubmitResp(ctx, (SmgpSubmitResp) message);
            case SmgpConst.ACTIVE_TEST_ID:
            case SmgpConst.ACTIVE_TEST_RESP_ID:
                return encodeHeader(ctx, message.header());
            default:
                throw new IllegalArgumentException("Unknown requestId: " + message.header().requestID());
        }
    }

    private ByteBuf encodeLogin(ChannelHandlerContext ctx, SmgpLogin message) {
        ByteBuf buf = ctx.alloc().buffer(SmgpConst.LEN_LOGIN_MSG);
        writeHeader(buf, message.header(), SmgpConst.LEN_LOGIN_MSG);
        writeString(buf, message.body().clientId(), SmgpConst.LEN_CLIENT_ID);
        writeString(buf, message.body().authenticatorClient(), SmgpConst.LEN_AUTHENTICATOR_CLIENT);
        buf.writeByte(message.body().loginMode());
        buf.writeInt(message.body().timestamp());
        buf.writeByte(message.body().version());
        return buf;
    }

    private ByteBuf encodeLoginResp(ChannelHandlerContext ctx, SmgpLoginResp message) {
        ByteBuf buf = ctx.alloc().buffer(SmgpConst.LEN_LOGIN_RESP_MSG);
        writeHeader(buf, message.header(), SmgpConst.LEN_LOGIN_RESP_MSG);
        buf.writeInt(message.body().status());
        writeString(buf, message.body().authenticatorServer(), SmgpConst.LEN_AUTHENTICATOR_SERVER);
        buf.writeByte(message.body().version());
        return buf;
    }

    private ByteBuf encodeSubmit(ChannelHandlerContext ctx, SmgpSubmit message) {
        int bodySize = SmgpConst.LEN_SUBMIT_MSG
                + SmgpConst.LEN_DEST_TERM_ID * message.body().destTermIdCount()
                + message.body().msgLength();
        ByteBuf buf = ctx.alloc().buffer(bodySize);
        writeHeader(buf, message.header(), bodySize);
        buf.writeByte(message.body().subType());
        buf.writeByte(message.body().needReport());
        buf.writeByte(message.body().priority());
        writeString(buf, message.body().serviceId(), SmgpConst.LEN_SERVICE_ID);
        writeString(buf, message.body().feeType(), SmgpConst.LEN_FEE_TYPE);
        writeString(buf, message.body().feeCode(), SmgpConst.LEN_FEE_CODE);
        writeString(buf, message.body().msgFormat(), SmgpConst.LEN_MESSAGE_FORMAT);
        writeString(buf, message.body().validTime(), SmgpConst.LEN_VALID_TIME);
        writeString(buf, message.body().atTime(), SmgpConst.LEN_AT_TIME);
        writeString(buf, message.body().srcTermId(), SmgpConst.LEN_SRC_TERM_ID);
        writeString(buf, message.body().chargeTermId(), SmgpConst.LEN_CHARGE_TERM_ID);
        buf.writeByte(message.body().destTermIdCount());
        for (String destTermId : message.body().destTermId()) {
            writeString(buf, destTermId, SmgpConst.LEN_DEST_TERM_ID);
        }
        buf.writeByte(message.body().msgLength());
        writeString(buf, message.body().msgContent(), message.body().msgLength());
        writeString(buf, message.body().reserve(), SmgpConst.LEN_SUBMIT_RESERVE);
        return buf;
    }

    private ByteBuf encodeSubmitResp(ChannelHandlerContext ctx, SmgpSubmitResp message) {
        ByteBuf buf = ctx.alloc().buffer(SmgpConst.LEN_SUBMIT_RESP_MSG);
        writeHeader(buf, message.header(), SmgpConst.LEN_SUBMIT_RESP_MSG);
        writeString(buf, message.body().msgId(), SmgpConst.LEN_SUBMIT_RESP_MESSAGE_ID);
        buf.writeInt(message.body().status());
        return buf;
    }
    private ByteBuf encodeHeader(ChannelHandlerContext ctx, SmgpHeader header) {
        ByteBuf buf = ctx.alloc().buffer(SmgpConst.LEN_HEADER);
        buf.writeInt(header.packetLength());
        buf.writeInt(header.requestID());
        buf.writeInt(header.sequenceID());
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

    private void writeHeader(ByteBuf buf, SmgpHeader header, int size) {
        if (header.packetLength() == 0) {
            buf.writeInt(size);
        } else {
            buf.writeInt(header.packetLength());
        }
        buf.writeInt(header.requestID());
        buf.writeInt(header.sequenceID());
    }

}
