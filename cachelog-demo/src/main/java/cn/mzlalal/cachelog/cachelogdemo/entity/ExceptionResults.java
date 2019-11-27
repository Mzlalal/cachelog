package cn.mzlalal.cachelog.cachelogdemo.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @description: 如果方法内抛出异常并捕获 使用该实体作为返回结果则不会进行存储redis缓存
 * @author: Mzlalal
 * @date: 2019/11/27 11:06
 * @version: 1.0
 */
@Data
public class ExceptionResults extends Results {
    /**
     * 异常信息
     */
    Throwable throwable;

    @Deprecated
    public static Results OK(String code, String msg, Object... obj) {
        return createExceptionResults(true, code, msg, null, obj);
    }

    /**
     * 返回是否成功标识为 true 的快速构建方法
     * @param msg 返回信息
     * @param obj 数据数组
     * @return
     */
    @Deprecated
    public static Results OK(String msg, Object... obj) {
        return createExceptionResults(true, "0", msg, null, obj);
    }

    /**
     * 返回是否成功标识为 false 的快速构建方法
     *
     * @param code      状态编码
     * @param msg       返回信息
     * @param throwable 异常
     * @param obj       数据数组
     * @return
     */
    public static Results FAIL(String code, String msg, Throwable throwable, Object... obj) {
        return createExceptionResults(false, code, msg, throwable, obj);
    }

    /**
     * 返回状态代码为-1 返回是否成功标识为 false 的快速构建方法
     *
     * @param msg       返回信息
     * @param throwable 异常
     * @param obj       数据数组
     * @return
     */
    public static Results FAIL(String msg, Throwable throwable, Object... obj) {
        return createExceptionResults(false, "-1", msg, throwable, obj);
    }

    /**
     * 创建返回结果
     *
     * @param flag      是否成功
     * @param code      返回状态编码 默认成功为0
     * @param msg       返回信息
     * @param throwable 异常
     * @param obj       数据数组
     * @return Results
     */
    private static Results createExceptionResults(boolean flag, String code, String msg, Throwable throwable, Object... obj) {
        ExceptionResults results = new ExceptionResults();
        results.setSuccess(flag);
        results.setCode(code);
        results.setMsg(msg);
        results.setThrowable(throwable);

        for (int i = 0; i < obj.length; i++) {
            results.getBody().put("data" + i, JSON.toJSONString(obj[i]));
        }
        return results;
    }

    /**
     * 返回异常时候只返回主要信息
     * @return
     */
    public String getThrowable() {
        return ExceptionUtils.getRootCauseMessage(throwable);
    }
}
