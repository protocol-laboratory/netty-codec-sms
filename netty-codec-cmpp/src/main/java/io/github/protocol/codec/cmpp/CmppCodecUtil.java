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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CmppCodecUtil {

    private static final MessageDigest md5;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getTimestamp(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");
        return localDateTime.format(formatter);
    }

    public static byte[] getAuthenticatorSource(String sourceAddr, String secret, LocalDateTime localDateTime) {
        String timestamp = getTimestamp(localDateTime);
        byte[] bytes = new byte[sourceAddr.length() + 9 + secret.length() + timestamp.length()];
        System.arraycopy(sourceAddr.getBytes(StandardCharsets.UTF_8), 0, bytes, 0, sourceAddr.length());
        System.arraycopy(secret.getBytes(StandardCharsets.UTF_8), 0, bytes,
                sourceAddr.length() + 9, secret.length());
        System.arraycopy(timestamp.getBytes(StandardCharsets.UTF_8), 0, bytes,
                sourceAddr.length() + 9 + secret.length(), timestamp.length());
        return md5.digest(bytes);
    }
}
