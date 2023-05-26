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

public class CmppConst {

    public static final int DEFAULT_MAX_BYTES_IN_MESSAGE = 8092;

    public static final int CONNECT_ID = 0x00000001;

    public static final int CONNECT_RESP_ID = 0x80000001;

    public static final int TERMINATE_ID = 0x00000002;

    public static final int TERMINATE_RESP_ID = 0x80000002;

    public static final int SUBMIT_ID = 0x00000004;

    public static final int SUBMIT_RESP_ID = 0x80000004;

    public static final int DELIVER_ID = 0x00000005;

    public static final int DELIVER_RESP_ID = 0x80000005;

    public static final int QUERY_ID = 0x00000006;

    public static final int QUERY_RESP_ID = 0x80000006;

    public static final int CANCEL_ID = 0x00000007;

    public static final int CANCEL_RESP_ID = 0x80000007;

    public static final int ACTIVE_TEST_ID = 0x00000008;

    public static final int ACTIVE_TEST_RESP_ID = 0x80000008;

    public static final int FWD_ID = 0x00000009;

    public static final int FWD_RESP_ID = 0x80000009;

    public static final int MT_ROUTE_ID = 0x00000010;

    public static final int MT_ROUTE_RESP_ID = 0x80000010;

    public static final int MO_ROUTE_ID = 0x00000011;

    public static final int MO_ROUTE_RESP_ID = 0x80000011;

    public static final int GET_MT_ROUTE_ID = 0x00000012;

    public static final int GET_MT_ROUTE_RESP_ID = 0x80000012;

    public static final int MT_ROUTE_UPDATE_ID = 0x00000013;

    public static final int MT_ROUTE_UPDATE_RESP_ID = 0x80000013;

    public static final int MO_ROUTE_UPDATE_ID = 0x00000014;

    public static final int MO_ROUTE_UPDATE_RESP_ID = 0x80000014;

    public static final int PUSH_MT_ROUTE_UPDATE_ID = 0x00000015;

    public static final int PUSH_MT_ROUTE_UPDATE_RESP_ID = 0x80000015;

    public static final int PUSH_MO_ROUTE_UPDATE_ID = 0x00000016;

    public static final int PUSH_MO_ROUTE_UPDATE_RESP_ID = 0x80000016;

    public static final int GET_MO_ROUTE_ID = 0x00000017;

    public static final int GET_MO_ROUTE_RESP_ID = 0x80000017;

    public static final int LEN_ACTIVE_TEST_RESP_MSG = CmppConst.LEN_HEADER + 1;

    public static final int LEN_AT_TIME = 17;

    public static final int LEN_AUTHENTICATOR_ISMG = 16;

    public static final int LEN_AUTHENTICATOR_SOURCE = 16;

    public static final int LEN_CONNECT_MSG = 39;

    public static final int LEN_CONNECT_RESP_MSG = CmppConst.LEN_HEADER + 21;

    public static final int LEN_DEST_TERMINAL_ID = 32;

    public static final int LEN_DEST_TERMINAL_TYPE = 1;

    public static final int LEN_DESTUSR_TL = 1;

    public static final int LEN_FEE_CODE = 6;

    public static final int LEN_FEE_TERMINAL_ID = 32;

    public static final int LEN_FEE_TERMINAL_TYPE = 1;

    public static final int LEN_FEE_TYPE = 2;

    public static final int LEN_FEE_USERTYPE = 1;

    public static final int LEN_HEADER = 12;

    public static final int LEN_LINK_ID = 20;

    public static final int LEN_MESSAGE_FMT = 1;

    public static final int LEN_MESSAGE_ID = 8;

    public static final int LEN_MESSAGE_LENGTH = 1;

    public static final int LEN_MESSAGE_LEVEL = 1;

    public static final int LEN_MESSAGE_SRC = 6;

    public static final int LEN_PK_NUMBER = 1;

    public static final int LEN_PK_TOTAL = 1;

    public static final int LEN_REGISTERED_DELIVERY = 1;

    public static final int LEN_SERVICE_ID = 10;

    public static final int LEN_SOURCE_ADDR = 6;

    public static final int LEN_SRC_ID = 21;

    public static final int LEN_SUBMIT_BODY_RESP_SIZE = CmppConst.LEN_HEADER + 12;

    public static final int LEN_TIMESTAMP = 4;

    public static final int LEN_TP_TPID = 1;

    public static final int LEN_TP_UDHI = 1;

    public static final int LEN_VALID_TIME = 17;

    public static final int LEN_VERSION = 1;

    public static final int UNSIGNED_BYTE_MAX = 0xff;

}
