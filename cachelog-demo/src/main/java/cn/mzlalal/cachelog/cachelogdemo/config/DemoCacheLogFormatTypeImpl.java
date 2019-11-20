package cn.mzlalal.cachelog.cachelogdemo.config;

import cn.mzlalal.cachelog.cachelogcore.entity.CacheLog;
import cn.mzlalal.cachelog.cachelogcore.interfaces.CacheLogFormatTypeInterface;
import com.alibaba.fastjson.JSON;

/**
 * @description: 自定义日志格式化
 * @author: Mzlalal
 * @date: 2019/11/20 15:23
 * @version: 1.0
 */
public class DemoCacheLogFormatTypeImpl implements CacheLogFormatTypeInterface {

    @Override
    public String formatLog(CacheLog cacheLog) {
        return "DemoCacheLogFormatTypeImpl:"+JSON.toJSONString(cacheLog);
    }
}
