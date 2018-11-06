package com.gsw.easyrpc.server;


import com.alibaba.fastjson.JSONObject;
import com.gsw.easyrpc.common.exceptions.RpcException;
import com.gsw.easyrpc.common.models.RpcRequest;
import com.gsw.easyrpc.common.models.RpcResponse;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/easyrpc/server/api")
public class EasyRpcServerApiController {

    private final Log logger = LogFactory.getLog(getClass());

    @Resource
    private ServiceDispatcher serviceDispatcher;

    @RequestMapping(value = "")
    public RpcResponse anyRequest(HttpServletRequest httpServletRequest) {
        RpcResponse rpcResponse = new RpcResponse();
        try (InputStream inputStream = httpServletRequest.getInputStream()) {
            RpcRequest rpcRequest = JSONObject.parseObject (inputStream, RpcRequest.class);

            Object result = serviceDispatcher.execute(rpcRequest);
            rpcResponse.data = JSONObject.toJSONString(result);
            rpcResponse.code = 0;
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            rpcResponse.code = 1;
            rpcResponse.rpcException = new RpcException(e);
        }

        return rpcResponse;
    }


}
