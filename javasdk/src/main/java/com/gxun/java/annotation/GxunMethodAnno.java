package com.gxun.java.annotation;

import java.lang.annotation.*;

/**
 * @author sunny
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GxunMethodAnno {
    /**
     * 标准scheme对应的方法名
     *
     * @return
     */
    String method();

    /**
     * 标准id
     * @return
     */
    int protocolId() default 0;

    /**
     * 标准版本
     * @return
     */
    String protocolVer() default "";
    /**
     * 标准名称
     * @return
     */
    String protocolName();
}
