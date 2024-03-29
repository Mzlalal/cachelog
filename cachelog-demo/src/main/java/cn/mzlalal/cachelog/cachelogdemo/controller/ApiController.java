package cn.mzlalal.cachelog.cachelogdemo.controller;

import cn.mzlalal.cachelog.cachelogcore.annotaion.Cachelog;
import cn.mzlalal.cachelog.cachelogdemo.entity.ExceptionResults;
import cn.mzlalal.cachelog.cachelogdemo.entity.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @description: 该类中所有方法都会被aop切入
 * @author: Mzlalal
 * @date: 2019/11/19 22:15
 * @version: 1.0
 */
@Slf4j
@Cachelog(moduleName = "测试服务")
@RestController
@RequestMapping("test")
public class ApiController {

    /**
     * 不会记录日志
     * @return
     */
    @RequestMapping("testAnnotation")
    @Cachelog(isLog = true, functionName = "查找")
    public Results find () {
        return Results.OK("不会记录日志");
    }

    /**
     * 不会记录操作日志类型
     * @return
     */
    @RequestMapping("testAnnotationX")
    public Results fxind () {
        return Results.OK("不会记录操作类型");
    }

    /**
     * 这个不会保存到redis
     * @return
     */
    @RequestMapping("testAnnotation2")
    @Cachelog
    public Results save () {
        return Results.OK("这个不会保存到redis");
    }

    /**
     * 这个不会保存到redis
     * @return
     */
    @RequestMapping("testAnnotation3")
    public Results list (String arg1, String args2) {
        try {
            throw new IOException("查询失败");
        } catch (IOException e) {
            log.error("查询失败!", e);
            return ExceptionResults.FAIL("查询失败", e);
        }
    }
}
