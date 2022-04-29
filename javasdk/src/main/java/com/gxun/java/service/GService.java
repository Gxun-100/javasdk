package com.gxun.java.service;

import com.alibaba.fastjson.JSONObject;
import com.gxun.core.GxunException;
import com.gxun.core.GxunNode;
import com.gxun.core.GxunResponse;
import com.gxun.java.global.GSession;
import com.gxun.java.request.GRequest;
import com.gxun.java.response.GResponse;

/**
 * @author sunny
 */
public abstract class GService {

    private GxunNode node;

    /**
     * 发送请求
     *
     * @param method  方法名
     * @param request 请求参数，需继承GRequest
     * @param clazz   返回值类型
     * @param <T>
     * @return
     * @throws GxunException
     */
    public <T> T rpcCall(long gnetId, String method, GRequest request, Class<T> clazz) throws GxunException {
        return rpcCall(gnetId, method, request, 3000, clazz);

    }
    public <T> T rpcCall(long gnetId, String method, GRequest request, int timeOutMs, Class<T> clazz) throws GxunException {
        String sendRes = node.send(gnetId, method, JSONObject.toJSONString(request, true), timeOutMs);
        return JSONObject.parseObject(sendRes, clazz);

    }
    public JSONObject rpcCall(long gnetId, String method, GRequest request) throws GxunException {
        return rpcCall(gnetId, method, request, 3000);

    }
    public JSONObject rpcCall(long gnetId, String method, GRequest request, int timeOutMs) throws GxunException {
        String sendRes = node.send(gnetId, method, JSONObject.toJSONString(request, true), timeOutMs);
        return JSONObject.parseObject(sendRes);

    }

    public void rpcResponse(GResponse resData) throws GxunException {
        GxunResponse response = GSession.getResponse();
        response.send(JSONObject.toJSONString(resData, true), 3000);
    }

    public void setNode(GxunNode node) {
        this.node = node;
    }
}
