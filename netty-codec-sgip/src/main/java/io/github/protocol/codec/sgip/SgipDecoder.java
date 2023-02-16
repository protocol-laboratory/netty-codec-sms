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
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.StandardCharsets;

public class SgipDecoder extends LengthFieldBasedFrameDecoder {

    public SgipDecoder() {
        this(SgipConst.DEFAULT_MAX_BYTES_IN_MESSAGE);
    }

    public SgipDecoder(int maxBytesInMessage) {
        super(maxBytesInMessage, 0, 4, -4, 0);
    }

    @Override
    protected SgipMessage decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
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

    SgipMessage decode(ByteBuf frame) {
        int messageLength = frame.readInt();
        int commandId = frame.readInt();
        long sequenceNumber = frame.readLong();
        SgipHeader sgipHeader = new SgipHeader(messageLength, commandId, sequenceNumber);
        switch (commandId) {
            case SgipConst.BIND_ID:
                return new SgipBind(sgipHeader, decodeBind(frame));
            case SgipConst.BIND_RESP_ID:
                return new SgipBindResp(sgipHeader, decodeBindResp(frame));
            case SgipConst.UNBIND_ID:
                return new SgipUnbind(sgipHeader);
            case SgipConst.UNBIND_RESP_ID:
                return new SgipUnbindResp(sgipHeader);
            case SgipConst.SUBMIT_ID:
                return new SgipSubmit(sgipHeader, decodeSubmit(frame));
            case SgipConst.SUBMIT_RESP_ID:
                return new SgipSubmitResp(sgipHeader, decodeSubmitResp(frame));
            case SgipConst.DELIVER_ID:
                return new SgipDeliver(sgipHeader, decodeDeliver(frame));
            case SgipConst.DELIVER_RESP_ID:
                return new SgipDeliverResp(sgipHeader, decodeDeliverResp(frame));
            case SgipConst.REPORT_ID:
                return new SgipReport(sgipHeader, decodeReport(frame));
            case SgipConst.REPORT_RESP_ID:
                return new SgipReportResp(sgipHeader, decodeReportResp(frame));
            case SgipConst.USERRPT_ID:
                return new SgipUserRpt(sgipHeader, decodeUserRpt(frame));
            case SgipConst.USERRPT_RESP_ID:
                return new SgipUserRptResp(sgipHeader, decodeUserRptResp(frame));
            case SgipConst.TRACE_ID:
                return new SgipTrace(sgipHeader, decodeTrace(frame));
            case SgipConst.TRACE_RESP_ID:
                return new SgipTraceResp(sgipHeader, decodeTraceResp(frame));
            default:
                throw new IllegalArgumentException("Unknown commandId: " + commandId);
        }
    }

    private SgipBindBody decodeBind(ByteBuf frame) {
        byte loginType = frame.readByte();
        String loginName = readString(frame, SgipConst.LEN_LOGIN_NAME);
        String loginPassword = readString(frame, SgipConst.LEN_LOGIN_PASSWORD);
        String reserve = readString(frame, SgipConst.LEN_BIND_RESERVE);
        return new SgipBindBody(loginType, loginName, loginPassword, reserve);
    }

    private SgipBindRespBody decodeBindResp(ByteBuf frame) {
        byte result = frame.readByte();
        String reserve = readString(frame, SgipConst.LEN_BIND_RESP_RESERVE);
        return new SgipBindRespBody(result, reserve);
    }

    private SgipSubmitBody decodeSubmit(ByteBuf frame) {
        String spNumber = readCString(frame);
        String chargeNumber = readCString(frame);
        String userNumber = readCString(frame);
        String corpId = readCString(frame);
        String serviceType = readCString(frame);
        String feeType = readCString(frame);
        String feeValue = readCString(frame);
        String givenValue = readCString(frame);
        String agentFlag = readCString(frame);
        String morelatetoMTFlag = readCString(frame);
        long priority = frame.readLong();
        String expireTime = readCString(frame);
        String scheduleTime = readCString(frame);
        long reportFlag = frame.readLong();
        long messageType = frame.readLong();
        long tpPid = frame.readLong();
        long tpUdhi = frame.readLong();
        long messageCoding = frame.readLong();
        long messageContent = frame.readLong();
        return new SgipSubmitBody(spNumber, chargeNumber, userNumber, corpId, serviceType, feeType, feeValue,
                givenValue, agentFlag, morelatetoMTFlag, priority, expireTime, scheduleTime, reportFlag,
                messageType, tpPid, tpUdhi, messageCoding, messageContent);
    }

    private SgipSubmitRespBody decodeSubmitResp(ByteBuf frame) {
        return new SgipSubmitRespBody(frame.readLong());
    }

    private SgipDeliverBody decodeDeliver(ByteBuf frame) {
        String userNumber = readCString(frame);
        String spNumber = readCString(frame);
        String tpPid = readCString(frame);
        String tpUdhi = readCString(frame);
        long messageCoding = frame.readLong();
        long messageContent = frame.readLong();
        return new SgipDeliverBody(userNumber, spNumber, tpPid, tpUdhi, messageCoding, messageContent);
    }

    private SgipDeliverRespBody decodeDeliverResp(ByteBuf frame) {
        return new SgipDeliverRespBody(frame.readLong());
    }

    private SgipReportBody decodeReport(ByteBuf frame) {
        long submitSequenceNumber = frame.readLong();
        long reportType = frame.readLong();
        String userNumber = readCString(frame);
        long state = frame.readLong();
        long errorCode = frame.readLong();
        return new SgipReportBody(submitSequenceNumber, reportType, userNumber, state, errorCode);
    }

    private SgipReportRespBody decodeReportResp(ByteBuf frame) {
        return new SgipReportRespBody(frame.readLong());
    }

    private SgipUserRptBody decodeUserRpt(ByteBuf frame) {
        String spNumber = readCString(frame);
        String userNumber = readCString(frame);
        long userCondition = frame.readLong();
        return new SgipUserRptBody(spNumber, userNumber, userCondition);
    }

    private SgipUserRptRespBody decodeUserRptResp(ByteBuf frame) {
        return new SgipUserRptRespBody(frame.readLong());
    }

    private SgipTraceBody decodeTrace(ByteBuf frame) {
        long submitSequenceNumber = frame.readLong();
        String userNumber = readCString(frame);
        return new SgipTraceBody(submitSequenceNumber, userNumber);
    }

    private SgipTraceRespBody decodeTraceResp(ByteBuf frame) {
        long count = frame.readLong();
        long result = frame.readLong();
        String nodeId = readCString(frame);
        String receiveTime = readCString(frame);
        String sendTime = readCString(frame);
        return new SgipTraceRespBody(count, result, nodeId, receiveTime, sendTime);
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
