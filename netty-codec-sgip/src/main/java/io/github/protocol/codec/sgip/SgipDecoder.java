package io.github.protocol.codec.sgip;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class SgipDecoder extends LengthFieldBasedFrameDecoder {

    public SgipDecoder() {
        this(SgipConst.DEFAULT_MAX_BYTES_IN_MESSAGE);
    }

    public SgipDecoder(int maxBytesInMessage) {
        super(maxBytesInMessage, 0, 4, -4, 0);
    }
}
