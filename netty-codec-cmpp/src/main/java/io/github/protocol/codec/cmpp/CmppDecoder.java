package io.github.protocol.codec.cmpp;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class CmppDecoder extends LengthFieldBasedFrameDecoder {

    public CmppDecoder() {
        this(CmppConst.DEFAULT_MAX_BYTES_IN_MESSAGE);
    }

    public CmppDecoder(int maxBytesInMessage) {
        super(maxBytesInMessage, 0, 4, -4, 0);
    }
}
