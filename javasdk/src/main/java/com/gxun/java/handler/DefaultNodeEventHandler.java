package com.gxun.java.handler;


import com.gxun.core.*;
import com.gxun.java.common.GxunConst;
import com.gxun.java.global.GSession;
import com.gxun.java.service.GService;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunny
 */
public class DefaultNodeEventHandler implements NodeEventHandler {
    private static final Logger LOGGER = Logger.getLogger(DefaultNodeEventHandler.class);
    private ConcurrentHashMap<String, Method> methods = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, GService> services = new ConcurrentHashMap<>();

    @Override
    public boolean onNewTunnel(GxunNode gxunNode, long l, long l1) {
        return true;
    }

    @Override
    public void onData(GxunNode gxunNode, GxunRequest gxunRequest, GxunResponse gxunResponse) {
        GSession.setRequest(gxunRequest);
        GSession.setResponse(gxunResponse);
        String methodKey = GxunConst.GXUN_PREFIX + gxunRequest.getProtocolName()+ "_" + gxunRequest.getMethod();
        Method method = methods.get(methodKey);
        if(null == method){
            LOGGER.error("method[" + gxunRequest.getMethod() + "] for protocol[" + gxunRequest.getProtocolName() + "] has not been defined");
        }else{
            try {
                method.invoke(services.get(methodKey), gxunRequest);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        GSession.clearRequest();
        GSession.clearResponse();
    }

    @Override
    public void onError(GxunNode gxunNode, GxunRequest gxunRequest, Status status) {
        LOGGER.error("an error appear, request from " + gxunRequest.getGnetId() + ":" + gxunRequest.getProtocolName() + ", status:" + status);
    }

    public void registerMethod(String key, Method m){
        methods.put(key, m);
    }
    public void registerService(String key, GService s){
        services.put(key, s);
    }
}
