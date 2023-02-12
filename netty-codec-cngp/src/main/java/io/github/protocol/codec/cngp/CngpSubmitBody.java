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

import java.util.List;

public class CngpSubmitBody {

    private final String spId;

    private final byte subType;

    private final byte needReport;

    private final byte priority;

    private final String serviceId;

    private final String feeType;

    private final String feeUserType;

    private final String feeCode;

    private final String msgFormat;

    private final String validTime;

    private final String atTime;

    private final String srcTermId;

    private final String chargeTermId;

    private final byte destTermIdCount;

    private final List<String> destTermId;

    private final byte msgLength;

    private final byte[] msgContent;

    public CngpSubmitBody(String spId, byte subType, byte needReport, byte priority, String serviceId, String feeType,
                          String feeUserType, String feeCode, String msgFormat, String validTime, String atTime,
                          String srcTermId, String chargeTermId, byte destTermIdCount, List<String> destTermId,
                          byte msgLength, byte[] msgContent) {
        this.spId = spId;
        this.subType = subType;
        this.needReport = needReport;
        this.priority = priority;
        this.serviceId = serviceId;
        this.feeType = feeType;
        this.feeUserType = feeUserType;
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
    }

    public String spId() {
        return spId;
    }

    public byte subType() {
        return subType;
    }

    public byte needReport() {
        return needReport;
    }

    public byte priority() {
        return priority;
    }

    public String serviceId() {
        return serviceId;
    }

    public String feeType() {
        return feeType;
    }

    public String feeUserType() {
        return feeUserType;
    }

    public String feeCode() {
        return feeCode;
    }

    public String msgFormat() {
        return msgFormat;
    }

    public String validTime() {
        return validTime;
    }

    public String atTime() {
        return atTime;
    }

    public String srcTermId() {
        return srcTermId;
    }

    public String chargeTermId() {
        return chargeTermId;
    }

    public byte destTermIdCount() {
        return destTermIdCount;
    }

    public List<String> destTermId() {
        return destTermId;
    }

    public byte msgLength() {
        return msgLength;
    }

    public byte[] msgContent() {
        return msgContent;
    }

}
