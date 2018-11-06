package com.gsw.easyrpc.server;

import com.alibaba.fastjson.JSONObject;
import com.gsw.easyrpc.common.models.RpcRequest;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class ServiceDispatcher {

    private final Log logger = LogFactory.getLog(getClass());


    public Object execute(RpcRequest rpcRequest) throws Exception {

        Object service = ServiceContainer.INSTANCE.getService(rpcRequest);
        if (service == null) {
            String errorMsg = String.format("找不到服务，service:%s", rpcRequest.interfaceClass);
            logger.error(errorMsg);
            throw new Exception(errorMsg);
        }

        Class<?>[] argClass = new Class<?>[rpcRequest.params.length];
        Object[] args = new Object[rpcRequest.params.length];

        for (int i = 0; i < rpcRequest.params.length; i++) {
            argClass[i] = Class.forName( rpcRequest.params[i].argClass);
            args[i] = JSONObject.parseObject(rpcRequest.params[i].argData, argClass[i]);
        }

        Method method = service.getClass().getMethod(rpcRequest.method, argClass);

        if (method == null) {
            String errorMsg = String.format("找不到服务，service:%s, method:%s, args:%s",
                    rpcRequest.interfaceClass, rpcRequest.method, ArrayUtils.toString(rpcRequest.params));
            logger.error(errorMsg);
            throw new Exception(errorMsg);
        }
        return method.invoke(service, args);
    }

}
