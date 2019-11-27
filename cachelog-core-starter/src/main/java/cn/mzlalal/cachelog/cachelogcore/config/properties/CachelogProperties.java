package cn.mzlalal.cachelog.cachelogcore.config.properties;

import cn.mzlalal.cachelog.cachelogcore.entity.enums.FormatTypeEnums;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.Objects;

/**
 * @description: 日志打印格式属性类
 * @author: Mzlalal
 * @date: 2019/11/20 12:07
 * @version: 1.0
 */
@ConfigurationProperties(prefix = "cn.mzlalal.cachelog")
public class CachelogProperties {
    /**
     * 日志打印类型
     */
    private FormatTypeEnums formatTypeEnums;

    /**
     * 类路径
     */
    private Class classPath;

    /**
     * 方法名称
     */
    private String methodName = "formatLog";

    /**
     * 类路径
     */
    private Class[] exceptionEntity;

    public FormatTypeEnums getFormatTypeEnums() {
        return formatTypeEnums;
    }

    public void setFormatTypeEnums(FormatTypeEnums formatType) {
        this.formatTypeEnums = formatType;
    }

    public Class getClassPath() {
        return classPath;
    }

    public void setClassPath(Class classPath) {
        this.classPath = classPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CachelogProperties that = (CachelogProperties) o;
        return formatTypeEnums == that.formatTypeEnums &&
                Objects.equals(classPath, that.classPath) &&
                Objects.equals(methodName, that.methodName) &&
                Arrays.equals(exceptionEntity, that.exceptionEntity);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(formatTypeEnums, classPath, methodName);
        result = 31 * result + Arrays.hashCode(exceptionEntity);
        return result;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class[] getExceptionEntity() {
        return exceptionEntity;
    }

    public void setExceptionEntity(Class[] exceptionEntity) {
        this.exceptionEntity = exceptionEntity;
    }

    @Override
    public String toString() {
        return "CachelogProperties{" +
                "formatTypeEnums=" + formatTypeEnums +
                ", classPath=" + classPath +
                ", methodName='" + methodName + '\'' +
                ", exceptionEntity=" + Arrays.toString(exceptionEntity) +
                '}';
    }
}
