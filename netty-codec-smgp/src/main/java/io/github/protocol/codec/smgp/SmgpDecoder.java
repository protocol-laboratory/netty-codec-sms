package io.github.protocol.codec.smgp;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class SmgpDecoder extends LengthFieldBasedFrameDecoder {

    public SmgpDecoder() {
        this(SmgpConst.DEFAULT_MAX_BYTES_IN_MESSAGE);
    }

    public SmgpDecoder(int maxBytesInMessage) {
        super(maxBytesInMessage, 0, 4, -4, 0);
    }
}
