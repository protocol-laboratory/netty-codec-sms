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
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SmgpDecoder extends LengthFieldBasedFrameDecoder {

    public SmgpDecoder() {
        this(SmgpConst.DEFAULT_MAX_BYTES_IN_MESSAGE);
    }

    public SmgpDecoder(int maxBytesInMessage) {
        super(maxBytesInMessage, 0, 4, -4, 0);
    }

    @Override
    protected SmgpMessage decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        } else {
            try {
                return decode(frame);
            } finally {
                frame.release();
            }
        }
    }

    SmgpMessage decode(ByteBuf frame) {
        int packetLength = frame.readInt();
        int requestID = frame.readInt();
        int sequenceID = frame.readInt();
        SmgpHeader header = new SmgpHeader(packetLength, requestID, sequenceID);
        switch (requestID) {
            case SmgpConst.LOGIN_ID:
                return new SmgpLogin(header, decodeLogin(frame));
            case SmgpConst.LOGIN_RESP_ID:
                return new SmgpLoginResp(header, decodeLoginResp(frame));
            case SmgpConst.SUBMIT_ID:
                return new SmgpSubmit(header, decodeSubmit(frame));
            case SmgpConst.SUBMIT_RESP_ID:
                return new SmgpSubmitResp(header, decodeSubmitResp(frame));
            case SmgpConst.ACTIVE_TEST_ID:
                return new SmgpActiveTest(header);
            case SmgpConst.ACTIVE_TEST_RESP_ID:
                return new SmgpActiveRespTest(header);
            default:
                throw new IllegalArgumentException("Unknown requestId: " + requestID);
        }
    }

    private SmgpLoginBody decodeLogin(ByteBuf frame) {
        String clientId = readString(frame, SmgpConst.LEN_CLIENT_ID);
        String authenticatorClient = readString(frame, SmgpConst.LEN_AUTHENTICATOR_CLIENT);
        byte loginMode = frame.readByte();
        int timestamp = frame.readInt();
        byte version = frame.readByte();
        return new SmgpLoginBody(clientId, authenticatorClient, loginMode, timestamp, version);
    }

    private SmgpLoginRespBody decodeLoginResp(ByteBuf frame) {
        int status = frame.readInt();
        String authenticatorServer = readString(frame, SmgpConst.LEN_AUTHENTICATOR_SERVER);
        byte version = frame.readByte();
        return new SmgpLoginRespBody(status, authenticatorServer, version);
    }


    private SmgpSubmitBody decodeSubmit(ByteBuf frame) {
        byte subType = frame.readByte();
        byte needReport = frame.readByte();
        byte priority = frame.readByte();
        String serviceId = readString(frame, SmgpConst.LEN_SERVICE_ID);
        String feeType = readString(frame, SmgpConst.LEN_FEE_TYPE);
        String feeCode = readString(frame, SmgpConst.LEN_FEE_CODE);
        String msgFormat = readString(frame, SmgpConst.LEN_MESSAGE_FORMAT);
        String validTime = readString(frame, SmgpConst.LEN_VALID_TIME);
        String atTime = readString(frame, SmgpConst.LEN_AT_TIME);
        String srcTermId = readString(frame, SmgpConst.LEN_SRC_TERM_ID);
        String chargeTermId = readString(frame, SmgpConst.LEN_CHARGE_TERM_ID);
        byte destTermIdCount = frame.readByte();
        List<String> destTermIds = new ArrayList<>();
        for (byte i = 0; i < destTermIdCount; i++) {
            destTermIds.add(readString(frame, SmgpConst.LEN_DEST_TERM_ID));
        }
        byte msgLength = frame.readByte();
        String msgContent = readString(frame, msgLength);
        String reserve = readString(frame, SmgpConst.LEN_SUBMIT_RESERVE);
        return new SmgpSubmitBody(subType, needReport, priority, serviceId, feeType, feeCode, msgFormat, validTime,
                atTime, srcTermId, chargeTermId, destTermIdCount, destTermIds, msgLength, msgContent, reserve);
    }

    private SmgpSubmitRespBody decodeSubmitResp(ByteBuf frame) {
        String msgId = readString(frame, SmgpConst.LEN_SUBMIT_RESP_MESSAGE_ID);
        int status = frame.readInt();
        return new SmgpSubmitRespBody(msgId, status);
    }

    private String readString(ByteBuf byteBuf, int length) {
        int idx = 0;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            byte b = byteBuf.readByte();
            if (b == 0 && idx == 0) {
                idx = i;
            }
            bytes[i] = b;
        }
        if (idx == 0) {
            idx = length;
        }
        return new String(bytes, 0, idx, StandardCharsets.UTF_8);
    }
}
