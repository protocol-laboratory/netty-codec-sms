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

package io.github.protocol.codec.cngp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

@ChannelHandler.Sharable
public class CngpEncoder extends MessageToMessageEncoder<CngpMessage> {

    public static final CngpEncoder INSTANCE = new CngpEncoder();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CngpMessage cngpMessage,
                          List<Object> list) throws Exception {
        list.add(doEncode(channelHandlerContext, cngpMessage));
    }

    ByteBuf doEncode(ChannelHandlerContext ctx, CngpMessage message) {
        switch (message.header().commandId()) {
            case CngpConst.LOGIN_ID:
                return encodeLogin(ctx, (CngpLogin) message);
            case CngpConst.LOGIN_RESP_ID:
                return encodeLoginResp(ctx, (CngpLoginResp) message);
            case CngpConst.SUBMIT_ID:
                return encodeSubmit(ctx, (CngpSubmit) message);
            case CngpConst.SUBMIT_RESP_ID:
                return encodeSubmitResp(ctx, (CngpSubmitResp) message);
            case CngpConst.DELIVER_ID:
                return encodeDeliver(ctx, (CngpDeliver) message);
            case CngpConst.DELIVER_RESP_ID:
                return encodeDeliverResp(ctx, (CngpDeliverResp) message);
            case CngpConst.ACTIVE_TEST_ID:
            case CngpConst.ACTIVE_TEST_RESP_ID:
            case CngpConst.EXIT_ID:
            case CngpConst.EXIT_RESP_ID:
                return encodeHeader(ctx, message.header());
            default:
                throw new IllegalArgumentException("Unknown commandId: " + message.header().commandId());
        }
    }


    private ByteBuf encodeLogin(ChannelHandlerContext ctx, CngpLogin message) {
        ByteBuf buf = ctx.alloc().buffer(CngpConst.LEN_LOGIN_MSG);
        writeHeader(buf, message.header(), CngpConst.LEN_LOGIN_MSG);
        writeString(buf, message.body().clientId(), CngpConst.LEN_CLIENT_ID);
        writeString(buf, message.body().authenticatorClient(), CngpConst.LEN_LOGIN_AUTHENTICATOR_CLIENT);
        buf.writeByte(message.body().loginMode());
        buf.writeInt(message.body().timeStamp());
        buf.writeByte(message.body().version());
        return buf;
    }

    private ByteBuf encodeLoginResp(ChannelHandlerContext ctx, CngpLoginResp message) {
        ByteBuf buf = ctx.alloc().buffer(CngpConst.LEN_LOGIN_RESP_MSG);
        writeHeader(buf, message.header(), CngpConst.LEN_LOGIN_RESP_MSG);
        writeString(buf, message.body().authenticatorServer(), CngpConst.LEN_LOGIN_RESP_AUTHENTICATOR_CLIENT);
        buf.writeByte(message.body().version());
        return buf;
    }

    private ByteBuf encodeSubmit(ChannelHandlerContext ctx, CngpSubmit message) {
        int bodySize = CngpConst.LEN_SUBMIT_MSG
                + CngpConst.LEN_DEST_TERM_ID * message.body().destTermIdCount()
                + message.body().msgLength();
        ByteBuf buf = ctx.alloc().buffer(bodySize);
        writeHeader(buf, message.header(), bodySize);
        writeString(buf, message.body().spId(), CngpConst.LEN_SP_ID);
        buf.writeByte(message.body().subType());
        buf.writeByte(message.body().needReport());
        buf.writeByte(message.body().priority());
        writeString(buf, message.body().serviceId(), CngpConst.LEN_SERVICE_ID);
        writeString(buf, message.body().feeType(), CngpConst.LEN_FEE_TYPE);
        writeString(buf, message.body().feeUserType(), CngpConst.LEN_FEE_USER_TYPE);
        writeString(buf, message.body().feeCode(), CngpConst.LEN_FEE_CODE);
        writeString(buf, message.body().msgFormat(), CngpConst.LEN_MESSAGE_FORMAT);
        writeString(buf, message.body().validTime(), CngpConst.LEN_VALID_TIME);
        writeString(buf, message.body().atTime(), CngpConst.LEN_AT_TIME);
        writeString(buf, message.body().srcTermId(), CngpConst.LEN_SUBMIT_SRC_TERM_ID);
        writeString(buf, message.body().chargeTermId(), CngpConst.LEN_CHARGE_TERM_ID);
        buf.writeByte(message.body().destTermIdCount());
        for (String destTermId : message.body().destTermId()) {
            writeString(buf, destTermId, CngpConst.LEN_DEST_TERM_ID);
        }
        buf.writeByte(message.body().msgLength());
        buf.writeBytes(message.body().msgContent());
        return buf;
    }

    private ByteBuf encodeSubmitResp(ChannelHandlerContext ctx, CngpSubmitResp message) {
        ByteBuf buf = ctx.alloc().buffer(CngpConst.LEN_SUBMIT_RESP_MSG);
        writeHeader(buf, message.header(), CngpConst.LEN_SUBMIT_RESP_MSG);
        writeString(buf, message.body().msgId(), CngpConst.LEN_MESSAGE_ID);
        return buf;
    }


    private ByteBuf encodeDeliver(ChannelHandlerContext ctx, CngpDeliver message) {
        ByteBuf buf = ctx.alloc().buffer(CngpConst.LEN_DELIVER_MSG + message.body().msgLength());
        writeHeader(buf, message.header(), CngpConst.LEN_DELIVER_MSG + message.body().msgLength());
        writeString(buf, message.body().msgId(), CngpConst.LEN_MESSAGE_ID);
        buf.writeByte(message.body().isResport());
        buf.writeByte(message.body().msgFormat());
        writeString(buf, message.body().recvTime(), CngpConst.LEN_RECV_TIME);
        writeString(buf, message.body().srcTermId(), CngpConst.LEN_DELIVER_SRC_TERM_ID);
        writeString(buf, message.body().destTermId(), CngpConst.LEN_DELIVER_DEST_TERM_ID);
        buf.writeByte(message.body().msgLength());
        buf.writeBytes(message.body().msgContent());
        return buf;
    }

    private ByteBuf encodeDeliverResp(ChannelHandlerContext ctx, CngpDeliverResp message) {
        ByteBuf buf = ctx.alloc().buffer(CngpConst.LEN_DELIVER_RESP_MSG);
        writeHeader(buf, message.header(), CngpConst.LEN_DELIVER_RESP_MSG);
        writeString(buf, message.body().msgId(), CngpConst.LEN_DELIVER_MESSAGE_ID);
        return buf;
    }


    private ByteBuf encodeHeader(ChannelHandlerContext ctx, CngpHeader header) {
        ByteBuf buf = ctx.alloc().buffer(CngpConst.LEN_HEADER);
        writeHeader(buf, header, CngpConst.LEN_HEADER);
        return buf;
    }

    private void writeHeader(ByteBuf buf, CngpHeader header, int size) {
        if (header.totalLength() == 0) {
            buf.writeInt(size);
        } else {
            buf.writeInt(header.totalLength());
        }
        buf.writeInt(header.commandId());
        buf.writeInt(header.commandStatus());
        buf.writeInt(header.sequenceId());
    }

    private void writeString(ByteBuf buf, String str, int length) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        int len = Math.min(bytes.length, length);
        buf.writeBytes(bytes, 0, len);
        for (int i = len; i < length; i++) {
            buf.writeByte(0);
        }
    }

}
