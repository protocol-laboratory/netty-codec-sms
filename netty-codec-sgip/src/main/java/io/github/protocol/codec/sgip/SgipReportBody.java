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

public class SgipReportBody {

    private final long submitSequenceNumber;

    private final long reportType;

    private final String userNumber;

    private final long state;

    private final long errorCode;

    public SgipReportBody(long submitSequenceNumber, long reportType, String userNumber, long state, long errorCode) {
        this.submitSequenceNumber = submitSequenceNumber;
        this.reportType = reportType;
        this.userNumber = userNumber;
        this.state = state;
        this.errorCode = errorCode;
    }

    public long submitSequenceNumber() {
        return submitSequenceNumber;
    }

    public long reportType() {
        return reportType;
    }

    public String userNumber() {
        return userNumber;
    }

    public long state() {
        return state;
    }

    public long errorCode() {
        return errorCode;
    }
}
