package com.gxun.java.response;

import com.alibaba.fastjson.JSONObject;

/**
 * @author sunny
 */
public class GResponse {
    public GResponse valueOf(String jsonStr){
        return JSONObject.parseObject(jsonStr, this.getClass());
    }
}
