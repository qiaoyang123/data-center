package com.ggj.datacenter.business.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.ggj.datacenter.api.server.DataSpoutAPI;
import com.ggj.datacenter.api.service.SqlService;
import com.ggj.datacenter.business.mapper.BaseMapper;
import com.ggj.datacenter.common.utils.DateUtils;
import com.ggj.datacenter.common.utils.Md5Utils;
import com.ggj.datacenter.common.utils.StrUtils;
import com.ggj.datacenter.entity.CenterSql;
import com.ggj.datacenter.enums.ResultCode;
import com.ggj.datacenter.result.ResultVo;
import com.ggj.platform.gsf.result.CodeMsg;
import com.ggj.platform.gsf.result.PlainResult;
import com.ggj.platform.onecache.factory.redis.RedisClientIF;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;

/**
 * 数据中心统一数据出口
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
@Service(interfaceClass = DataSpoutAPI.class)
@Slf4j
public class DataSpoutAPIImpl implements DataSpoutAPI {

    private static final Logger dataSpoutLog = LoggerFactory.getLogger("dataSpoutLogger");

    @Reference
    private SqlService sqlService;

    @Autowired
    private BaseMapper mapper;

    @Resource
    private RedisClientIF redisClient;

    private static final String REDIS_SUFFIX = "_result";

    @Override
    public PlainResult getData(String serviceName, Map<String, Object> params) {

        String matchKey = "bigdata" + ":" + serviceName + REDIS_SUFFIX + "*";
        Set<String> keysSet = redisClient.keys(matchKey);
        Set<String> scan = redisClient.scan(matchKey);
        Set<String> scan1 = redisClient.get(matchKey);
        String prefix = redisClient.getPrefix()+"*";
        Set<String> keysSet1 = redisClient.keys(prefix);
        Set<String> scan2 = redisClient.scan(prefix);
        Set<String> scan22 = redisClient.get(prefix);

        String logServiceName = serviceName;
        String logReqeustTime = DateUtils.getTimeStampStr(new Date());
        String logIp = params.get("ip") == null ? "":params.get("ip").toString();
        String logIsSuccess = "1";
        String logErrorMsg = "success";
        String logRequestSql = "";
        String logRequestRes = "";
        String logIsCacheResult = "0";
        String resultFrom = "0";



        PlainResult executorPr = new PlainResult();
        try {

            //获取执行的sql
            PlainResult sqlResult = getSql(serviceName, params);
            if (!sqlResult.isOk()) {
                logErrorMsg = sqlResult.getMessage();
                return sqlResult;
            }

            List<Map<String, Object>> executorResult = new ArrayList<>();

            //获取sql对象
            CenterSql centerSql = JSONObject.parseObject(sqlResult.getData().toString(), CenterSql.class);
            //查询缓存中是否存在,如果存在，返回缓存值
            String requestSql = centerSql.getSql();
            logRequestSql = requestSql;
            log.info("查询sql：" + requestSql);
            Integer isCacheResult = centerSql.getCacheResult();
            if (isCacheResult.equals(1)) {
                logIsCacheResult = "1";
                Object result = redisClient.get(serviceName + REDIS_SUFFIX + Md5Utils.md5(JSONObject.toJSONString(params)));
                log.info("缓存结果前缀:"+serviceName+REDIS_SUFFIX);
                log.info("缓存结果key:"+serviceName + REDIS_SUFFIX + Md5Utils.md5(JSONObject.toJSONString(params)));
                if (result != null) {
                    log.info("缓存结果:" + result.toString());
                    logRequestRes = result.toString();
                    resultFrom = "1";
                    executorPr.success(result.toString());
                    dataSpoutLog.info(logServiceName+"|"+logReqeustTime+"|"+logIp+"|"+logIsSuccess+"|"+logErrorMsg+"|"+
                            logRequestSql+"|"+logRequestRes+"|"+logIsCacheResult+"|"+resultFrom);
                    return executorPr;
                }
            }

            executorResult = mapper.executorSelect(centerSql.getSql());
            //转换key命名
            List<Map<String, Object>> transformResult = transformMapKey(executorResult);
            log.info("查询结果:" + JSONObject.toJSONString(transformResult));

            //判断结果是否记入缓存
            Integer cacheResult = centerSql.getCacheResult();

            if (cacheResult.equals(1)) {
                redisClient.put(serviceName + REDIS_SUFFIX + Md5Utils.md5(JSONObject.toJSONString(params)), JSONObject.toJSONString(transformResult), centerSql.getCacheResultTime().intValue());
            }

            executorPr.success(JSONObject.toJSONString(transformResult));
            logRequestRes = JSONObject.toJSONString(transformResult);
        } catch (Exception e) {
            logIsSuccess = "0";
            logErrorMsg = e.getMessage();
            log.error("service_name" + serviceName + "executor exception", e);
            executorPr.failure(new CodeMsg(1L, "服务异常"));

        }
        dataSpoutLog.info(logServiceName+"|"+logReqeustTime+"|"+logIp+"|"+logIsSuccess+"|"+logErrorMsg+"|"+
                logRequestSql+"|"+logRequestRes+"|"+logIsCacheResult+"|"+resultFrom);
        return executorPr;
    }

    public PlainResult getSql(String serviceName, Map<String, Object> params) {
        PlainResult pr = new PlainResult();
        ResultVo result = sqlService.getSql(serviceName, params);
        if (result.getCode().equals(ResultCode.SUCCESS.getCode())) {
            pr.success(result.getData());
        } else {
            pr.failure(new CodeMsg(1L, result.getMessage()));
        }
        return pr;
    }

    public List<Map<String, Object>> transformMapKey(List<Map<String, Object>> originalMap) {
        List<Map<String, Object>> tranformResult = new ArrayList<>();

        for (Map<String, Object> m : originalMap) {
            Map<String, Object> newMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : m.entrySet()) {
                String key = entry.getKey();
                String mySqlField = StrUtils.underline2Camel(key);
                newMap.put(mySqlField, entry.getValue());
            }
            tranformResult.add(newMap);
        }

        return tranformResult;
    }
}
