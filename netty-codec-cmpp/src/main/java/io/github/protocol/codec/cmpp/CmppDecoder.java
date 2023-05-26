package io.github.protocol.codec.cmpp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CmppDecoder extends LengthFieldBasedFrameDecoder {

    public CmppDecoder() {
        this(CmppConst.DEFAULT_MAX_BYTES_IN_MESSAGE);
    }

    public CmppDecoder(int maxBytesInMessage) {
        super(maxBytesInMessage, 0, 4, -4, 0);
    }

    @Override
    protected CmppMessage decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
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

    CmppMessage decode(ByteBuf frame) {
        int totalLength = frame.readInt();
        int commandId = frame.readInt();
        int sequenceId = frame.readInt();
        CmppHeader header = new CmppHeader(totalLength, commandId, sequenceId);
        switch (commandId) {
            case CmppConst.CONNECT_ID:
                return new CmppConnect(header, decodeConnectBody(frame));
            case CmppConst.CONNECT_RESP_ID:
                return new CmppConnectResp(header, decodeConnectRespBody(frame));
            case CmppConst.SUBMIT_ID:
                return new CmppSubmit(header, decodeSubmit(frame));
            case CmppConst.SUBMIT_RESP_ID:
                return new CmppSubmitResp(header, decodeSubmitResp(frame));
            case CmppConst.ACTIVE_TEST_ID:
                return new CmppActiveTest(header);
            case CmppConst.ACTIVE_TEST_RESP_ID:
                return new CmppActiveTestResp(header, decodeActiveTestRespBody(frame));
            default:
                throw new IllegalArgumentException("Unknown commandId: " + commandId);
        }
    }

    private CmppConnectBody decodeConnectBody(ByteBuf byteBuf) {
        String sourceAddr = readString(byteBuf, CmppConst.LEN_SOURCE_ADDR);
        String authenticatorSource = readString(byteBuf, CmppConst.LEN_AUTHENTICATOR_SOURCE);
        byte version = byteBuf.readByte();
        int timestamp = byteBuf.readInt();
        return new CmppConnectBody(sourceAddr, authenticatorSource, version, timestamp);
    }

    private CmppConnectRespBody decodeConnectRespBody(ByteBuf buf) {
        int status = buf.readInt();
        String authenticatorISMG = readString(buf, CmppConst.LEN_AUTHENTICATOR_ISMG);
        byte version = buf.readByte();
        return new CmppConnectRespBody(status, authenticatorISMG, version);
    }

    private CmppSubmitBody decodeSubmit(ByteBuf buf) {
        long msgId = buf.readLong();
        byte pkTotal = buf.readByte();
        byte pkNumber = buf.readByte();
        byte registeredDelivery = buf.readByte();
        byte msgLevel = buf.readByte();
        String serviceId = readString(buf, CmppConst.LEN_SERVICE_ID);
        byte feeUserType = buf.readByte();
        String feeTerminalId = readString(buf, CmppConst.LEN_FEE_TERMINAL_ID);
        byte feeTerminalType = buf.readByte();
        byte tpPid = buf.readByte();
        byte tpUdhi = buf.readByte();
        byte msgFmt = buf.readByte();
        String messageSrc = readString(buf, CmppConst.LEN_MESSAGE_SRC);
        String feeType = readString(buf, CmppConst.LEN_FEE_TYPE);
        String feeCode = readString(buf, CmppConst.LEN_FEE_CODE);
        String validTime = readString(buf, CmppConst.LEN_VALID_TIME);
        String atTime = readString(buf, CmppConst.LEN_AT_TIME);
        String srcId = readString(buf, CmppConst.LEN_SRC_ID);
        byte destUsrTl = buf.readByte();
        List<String> destTerminalIds = new ArrayList<>();
        for (byte i = 0; i < destUsrTl; i++) {
            destTerminalIds.add(readCString(buf, CmppConst.LEN_DEST_TERMINAL_ID));
        }
        byte destTerminalType = buf.readByte();
        short msgLength = buf.readUnsignedByte();
        byte[] msgContent = new byte[msgLength];
        buf.readBytes(msgContent);
        String linkId = readString(buf, CmppConst.LEN_LINK_ID);
        return new CmppSubmitBody(msgId, pkTotal, pkNumber, registeredDelivery, msgLevel, serviceId, feeUserType
                , feeTerminalId, feeTerminalType, tpPid, tpUdhi, msgFmt, messageSrc, feeType, feeCode, validTime
                , atTime, srcId, destUsrTl, destTerminalIds, destTerminalType, msgLength, msgContent, linkId);
    }

    private CmppSubmitRespBody decodeSubmitResp(ByteBuf buf) {
        long msgId = buf.readLong();
        int result = buf.readInt();
        return new CmppSubmitRespBody(msgId, result);
    }

    private CmppActiveTestRespBody decodeActiveTestRespBody(ByteBuf buf) {
        byte reserved = buf.readByte();
        return new CmppActiveTestRespBody(reserved);

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

    private String readCString(ByteBuf frame, int fixLength) {
        int length = frame.bytesBefore((byte) 0);
        if (length == -1) {
            throw new IllegalArgumentException("No \\0 found");
        }
        CharSequence charSequence = frame.readCharSequence(length, StandardCharsets.UTF_8);
        frame.skipBytes(fixLength - length);
        return charSequence.toString();
    }
}
