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

public class CngpLoginBody {

    private final String clientId;

    private final String authenticatorClient;

    private final byte loginMode;

    private final int timeStamp;

    private final byte version;

    public CngpLoginBody(String clientId, String authenticatorClient, byte loginMode, int timeStamp, byte version) {
        this.clientId = clientId;
        this.authenticatorClient = authenticatorClient;
        this.loginMode = loginMode;
        this.timeStamp = timeStamp;
        this.version = version;
    }

    public String clientId() {
        return clientId;
    }

    public String authenticatorClient() {
        return authenticatorClient;
    }

    public byte loginMode() {
        return loginMode;
    }

    public int timeStamp() {
        return timeStamp;
    }

    public byte version() {
        return version;
    }
}
