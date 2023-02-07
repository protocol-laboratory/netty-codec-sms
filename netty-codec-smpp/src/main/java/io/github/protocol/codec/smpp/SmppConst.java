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

public class SmppConst {

    private SmppConst() {
    }

    public static final int DEFAULT_MAX_BYTES_IN_MESSAGE = 8092;

    public static final int BIND_RECEIVER_ID = 0x00000001;

    public static final int BIND_RECEIVER_RESP_ID = 0x80000001;

    public static final int BIND_TRANSMITTER_ID = 0x00000002;

    public static final int BIND_TRANSMITTER_RESP_ID = 0x80000002;

    public static final int QUERY_SM_ID = 0x00000003;

    public static final int QUERY_SM_RESP_ID = 0x80000003;

    public static final int SUBMIT_SM_ID = 0x00000004;

    public static final int SUBMIT_SM_RESP_ID = 0x80000004;

    public static final int DELIVER_SM_ID = 0x00000005;

    public static final int DELIVER_SM_RESP_ID = 0x80000005;

    public static final int UNBIND_ID = 0x00000006;

    public static final int UNBIND_RESP_ID = 0x80000006;

    public static final int REPLACE_SM_ID = 0x00000007;

    public static final int REPLACE_SM_RESP_ID = 0x80000007;

    public static final int CANCEL_SM_ID = 0x00000008;

    public static final int CANCEL_SM_RESP_ID = 0x80000008;

    public static final int BIND_TRANSCEIVER_ID = 0x00000009;

    public static final int BIND_TRANSCEIVER_RESP_ID = 0x80000009;

    public static final int OUTBIND_ID = 0x0000000B;

    public static final int ENQUIRE_LINK_ID = 0x00000015;

    public static final int ENQUIRE_LINK_RESP_ID = 0x80000015;

    public static final int SUBMIT_MULTI_ID = 0x00000021;

    public static final int SUBMIT_MULTI_RESP_ID = 0x80000021;

    public static final int STATUS_OK = 0;

    public static final int STATUS_INVMSGLEN = 1;

    public static final int STATUS_INVCMDLEN = 2;

    public static final int STATUS_INVCMDID = 3;

    public static final int LEN_ADDR_NPI = 1;

    public static final int LEN_ADDR_TON = 1;

    public static final int LEN_DATA_CODING = 1;

    public static final int LEN_DEST_ADDR_NPI = LEN_ADDR_NPI;

    public static final int LEN_DEST_ADDR_TON = LEN_ADDR_TON;

    public static final int LEN_ERROR_CODE = 1;

    public static final int LEN_ERROR_STATUS_CODE = LEN_ERROR_CODE;

    public static final int LEN_ESM_CLASS = 1;

    public static final int LEN_HEADER = 16;

    public static final int LEN_INTERFACE_VERSION = 1;

    public static final int LEN_MESSAGE_STATE = 1;

    public static final int LEN_NO_UNSUCCESS = 1;

    public static final int LEN_NUMBER_OF_DESTS = 1;

    public static final int LEN_PRIORITY_FLAG = 1;

    public static final int LEN_PROTOCOL_ID = 1;

    public static final int LEN_REGISTERED_DELIVERY = 1;

    public static final int LEN_REPLACE_IF_PRESENT_FLAG = 1;

    public static final int LEN_SM_DEFAULT_MSG_ID = 1;

    public static final int LEN_SM_LENGTH = 1;

    public static final int LEN_SOURCE_ADDR_NPI = LEN_ADDR_NPI;

    public static final int LEN_SOURCE_ADDR_TON = LEN_ADDR_TON;

}
