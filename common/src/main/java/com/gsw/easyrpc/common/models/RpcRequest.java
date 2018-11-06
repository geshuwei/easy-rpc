package com.gsw.easyrpc.common.models;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class RpcRequest implements Serializable {

    public String interfaceClass;
    public String method;
    public long sendTime;
    public Param[] params;


    public static class Param {

        public String argClass;
        public String argData;

        public Param(){}

        public Param(Object arg) {
            this.argClass = arg.getClass().getName();
            this.argData = JSONObject.toJSONString(arg);
        }
    }
}
