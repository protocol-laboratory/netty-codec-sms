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

    private long msgId;

    private byte pkTotal;

    private byte pkNumber;

    private byte registeredDelivery;

    private byte msgLevel;

    private String serviceId;

    private byte feeUserType;

    private String feeTerminalId;

    private byte feeTerminalType;

    private byte tpPId;

    private byte tpUdhi;

    private byte msgFmt;

    private String msgSrc;

    private String feeType;

    private String feeCode;

    private String validTime;

    private String atTime;

    private String srcId;

    private byte destUsrTl;

    private List<String> destTerminalId;

    private byte destTerminalType;

    private byte msgLength;

    private String msgContent;

    private String linkId;

    public CmppSubmitBody(long msgId, byte pkTotal, byte pkNumber, byte registeredDelivery, byte msgLevel,
                          String serviceId, byte feeUserType, String feeTerminalId, byte feeTerminalType, byte tpPId,
                          byte tpUdhi, byte msgFmt, String msgSrc, String feeType, String feeCode, String validTime,
                          String atTime, String srcId, byte destUsrTl, List<String> destTerminalId,
                          byte destTerminalType, byte msgLength, String msgContent, String linkId) {
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

    public byte msgLength() {
        return msgLength;
    }

    public String msgContent() {
        return msgContent;
    }

    public String linkId() {
        return linkId;
    }
}
