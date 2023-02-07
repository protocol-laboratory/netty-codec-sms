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

package io.github.protocol.codec.smgp;

public class SmgpConst {

    public static final int DEFAULT_MAX_BYTES_IN_MESSAGE = 8092;

    public static final int LOGIN_ID = 0x00000001;

    public static final int LOGIN_RESP_ID = 0x80000001;

    public static final int SUBMIT_ID = 0x00000002;

    public static final int SUBMIT_RESP_ID = 0x80000002;

    public static final int DELIVER_ID = 0x00000003;

    public static final int DELIVER_RESP_ID = 0x80000003;

    public static final int ACTIVE_TEST_ID = 0x00000004;

    public static final int ACTIVE_TEST_RESP_ID = 0x80000004;

    public static final int FORWARD_ID = 0x00000005;

    public static final int FORWARD_RESP_ID = 0x80000005;

    public static final int EXIT_ID = 0x00000006;

    public static final int EXIT_RESP_ID = 0x80000006;

    public static final int QUERY_ID = 0x00000007;

    public static final int QUERY_RESP_ID = 0x80000007;

    public static final int MT_ROUTE_UPDATE_ID = 0x00000008;

    public static final int MT_ROUTE_UPDATE_RESP_ID = 0x80000008;

    public static final int LEN_HEADER = 12;
}
