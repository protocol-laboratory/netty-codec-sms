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
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CngpDecoder extends LengthFieldBasedFrameDecoder {

    public CngpDecoder() {
        this(CngpConst.DEFAULT_MAX_BYTES_IN_MESSAGE);
    }

    public CngpDecoder(int maxBytesInMessage) {
        super(maxBytesInMessage, 0, 4, -4, 0);
    }

    @Override
    protected CngpMessage decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
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

    CngpMessage decode(ByteBuf frame) {
        int totalLength = frame.readInt();
        int commandId = frame.readInt();
        int commandStatus = frame.readInt();
        int sequenceId = frame.readInt();
        CngpHeader header = new CngpHeader(totalLength, commandId, commandStatus, sequenceId);
        switch (commandId) {
            case CngpConst.LOGIN_ID:
                return new CngpLogin(header, decodeLogin(frame));
            case CngpConst.LOGIN_RESP_ID:
                return new CngpLoginResp(header, decodeLoginResp(frame));
            case CngpConst.SUBMIT_ID:
                return new CngpSubmit(header, decodeSubmit(frame));
            case CngpConst.SUBMIT_RESP_ID:
                return new CngpSubmitResp(header, decodeSubmitResp(frame));
            case CngpConst.DELIVER_ID:
                return new CngpDeliver(header, decodeDeliver(frame));
            case CngpConst.DELIVER_RESP_ID:
                return new CngpDeliverResp(header, decodeDeliverResp(frame));
            case CngpConst.ACTIVE_TEST_ID:
                return new CngpActiveTest(header);
            case CngpConst.ACTIVE_TEST_RESP_ID:
                return new CngpActiveRespTest(header);
            case CngpConst.EXIT_ID:
                return new CngpExit(header);
            case CngpConst.EXIT_RESP_ID:
                return new CngpExitResp(header);
            default:
                throw new IllegalArgumentException("Unknown commandId: " + commandId);
        }
    }

    private CngpLoginBody decodeLogin(ByteBuf frame) {
        String clientId = readString(frame, CngpConst.LEN_CLIENT_ID);
        String authenticatorClient = readString(frame, CngpConst.LEN_LOGIN_AUTHENTICATOR_CLIENT);
        byte loginMode = frame.readByte();
        int timeStamp = frame.readInt();
        byte version = frame.readByte();
        return new CngpLoginBody(clientId, authenticatorClient, loginMode, timeStamp, version);
    }

    private CngpLoginRespBody decodeLoginResp(ByteBuf frame) {
        String authenticatorClient = readString(frame, CngpConst.LEN_LOGIN_RESP_AUTHENTICATOR_CLIENT);
        byte version = frame.readByte();
        return new CngpLoginRespBody(authenticatorClient, version);
    }

    private CngpSubmitBody decodeSubmit(ByteBuf frame) {
        String spId = readString(frame, CngpConst.LEN_SP_ID);
        byte subType = frame.readByte();
        byte needReport = frame.readByte();
        byte priority = frame.readByte();
        String serviceId = readString(frame, CngpConst.LEN_SERVICE_ID);
        String feeType = readString(frame, CngpConst.LEN_FEE_TYPE);
        String feeUserType = readString(frame, CngpConst.LEN_FEE_USER_TYPE);
        String feeCode = readString(frame, CngpConst.LEN_FEE_CODE);
        String msgFormat = readString(frame, CngpConst.LEN_MESSAGE_FORMAT);
        String validTime = readString(frame, CngpConst.LEN_VALID_TIME);
        String atTime = readString(frame, CngpConst.LEN_AT_TIME);
        String srcTermId = readString(frame, CngpConst.LEN_SUBMIT_SRC_TERM_ID);
        String chargeTermId = readString(frame, CngpConst.LEN_CHARGE_TERM_ID);
        byte destTermIdCount = frame.readByte();
        List<String> destTermId = new ArrayList<>();
        for (byte i = 0; i < destTermIdCount; i++) {
            destTermId.add(readString(frame, CngpConst.LEN_DEST_TERM_ID));
        }
        byte msgLength = frame.readByte();
        byte[] msgContent = new byte[msgLength];
        frame.readBytes(msgContent);
        return new CngpSubmitBody(spId, subType, needReport, priority, serviceId, feeType, feeUserType, feeCode,
                msgFormat, validTime, atTime, srcTermId, chargeTermId, destTermIdCount,
                destTermId, msgLength, msgContent);
    }

    private CngpSubmitRespBody decodeSubmitResp(ByteBuf frame) {
        String msgId = readString(frame, CngpConst.LEN_DELIVER_MESSAGE_ID);
        return new CngpSubmitRespBody(msgId);
    }

    private CngpDeliverBody decodeDeliver(ByteBuf frame) {
        String msgId = readString(frame, CngpConst.LEN_DELIVER_MESSAGE_ID);
        byte isReport = frame.readByte();
        byte msgFormat = frame.readByte();
        String recvTime = readString(frame, CngpConst.LEN_RECV_TIME);
        String srcTermId = readString(frame, CngpConst.LEN_DELIVER_SRC_TERM_ID);
        String destTermId = readString(frame, CngpConst.LEN_DELIVER_DEST_TERM_ID);
        byte msgLength = frame.readByte();
        byte[] msgContent = new byte[msgLength];
        frame.readBytes(msgContent);
        return new CngpDeliverBody(msgId, isReport, msgFormat, recvTime, srcTermId, destTermId, msgLength, msgContent);
    }

    private CngpDeliverRespBody decodeDeliverResp(ByteBuf frame) {
        String msgId = readString(frame, CngpConst.LEN_DELIVER_MESSAGE_ID);
        return new CngpDeliverRespBody(msgId);
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
