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

public class SmppReplaceSmBody {

    private final String messageId;

    private final byte sourceAddrTon;

    private final byte sourceAddrNpi;

    private final String sourceAddr;

    private final String scheduleDeliveryTime;

    private final String validityPeriod;

    private final byte registeredDelivery;

    private final byte smDefaultMsgId;

    private final byte smLength;

    private final String shortMessage;

    public SmppReplaceSmBody(String messageId, byte sourceAddrTon, byte sourceAddrNpi, String sourceAddr,
                             String scheduleDeliveryTime, String validityPeriod, byte registeredDelivery,
                             byte smDefaultMsgId, byte smLength, String shortMessage) {
        this.messageId = messageId;
        this.sourceAddrTon = sourceAddrTon;
        this.sourceAddrNpi = sourceAddrNpi;
        this.sourceAddr = sourceAddr;
        this.scheduleDeliveryTime = scheduleDeliveryTime;
        this.validityPeriod = validityPeriod;
        this.registeredDelivery = registeredDelivery;
        this.smDefaultMsgId = smDefaultMsgId;
        this.smLength = smLength;
        this.shortMessage = shortMessage;
    }

    public String messageId() {
        return messageId;
    }

    public byte sourceAddrTon() {
        return sourceAddrTon;
    }

    public byte sourceAddrNpi() {
        return sourceAddrNpi;
    }

    public String sourceAddr() {
        return sourceAddr;
    }

    public String scheduleDeliveryTime() {
        return scheduleDeliveryTime;
    }

    public String validityPeriod() {
        return validityPeriod;
    }

    public byte registeredDelivery() {
        return registeredDelivery;
    }

    public byte smDefaultMsgId() {
        return smDefaultMsgId;
    }

    public byte smLength() {
        return smLength;
    }

    public String shortMessage() {
        return shortMessage;
    }
}
