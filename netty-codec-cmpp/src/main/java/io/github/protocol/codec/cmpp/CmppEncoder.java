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
            case CmppConst.CONNECT_RESP_ID:
                return encodeConnectResp(ctx, (CmppConnectResp) message);
            case CmppConst.SUBMIT_ID:
                return encodeSubmit(ctx, (CmppSubmit) message);
            case CmppConst.SUBMIT_RESP_ID:
                return encodeSubmitResp(ctx, (CmppSubmitResp) message);
            case CmppConst.ACTIVE_TEST_ID:
                return encodeHeader(ctx, message.header());
            case CmppConst.ACTIVE_TEST_RESP_ID:
                return encodeActiveTestRespBody(ctx, (CmppActiveTestResp) message);
            default:
                throw new IllegalArgumentException("Unknown commandId: " + message.header().commandId());
        }
    }

    private ByteBuf encodeHeader(ChannelHandlerContext ctx, CmppHeader header) {
        ByteBuf buf = ctx.alloc().buffer(CmppConst.LEN_HEADER);
        if (header.totalLength() == 0) {
            buf.writeInt(CmppConst.LEN_HEADER);
        } else {
            buf.writeInt(header.totalLength());
        }
        buf.writeInt(header.commandId());
        buf.writeInt(header.sequenceId());
        return buf;
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

    private ByteBuf encodeConnectResp(ChannelHandlerContext ctx, CmppConnectResp message) {
        ByteBuf buf = ctx.alloc().buffer(CmppConst.LEN_CONNECT_RESP_MSG);
        writeHeader(buf, message.header(), CmppConst.LEN_CONNECT_RESP_MSG);
        buf.writeInt(message.body().status());
        writeString(buf, message.body().authenticatorISMG(), CmppConst.LEN_AUTHENTICATOR_ISMG);
        buf.writeByte(message.body().version());
        return buf;
    }

    private ByteBuf encodeSubmit(ChannelHandlerContext ctx, CmppSubmit message) {
        int msgLength = message.body().msgLength() & CmppConst.UNSIGNED_BYTE_MAX;
        int bodySize = CmppConst.LEN_HEADER + CmppConst.LEN_MESSAGE_ID + CmppConst.LEN_PK_TOTAL
                + CmppConst.LEN_PK_NUMBER + CmppConst.LEN_REGISTERED_DELIVERY + CmppConst.LEN_MESSAGE_LEVEL
                + CmppConst.LEN_SERVICE_ID + CmppConst.LEN_FEE_USERTYPE + CmppConst.LEN_FEE_TERMINAL_ID
                + CmppConst.LEN_FEE_TERMINAL_TYPE + CmppConst.LEN_TP_TPID + CmppConst.LEN_TP_UDHI
                + CmppConst.LEN_MESSAGE_FMT + CmppConst.LEN_MESSAGE_SRC + CmppConst.LEN_FEE_TYPE
                + CmppConst.LEN_FEE_CODE + CmppConst.LEN_VALID_TIME + CmppConst.LEN_AT_TIME
                + CmppConst.LEN_SRC_ID + CmppConst.LEN_DESTUSR_TL
                + CmppConst.LEN_DEST_TERMINAL_ID * message.body().destUsrTl()
                + CmppConst.LEN_DEST_TERMINAL_TYPE + CmppConst.LEN_MESSAGE_LENGTH
                + msgLength + CmppConst.LEN_LINK_ID;
        ByteBuf buf = ctx.alloc().buffer(bodySize);
        writeHeader(buf, message.header(), bodySize);
        buf.writeLong(message.body().msgId());
        buf.writeByte(message.body().pkTotal());
        buf.writeByte(message.body().pkNumber());
        buf.writeByte(message.body().registeredDelivery());
        buf.writeByte(message.body().msgLevel());
        writeString(buf, message.body().serviceId(), CmppConst.LEN_SERVICE_ID);
        buf.writeByte(message.body().feeUserType());
        writeString(buf, message.body().feeTerminalId(), CmppConst.LEN_FEE_TERMINAL_ID);
        buf.writeByte(message.body().feeTerminalType());
        buf.writeByte(message.body().tpPId());
        buf.writeByte(message.body().tpUdhi());
        buf.writeByte(message.body().msgFmt());
        writeString(buf, message.body().msgSrc(), CmppConst.LEN_MESSAGE_SRC);
        writeString(buf, message.body().feeType(), CmppConst.LEN_FEE_TYPE);
        writeString(buf, message.body().feeCode(), CmppConst.LEN_FEE_CODE);
        writeString(buf, message.body().validTime(), CmppConst.LEN_VALID_TIME);
        writeString(buf, message.body().atTime(), CmppConst.LEN_AT_TIME);
        writeString(buf, message.body().srcId(), CmppConst.LEN_SRC_ID);
        buf.writeByte(message.body().destUsrTl());
        for (String destTerminalId : message.body().destTerminalId()) {
            writeString(buf, destTerminalId, CmppConst.LEN_DEST_TERMINAL_ID);
        }
        buf.writeByte(message.body().destTerminalType());
        buf.writeByte(msgLength);
        buf.writeBytes(message.body().msgContent());
        writeString(buf, message.body().linkId(), CmppConst.LEN_LINK_ID);
        return buf;
    }

    private ByteBuf encodeSubmitResp(ChannelHandlerContext ctx, CmppSubmitResp message) {
        ByteBuf buf = ctx.alloc().buffer(CmppConst.LEN_SUBMIT_BODY_RESP_SIZE);
        writeHeader(buf, message.header(), CmppConst.LEN_SUBMIT_BODY_RESP_SIZE);
        buf.writeLong(message.body().msgId());
        buf.writeInt(message.body().result());
        return buf;
    }

    private ByteBuf encodeActiveTestRespBody(ChannelHandlerContext ctx, CmppActiveTestResp message) {
        ByteBuf buf = ctx.alloc().buffer(CmppConst.LEN_ACTIVE_TEST_RESP_MSG);
        writeHeader(buf, message.header(), CmppConst.LEN_ACTIVE_TEST_RESP_MSG);
        buf.writeByte(message.body().reserved());
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
