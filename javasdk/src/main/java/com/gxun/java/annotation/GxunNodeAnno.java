package com.gxun.java.annotation;

import java.lang.annotation.*;

/**
 * @author sunny
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GxunNodeAnno {
    /**
     * 节点GID
     *
     * @return
     */
    int gid();

    /**
     * 节点sid
     *
     * @return
     */
    int sid();

    /**
     * 秘钥
     *
     * @return
     */
    String key();

    /**
     * 私钥密码
     *
     * @return
     */
    String pwd() default "gxunyunke";

    /**
     * 端口
     *
     * @return
     */
    int port() default 0;
}
