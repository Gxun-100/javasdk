package com.gxun.java.annotation;

import java.lang.annotation.*;

/**
 * GxunMethodAnno 用于将一个Java的method描述为特定“数据交换标准”的特定“版本”的特定“Method”的事件响应句柄。
 * method的定义格式需要符合：public void yourmethodhandler(GxunRequest gxunRequest);
 * 在指定数据交换标准时，我们可以使用ID或者名称，通常由于数据交换标准ID并不友好，我们推荐使用数据交换标准名称。
 * @author sunny
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GxunMethodAnno {

    /**
     * 极迅数据交换标准ID，该参数可选
     * 默认为0，为0时，程序将参考数据交换标准名称
     * @return
     */
    int protocolId() default 0;

    /**
     * 极迅数据交换标准的名称，该参数必须提供
     * 注意，该参数需要和www.gxun.com中定义的数据交换标准的名称严格一致
     * @return
     */
    String protocolName();

    /**
     * 极迅数据交换标准的版本，该参数可选
     * 如果不提供该参数，那么对应当前数据交换标准的所有版本的调用都将进入该句柄，由开发者自行处理不同数据交换标准之间的差异。
     * 开发者可以通过：gxunRequest.getProtocolVer()得到当前调用request的数据交换标准的版本。
     * @return
     */
    String protocolVer() default "";

    /**
     * 极迅数据交换标准的远程过程调用Method名称
     * 该方法名称被定义在www.gxun.com的数据交换标准中
     * 开发者使用时，需要和数据交换标准定义的“API编码”严格一致
     * @return
     */
    String method();
}
