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

public class CmppQueryRespBody {

    private String time;

    private byte queryType;

    private String queryCode;

    private int mtTlMsg;

    private int mtTlUsr;

    private int mtScs;

    private int mtWt;

    private int mtFl;

    private int moScs;

    private int moWt;

    private int moFl;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public byte getQueryType() {
        return queryType;
    }

    public void setQueryType(byte queryType) {
        this.queryType = queryType;
    }

    public String getQueryCode() {
        return queryCode;
    }

    public void setQueryCode(String queryCode) {
        this.queryCode = queryCode;
    }

    public int getMtTlMsg() {
        return mtTlMsg;
    }

    public void setMtTlMsg(int mtTlMsg) {
        this.mtTlMsg = mtTlMsg;
    }

    public int getMtTlUsr() {
        return mtTlUsr;
    }

    public void setMtTlUsr(int mtTlUsr) {
        this.mtTlUsr = mtTlUsr;
    }

    public int getMtScs() {
        return mtScs;
    }

    public void setMtScs(int mtScs) {
        this.mtScs = mtScs;
    }

    public int getMtWt() {
        return mtWt;
    }

    public void setMtWt(int mtWt) {
        this.mtWt = mtWt;
    }

    public int getMtFl() {
        return mtFl;
    }

    public void setMtFl(int mtFl) {
        this.mtFl = mtFl;
    }

    public int getMoScs() {
        return moScs;
    }

    public void setMoScs(int moScs) {
        this.moScs = moScs;
    }

    public int getMoWt() {
        return moWt;
    }

    public void setMoWt(int moWt) {
        this.moWt = moWt;
    }

    public int getMoFl() {
        return moFl;
    }

    public void setMoFl(int moFl) {
        this.moFl = moFl;
    }
}
