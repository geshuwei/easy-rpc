package com.gsw.easyrpc.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: gsw DateTime: 2018/9/20 上午10:49
 */
public class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);


    public static HttpResponse get(String reqURL) throws Exception {
        return doRequest(reqURL, null);
    }

    public static <T> T get(String reqURL, Class<T> respClass) throws Exception {
        return JSONObject.parseObject(doRequest(reqURL, null).getStringData(), respClass);
    }


    public static <T> T getParameterType(String reqURL, TypeReference<T> typeReference) throws Exception {
        return JSONObject.parseObject(doRequest(reqURL, null).getStringData(), typeReference);
    }




    public static <T> T post(String reqURL, HttpEntity data, Class<T> respClass) throws Exception {
        byte[] respData = request(reqURL, data);
        return JSONObject.parseObject(respData, respClass);
    }

    public static <T> T post(String reqURL, byte[] data , Class<T> respClass) throws Exception {

        HttpEntity httpEntity = new ByteArrayEntity(data);
        byte[] respData = request(reqURL, httpEntity);
        return JSONObject.parseObject(respData, respClass);
    }

    public static <T> T post(String reqURL, String data , Class<T> respClass) throws Exception {

        HttpEntity httpEntity = new StringEntity(data);
        byte[] respData = request(reqURL, httpEntity);
        return JSONObject.parseObject(respData, respClass);
    }


    public static <T> T postParameterType(String reqURL, HttpEntity data, TypeReference<T> typeReference) throws Exception {
        HttpResponse respData = doRequest(reqURL, data);
        return JSONObject.parseObject(respData.getStringData(), typeReference);
    }

    public static <T> T postParameterType(String reqURL, byte[] data ,  TypeReference<T> typeReference) throws Exception {

        HttpEntity httpEntity = new ByteArrayEntity(data);
        HttpResponse respData = doRequest(reqURL, httpEntity);
        return JSONObject.parseObject(respData.getStringData(), typeReference);
    }


    public static <T> T postParameterType(String reqURL, String data ,  TypeReference<T> typeReference) throws Exception {
        HttpEntity httpEntity = new StringEntity(data);
        HttpResponse respData = doRequest(reqURL, httpEntity);
        return JSONObject.parseObject(respData.getStringData(), typeReference);
    }

    /**
     * post request
     */
    public static byte[] request(String reqURL, HttpEntity data) throws Exception {

            HttpResponse response = doRequest(reqURL, data);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                LOGGER.error("error, code:{}, msg:{}", response.getStatusLine().getStatusCode(), response.getStatusLine());
                throw new HttpException(response.getStatusLine().toString());
            }
        return response.getByteData();
    }

    private static HttpResponse doRequest(String reqURL, HttpEntity data) throws IOException {

        HttpRequestBase requestBase = data != null ? new HttpPost(reqURL) : new HttpGet(reqURL);
        CloseableHttpClient httpClient = HttpClients.custom().disableAutomaticRetries().build();	// disable retry

        try {
            // timeout
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000)
                    .setConnectTimeout(10000)
                    .build();

            requestBase.setConfig(requestConfig);

            // data
            if (data != null) {
                ((HttpPost)requestBase).setEntity(data);
            }
            // do post
            CloseableHttpResponse response = httpClient.execute(requestBase);

            HttpResponse httpResponse = new HttpResponse();
            HttpEntity entity = response.getEntity();
            httpResponse.setStatusLine(response.getStatusLine());


            if (null != entity) {
                byte[] responseBytes = EntityUtils.toByteArray(entity);
                EntityUtils.consume(entity);
                httpResponse.setByteData(responseBytes);
                httpResponse.setStringData(new String(responseBytes));
            }
            return httpResponse;
        } finally {
            requestBase.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

}
