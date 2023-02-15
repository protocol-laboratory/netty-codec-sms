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

public class SgipSubmitBody {

    private final String spNumber;

    private final String chargeNumber;

    private final String userNumber;

    private final String corpId;

    private final String serviceType;

    private final String feeType;

    private final String feeValue;

    private final String givenValue;

    private final String agentFlag;

    private final String morelatetoMTFlag;

    private final long priority;

    private final String expireTime;

    private final String scheduleTime;

    private final long reportFlag;

    private final long messageType;

    private final long tpPid;

    private final long tpUdhi;

    private final long messageCoding;

    private final long messageContent;

    public SgipSubmitBody(String spNumber, String chargeNumber, String userNumber,
                          String corpId, String serviceType, String feeType, String feeValue, String givenValue,
                          String agentFlag, String morelatetoMTFlag, long priority, String expireTime,
                          String scheduleTime, long reportFlag, long messageType, long tpPid, long tpUdhi,
                          long messageCoding, long messageContent) {
        this.spNumber = spNumber;
        this.chargeNumber = chargeNumber;
        this.userNumber = userNumber;
        this.corpId = corpId;
        this.serviceType = serviceType;
        this.feeType = feeType;
        this.feeValue = feeValue;
        this.givenValue = givenValue;
        this.agentFlag = agentFlag;
        this.morelatetoMTFlag = morelatetoMTFlag;
        this.priority = priority;
        this.expireTime = expireTime;
        this.scheduleTime = scheduleTime;
        this.reportFlag = reportFlag;
        this.messageType = messageType;
        this.tpPid = tpPid;
        this.tpUdhi = tpUdhi;
        this.messageCoding = messageCoding;
        this.messageContent = messageContent;
    }

    public String spNumber() {
        return spNumber;
    }

    public String chargeNumber() {
        return chargeNumber;
    }

    public String userNumber() {
        return userNumber;
    }

    public String corpId() {
        return corpId;
    }

    public String serviceType() {
        return serviceType;
    }

    public String feeType() {
        return feeType;
    }

    public String feeValue() {
        return feeValue;
    }

    public String givenValue() {
        return givenValue;
    }

    public String agentFlag() {
        return agentFlag;
    }

    public String morelatetoMTFlag() {
        return morelatetoMTFlag;
    }

    public long priority() {
        return priority;
    }

    public String expireTime() {
        return expireTime;
    }

    public String scheduleTime() {
        return scheduleTime;
    }

    public long reportFlag() {
        return reportFlag;
    }

    public long messageType() {
        return messageType;
    }

    public long tpPid() {
        return tpPid;
    }

    public long tpUdhi() {
        return tpUdhi;
    }

    public long messageCoding() {
        return messageCoding;
    }

    public long messageContent() {
        return messageContent;
    }
}
