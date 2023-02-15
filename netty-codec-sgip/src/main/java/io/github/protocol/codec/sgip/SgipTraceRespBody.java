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

public class SgipTraceRespBody {

    private final long count;

    private final long result;

    private final String nodeId;

    private final String receiveTime;

    private final String sendTime;

    public SgipTraceRespBody(long count, long result,
                             String nodeId, String receiveTime, String sendTime) {
        this.count = count;
        this.result = result;
        this.nodeId = nodeId;
        this.receiveTime = receiveTime;
        this.sendTime = sendTime;
    }

    public long count() {
        return count;
    }

    public long result() {
        return result;
    }

    public String nodeId() {
        return nodeId;
    }

    public String receiveTime() {
        return receiveTime;
    }

    public String sendTime() {
        return sendTime;
    }
}
