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
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SmppDecoder extends LengthFieldBasedFrameDecoder {

    public SmppDecoder() {
        this(SmppConst.DEFAULT_MAX_BYTES_IN_MESSAGE);
    }

    public SmppDecoder(int maxBytesInMessage) {
        super(maxBytesInMessage, 0, 4, -4, 0);
    }

    @Override
    protected SmppMessage decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        try {
            return decode(frame);
        } finally {
            frame.release();
        }
    }

    SmppMessage decode(ByteBuf frame) {
        int commandLength = frame.readInt();
        int commandId = frame.readInt();
        int commandStatus = frame.readInt();
        int sequenceNumber = frame.readInt();
        SmppHeader header = new SmppHeader(commandLength, commandId, commandStatus, sequenceNumber);
        switch (commandId) {
            case SmppConst.BIND_RECEIVER_ID:
                return new SmppBindReceiver(header, decodeBindReceiverBody(frame));
            case SmppConst.BIND_RECEIVER_RESP_ID:
                return new SmppBindReceiverResp(header, decodeBindReceiverRespBody(frame));
            case SmppConst.BIND_TRANSMITTER_ID:
                return new SmppBindTransmitter(header, decodeBindTransmitterBody(frame));
            case SmppConst.BIND_TRANSMITTER_RESP_ID:
                return new SmppBindTransmitterResp(header, decodeBindTransmitterRespBody(frame));
            case SmppConst.QUERY_SM_ID:
                return new SmppQuerySm(header, decodeQuerySmBody(frame));
            case SmppConst.QUERY_SM_RESP_ID:
                return new SmppQuerySmResp(header, decodeQuerySmRespBody(frame));
            case SmppConst.SUBMIT_SM_ID:
                return new SmppSubmitSm(header, decodeSubmitSmBody(frame));
            case SmppConst.SUBMIT_SM_RESP_ID:
                return new SmppSubmitSmResp(header, decodeSubmitSmRespBody(frame));
            case SmppConst.DELIVER_SM_ID:
                return new SmppDeliverSm(header, decodeDeliverSmBody(frame));
            case SmppConst.DELIVER_SM_RESP_ID:
                return new SmppDeliverSmResp(header, decodeDeliverSmRespBody(frame));
            case SmppConst.UNBIND_ID:
                return new SmppUnbind(header);
            case SmppConst.UNBIND_RESP_ID:
                return new SmppUnbindResp(header);
            case SmppConst.REPLACE_SM_ID:
                return new SmppReplaceSm(header, decodeReplaceSm(frame));
            case SmppConst.REPLACE_SM_RESP_ID:
                return new SmppReplaceSmResp(header);
            case SmppConst.CANCEL_SM_ID:
                return new SmppCancelSm(header, decodeCancelSm(frame));
            case SmppConst.CANCEL_SM_RESP_ID:
                return new SmppCancelSmResp(header);
            case SmppConst.BIND_TRANSCEIVER_ID:
                return new SmppBindTransceiver(header, decodeBindTransceiverBody(frame));
            case SmppConst.BIND_TRANSCEIVER_RESP_ID:
                return new SmppBindTransceiverResp(header, decodeBindTransceiverRespBody(frame));
            case SmppConst.OUTBIND_ID:
                return new SmppOutBind(header, decodeOutBind(frame));
            case SmppConst.ENQUIRE_LINK_ID:
                return new SmppEnquireLink(header);
            case SmppConst.ENQUIRE_LINK_RESP_ID:
                return new SmppEnquireLinkResp(header);
            case SmppConst.SUBMIT_MULTI_ID:
                return new SmppSubmitMulti(header, decodeSubmitMultiBody(frame));
            case SmppConst.SUBMIT_MULTI_RESP_ID:
                return new SmppSubmitMultiResp(header, decodeSubmitMultiRespBody(frame));
            default:
                throw new IllegalArgumentException("Unknown commandId: " + commandId);
        }
    }

    private SmppBindReceiverBody decodeBindReceiverBody(ByteBuf frame) {
        String systemId = readCString(frame);
        String password = readCString(frame);
        String systemType = readCString(frame);
        byte interfaceVersion = frame.readByte();
        byte addrTon = frame.readByte();
        byte addrNpi = frame.readByte();
        String addressRange = readCString(frame);
        return new SmppBindReceiverBody(systemId, password, systemType, interfaceVersion,
                addrTon, addrNpi, addressRange);
    }

    private SmppBindReceiverRespBody decodeBindReceiverRespBody(ByteBuf frame) {
        String systemId = readCString(frame);
        return new SmppBindReceiverRespBody(systemId);
    }

    private SmppBindTransmitterBody decodeBindTransmitterBody(ByteBuf frame) {
        String systemId = readCString(frame);
        String password = readCString(frame);
        String systemType = readCString(frame);
        byte interfaceVersion = frame.readByte();
        byte addrTon = frame.readByte();
        byte addrNpi = frame.readByte();
        String addressRange = readCString(frame);
        return new SmppBindTransmitterBody(systemId, password, systemType, interfaceVersion,
                addrTon, addrNpi, addressRange);
    }

    private SmppBindTransmitterRespBody decodeBindTransmitterRespBody(ByteBuf frame) {
        String systemId = readCString(frame);
        return new SmppBindTransmitterRespBody(systemId);
    }

    private SmppQuerySmBody decodeQuerySmBody(ByteBuf frame) {
        String messageId = readCString(frame);
        byte sourceAddrTon = frame.readByte();
        byte sourceAddrNpi = frame.readByte();
        String sourceAddr = readCString(frame);
        return new SmppQuerySmBody(messageId, sourceAddrTon, sourceAddrNpi, sourceAddr);
    }

    private SmppQuerySmRespBody decodeQuerySmRespBody(ByteBuf frame) {
        String messageId = readCString(frame);
        String finalDate = readCString(frame);
        byte messageState = frame.readByte();
        byte errorCode = frame.readByte();
        return new SmppQuerySmRespBody(messageId, finalDate, messageState, errorCode);
    }

    private SmppSubmitSmBody decodeSubmitSmBody(ByteBuf frame) {
        String serviceType = readCString(frame);
        byte sourceAddrTon = frame.readByte();
        byte sourceAddrNpi = frame.readByte();
        String sourceAddr = readCString(frame);
        byte destAddrTon = frame.readByte();
        byte destAddrNpi = frame.readByte();
        String destinationAddr = readCString(frame);
        byte esmClass = frame.readByte();
        byte protocolId = frame.readByte();
        byte priorityFlag = frame.readByte();
        String scheduleDeliveryTime = readCString(frame);
        String validityPeriod = readCString(frame);
        byte registeredDelivery = frame.readByte();
        byte replaceIfPresentFlag = frame.readByte();
        byte dataCoding = frame.readByte();
        byte smDefaultMsgId = frame.readByte();
        byte smLength = frame.readByte();
        byte[] shortMessage = new byte[smLength];
        frame.readBytes(shortMessage);
        return new SmppSubmitSmBody(serviceType, sourceAddrTon, sourceAddrNpi, sourceAddr,
                destAddrTon, destAddrNpi, destinationAddr, esmClass, protocolId, priorityFlag,
                scheduleDeliveryTime, validityPeriod, registeredDelivery, replaceIfPresentFlag,
                dataCoding, smDefaultMsgId, smLength, shortMessage);
    }

    private SmppSubmitSmRespBody decodeSubmitSmRespBody(ByteBuf frame) {
        String messageId = readCString(frame);
        return new SmppSubmitSmRespBody(messageId);
    }

    private SmppDeliverSmBody decodeDeliverSmBody(ByteBuf frame) {
        String serviceType = readCString(frame);
        byte sourceAddrTon = frame.readByte();
        byte sourceAddrNpi = frame.readByte();
        String sourceAddr = readCString(frame);
        byte destAddrTon = frame.readByte();
        byte destAddrNpi = frame.readByte();
        String destinationAddr = readCString(frame);
        byte esmClass = frame.readByte();
        byte protocolId = frame.readByte();
        byte priorityFlag = frame.readByte();
        String scheduleDeliveryTime = readCString(frame);
        String validityPeriod = readCString(frame);
        byte registeredDelivery = frame.readByte();
        byte replaceIfPresentFlag = frame.readByte();
        byte dataCoding = frame.readByte();
        byte smDefaultMsgId = frame.readByte();
        byte smLength = frame.readByte();
        byte[] shortMessage = new byte[smLength];
        frame.readBytes(shortMessage);
        return new SmppDeliverSmBody(serviceType, sourceAddrTon, sourceAddrNpi, sourceAddr,
                destAddrTon, destAddrNpi, destinationAddr, esmClass, protocolId, priorityFlag,
                scheduleDeliveryTime, validityPeriod, registeredDelivery, replaceIfPresentFlag,
                dataCoding, smDefaultMsgId, smLength, shortMessage);
    }

    private SmppDeliverSmRespBody decodeDeliverSmRespBody(ByteBuf frame) {
        String messageId = readCString(frame);
        return new SmppDeliverSmRespBody(messageId);
    }

    private SmppReplaceSmBody decodeReplaceSm(ByteBuf frame) {
        String messageId = readCString(frame);
        byte sourceAddrTon = frame.readByte();
        byte sourceAddrNpi = frame.readByte();
        String sourceAddr = readCString(frame);
        String scheduleDeliveryTime = readCString(frame);
        String validityPeriod = readCString(frame);
        byte registeredDelivery = frame.readByte();
        byte smDefaultMsgId = frame.readByte();
        byte smLength = frame.readByte();
        String shortMessage = readCString(frame);
        return new SmppReplaceSmBody(messageId, sourceAddrTon, sourceAddrNpi, sourceAddr, scheduleDeliveryTime,
                validityPeriod, registeredDelivery, smDefaultMsgId, smLength, shortMessage);
    }

    private SmppCancelSmBody decodeCancelSm(ByteBuf frame) {
        String serviceType = readCString(frame);
        String messageId = readCString(frame);
        byte sourceAddrTon = frame.readByte();
        byte sourceAddrNpi = frame.readByte();
        String sourceAddr = readCString(frame);
        byte destAddrTon = frame.readByte();
        byte destAddrNpi = frame.readByte();
        String destAddr = readCString(frame);
        return new SmppCancelSmBody(serviceType, messageId, sourceAddrTon, sourceAddrNpi, sourceAddr, destAddrTon,
                destAddrNpi, destAddr);
    }

    private SmppBindTransceiverBody decodeBindTransceiverBody(ByteBuf frame) {
        String systemId = readCString(frame);
        String password = readCString(frame);
        String systemType = readCString(frame);
        byte interfaceVersion = frame.readByte();
        byte addrTon = frame.readByte();
        byte addrNpi = frame.readByte();
        String addressRange = readCString(frame);
        return new SmppBindTransceiverBody(systemId, password, systemType, interfaceVersion,
                addrTon, addrNpi, addressRange);
    }

    private SmppBindTransceiverRespBody decodeBindTransceiverRespBody(ByteBuf frame) {
        String systemId = readCString(frame);
        return new SmppBindTransceiverRespBody(systemId);
    }


    private SmppOutBindBody decodeOutBind(ByteBuf frame) {
        String systemId = readCString(frame);
        String password = readCString(frame);
        return new SmppOutBindBody(systemId, password);
    }

    private SmppSubmitMultiBody decodeSubmitMultiBody(ByteBuf frame) {
        String serviceType = readCString(frame);
        byte sourceAddrTon = frame.readByte();
        byte sourceAddrNpi = frame.readByte();
        String sourceAddr = readCString(frame);
        byte numberOfDest = frame.readByte();
        List<DestAddress> destAddresses = new ArrayList<>();
        for (byte i = 0; i < numberOfDest; i++) {
            byte destAddrTon = frame.readByte();
            byte destAddrNpi = frame.readByte();
            String destinationAddr = readCString(frame);
            destAddresses.add(new DestAddress(destAddrTon, destAddrNpi, destinationAddr));
        }
        byte esmClass = frame.readByte();
        byte protocolId = frame.readByte();
        byte priorityFlag = frame.readByte();
        String scheduleDeliveryTime = readCString(frame);
        String validityPeriod = readCString(frame);
        byte registeredDelivery = frame.readByte();
        byte replaceIfPresentFlag = frame.readByte();
        byte dataCoding = frame.readByte();
        byte smDefaultMsgId = frame.readByte();
        byte smLength = frame.readByte();
        byte[] shortMessage = new byte[smLength];
        frame.readBytes(shortMessage);
        return new SmppSubmitMultiBody(serviceType, sourceAddrTon, sourceAddrNpi, sourceAddr,
                numberOfDest, destAddresses, esmClass, protocolId, priorityFlag, scheduleDeliveryTime,
                validityPeriod, registeredDelivery, replaceIfPresentFlag, dataCoding, smDefaultMsgId,
                smLength, shortMessage);
    }

    private SmppSubmitMultiRespBody decodeSubmitMultiRespBody(ByteBuf frame) {
        String messageId = readCString(frame);
        byte noUnsuccess = frame.readByte();
        List<UnsuccessfulDelivery> unsuccessSmes = new ArrayList<>();
        for (byte i = 0; i < noUnsuccess; i++) {
            byte destAddrTon = frame.readByte();
            byte destAddrNpi = frame.readByte();
            String destinationAddr = readCString(frame);
            byte errorStatus = frame.readByte();
            unsuccessSmes.add(new UnsuccessfulDelivery(destAddrTon, destAddrNpi, destinationAddr, errorStatus));
        }
        return new SmppSubmitMultiRespBody(messageId, noUnsuccess, unsuccessSmes);
    }

    private String readCString(ByteBuf frame) {
        int length = frame.bytesBefore((byte) 0);
        if (length == -1) {
            throw new IllegalArgumentException("No \\0 found");
        }
        CharSequence charSequence = frame.readCharSequence(length, StandardCharsets.UTF_8);
        frame.skipBytes(1);
        return charSequence.toString();
    }

}
