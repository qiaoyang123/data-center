package com.ggj.datacenter.enums;

/**
 * sql操作符枚举
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
public enum SqlOperator {
    AND("and"),
    OR("or"),
    GROUPBY("group by"),
    LIMIT("limit"),
    GT(">"),
    LT("<"),
    GE(">="),
    LE("<="),
    IN("IN"),
    NE("!=");

    private String operator;

    SqlOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
