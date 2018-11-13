package com.ggj.datacenter.api.service;

import com.ggj.datacenter.result.ResultVo;

import java.util.Map;

/**
 * 获取执行sql的接口
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
public interface SqlService {

    /**
     * 获取执行的sql
     *
     * @param serviceName
     * @param params
     * @return
     */
    ResultVo getSql(String serviceName, Map<String, Object> params);
}
