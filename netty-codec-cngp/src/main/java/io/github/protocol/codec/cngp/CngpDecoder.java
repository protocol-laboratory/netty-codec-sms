package io.github.protocol.codec.cngp;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class CngpDecoder extends LengthFieldBasedFrameDecoder {

    public CngpDecoder() {
        this(CngpConst.DEFAULT_MAX_BYTES_IN_MESSAGE);
    }

    public CngpDecoder(int maxBytesInMessage) {
        super(maxBytesInMessage, 0, 4, -4, 0);
    }
}
