/*
 * * Licensed to the Apache Software Foundation (ASF) under one
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class CmppCodecUtilTest {

    @Test
    void testGetTimestamp() {
        LocalDateTime dateTime = LocalDateTime.of(1997, 11, 21, 2, 8, 26); // example date
        // corresponding formatted string
        String expectedTimestamp = "1121020826";
        String actualTimestamp = CmppCodecUtil.getTimestamp(dateTime);
        Assertions.assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    void testGetAuthenticatorSource() {
        LocalDateTime dateTime = LocalDateTime.of(1997, 11, 21, 2, 8, 26); // example date
        String sourceAddr = "source";
        String password = "password";

        // MD5 hash of concatenation of sourceAddr, 9 zero bytes, password and timestamp
        byte[] expectedBytes = {-77, -105, 4, -75, -22, -10, 15, 69, -30, -33, -108, 2, 26, 102, -9, -30};
        byte[] actualBytes = CmppCodecUtil.getAuthenticatorSource(sourceAddr, password, dateTime);

        Assertions.assertArrayEquals(expectedBytes, actualBytes);
    }
}
