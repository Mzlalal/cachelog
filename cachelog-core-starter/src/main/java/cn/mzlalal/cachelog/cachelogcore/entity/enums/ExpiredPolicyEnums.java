package cn.mzlalal.cachelog.cachelogcore.entity.enums;

/**
 * @description: redis过期策略枚举类
 * @author: Mzlalal
 * @date: 2019/11/21 15:30
 * @version: 1.0
 */
public enum ExpiredPolicyEnums {
    /**
     * 从不过期
     */
    NERVER,
    /**
     * 在300-3000秒内随机过期
     */
    RANDOM,
    /**
     * 自定义 默认300秒
     */
    DEFAULT
}
