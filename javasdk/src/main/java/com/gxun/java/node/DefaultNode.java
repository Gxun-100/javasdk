package com.gxun.java.node;

import com.gxun.core.*;
import com.gxun.java.annotation.GxunMethodAnno;
import com.gxun.java.annotation.GxunNodeAnno;
import com.gxun.java.common.GxunConst;
import com.gxun.java.handler.DefaultNodeEventHandler;
import com.gxun.java.service.GService;
import com.gxun.java.exception.GException;
import org.apache.log4j.Logger;

import java.io.File;
import java.lang.reflect.Method;


/**
 * @author sunny
 */
public class DefaultNode {
    private static final Logger LOGGER = Logger.getLogger(DefaultNode.class);
    private GxunNode node;

    private DefaultNodeEventHandler eventHandler;

    public void registerService(GService service) throws GxunException {
        if (null == node) {
            throw new GException("node has not been started");
        }
        service.setNode(node);
        Method[] methods = service.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            GxunMethodAnno anno = method.getAnnotation(GxunMethodAnno.class);
            if(null != anno){
                if(method.getParameterCount() != 1){
                    throw new GException("The number of method[" + method.getName() + "] parameters does not match, it should be one" );
                }
                String key = GxunConst.GXUN_PREFIX + anno.protocolName() + "_" + anno.method();
                this.eventHandler.registerMethod(key, method);
                this.eventHandler.registerService(key, service);
            }
        }
    }

    public void setNode(GxunNode node) {
        this.node = node;
    }

    public void setEventHandler(DefaultNodeEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public boolean stop() {
        node.close();
        return true;
    }

}
