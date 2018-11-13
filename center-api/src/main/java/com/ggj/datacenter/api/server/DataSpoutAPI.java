package com.ggj.datacenter.api.server;

import com.ggj.platform.gsf.result.PlainResult;

import java.util.Map;

/**
 * 数据中心统一数据出口
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
public interface DataSpoutAPI {

    /**
     * 获取数据
     *
     * @param serviceName 接口名称
     * @param params      参数map
     * @return
     */
    PlainResult getData(String serviceName, Map<String, Object> params);
}
