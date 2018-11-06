package com.gsw.easyrpc.common.utils;

import org.apache.http.StatusLine;

public class HttpResponse {

    private StatusLine statusLine;

    private String stringData;

    private byte[] byteData;


    public StatusLine getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public String getStringData() {
        return stringData;
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
    }

    public byte[] getByteData() {
        return byteData;
    }

    public void setByteData(byte[] byteData) {
        this.byteData = byteData;
    }
}
