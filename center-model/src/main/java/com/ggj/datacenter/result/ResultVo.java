package com.ggj.datacenter.result;

import java.io.Serializable;

/**
 * 统一返回实体类
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
public class ResultVo implements Serializable {


    private static final long serialVersionUID = 849289562665810990L;

    private Integer code;

    private String message;

    private String data;

    public static ResultVo success(String data) {
        ResultVo vo = new ResultVo();
        vo.setCode(1);
        vo.setMessage("success");
        vo.setData(data);
        return vo;
    }

    public static ResultVo faild(String data) {
        ResultVo vo = new ResultVo();
        vo.setCode(0);
        vo.setMessage(data);
        return vo;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
