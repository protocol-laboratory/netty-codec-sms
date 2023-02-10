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

package io.github.protocol.codec.sgip;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.StandardCharsets;

public class SgipDecoder extends LengthFieldBasedFrameDecoder {

    public SgipDecoder() {
        this(SgipConst.DEFAULT_MAX_BYTES_IN_MESSAGE);
    }

    public SgipDecoder(int maxBytesInMessage) {
        super(maxBytesInMessage, 0, 4, -4, 0);
    }

    @Override
    protected SgipMessage decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
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

    SgipMessage decode(ByteBuf frame) {
        int messageLength = frame.readInt();
        int commandId = frame.readInt();
        long sequenceNumber = frame.readLong();
        SgipHeader sgipHeader = new SgipHeader(messageLength, commandId, sequenceNumber);
        switch (commandId) {
            case SgipConst.BIND_ID:
                return new SgipBind(sgipHeader, decodeBind(frame));
            case SgipConst.BIND_RESP_ID:
                return new SgipBindResp(sgipHeader, decodeBindResp(frame));
            default:
                throw new IllegalArgumentException("Unknown commandId: " + commandId);
        }
    }

    private SgipBindBody decodeBind(ByteBuf frame) {
        byte loginType = frame.readByte();
        String loginName = readString(frame, SgipConst.LEN_LOGIN_NAME);
        String loginPassword = readString(frame, SgipConst.LEN_LOGIN_PASSWORD);
        String reserve = readString(frame, SgipConst.LEN_BIND_RESERVE);
        return new SgipBindBody(loginType, loginName, loginPassword, reserve);
    }

    private SgipBindRespBody decodeBindResp(ByteBuf frame) {
        byte result = frame.readByte();
        String reserve = readString(frame, SgipConst.LEN_BIND_RESP_RESERVE);
        return new SgipBindRespBody(result, reserve);
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
