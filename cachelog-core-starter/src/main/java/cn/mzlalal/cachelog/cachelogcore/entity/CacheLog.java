package cn.mzlalal.cachelog.cachelogcore.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description: log实体类
 * @author: Mzlalal
 * @date: 2019/11/20 11:24
 * @version: 1.0
 */
@Data
public class CacheLog {
    /**
     * 操作类型
     */
    private String operationType;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 调用方IP
     */
    private String remoteIP;
    /**
     * 模块名称 建议在类上使用 当前这个类是那个服务模块
     */
    private String moduleName;
    /**
     * 功能名称 建议在方法上使用 当前这个方法是为前端那个功能服务
     */
    private String functionName;
    /**
     * 请求参数
     */
    private String requestParameter;
    /**
     * 返回结果
     */
    private String returnValue;
    /**
     * 总耗时(ms)
     */
    private long totalConsumerTime;
    /**
     * 开始时间
     */
    private Date startTimestamp;
    /**
     * 结束时间
     */
    private Date endTimestamp;
}
