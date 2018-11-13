package com.ggj.datacenter.business.mapper;

import java.util.List;
import java.util.Map;

/**
 * BaseMapper
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
public interface BaseMapper {

    List<Map<String, Object>> executorSelect(String sql);
}
