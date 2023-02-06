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

package io.github.protocol.codec.smpp;

public class SmppBindTransmitterBody {

    private final String systemId;

    private final String password;

    private final String systemType;

    private final byte interfaceVersion;

    private final byte addrTon;

    private final byte addrNpi;

    private final String addressRange;

    public SmppBindTransmitterBody(String systemId, String password, String systemType, byte interfaceVersion,
                                   byte addrTon, byte addrNpi, String addressRange) {
        this.systemId = systemId;
        this.password = password;
        this.systemType = systemType;
        this.interfaceVersion = interfaceVersion;
        this.addrTon = addrTon;
        this.addrNpi = addrNpi;
        this.addressRange = addressRange;
    }

    public String systemId() {
        return systemId;
    }

    public String password() {
        return password;
    }

    public String systemType() {
        return systemType;
    }

    public byte interfaceVersion() {
        return interfaceVersion;
    }

    public byte addrTon() {
        return addrTon;
    }

    public byte addrNpi() {
        return addrNpi;
    }

    public String addressRange() {
        return addressRange;
    }
}
