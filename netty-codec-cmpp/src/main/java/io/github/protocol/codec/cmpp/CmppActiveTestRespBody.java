package io.github.protocol.codec.cmpp;

public class CmppActiveTestRespBody {

    private final byte reserved;

    public CmppActiveTestRespBody(byte reserved) {
        this.reserved = reserved;
    }

    public byte reserved() {
        return reserved;
    }
}
