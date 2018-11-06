package com.gsw.easyrpc.common.exceptions;

public class RpcException extends Exception {

    public RpcException() {}

    public RpcException(String msg) {
        super(msg);
    }

    public RpcException(Throwable e) {
        super(e);
    }


}
