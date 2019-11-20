package cn.mzlalal.cachelog.cachelogcore.interfaces;

import cn.mzlalal.cachelog.cachelogcore.entity.CacheLog;
import com.alibaba.fastjson.JSON;

/**
 * @description: 自定义日志格式化结构 需要实现这个接口
 * @author: Mzlalal
 * @date: 2019/11/20 15:06
 * @version: 1.0
 */
public interface CacheLogFormatTypeInterface {

    /**
     * 自定义转换cachelog
     * 默认实现为fastjson JSON.toJSONString(cacheLog);
     * @param cacheLog 日志对象
     * @return string 对象
     */
    default String formatLog(CacheLog cacheLog) {
        return JSON.toJSONString(cacheLog);
    }
}
