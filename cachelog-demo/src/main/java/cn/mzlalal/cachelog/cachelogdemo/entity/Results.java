package cn.mzlalal.cachelog.cachelogdemo.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * @description:
 * @author: Mzlalal
 * @date: 2019/11/19 22:16
 * @version: 1.0
 */
@Data
public class Results implements Serializable {
    /**
     * 是否成功
     */
    private boolean success = true;
    /**
     * 状态代码
     */
    private String code = "0";
    /**
     * 提示信息
     */
    private String msg = "操作成功";

    /**
     * 附带参数
     */
    private LinkedHashMap<String, Object> body = new LinkedHashMap();

    /**
     * 返回状态代码为0 返回是否成功标识为 true 的快速构建方法
     * @param code 状态编码
     * @param msg 返回信息
     * @param obj 数据数组
     * @return
     */
    public static Results OK(String code, String msg, Object... obj) {
        return createResults(true, code, msg, obj);
    }

    /**
     * 返回是否成功标识为 true 的快速构建方法
     * @param msg 返回信息
     * @param obj 数据数组
     * @return
     */
    public static Results OK(String msg, Object... obj) {
        return createResults(true, "0", msg, obj);
    }

    /**
     * 返回是否成功标识为 false 的快速构建方法
     * @param code 状态编码
     * @param msg 返回信息
     * @param obj 数据数组
     * @return
     */
    public static Results FAIL(String code, String msg, Object... obj) {
        return createResults(false, code, msg, obj);
    }

    /**
     * 返回状态代码为-1 返回是否成功标识为 false 的快速构建方法
     * @param msg 返回信息
     * @param obj 数据数组
     * @return
     */
    public static Results FAIL(String msg, Object... obj) {
        return createResults(false, "-1", msg, obj);
    }

    /**
     * 创建返回结果
     * @param flag 是否成功
     * @param code 返回状态编码 默认成功为0
     * @param msg 返回信息
     * @param obj 数据数组
     * @return Results
     */
    private static Results createResults (boolean flag, String code, String msg, Object... obj) {
        Results results = new Results();
        results.setSuccess(flag);
        results.setCode(code);
        results.setMsg(msg);

        for (int i = 0; i < obj.length; i++) {
            results.getBody().put("data"+i, JSON.toJSONString(obj[i]));
        }
        return results;
    }
}
