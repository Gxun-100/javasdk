package com.gxun.java.node;

import com.gxun.core.GxunApi;
import com.gxun.core.GxunException;
import com.gxun.core.GxunNode;
import com.gxun.core.GxunNodeConfig;
import com.gxun.java.annotation.GxunNodeAnno;
import com.gxun.java.exception.GException;
import com.gxun.java.handler.DefaultNodeEventHandler;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * @author sunny
 */
public class GxunNodeHelper {
    private static final Logger LOGGER = Logger.getLogger(GxunNodeHelper.class);

    public static synchronized DefaultNode startDefaultNode(int gid, int sid, String keyPath, String password, int port) throws GxunException {
        return getNode(gid, sid, keyPath, password, port);
    }

    public static synchronized DefaultNode startAnnotationNode(Class nodeClass) throws GxunException {
        try {
            if (!DefaultNode.class.isAssignableFrom(nodeClass)) {
                throw new GException("class must extend DefaultNode");
            }
            DefaultNode node = (DefaultNode) nodeClass.newInstance();
            GxunNodeAnno cfg = (GxunNodeAnno) nodeClass.getAnnotation(GxunNodeAnno.class);
            if (null == cfg) {
                throw new GException("node must been annotated by GxunNodeAnno");
            }
            GxunNodeConfig config = getConfig(cfg.gid(), cfg.sid(), cfg.key(), cfg.pwd(), cfg.port());
            DefaultNodeEventHandler eventHandler = new DefaultNodeEventHandler();
            GxunNode gxunNode = GxunApi.openNode(config, eventHandler);
            gxunNode.startup();
            node.setNode(gxunNode);
            node.setEventHandler(eventHandler);
            LOGGER.debug("node start success");
            return node;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            LOGGER.error(e);
        }
        return null;
    }

    public static synchronized DefaultNode startProfileNode() {
        return startProfileNode("gxun.properties");
    }

    public static synchronized DefaultNode startProfileNode(String propFile) {
        try {
            InputStream inputStream = GxunNodeHelper.class.getResourceAsStream("/" + propFile);
            Properties prop = new Properties();
            prop.load(inputStream);
            int gid = Integer.parseInt(prop.getProperty("gxun.config.node.gid"));
            int sid = Integer.parseInt(prop.getProperty("gxun.config.node.sid"));
            int port = Integer.parseInt(prop.getProperty("gxun.config.node.port"));
            String key = prop.getProperty("gxun.config.node.keyPath");
            String pwd = prop.getProperty("gxun.config.node.password");
            return getNode(gid, sid, key, pwd, port);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("gxun.properties is not found");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e);
        }
        return null;
    }

    private static DefaultNode getNode(long gid, long sid, String key, String pwd, int port) throws GxunException {
        DefaultNode node = new DefaultNode();
        GxunNodeConfig config = getConfig(gid, sid, key, pwd, port);
        DefaultNodeEventHandler eventHandler = new DefaultNodeEventHandler();
        GxunNode gxunNode = GxunApi.openNode(config, eventHandler);
        gxunNode.startup();
        node.setNode(gxunNode);
        node.setEventHandler(eventHandler);
        LOGGER.debug("node start success");
        return node;
    }

    private static GxunNodeConfig getConfig(long gid, long sid, String key, String pwd, int port) throws GException {
        GxunNodeConfig.Builder builder = GxunNodeConfig.newBuilder()
                .setGid(gid)
                .setSubId(sid)
                .setPrivateKeyPwd(pwd)
                .setPort(port);
        String keyPath = getPath(key);
        String keyStr = getKeyStr(key);
        if(null != keyPath){
            builder.setPrivateKeyPath(keyPath);
        }else if(null != keyStr){
            builder.setPrivateKey(keyStr);
        }else{
            throw new GException("private key is not found");
        }
        GxunNodeConfig config = builder.build();
        return config;
    }

    private static String getKeyStr(String path) {
        InputStream inputStream = GxunNodeHelper.class.getResourceAsStream("/" + path);
        try {
            byte[] keyBytes = new byte[2048];
            inputStream.read(keyBytes);
            return new String(keyBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file = new File(GxunNodeHelper.class.getClassLoader().getResource(path).getPath());
        }
        if (file.exists()) {
            return file.getAbsolutePath();
        }
        return null;
    }
}
