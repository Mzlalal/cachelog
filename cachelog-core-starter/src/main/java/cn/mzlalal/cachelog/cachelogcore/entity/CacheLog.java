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

    @Override
    public String toString() {
        return "CacheLog{" +
                "operationType='" + operationType + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", remoteIP='" + remoteIP + '\'' +
                ", requestParameter='" + requestParameter + '\'' +
                ", returnValue='" + returnValue + '\'' +
                ", totalConsumerTime=" + totalConsumerTime +
                ", startTimestamp=" + startTimestamp.toLocaleString() +
                ", endTimestamp=" + startTimestamp.toLocaleString() +
                '}';
    }
}
