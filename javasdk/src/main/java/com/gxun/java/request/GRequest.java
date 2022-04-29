package com.gxun.java.request;

import com.alibaba.fastjson.JSONObject;

/**
 * @author sunny
 */
public class GRequest {
    public GRequest valueOf(String jsonStr){
        return JSONObject.parseObject(jsonStr, this.getClass());
    }
}
