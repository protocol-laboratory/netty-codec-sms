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

import java.time.LocalDateTime;

public class CmppConnectBody {

    private final String sourceAddr;

    private final byte[] authenticatorSource;

    private final byte version;

    /**
     * MMDDHHMMSS format timestamp
     */
    private final int timestamp;

    public CmppConnectBody(String sourceAddr, String secret, byte version) {
        this.sourceAddr = sourceAddr;
        LocalDateTime localDateTime = LocalDateTime.now();
        this.authenticatorSource = CmppCodecUtil.getAuthenticatorSource(sourceAddr, secret, localDateTime);
        this.version = version;
        this.timestamp = Integer.parseInt(CmppCodecUtil.getTimestamp(localDateTime));
    }

    public CmppConnectBody(String sourceAddr, byte[] authenticatorSource, byte version, int timestamp) {
        this.sourceAddr = sourceAddr;
        if (authenticatorSource.length != CmppConst.LEN_AUTHENTICATOR_SOURCE) {
            throw new IllegalArgumentException("authenticatorSource length must be "
                    + CmppConst.LEN_AUTHENTICATOR_SOURCE);
        }
        this.authenticatorSource = authenticatorSource;
        this.version = version;
        this.timestamp = timestamp;
    }

    public String sourceAddr() {
        return sourceAddr;
    }

    public byte[] authenticatorSource() {
        return authenticatorSource;
    }

    public byte version() {
        return version;
    }

    public int timestamp() {
        return timestamp;
    }
}
