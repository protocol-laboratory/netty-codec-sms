package io.github.protocol.codec.smpp;

public class EsmClassConst {
    private EsmClassConst() {
    }

    public static final byte DEFAULT = 0x00;
    public static final byte DATAGRAM = 0x01;
    public static final byte FORWARD = 0x04;
    public static final byte STORE_AND_FORWARD = 0x05;
}
