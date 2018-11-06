package com.gsw.easyrpc.common.models;

import com.gsw.easyrpc.common.exceptions.RpcException;

public class RpcResponse {

    public int code;
    public String desc;
    public String data;
    public RpcException rpcException;

}
