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

public class DestAddress {

    private final byte destAddrTon;

    private final byte destAddrNpi;

    private final String destinationAddr;

    public DestAddress(byte destAddrTon, byte destAddrNpi, String destinationAddr) {
        this.destAddrTon = destAddrTon;
        this.destAddrNpi = destAddrNpi;
        this.destinationAddr = destinationAddr;
    }

    public byte destAddrTon() {
        return destAddrTon;
    }

    public byte destAddrNpi() {
        return destAddrNpi;
    }

    public String destinationAddr() {
        return destinationAddr;
    }
}
