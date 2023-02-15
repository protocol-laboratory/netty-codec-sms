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


public class SgipDeliverBody {

    private final String userNumber;

    private final String spNumber;

    private final String tpPid;

    private final String tpUdhi;

    private final long messageCoding;

    private final long messageContent;

    public SgipDeliverBody(String userNumber, String spNumber,
                           String tpPid, String tpUdhi, long messageCoding, long messageContent) {
        this.userNumber = userNumber;
        this.spNumber = spNumber;
        this.tpPid = tpPid;
        this.tpUdhi = tpUdhi;
        this.messageCoding = messageCoding;
        this.messageContent = messageContent;
    }

    public String userNumber() {
        return userNumber;
    }

    public String spNumber() {
        return spNumber;
    }

    public String tpPid() {
        return tpPid;
    }

    public String tpUdhi() {
        return tpUdhi;
    }

    public long messageCoding() {
        return messageCoding;
    }

    public long messageContent() {
        return messageContent;
    }
}
