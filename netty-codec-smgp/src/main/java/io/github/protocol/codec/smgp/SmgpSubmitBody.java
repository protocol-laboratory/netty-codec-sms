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

package io.github.protocol.codec.smgp;

import java.util.List;

public class SmgpSubmitBody {

    private final byte subType;

    private final byte needReport;

    private final byte priority;

    private final String serviceId;

    private final String feeType;

    private final String feeCode;

    private final String msgFormat;

    private final String validTime;

    private final String atTime;

    private final String srcTermId;

    private final String chargeTermId;

    private final byte destTermIdCount; // todo count <= 100

    private final List<String> destTermId;

    private final byte msgLength;

    private final String msgContent; // todo msg length <= 252

    private final String reserve;


    public SmgpSubmitBody(byte subType, byte needReport, byte priority, String serviceId, String feeType,
                          String feeCode, String msgFormat, String validTime, String atTime, String srcTermId,
                          String chargeTermId, byte destTermIdCount, List<String> destTermId, byte msgLength,
                          String msgContent, String reserve) {
        this.subType = subType;
        this.needReport = needReport;
        this.priority = priority;
        this.serviceId = serviceId;
        this.feeType = feeType;
        this.feeCode = feeCode;
        this.msgFormat = msgFormat;
        this.validTime = validTime;
        this.atTime = atTime;
        this.srcTermId = srcTermId;
        this.chargeTermId = chargeTermId;
        this.destTermIdCount = destTermIdCount;
        this.destTermId = destTermId;
        this.msgLength = msgLength;
        this.msgContent = msgContent;
        this.reserve = reserve;
    }

    public byte subType() {
        return this.subType;
    }

    public byte needReport() {
        return this.needReport;
    }

    public byte priority() {
        return this.priority;
    }

    public String serviceId() {
        return this.serviceId;
    }

    public String feeType() {
        return this.feeType;
    }

    public String feeCode() {
        return this.feeCode;
    }

    public String msgFormat() {
        return this.msgFormat;
    }

    public String validTime() {
        return this.validTime;
    }

    public String atTime() {
        return this.atTime;
    }

    public String srcTermId() {
        return this.srcTermId;
    }

    public String chargeTermId() {
        return this.chargeTermId;
    }

    public byte destTermIdCount() {
        return this.destTermIdCount;
    }

    public List<String> destTermId() {
        return this.destTermId;
    }

    public byte msgLength() {
        return this.msgLength;
    }

    public String msgContent() {
        return this.msgContent;
    }

    public String reserve() {
        return this.reserve;
    }

}
