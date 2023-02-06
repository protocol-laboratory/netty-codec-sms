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

public class SmppHeader {

    private final int commandLength;

    private final int commandId;

    private final int commandStatus;

    private final int sequenceNumber;

    public SmppHeader(int commandId, int sequenceNumber) {
        this(0, commandId, 0, sequenceNumber);
    }

    public SmppHeader(int commandId, int commandStatus, int sequenceNumber) {
        this(0, commandId, commandStatus, sequenceNumber);
    }

    public SmppHeader(int commandLength, int commandId, int commandStatus, int sequenceNumber) {
        this.commandLength = commandLength;
        this.commandId = commandId;
        this.commandStatus = commandStatus;
        this.sequenceNumber = sequenceNumber;
    }

    public int commandLength() {
        return commandLength;
    }

    public int commandId() {
        return commandId;
    }

    public int commandStatus() {
        return commandStatus;
    }

    public int sequenceNumber() {
        return sequenceNumber;
    }
}
