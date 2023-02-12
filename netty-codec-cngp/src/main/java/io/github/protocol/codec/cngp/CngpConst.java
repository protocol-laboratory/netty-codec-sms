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

public class CngpConst {

    public static final int DEFAULT_MAX_BYTES_IN_MESSAGE = 8092;

    public static final int LOGIN_ID = 0x00000001;

    public static final int LOGIN_RESP_ID = 0x80000001;

    public static final int SUBMIT_ID = 0x00000002;

    public static final int SUBMIT_RESP_ID = 0x80000002;

    public static final int DELIVER_ID = 0x00000003;

    public static final int DELIVER_RESP_ID = 0x80000003;

    public static final int ACTIVE_TEST_ID = 0x00000004;

    public static final int ACTIVE_TEST_RESP_ID = 0x80000004;

    public static final int EXIT_ID = 0x00000006;

    public static final int EXIT_RESP_ID = 0x80000006;

    public static final int LEN_AT_TIME = 17;
    public static final int LEN_CHARGE_TERM_ID = 21;
    public static final int LEN_CLIENT_ID = 10;
    public static final int LEN_DELIVER_MSG =  CngpConst.LEN_HEADER + 69;

    public static final int LEN_DELIVER_DEST_TERM_ID = 21;
    public static final int LEN_DELIVER_MESSAGE_ID = 10;
    public static final int LEN_DELIVER_RESP_MSG = CngpConst.LEN_HEADER +  10;
    public static final int LEN_DELIVER_SRC_TERM_ID = 21;
    public static final int LEN_DEST_TERM_ID = 21;

    public static final int LEN_FEE_CODE = 6;

    public static final int LEN_FEE_TYPE = 2;

    public static final int LEN_FEE_USER_TYPE = 1;

    public static final int LEN_HEADER = 16;
    public static final int LEN_LOGIN_AUTHENTICATOR_CLIENT = 16;

    public static final int LEN_LOGIN_RESP_AUTHENTICATOR_CLIENT = 16;
    public static final int LEN_LOGIN_MSG = CngpConst.LEN_HEADER + 32;
    public static final int LEN_LOGIN_RESP_MSG = CngpConst.LEN_HEADER + 17;
    public static final int LEN_MESSAGE_FORMAT = 1;
    public static final int LEN_RECV_TIME = 14;
    public static final int LEN_SERVICE_ID = 10;
    public static final int LEN_SP_ID = 10;
    public static final int LEN_SUBMIT_RESP_MSG = CngpConst.LEN_HEADER + 10;
    public static final int LEN_SUBMIT_MSG = CngpConst.LEN_HEADER + 111;

    public static final int LEN_SUBMIT_SRC_TERM_ID = 21;

    public static final int LEN_MESSAGE_ID = 10;
    public static final int LEN_VALID_TIME = 17;

}
