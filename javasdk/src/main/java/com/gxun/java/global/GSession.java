package com.gxun.java.global;


import com.gxun.core.GxunRequest;
import com.gxun.core.GxunResponse;

/**
 * @author sunny
 */
public class GSession {
    private static ThreadLocal<GxunRequest> requestCache = new ThreadLocal<>();
    private static ThreadLocal<GxunResponse> responseCache = new ThreadLocal<>();

    public static GxunRequest getRequest() {
        return requestCache.get();
    }

    public static void setRequest(GxunRequest req) {
        requestCache.set(req);
    }
    public static void clearRequest() {
        requestCache.remove();
    }
    public static GxunResponse getResponse() {
        return responseCache.get();
    }

    public static void setResponse(GxunResponse resp) {
        responseCache.set(resp);
    }
    public static void clearResponse() {
        responseCache.remove();
    }
}
