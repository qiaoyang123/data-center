package com.ggj.datacenter.protocol;

import lombok.Data;

/**
 * @author <a href="mailto:zhongchao@gegejia.com">珞玉</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
@Data
public class Result<T> {

    /**
     * 错误码
     */
    private Long code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据对象
     */
    private T data;

    public static <T> Result<T> success() {
        Result<T> result = gen(1L, "success");
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = success();
        result.data = data;
        return result;
    }

    public static Result failure(Long code, String msg) {
        return gen(code, msg);
    }


    private static Result gen(Long code, String msg) {
        Result result = new Result();
        result.code = code;
        result.msg = msg;
        return result;
    }
}
