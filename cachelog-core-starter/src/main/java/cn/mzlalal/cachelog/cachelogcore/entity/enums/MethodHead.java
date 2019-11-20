package cn.mzlalal.cachelog.cachelogcore.entity.enums;

/**
 * @description: 命名方法头部
 * @author: Mzlalal
 * @date: 2019/11/20 14:31
 * @version: 1.0
 */
public enum MethodHead {
    /**
     * get查询单个
     */
    GET("get", "查询"),
    /**
     * list查询多个
     */
    LIST("list", "查询"),
    /**
     * 查询
     */
    FIND("find", "查询"),
    /**
     * 统计技术
     */
    COUNT("count", "统计"),
    /**
     * 保存
     */
    SAVE("save", "新增"),
    /**
     * 保存
     */
    INSERT("insert", "新增"),
    /**
     * 删除
     */
    REMOVE("remove", "删除"),
    /**
     * 删除
     */
    DELETE("delete", "删除"),
    /**
     * 更新
     */
    UPDATE("update", "更新");
    /**
     * 方法命名头部
     */
    private String head;
    /**
     * 方法操作类型
     */
    private String type;

    MethodHead(String head, String type) {
        this.head = head;
        this.type = type;
    }

    public String getHead() {
        return head;
    }

    public String getType() {
        return type;
    }
}
