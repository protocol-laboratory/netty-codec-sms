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

package io.github.protocol.codec.cmpp;

import java.util.List;

public class CmppSubmitBody {

    private final long msgId;

    private final byte pkTotal;

    private final byte pkNumber;

    private final byte registeredDelivery;

    private final byte msgLevel;

    private final String serviceId;

    private final byte feeUserType;

    private final String feeTerminalId;

    private final byte feeTerminalType;

    private final byte tpPId;

    private final byte tpUdhi;

    private final byte msgFmt;

    private final String msgSrc;

    private final String feeType;

    private final String feeCode;

    private final String validTime;

    private final String atTime;

    private final String srcId;

    private final byte destUsrTl;

    private final List<String> destTerminalId;

    private final byte destTerminalType;

    private final short msgLength;

    private final byte[] msgContent;

    private final String linkId;

    public CmppSubmitBody(long msgId, byte pkTotal, byte pkNumber, byte registeredDelivery, byte msgLevel,
                          String serviceId, byte feeUserType, String feeTerminalId, byte feeTerminalType, byte tpPId,
                          byte tpUdhi, byte msgFmt, String msgSrc, String feeType, String feeCode, String validTime,
                          String atTime, String srcId, byte destUsrTl, List<String> destTerminalId,
                          byte destTerminalType, short msgLength, byte[] msgContent, String linkId) {
        this.msgId = msgId;
        this.pkTotal = pkTotal;
        this.pkNumber = pkNumber;
        this.registeredDelivery = registeredDelivery;
        this.msgLevel = msgLevel;
        this.serviceId = serviceId;
        this.feeUserType = feeUserType;
        this.feeTerminalId = feeTerminalId;
        this.feeTerminalType = feeTerminalType;
        this.tpPId = tpPId;
        this.tpUdhi = tpUdhi;
        this.msgFmt = msgFmt;
        this.msgSrc = msgSrc;
        this.feeType = feeType;
        this.feeCode = feeCode;
        this.validTime = validTime;
        this.atTime = atTime;
        this.srcId = srcId;
        this.destUsrTl = destUsrTl;
        this.destTerminalId = destTerminalId;
        this.destTerminalType = destTerminalType;
        this.msgLength = msgLength;
        this.msgContent = msgContent;
        this.linkId = linkId;
    }

    public long msgId() {
        return msgId;
    }

    public byte pkTotal() {
        return pkTotal;
    }

    public byte pkNumber() {
        return pkNumber;
    }

    public byte registeredDelivery() {
        return registeredDelivery;
    }

    public byte msgLevel() {
        return msgLevel;
    }

    public String serviceId() {
        return serviceId;
    }

    public byte feeUserType() {
        return feeUserType;
    }

    public String feeTerminalId() {
        return feeTerminalId;
    }

    public byte feeTerminalType() {
        return feeTerminalType;
    }

    public byte tpPId() {
        return tpPId;
    }

    public byte tpUdhi() {
        return tpUdhi;
    }

    public byte msgFmt() {
        return msgFmt;
    }

    public String msgSrc() {
        return msgSrc;
    }

    public String feeType() {
        return feeType;
    }

    public String feeCode() {
        return feeCode;
    }

    public String validTime() {
        return validTime;
    }

    public String atTime() {
        return atTime;
    }

    public String srcId() {
        return srcId;
    }

    public byte destUsrTl() {
        return destUsrTl;
    }

    public List<String> destTerminalId() {
        return destTerminalId;
    }

    public byte destTerminalType() {
        return destTerminalType;
    }

    public short msgLength() {
        return msgLength;
    }

    public byte[] msgContent() {
        return msgContent;
    }

    public String linkId() {
        return linkId;
    }
}
