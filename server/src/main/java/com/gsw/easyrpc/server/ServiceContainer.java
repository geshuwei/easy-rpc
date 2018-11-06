package com.gsw.easyrpc.server;

import com.gsw.easyrpc.common.models.RpcRequest;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public enum ServiceContainer {

    // 单例
    INSTANCE;

    // interface name, object
    private Map<String, Object> serviceMap = new HashMap<>();


    public synchronized void addEasyRpcService(EasyRpcService serviceObject) throws Exception {

        List<String> interfaceClassList = new ArrayList<>();
        findEasyRpcServices(serviceObject.getClass(), interfaceClassList);
        for (String interfaceClass : interfaceClassList) {
            if (serviceMap.containsKey(interfaceClass)) {
                throw new Exception("service already Exist:" + interfaceClass);
            }
            serviceMap.put(interfaceClass, serviceObject);
        }
    }


    public Object getService(RpcRequest rpcRequest) {
        return serviceMap.get(rpcRequest.interfaceClass);
    }

    private void findEasyRpcServices(Class<?> interfaceClass, List<String> interfaceClassList) {

        if (ArrayUtils.isEmpty(interfaceClass.getInterfaces())
                || !EasyRpcService.class.isAssignableFrom(interfaceClass)) {
            return;
        }
        // EasyRpcService的子接口
        if (interfaceClass.isInterface()
                && ArrayUtils.contains(interfaceClass.getInterfaces(), EasyRpcService.class)) {

            interfaceClassList.add(interfaceClass.getName());
        }

        for (Class<?> superInterfaceClass : interfaceClass.getInterfaces()) {
            if (EasyRpcService.class.isAssignableFrom(superInterfaceClass)) {
                findEasyRpcServices(superInterfaceClass, interfaceClassList);
            }
        }
    }

}
