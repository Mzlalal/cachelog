package cn.mzlalal.cachelog.cachelogcore.entity.enums;

/**
 * @description: 日志实体类打印格式
 * @author: Mzlalal
 * @date: 2019/11/20 13:26
 * @version: 1.0
 */
public enum FormatType {
    /**
     * 通过 alibaba fastjson 将实体转换成string
     */
    FASTJSON,

    /**
     * 通过 org.apache.commons.lang3.builder.ReflectionToStringBuilder 将实体转换成string
     */
    REFLECTIONTOSTRINGBUILDER,

    /**
     * 自定义 通过类名和方法名进行反射调用
     */
    CUSTOMIZATION
}
