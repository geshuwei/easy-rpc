package com.gsw.easyrpc.client;

import com.alibaba.fastjson.JSONObject;
import com.gsw.easyrpc.common.exceptions.RpcException;
import com.gsw.easyrpc.common.models.RpcRequest;
import com.gsw.easyrpc.common.models.RpcResponse;
import com.gsw.easyrpc.common.utils.HttpUtil;
import com.netflix.appinfo.InstanceInfo;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RemoteServiceProxy {

    @Value("${easyrpc.server.api:/easyrpc/server/api}")
    private String serverUrl;

    @Autowired
    private DiscoveryClient discoveryClient;
    private AtomicInteger serviceInstanceIndex = new AtomicInteger(0);

    public Object getProxy(Class<?> interfaceClass, String serverId) {

        return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{interfaceClass}, (proxy, method, args) -> {
            try {
                System.out.println("method:" + method + ", args" + JSONObject.toJSONString(args));

                RpcRequest rpcRequest = new RpcRequest();
                rpcRequest.interfaceClass = interfaceClass.getName();
                rpcRequest.method = method.getName();
                rpcRequest.sendTime = System.currentTimeMillis();
                RpcRequest.Param[] params;

                if (ArrayUtils.isNotEmpty(args)) {
                    params = new RpcRequest.Param[args.length];
                    for (int i = 0; i < args.length; i++) {
                        params[i] = new RpcRequest.Param(args[i]);
                    }
                    rpcRequest.params = params;
                } else {
                    params = new RpcRequest.Param[0];
                }
                rpcRequest.params = params;

                List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serverId);

                if (CollectionUtils.isEmpty(serviceInstances)) {
                    throw new Exception("service instance is empty");
                }
                ServiceInstance nextServiceInstance = serviceInstances.get(serviceInstanceIndex.getAndIncrement() % serviceInstances.size());

                String requestUrl = String.format("http://%s:%s/%s", nextServiceInstance.getHost(), nextServiceInstance.getPort(), serverUrl);

                RpcResponse rpcResponse = JSONObject.parseObject(HttpUtil.request(requestUrl, new StringEntity(JSONObject.toJSONString(rpcRequest))), RpcResponse.class);
                if (rpcResponse == null) {
                    throw new RpcException("服务器无法响应");
                }

                if (rpcResponse.code != 0) {
                    String errorMsg = String.format("服务器响应错误，code：%s, desc:%s", rpcResponse.code, rpcResponse.desc);
                    throw new RpcException(errorMsg);
                }

                if (rpcResponse.rpcException != null) {
                    throw rpcResponse.rpcException;
                }
                Type returnType = method.getGenericReturnType();
                return JSONObject.parseObject(rpcResponse.data, returnType);
            } catch (Throwable e) {
                throw new RpcException(e);
            }
        });
    }



}
