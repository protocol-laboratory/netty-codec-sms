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

package io.github.protocol.codec.smpp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

@ChannelHandler.Sharable
public class SmppEncoder extends MessageToMessageEncoder<SmppMessage> {

    public static final SmppEncoder INSTANCE = new SmppEncoder();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SmppMessage smppMessage,
                          List<Object> list) throws Exception {
        list.add(doEncode(channelHandlerContext, smppMessage));
    }

    ByteBuf doEncode(ChannelHandlerContext ctx, SmppMessage message) {
        switch (message.header().commandId()) {
            case SmppConst.BIND_RECEIVER_ID:
                return encodeBindReceiver(ctx, (SmppBindReceiver) message);
            case SmppConst.BIND_RECEIVER_RESP_ID:
                return encodeBindReceiverResp(ctx, (SmppBindReceiverResp) message);
            case SmppConst.BIND_TRANSMITTER_ID:
                return encodeBindTransmitter(ctx, (SmppBindTransmitter) message);
            case SmppConst.BIND_TRANSMITTER_RESP_ID:
                return encodeBindTransmitterResp(ctx, (SmppBindTransmitterResp) message);
            case SmppConst.QUERY_SM_ID:
                return encodeQuerySm(ctx, (SmppQuerySm) message);
            case SmppConst.QUERY_SM_RESP_ID:
                return encodeQuerySmResp(ctx, (SmppQuerySmResp) message);
            case SmppConst.SUBMIT_SM_ID:
                return encodeSubmitSm(ctx, (SmppSubmitSm) message);
            case SmppConst.SUBMIT_SM_RESP_ID:
                return encodeSubmitSmResp(ctx, (SmppSubmitSmResp) message);
            case SmppConst.DELIVER_SM_ID:
                return encodeDeliverSm(ctx, (SmppDeliverSm) message);
            case SmppConst.DELIVER_SM_RESP_ID:
                return encodeDeliverSmResp(ctx, (SmppDeliverSmResp) message);
            case SmppConst.UNBIND_ID:
            case SmppConst.UNBIND_RESP_ID:
                return encodeHeader(ctx, message.header());
            case SmppConst.REPLACE_SM_ID:
                return encodeReplaceSm(ctx, (SmppReplaceSm) message);
            case SmppConst.REPLACE_SM_RESP_ID:
                return encodeHeader(ctx, message.header());
            case SmppConst.CANCEL_SM_ID:
                return encodeCancelSm(ctx, (SmppCancelSm) message);
            case SmppConst.CANCEL_SM_RESP_ID:
                return encodeHeader(ctx, message.header());
            case SmppConst.BIND_TRANSCEIVER_ID:
                return encodeBindTransceiver(ctx, (SmppBindTransceiver) message);
            case SmppConst.BIND_TRANSCEIVER_RESP_ID:
                return encodeBindTransceiverResp(ctx, (SmppBindTransceiverResp) message);
            case SmppConst.OUTBIND_ID:
                return encodeOutBind(ctx, (SmppOutBind) message);
            case SmppConst.ENQUIRE_LINK_ID:
            case SmppConst.ENQUIRE_LINK_RESP_ID:
                return encodeHeader(ctx, message.header());
            case SmppConst.SUBMIT_MULTI_ID:
                return encodeSubmitMulti(ctx, (SmppSubmitMulti) message);
            case SmppConst.SUBMIT_MULTI_RESP_ID:
                return encodeSubmitMultiResp(ctx, (SmppSubmitMultiResp) message);
            default:
                throw new IllegalArgumentException("Unknown command id: " + message.header().commandId());
        }
    }

    private ByteBuf encodeHeader(ChannelHandlerContext ctx, SmppHeader header) {
        ByteBuf buf = ctx.alloc().buffer(SmppConst.LEN_HEADER);
        if (header.commandLength() == 0) {
            buf.writeInt(SmppConst.LEN_HEADER);
        } else {
            buf.writeInt(header.commandLength());
        }
        buf.writeInt(header.commandId());
        buf.writeInt(header.commandStatus());
        buf.writeInt(header.sequenceNumber());
        return buf;
    }

    private ByteBuf encodeBindReceiver(ChannelHandlerContext ctx, SmppBindReceiver bindReceiver) {
        SmppHeader header = bindReceiver.header();
        SmppBindReceiverBody body = bindReceiver.body();
        int bodySize = len(body.systemId()) + len(body.password()) + len(body.systemType())
                + SmppConst.LEN_INTERFACE_VERSION + SmppConst.LEN_ADDR_TON + SmppConst.LEN_ADDR_NPI
                + len(body.addressRange());
        int size = SmppConst.LEN_HEADER + bodySize;
        ByteBuf buf = ctx.alloc().buffer(size);
        writeHeader(buf, header, size);
        writeCString(buf, body.systemId());
        writeCString(buf, body.password());
        writeCString(buf, body.systemType());
        buf.writeByte(body.interfaceVersion());
        buf.writeByte(body.addrTon());
        buf.writeByte(body.addrNpi());
        writeCString(buf, body.addressRange());
        return buf;
    }

    private ByteBuf encodeBindReceiverResp(ChannelHandlerContext ctx, SmppBindReceiverResp bindReceiverResp) {
        SmppHeader header = bindReceiverResp.header();
        SmppBindReceiverRespBody body = bindReceiverResp.body();
        int bodySize = len(body.systemId());
        int size = SmppConst.LEN_HEADER + bodySize;
        ByteBuf buf = ctx.alloc().buffer(size);
        writeHeader(buf, header, size);
        writeCString(buf, body.systemId());
        return buf;
    }

    private ByteBuf encodeBindTransmitter(ChannelHandlerContext ctx, SmppBindTransmitter bindTransmitter) {
        SmppHeader header = bindTransmitter.header();
        SmppBindTransmitterBody body = bindTransmitter.body();
        int bodySize = len(body.systemId()) + len(body.password()) + len(body.systemType())
                + SmppConst.LEN_INTERFACE_VERSION + SmppConst.LEN_ADDR_TON + SmppConst.LEN_ADDR_NPI
                + len(body.addressRange());
        int size = SmppConst.LEN_HEADER + bodySize;
        ByteBuf buf = ctx.alloc().buffer(size);
        writeHeader(buf, header, size);
        writeCString(buf, body.systemId());
        writeCString(buf, body.password());
        writeCString(buf, body.systemType());
        buf.writeByte(body.interfaceVersion());
        buf.writeByte(body.addrTon());
        buf.writeByte(body.addrNpi());
        writeCString(buf, body.addressRange());
        return buf;
    }

    private ByteBuf encodeBindTransmitterResp(ChannelHandlerContext ctx, SmppBindTransmitterResp bindTransmitterResp) {
        SmppHeader header = bindTransmitterResp.header();
        SmppBindTransmitterRespBody body = bindTransmitterResp.body();
        int bodySize = len(body.systemId());
        int size = SmppConst.LEN_HEADER + bodySize;
        ByteBuf buf = ctx.alloc().buffer(size);
        writeHeader(buf, header, size);
        writeCString(buf, body.systemId());
        return buf;
    }

    private ByteBuf encodeQuerySm(ChannelHandlerContext ctx, SmppQuerySm querySm) {
        SmppHeader header = querySm.header();
        SmppQuerySmBody body = querySm.body();
        int bodySize = len(body.messageId()) + SmppConst.LEN_SOURCE_ADDR_TON + SmppConst.LEN_SOURCE_ADDR_NPI
                + len(body.sourceAddr());
        int size = SmppConst.LEN_HEADER + bodySize;
        ByteBuf buf = ctx.alloc().buffer(size);
        writeHeader(buf, header, size);
        writeCString(buf, body.messageId());
        buf.writeByte(body.sourceAddrTon());
        buf.writeByte(body.sourceAddrNpi());
        writeCString(buf, body.sourceAddr());
        return buf;
    }

    private ByteBuf encodeQuerySmResp(ChannelHandlerContext ctx, SmppQuerySmResp querySmResp) {
        SmppHeader header = querySmResp.header();
        SmppQuerySmRespBody body = querySmResp.body();
        int bodySize = len(body.messageId()) + len(body.finalDate()) + SmppConst.LEN_MESSAGE_STATE
                + SmppConst.LEN_ERROR_CODE;
        int size = SmppConst.LEN_HEADER + bodySize;
        ByteBuf buf = ctx.alloc().buffer(size);
        writeHeader(buf, header, size);
        writeCString(buf, body.messageId());
        writeCString(buf, body.finalDate());
        buf.writeByte(body.messageState());
        buf.writeByte(body.errorCode());
        return buf;
    }

    private ByteBuf encodeSubmitSm(ChannelHandlerContext ctx, SmppSubmitSm submitSm) {
        SmppHeader header = submitSm.header();
        SmppSubmitSmBody body = submitSm.body();
        int bodySize = len(body.serviceType()) + SmppConst.LEN_SOURCE_ADDR_TON + SmppConst.LEN_SOURCE_ADDR_NPI
                + len(body.sourceAddr()) + SmppConst.LEN_DEST_ADDR_TON + SmppConst.LEN_DEST_ADDR_NPI
                + len(body.destinationAddr()) + SmppConst.LEN_ESM_CLASS + SmppConst.LEN_PROTOCOL_ID
                + SmppConst.LEN_PRIORITY_FLAG + len(body.scheduleDeliveryTime()) + len(body.validityPeriod())
                + SmppConst.LEN_REGISTERED_DELIVERY + SmppConst.LEN_REPLACE_IF_PRESENT_FLAG
                + SmppConst.LEN_DATA_CODING + SmppConst.LEN_SM_DEFAULT_MSG_ID + SmppConst.LEN_SM_LENGTH
                + body.smLength();
        int size = SmppConst.LEN_HEADER + bodySize;
        ByteBuf buf = ctx.alloc().buffer(size);
        writeHeader(buf, header, size);
        writeCString(buf, body.serviceType());
        buf.writeByte(body.sourceAddrTon());
        buf.writeByte(body.sourceAddrNpi());
        writeCString(buf, body.sourceAddr());
        buf.writeByte(body.destAddrTon());
        buf.writeByte(body.destAddrNpi());
        writeCString(buf, body.destinationAddr());
        buf.writeByte(body.esmClass());
        buf.writeByte(body.protocolId());
        buf.writeByte(body.priorityFlag());
        writeCString(buf, body.scheduleDeliveryTime());
        writeCString(buf, body.validityPeriod());
        buf.writeByte(body.registeredDelivery());
        buf.writeByte(body.replaceIfPresentFlag());
        buf.writeByte(body.dataCoding());
        buf.writeByte(body.smDefaultMsgId());
        buf.writeShort(body.smLength());
        buf.writeBytes(body.shortMessage());
        return buf;
    }

    private ByteBuf encodeSubmitSmResp(ChannelHandlerContext ctx, SmppSubmitSmResp submitSmResp) {
        SmppHeader header = submitSmResp.header();
        SmppSubmitSmRespBody body = submitSmResp.body();
        int bodySize = len(body.messageId());
        int size = SmppConst.LEN_HEADER + bodySize;
        ByteBuf buf = ctx.alloc().buffer(size);
        writeHeader(buf, header, size);
        writeCString(buf, body.messageId());
        return buf;
    }

    private ByteBuf encodeDeliverSm(ChannelHandlerContext ctx, SmppDeliverSm deliverSm) {
        SmppHeader header = deliverSm.header();
        SmppDeliverSmBody body = deliverSm.body();
        int bodySize = len(body.serviceType()) + SmppConst.LEN_ADDR_TON + SmppConst.LEN_ADDR_NPI
                + len(body.sourceAddr()) + SmppConst.LEN_ADDR_TON + SmppConst.LEN_ADDR_NPI
                + len(body.destinationAddr()) + SmppConst.LEN_ESM_CLASS + SmppConst.LEN_PROTOCOL_ID
                + SmppConst.LEN_PRIORITY_FLAG + len(body.scheduleDeliveryTime()) + len(body.validityPeriod())
                + SmppConst.LEN_REGISTERED_DELIVERY + SmppConst.LEN_REPLACE_IF_PRESENT_FLAG + SmppConst.LEN_DATA_CODING
                + SmppConst.LEN_SM_DEFAULT_MSG_ID + SmppConst.LEN_SM_LENGTH + body.smLength();
        int size = SmppConst.LEN_HEADER + bodySize;
        ByteBuf buf = ctx.alloc().buffer(size);
        writeHeader(buf, header, size);
        writeCString(buf, body.serviceType());
        buf.writeByte(body.sourceAddrTon());
        buf.writeByte(body.sourceAddrNpi());
        writeCString(buf, body.sourceAddr());
        buf.writeByte(body.destAddrTon());
        buf.writeByte(body.destAddrNpi());
        writeCString(buf, body.destinationAddr());
        buf.writeByte(body.esmClass());
        buf.writeByte(body.protocolId());
        buf.writeByte(body.priorityFlag());
        writeCString(buf, body.scheduleDeliveryTime());
        writeCString(buf, body.validityPeriod());
        buf.writeByte(body.registeredDelivery());
        buf.writeByte(body.replaceIfPresentFlag());
        buf.writeByte(body.dataCoding());
        buf.writeByte(body.smDefaultMsgId());
        buf.writeByte(body.smLength());
        buf.writeBytes(body.shortMessage());
        return buf;
    }

    private ByteBuf encodeDeliverSmResp(ChannelHandlerContext ctx, SmppDeliverSmResp deliverSmResp) {
        SmppHeader header = deliverSmResp.header();
        SmppDeliverSmRespBody body = deliverSmResp.body();
        int bodySize = len(body.messageId());
        int size = SmppConst.LEN_HEADER + bodySize;
        ByteBuf buf = ctx.alloc().buffer(size);
        writeHeader(buf, header, size);
        writeCString(buf, body.messageId());
        return buf;
    }

    private ByteBuf encodeReplaceSm(ChannelHandlerContext ctx, SmppReplaceSm message) {
        int bodyLen = SmppConst.LEN_REPLACE_MSG + message.body().smLength();
        ByteBuf buf = ctx.alloc().buffer(bodyLen);
        writeHeader(buf, message.header(), bodyLen);
        writeCString(buf, message.body().messageId());
        buf.writeByte(message.body().sourceAddrTon());
        buf.writeByte(message.body().sourceAddrNpi());
        writeCString(buf, message.body().sourceAddr());
        writeCString(buf, message.body().scheduleDeliveryTime());
        writeCString(buf, message.body().validityPeriod());
        buf.writeByte(message.body().registeredDelivery());
        buf.writeByte(message.body().smDefaultMsgId());
        buf.writeByte(message.body().smLength());
        writeCString(buf, message.body().shortMessage());
        return buf;
    }

    private ByteBuf encodeCancelSm(ChannelHandlerContext ctx, SmppCancelSm message) {
        ByteBuf buf = ctx.alloc().buffer(SmppConst.LEN_CANCEL_MSG);
        writeHeader(buf, message.header(), SmppConst.LEN_CANCEL_MSG);
        writeCString(buf, message.body().serviceType());
        writeCString(buf, message.body().messageId());
        buf.writeByte(message.body().sourceAddrTon());
        buf.writeByte(message.body().sourceAddrTon());
        writeCString(buf, message.body().sourceAddr());
        buf.writeByte(message.body().destAddrNpi());
        buf.writeByte(message.body().destAddrTon());
        writeCString(buf, message.body().destinationAddr());
        return buf;
    }
    private ByteBuf encodeBindTransceiver(ChannelHandlerContext ctx, SmppBindTransceiver bindTransceiver) {
        SmppHeader header = bindTransceiver.header();
        SmppBindTransceiverBody body = bindTransceiver.body();
        int bodySize = len(body.systemId()) + len(body.password()) + len(body.systemType())
                + SmppConst.LEN_INTERFACE_VERSION + SmppConst.LEN_ADDR_TON + SmppConst.LEN_ADDR_NPI
                + len(body.addressRange());
        int size = SmppConst.LEN_HEADER + bodySize;
        ByteBuf buf = ctx.alloc().buffer(size);
        writeHeader(buf, header, size);
        writeCString(buf, body.systemId());
        writeCString(buf, body.password());
        writeCString(buf, body.systemType());
        buf.writeByte(body.interfaceVersion());
        buf.writeByte(body.addrTon());
        buf.writeByte(body.addrNpi());
        writeCString(buf, body.addressRange());
        return buf;
    }

    private ByteBuf encodeBindTransceiverResp(ChannelHandlerContext ctx, SmppBindTransceiverResp bindTransceiverResp) {
        SmppHeader header = bindTransceiverResp.header();
        SmppBindTransceiverRespBody body = bindTransceiverResp.body();
        int bodySize = len(body.systemId());
        int size = SmppConst.LEN_HEADER + bodySize;
        ByteBuf buf = ctx.alloc().buffer(size);
        writeHeader(buf, header, size);
        writeCString(buf, body.systemId());
        return buf;
    }


    private ByteBuf encodeOutBind(ChannelHandlerContext ctx, SmppOutBind message) {
        ByteBuf buf = ctx.alloc().buffer(SmppConst.LEN_OUT_BIND_MSG);
        writeHeader(buf, message.header(), SmppConst.LEN_OUT_BIND_MSG);
        writeCString(buf, message.body().systemId());
        writeCString(buf, message.body().password());
        return buf;
    }
    private ByteBuf encodeSubmitMulti(ChannelHandlerContext ctx, SmppSubmitMulti submitMulti) {
        SmppHeader header = submitMulti.header();
        SmppSubmitMultiBody body = submitMulti.body();
        int bodySize = len(body.serviceType()) + SmppConst.LEN_SOURCE_ADDR_TON + SmppConst.LEN_SOURCE_ADDR_NPI
                + len(body.sourceAddr()) + SmppConst.LEN_NUMBER_OF_DESTS;
        for (DestAddress address : body.destAddresses()) {
            bodySize += len(address);
        }
        bodySize = bodySize + SmppConst.LEN_ESM_CLASS + SmppConst.LEN_PROTOCOL_ID + SmppConst.LEN_PRIORITY_FLAG
                + len(body.scheduleDeliveryTime()) + len(body.validityPeriod()) + SmppConst.LEN_REGISTERED_DELIVERY
                + SmppConst.LEN_REPLACE_IF_PRESENT_FLAG + SmppConst.LEN_DATA_CODING + SmppConst.LEN_SM_DEFAULT_MSG_ID
                + SmppConst.LEN_SM_LENGTH + body.smLength();
        int size = SmppConst.LEN_HEADER + bodySize;
        ByteBuf buf = ctx.alloc().buffer(size);
        writeHeader(buf, header, size);
        writeCString(buf, body.serviceType());
        buf.writeByte(body.sourceAddrTon());
        buf.writeByte(body.sourceAddrNpi());
        writeCString(buf, body.sourceAddr());
        buf.writeByte(body.numberOfDests());
        for (DestAddress address : body.destAddresses()) {
            buf.writeByte(address.destAddrTon());
            buf.writeByte(address.destAddrNpi());
            writeCString(buf, address.destinationAddr());
        }
        buf.writeByte(body.esmClass());
        buf.writeByte(body.protocolId());
        buf.writeByte(body.priorityFlag());
        writeCString(buf, body.scheduleDeliveryTime());
        writeCString(buf, body.validityPeriod());
        buf.writeByte(body.registeredDelivery());
        buf.writeByte(body.replaceIfPresentFlag());
        buf.writeByte(body.dataCoding());
        buf.writeByte(body.smDefaultMsgId());
        buf.writeByte(body.smLength());
        buf.writeBytes(body.shortMessage());
        return buf;
    }

    private ByteBuf encodeSubmitMultiResp(ChannelHandlerContext ctx, SmppSubmitMultiResp submitMultiResp) {
        SmppHeader header = submitMultiResp.header();
        SmppSubmitMultiRespBody body = submitMultiResp.body();
        int bodySize = len(body.messageId()) + SmppConst.LEN_NO_UNSUCCESS;
        for (UnsuccessfulDelivery unsuccessfulDelivery : body.unsuccessSmes()) {
            bodySize += len(unsuccessfulDelivery);
        }
        int size = SmppConst.LEN_HEADER + bodySize;
        ByteBuf buf = ctx.alloc().buffer(size);
        writeHeader(buf, header, size);
        writeCString(buf, body.messageId());
        buf.writeByte(body.noUnsuccess());
        for (UnsuccessfulDelivery unsuccessfulDelivery : body.unsuccessSmes()) {
            buf.writeByte(unsuccessfulDelivery.destAddrTon());
            buf.writeByte(unsuccessfulDelivery.destAddrNpi());
            writeCString(buf, unsuccessfulDelivery.destinationAddr());
            buf.writeInt(unsuccessfulDelivery.errorStatusCode());
        }
        return buf;
    }

    private void writeCString(ByteBuf buf, String str) {
        if (str == null) {
            buf.writeByte(0);
        } else {
            buf.writeBytes(str.getBytes(StandardCharsets.UTF_8));
            buf.writeByte(0);
        }
    }

    private void writeHeader(ByteBuf buf, SmppHeader header, int size) {
        if (header.commandLength() == 0) {
            buf.writeInt(size);
        } else {
            buf.writeInt(header.commandLength());
        }
        buf.writeInt(header.commandId());
        buf.writeInt(header.commandStatus());
        buf.writeInt(header.sequenceNumber());
    }

    private int len(DestAddress destAddress) {
        return SmppConst.LEN_DEST_ADDR_TON + SmppConst.LEN_DEST_ADDR_NPI + len(destAddress.destinationAddr());
    }

    private int len(String str) {
        return str == null ? 1 : str.length() + 1;
    }

    private int len(UnsuccessfulDelivery unsuccessfulDelivery) {
        int len = SmppConst.LEN_DEST_ADDR_TON + SmppConst.LEN_DEST_ADDR_NPI
                + len(unsuccessfulDelivery.destinationAddr()) + SmppConst.LEN_ERROR_STATUS_CODE;
        return len;
    }
}
