package com.ggj.datacenter.business.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.ggj.datacenter.api.service.SqlService;
import com.ggj.datacenter.business.service.base.CenterSqlParamService;
import com.ggj.datacenter.business.service.base.CenterSqlService;
import com.ggj.datacenter.common.utils.Md5Utils;
import com.ggj.datacenter.common.utils.SqlUtils;
import com.ggj.datacenter.entity.CenterSql;
import com.ggj.datacenter.entity.CenterSqlParam;
import com.ggj.datacenter.result.ResultVo;
import com.ggj.platform.onecache.factory.redis.RedisClientIF;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 获取sqlService
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
@Service(interfaceClass = SqlService.class)
@Slf4j
public class SqlServiceImpl implements SqlService {

    @Autowired
    private CenterSqlService sqlService;

    @Autowired
    private CenterSqlParamService paramService;

    @Autowired
    private RedisClientIF redisCacheClient;

    private static final String REDIS_SUFFIX = "_sql";

    @Override
    public ResultVo getSql(String serviceName, Map<String, Object> params) {
        //获取sql 先查询缓存是否有值，如果有值，取缓存，如果没有根据配置，决定是否缓存
//        redisCacheClient.delete(serviceName + REDIS_SUFFIX + Md5Utils.md5(JSONObject.toJSONString(params)));
//        redisCacheClient.delete(serviceName + REDIS_SUFFIX);
        Object cacheSql = redisCacheClient.get(serviceName + REDIS_SUFFIX + Md5Utils.md5(JSONObject.toJSONString(params)));
        if (cacheSql != null) {
            return ResultVo.success(cacheSql.toString());
        }

        CenterSql record = new CenterSql();
        record.setName(serviceName);
        record.setStatus(1);
        List<CenterSql> sqlRecords = sqlService.findAll(record);
        if (CollectionUtils.isEmpty(sqlRecords)) {
            return ResultVo.faild("服务不存在");
        }
        record = sqlRecords.get(0);

        //获取参数
        CenterSqlParam param = new CenterSqlParam();
        param.setSqlId(record.getId());
        List<CenterSqlParam> sqlParams = paramService.getBySqlId(param);
        List<Map<String, String>> paramKeys = sqlParams.stream().map(e -> {
            Map<String, String> paramMap = new HashMap<>();
            String paramKey = e.getParamKey();
            String paramOperator = e.getParamOperator();
            Integer isNecessary = e.getIsNecessary();
            paramMap.put("paramKey", paramKey);
            paramMap.put("paramOperator", paramOperator);
            paramMap.put("isNecessary", isNecessary + "");
            return paramMap;
        }).collect(Collectors.toList());

        String sql = null;
        try {
            sql = SqlUtils.buildQueryCondition(record.getSql(), params, paramKeys);
        } catch (Exception e) {
            log.error("sql语句解析异常", e);
            return ResultVo.faild(e.getMessage());
        }
        //判断是否将sql记入缓存
        Integer isCache = record.getCacheSql();
        record.setSql(sql);
        if (isCache.equals(1)) {
            redisCacheClient.put(serviceName + REDIS_SUFFIX + Md5Utils.md5(JSONObject.toJSONString(params)), JSONObject.toJSONString(record), record.getCacheSqlTime().intValue());
        }

        return ResultVo.success(JSONObject.toJSONString(record));
    }
}
