package cn.mzlalal.cachelog.cachelogcore.config.properties;

import cn.mzlalal.cachelog.cachelogcore.entity.FormatType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 日志打印格式属性类
 * @author: Mzlalal
 * @date: 2019/11/20 12:07
 * @version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "cn.mzlalal.cachelog")
public class CachelogProperties {
    /**
     * 日志打印类型
     */
    private FormatType formatType;

    /**
     * 类路径
     */
    private String classPath;

    /**
     * 方法名称
     */
    private String methodName;
}
