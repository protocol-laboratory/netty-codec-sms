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

package io.github.protocol.codec.smpp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class TagLengthValue {

    private final short tag;

    private final int length;

    private final byte[] value;

    public TagLengthValue(short tag, int length, byte[] value) {
        this.tag = tag;
        this.length = length;
        this.value = value;
        if (length != value.length) {
            throw new IllegalArgumentException(
                    String.format("length of value is unexpected, expect: %d, actual: %d", length, value.length));
        }
    }

    public ByteBuf encode(ChannelHandlerContext ctx) {
        ByteBuf buf = ctx.alloc().buffer(this.tlvLength());
        buf.writeShort(this.tag);
        buf.writeInt(this.length);
        buf.writeBytes(this.value);
        return buf;
    }

    public static TagLengthValue decode(ByteBuf byteBuf) {
        short tag = byteBuf.readShort();
        int length = byteBuf.readInt();
        byte[] value = new byte[length];
        byteBuf.readBytes(value);
        return new TagLengthValue(tag, length, value);
    }

    public int tlvLength() {
        return 2 + 4 + this.length;
    }

    public short tag() {
        return this.tag;
    }

    public int length() {
        return this.length;
    }

    public byte[] value() {
        return this.value;
    }

}
