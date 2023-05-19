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

public class SmppDeliverSmBody {

    private final String serviceType;

    private final byte sourceAddrTon;

    private final byte sourceAddrNpi;

    private final String sourceAddr;

    private final byte destAddrTon;

    private final byte destAddrNpi;

    private final String destinationAddr;

    private final byte esmClass;

    private final byte protocolId;

    private final byte priorityFlag;

    private final String scheduleDeliveryTime;

    private final String validityPeriod;

    private final byte registeredDelivery;

    private final byte replaceIfPresentFlag;

    private final byte dataCoding;

    private final byte smDefaultMsgId;

    private final short smLength;

    private final byte[] shortMessage;

    public SmppDeliverSmBody(String serviceType, byte sourceAddrTon, byte sourceAddrNpi, String sourceAddr,
                             byte destAddrTon, byte destAddrNpi, String destinationAddr,
                             byte esmClass, byte protocolId, byte priorityFlag, String scheduleDeliveryTime,
                             String validityPeriod, byte registeredDelivery, byte replaceIfPresentFlag,
                             byte dataCoding, byte smDefaultMsgId, short smLength, byte[] shortMessage) {
        this.serviceType = serviceType;
        this.sourceAddrTon = sourceAddrTon;
        this.sourceAddrNpi = sourceAddrNpi;
        this.sourceAddr = sourceAddr;
        this.destAddrTon = destAddrTon;
        this.destAddrNpi = destAddrNpi;
        this.destinationAddr = destinationAddr;
        this.esmClass = esmClass;
        this.protocolId = protocolId;
        this.priorityFlag = priorityFlag;
        this.scheduleDeliveryTime = scheduleDeliveryTime;
        this.validityPeriod = validityPeriod;
        this.registeredDelivery = registeredDelivery;
        this.replaceIfPresentFlag = replaceIfPresentFlag;
        this.dataCoding = dataCoding;
        this.smDefaultMsgId = smDefaultMsgId;
        this.smLength = smLength;
        if (smLength >= SmppConst.UNSIGNED_BYTE_MAX) {
            throw new IllegalArgumentException("sm length cannot be bigger than 254.");
        }
        this.shortMessage = shortMessage;
    }

    public String serviceType() {
        return serviceType;
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

    public byte destAddrTon() {
        return destAddrTon;
    }

    public byte destAddrNpi() {
        return destAddrNpi;
    }

    public String destinationAddr() {
        return destinationAddr;
    }

    public byte esmClass() {
        return esmClass;
    }

    public byte protocolId() {
        return protocolId;
    }

    public byte priorityFlag() {
        return priorityFlag;
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

    public byte replaceIfPresentFlag() {
        return replaceIfPresentFlag;
    }

    public byte dataCoding() {
        return dataCoding;
    }

    public byte smDefaultMsgId() {
        return smDefaultMsgId;
    }

    public short smLength() {
        return smLength;
    }

    public byte[] shortMessage() {
        return shortMessage;
    }
}
