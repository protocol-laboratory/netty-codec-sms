package io.github.protocol.codec.cmpp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.StandardCharsets;

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
