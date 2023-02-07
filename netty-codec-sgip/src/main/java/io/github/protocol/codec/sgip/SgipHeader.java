package io.github.protocol.codec.sgip;

public class SgipHeader {

    private final int messageLength;

    private final int commandId;

    private final long sequenceNumber;

    public SgipHeader(int commandId, long sequenceNumber) {
        this(0, commandId, sequenceNumber);
    }

    public SgipHeader(int messageLength, int commandId, long sequenceNumber) {
        this.messageLength = messageLength;
        this.commandId = commandId;
        this.sequenceNumber = sequenceNumber;
    }
}
