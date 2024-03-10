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
            case SgipConst.UNBIND_ID:
            case SgipConst.UNBIND_RESP_ID:
                return encodeHeader(ctx, message.header());
            case SgipConst.SUBMIT_ID:
                return encodeSubmit(ctx, (SgipSubmit) message);
            case SgipConst.SUBMIT_RESP_ID:
                return encodeSubmitResp(ctx, (SgipSubmitResp) message);
            case SgipConst.DELIVER_ID:
                return encodeDeliver(ctx, (SgipDeliver) message);
            case SgipConst.DELIVER_RESP_ID:
                return encodeDeliverResp(ctx, (SgipDeliverResp) message);
            case SgipConst.REPORT_ID:
                return encodeReport(ctx, (SgipReport) message);
            case SgipConst.REPORT_RESP_ID:
                return encodeReportResp(ctx, (SgipReportResp) message);
            case SgipConst.USERRPT_ID:
                return encodeUserRpt(ctx, (SgipUserRpt) message);
            case SgipConst.USERRPT_RESP_ID:
                return encodeUserRptResp(ctx, (SgipUserRptResp) message);
            case SgipConst.TRACE_ID:
                return encodeTrace(ctx, (SgipTrace) message);
            case SgipConst.TRACE_RESP_ID:
                return encodeTraceResp(ctx, (SgipTraceResp) message);
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
        writeHeader(buf, message.header(), SgipConst.LEN_BIND_RESP_MSG);
        buf.writeByte(message.body().result());
        writeString(buf, message.body().reserve(), SgipConst.LEN_BIND_RESP_RESERVE);
        return buf;
    }

    private ByteBuf encodeSubmit(ChannelHandlerContext ctx, SgipSubmit message) {
        int bodyLen = submitBodyLen(message.body());
        ByteBuf buf = ctx.alloc().buffer(bodyLen);
        writeHeader(buf, message.header(), bodyLen);
        SgipSubmitBody submitBody = message.body();
        writeString(buf, submitBody.spNumber(), len(submitBody.spNumber()));
        writeString(buf, submitBody.chargeNumber(), len(submitBody.chargeNumber()));
        writeString(buf, submitBody.userNumber(), len(submitBody.userNumber()));
        writeString(buf, submitBody.corpId(), len(submitBody.corpId()));
        writeString(buf, submitBody.serviceType(), len(submitBody.serviceType()));
        writeString(buf, submitBody.feeType(), len(submitBody.feeType()));
        writeString(buf, submitBody.feeValue(), len(submitBody.feeValue()));
        writeString(buf, submitBody.givenValue(), len(submitBody.givenValue()));
        writeString(buf, submitBody.agentFlag(), len(submitBody.agentFlag()));
        writeString(buf, submitBody.morelatetoMTFlag(), len(submitBody.morelatetoMTFlag()));
        buf.writeLong(submitBody.priority());
        writeString(buf, submitBody.expireTime(), len(submitBody.expireTime()));
        writeString(buf, submitBody.scheduleTime(), len(submitBody.scheduleTime()));
        buf.writeLong(submitBody.reportFlag());
        buf.writeLong(submitBody.messageType());
        buf.writeLong(submitBody.tpPid());
        buf.writeLong(submitBody.tpUdhi());
        buf.writeLong(submitBody.messageCoding());
        buf.writeLong(submitBody.messageContent());
        return buf;
    }


    private ByteBuf encodeSubmitResp(ChannelHandlerContext ctx, SgipSubmitResp message) {
        SgipSubmitRespBody body = message.body();
        ByteBuf buf = ctx.alloc().buffer(SgipConst.LEN_SUBMIT_RESP_MSG);
        writeHeader(buf, message.header(), SgipConst.LEN_SUBMIT_RESP_MSG);
        buf.writeLong(body.result());
        return buf;
    }


    private ByteBuf encodeDeliver(ChannelHandlerContext ctx, SgipDeliver message) {
        SgipDeliverBody deliverBody = message.body();
        int bodyLen = deliverBodyLen(deliverBody);
        ByteBuf buf = ctx.alloc().buffer(bodyLen);
        writeHeader(buf, message.header(), bodyLen);
        writeString(buf, deliverBody.userNumber(), len(deliverBody.userNumber()));
        writeString(buf, deliverBody.spNumber(), len(deliverBody.spNumber()));
        writeString(buf, deliverBody.tpPid(), len(deliverBody.tpPid()));
        writeString(buf, deliverBody.tpUdhi(), len(deliverBody.tpUdhi()));
        buf.writeLong(deliverBody.messageCoding());
        buf.writeLong(deliverBody.messageContent());
        return buf;
    }

    private ByteBuf encodeDeliverResp(ChannelHandlerContext ctx, SgipDeliverResp message) {
        ByteBuf buf = ctx.alloc().buffer(SgipConst.LEN_DELIVER_RESP_MSG);
        writeHeader(buf, message.header(), SgipConst.LEN_DELIVER_RESP_MSG);
        buf.writeLong(message.body().result());
        return buf;
    }

    private ByteBuf encodeReport(ChannelHandlerContext ctx, SgipReport message) {
        SgipReportBody reportBody = message.body();
        int bodyLen = reportBodyLen(reportBody);
        ByteBuf buf = ctx.alloc().buffer(bodyLen);
        writeHeader(buf, message.header(), bodyLen);
        buf.writeLong(reportBody.submitSequenceNumber());
        buf.writeLong(reportBody.reportType());
        writeString(buf, reportBody.userNumber(), len(reportBody.userNumber()));
        buf.writeLong(reportBody.state());
        buf.writeLong(reportBody.errorCode());
        return buf;
    }

    private ByteBuf encodeReportResp(ChannelHandlerContext ctx, SgipReportResp message) {
        SgipReportRespBody reportRespBody = message.body();
        ByteBuf buf = ctx.alloc().buffer(SgipConst.LEN_REPORT_RESP_MSG);
        writeHeader(buf, message.header(), SgipConst.LEN_REPORT_RESP_MSG);
        buf.writeLong(reportRespBody.result());
        return buf;
    }


    private ByteBuf encodeUserRpt(ChannelHandlerContext ctx, SgipUserRpt message) {
        SgipUserRptBody userRptBody = message.body();
        int bodyLen = userRptBodyLen(userRptBody);
        ByteBuf buf = ctx.alloc().buffer(bodyLen);
        writeHeader(buf, message.header(), bodyLen);
        writeString(buf, userRptBody.spNumber(), len(userRptBody.spNumber()));
        writeString(buf, userRptBody.userNumber(), len(userRptBody.userNumber()));
        buf.writeLong(userRptBody.userCondition());
        return buf;
    }
    private ByteBuf encodeUserRptResp(ChannelHandlerContext ctx, SgipUserRptResp message) {
        ByteBuf buf = ctx.alloc().buffer(SgipConst.LEN_USERRPT_RESP_MSG);
        writeHeader(buf, message.header(), SgipConst.LEN_USERRPT_RESP_MSG);
        buf.writeLong(message.body().result());
        return buf;
    }

    private ByteBuf encodeTrace(ChannelHandlerContext ctx, SgipTrace message) {
        SgipTraceBody traceBody = message.body();
        int bodyLen = traceBodyLen(traceBody);
        ByteBuf buf = ctx.alloc().buffer(bodyLen);
        writeHeader(buf, message.header(), bodyLen);
        buf.writeLong(message.body().submitSequenceNumber());
        writeString(buf, traceBody.userNumber(), len(traceBody.userNumber()));
        return buf;
    }

    private ByteBuf encodeTraceResp(ChannelHandlerContext ctx, SgipTraceResp message) {
        SgipTraceRespBody traceRespBody = message.body();
        int bodyLen = traceRespBodyLen(traceRespBody);
        ByteBuf buf = ctx.alloc().buffer(bodyLen);
        writeHeader(buf, message.header(), bodyLen);
        buf.writeLong(message.body().count());
        buf.writeLong(message.body().result());
        writeString(buf, traceRespBody.nodeId(), len(traceRespBody.nodeId()));
        writeString(buf, traceRespBody.receiveTime(), len(traceRespBody.receiveTime()));
        writeString(buf, traceRespBody.sendTime(), len(traceRespBody.sendTime()));
        return buf;
    }

    private ByteBuf encodeHeader(ChannelHandlerContext ctx, SgipHeader header) {
        ByteBuf buf = ctx.alloc().buffer(SgipConst.LEN_HEADER);
        if (header.messageLength() == 0) {
            buf.writeInt(SgipConst.LEN_HEADER);
        } else {
            buf.writeInt(header.messageLength());
        }
        buf.writeInt(header.commandId());
        buf.writeLong(header.sequenceNumber());
        return buf;
    }

    private int deliverBodyLen(SgipDeliverBody body) {
        return len(body.userNumber())
                + len(body.spNumber())
                + len(body.tpPid())
                + len(body.tpUdhi())
                + SgipConst.LEN_DELIVER_MSG;
    }

    private int submitBodyLen(SgipSubmitBody body) {
        return len(body.spNumber()) + len(body.chargeNumber()) + len(body.userNumber()) + len(body.corpId())
                + len(body.serviceType()) + len(body.feeType()) + len(body.feeValue()) + len(body.givenValue())
                + len(body.givenValue()) + len(body.morelatetoMTFlag()) + len(body.expireTime())
                + len(body.scheduleTime()) + SgipConst.LEN_SUBMIT_MSG;
    }

    private int reportBodyLen(SgipReportBody body) {
        return len(body.userNumber()) + SgipConst.LEN_REPORT_MSG;
    }

    private int userRptBodyLen(SgipUserRptBody body) {
        return len(body.spNumber()) + len(body.userNumber()) + SgipConst.LEN_USERRPT_MSG;
    }

    private int traceBodyLen(SgipTraceBody body) {
        return len(body.userNumber()) + SgipConst.LEN_TRACE_MSG;
    }

    private int traceRespBodyLen(SgipTraceRespBody body) {
        return len(body.nodeId())
                + len(body.receiveTime())
                + len(body.sendTime())
                + SgipConst.LEN_TRACE_RESP_MSG;
    }

    private int len(String str) {
        return str == null ? 1 : str.length() + 1;
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
