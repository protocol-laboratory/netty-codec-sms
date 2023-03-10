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

    public int messageLength() {
        return this.messageLength;
    }

    public int commandId() {
        return this.commandId;
    }

    public long sequenceNumber() {
        return this.sequenceNumber;
    }
}
