package cn.mzlalal.cachelog.cachelogcore.config.properties;

import cn.mzlalal.cachelog.cachelogcore.entity.enums.FormatTypeEnums;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
    private String classPath;

    /**
     * 方法名称
     */
    private String methodName = "formatLog";

    public FormatTypeEnums getFormatTypeEnums() {
        return formatTypeEnums;
    }

    public void setFormatTypeEnums(FormatTypeEnums formatType) {
        this.formatTypeEnums = formatType;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CachelogProperties that = (CachelogProperties) o;
        return formatTypeEnums == that.formatTypeEnums &&
                Objects.equals(classPath, that.classPath) &&
                Objects.equals(methodName, that.methodName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(formatTypeEnums, classPath, methodName);
    }

    @Override
    public String toString() {
        return "CachelogProperties{" +
                "FormatTypeEnums=" + formatTypeEnums +
                ", classPath='" + classPath + '\'' +
                ", methodName='" + methodName + '\'' +
                '}';
    }

    public String getMethodName() {
        return methodName;
    }
}
