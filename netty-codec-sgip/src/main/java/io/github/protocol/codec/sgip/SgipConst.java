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

public class SgipConst {

    public static final int DEFAULT_MAX_BYTES_IN_MESSAGE = 8092;

    public static final int BIND_ID = 0x00000001;

    public static final int BIND_RESP_ID = 0x80000001;

    public static final int UNBIND_ID = 0x00000002;

    public static final int UNBIND_RESP_ID = 0x80000002;

    public static final int SUBMIT_ID = 0x00000003;

    public static final int SUBMIT_RESP_ID = 0x80000003;

    public static final int DELIVER_ID = 0x00000004;

    public static final int DELIVER_RESP_ID = 0x80000004;

    public static final int REPORT_ID = 0x00000005;

    public static final int REPORT_RESP_ID = 0x80000005;

    public static final int ADDSP_ID = 0x00000006;

    public static final int ADDSP_RESP_ID = 0x80000006;

    public static final int MODIFYSP_ID = 0x00000007;

    public static final int MODIFYSP_RESP_ID = 0x80000007;

    public static final int DELETESP_ID = 0x00000008;

    public static final int DELETESP_RESP_ID = 0x80000008;

    public static final int QUERYROUTE_ID = 0x00000009;

    public static final int QUERYROUTE_RESP_ID = 0x80000009;

    public static final int ADDTELESEG_ID = 0x0000000A;

    public static final int ADDTELESEG_RESP_ID = 0x8000000A;

    public static final int MODIFYTELESEG_ID = 0x0000000B;

    public static final int MODIFYTELESEG_RESP_ID = 0x8000000B;

    public static final int DELETETELESEG_ID = 0x0000000C;

    public static final int DELETETELESEG_RESP_ID = 0x8000000C;

    public static final int ADDSMG_ID = 0x0000000D;

    public static final int ADDSMG_RESP_ID = 0x8000000D;

    public static final int MODIFYSMG_ID = 0x0000000E;

    public static final int MODIFYSMG_RESP_ID = 0x8000000E;

    public static final int DELETESMG_ID = 0x0000000F;

    public static final int DELETESMG_RESP_ID = 0x8000000F;

    public static final int CHECKUSER_ID = 0x00000010;

    public static final int CHECKUSER_RESP_ID = 0x80000010;

    public static final int USERRPT_ID = 0x00000011;

    public static final int USERRPT_RESP_ID = 0x80000011;

    public static final int TRACE_ID = 0x00001000;

    public static final int TRACE_RESP_ID = 0x80001000;

    public static final int LEN_BIND_MSG = 41;

    public static final int LEN_BIND_RESERVE = 8;

    public static final int LEN_BIND_RESP_RESERVE = 8;

    public static final int LEN_BIND_RESP_MSG = 9;
    public static final int LEN_LOGIN_NAME = 16;

    public static final int LEN_LOGIN_PASSWORD = 16;


}
