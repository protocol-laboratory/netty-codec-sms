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

package io.github.protocol.codec.cngp;

public class CngpDeliverBody {

    private final String msgId;

    private final byte isResport;

    private final byte msgFormat;

    private final String recvTime;

    private final String srcTermId;

    private final String destTermId;

    private final byte msgLength;

    private final byte[] msgContent;

    public CngpDeliverBody(String msgId, byte isResport, byte msgFormat, String recvTime, String srcTermId,
                           String destTermId, byte msgLength, byte[] msgContent) {
        this.msgId = msgId;
        this.isResport = isResport;
        this.msgFormat = msgFormat;
        this.recvTime = recvTime;
        this.srcTermId = srcTermId;
        this.destTermId = destTermId;
        this.msgLength = msgLength;
        this.msgContent = msgContent;
    }

    public String msgId() {
        return msgId;
    }

    public byte isResport() {
        return isResport;
    }

    public byte msgFormat() {
        return msgFormat;
    }

    public String recvTime() {
        return recvTime;
    }

    public String srcTermId() {
        return srcTermId;
    }

    public String destTermId() {
        return destTermId;
    }

    public byte msgLength() {
        return msgLength;
    }

    public byte[] msgContent() {
        return msgContent;
    }
}
