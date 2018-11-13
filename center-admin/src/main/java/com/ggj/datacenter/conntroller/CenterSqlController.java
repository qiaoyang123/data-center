package com.ggj.datacenter.conntroller;

import com.ggj.datacenter.business.service.CenterSqlChannelService;
import com.ggj.datacenter.business.service.CenterSqlService;
import com.ggj.datacenter.common.utils.DateUtils;
import com.ggj.datacenter.common.utils.StrUtils;
import com.ggj.datacenter.entity.CenterSqlChannel;
import com.ggj.datacenter.entity.CenterSqlParam;
import com.ggj.datacenter.entity.page.PageResult;
import com.ggj.datacenter.enums.ResultCode;
import com.ggj.datacenter.model.param.CenterSqlAddParam;
import com.ggj.datacenter.model.param.CenterSqlUpdateParam;
import com.ggj.datacenter.model.query.CenterSqlQuery;
import com.ggj.datacenter.model.vo.CenterSqlChannelVo;
import com.ggj.datacenter.model.vo.CenterSqlVo;
import com.ggj.datacenter.protocol.Result;
import com.ggj.platform.onecache.factory.redis.RedisClientIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * center sql controller
 *
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/10/11 19:31
 * @since 1.0
 */
@RestController
@RequestMapping("centerSql")
public class CenterSqlController {

    @Autowired
    private CenterSqlService sqlService;

    @Resource
    @Lazy
    private RedisClientIF redisClient;

    private static final String CACHE_SQL_SUFFIX = "_sql";

    private static final String CACHE_RESULT_SUFFIX = "_result";

    @Autowired
    private CenterSqlChannelService sqlChannelService;


    /**
     * 添加执行sql
     * @param param
     * @return
     */
    @RequestMapping("add")
    public Result addCenterSql(@RequestBody CenterSqlAddParam param) {
        return syncAddCenterSql(param);
    }

    /**
     * 更新sql
     * @param param
     * @return
     */
    @RequestMapping("update")
    public Result updateCenterSql(@RequestBody CenterSqlUpdateParam param){
//        deleteCache(param.getDeleteCacheSql(),param.getDeleteCacheResult(),param.getServiceName());
        return sqlService.updateSql(param.convert(param));
    }

    /**
     * 根据sqlId删除sql和参数
     * @param param
     * @return
     */
    @RequestMapping("delete")
    public Result deleteCenterSql(@RequestBody CenterSqlUpdateParam param){
        Long sqlId = param.getSqlId();
        if(sqlId == null){
            return Result.failure(ResultCode.FAILD.getCode().longValue(),"id不能为空");
        }
        sqlService.deleteById(sqlId);
        return Result.success();
    }

    /**
     * 分页获取sql
     * @param sqlQuery
     * @return
     */
    @RequestMapping("getByPage")
    public Result getByPage(@RequestBody CenterSqlQuery sqlQuery){
        CenterSqlVo vo = sqlQuery.convert(sqlQuery);
        vo.setOrderMap(StrUtils.getOrderMap(vo.getOrders()));
        List<CenterSqlVo> sqls = sqlService.findAll(vo);
        sqls = sqls.stream().map(e ->{
            e.setStatusStr(e.getStatus() == 1?"生效":"未生效");
            e.setCacheSqlStr(e.getCacheSql() == 1?"是":"否");
            e.setCacheSqlResultStr(e.getCacheResult() == 1?"是":"否");
            e.setCreateTimeStr(DateUtils.getTimeStampStr(e.getCreateTime()));
            e.setUpdateTimeStr(DateUtils.getTimeStampStr(e.getUpdateTime()));
            e.setSqlId(e.getId().toString());
            List<CenterSqlChannelVo> vos = sqlChannelService.findBySqlId(e.getId());
            if(!CollectionUtils.isEmpty(vos)){
                String channels = new String();
                String channelNames = new String();
                for (CenterSqlChannelVo channelVo:vos) {
                    channels += "," + channelVo.getChannelId();
                    channelNames += "," + channelVo.getChannelName();
                }

                e.setChannels(channels.replaceFirst(",",""));
                e.setChannelNames(channelNames.replaceFirst(",",""));
            }
            return  e;
        }).collect(Collectors.toList());
        Long count = sqlService.findCount(sqlQuery.convert(sqlQuery));
        PageResult<CenterSqlVo> pageResult = new PageResult<>();
        pageResult.setList(sqls);
        pageResult.setTotal(count.intValue());
        return Result.success(pageResult);
    }

    @RequestMapping("/getById")
    public Result getById(@RequestBody Long id){
        CenterSqlVo vo = sqlService.findById(id);
        return Result.success(vo);
    }

    @RequestMapping("/forbidden")
    public Result forbidden(@RequestBody CenterSqlUpdateParam param){
        Long sqlId = param.getSqlId();
        if(sqlId == null){
            return Result.failure(ResultCode.FAILD.getCode().longValue(),"id不能为空");
        }
        CenterSqlVo vo = sqlService.findById(sqlId);
        if(vo == null){
            return Result.failure(ResultCode.FAILD.getCode().longValue(),"此记录不存在");
        }

        sqlService.update(param.convert(param));
        return Result.success();
    }

    private  synchronized Result syncAddCenterSql(CenterSqlAddParam param){
        String serviceName = param.getServiceName();
        CenterSqlVo vo = new CenterSqlVo();
        vo.setName(serviceName);
        List<CenterSqlVo> sqls = sqlService.findAll(vo);
        if(!CollectionUtils.isEmpty(sqls)){
            return Result.failure(ResultCode.FAILD.getCode().longValue(),"不能重复添加已经存在的服务");
        }

        return sqlService.add(param.convert(param));
    }

//    private void deleteCache(Boolean deleteSql,Boolean deleteResult,String serviceName){
//        if(deleteSql){
//            String matchKey = cachePrefix+":" + serviceName + CACHE_SQL_SUFFIX + "*";
//            Set<String> keysSet = redisClient.keys(matchKey);
//            Set<String> scan = redisClient.scan(matchKey);
//            deleteRedisByKeySet(keysSet);
//        }
//
//        if(deleteResult){
//            String matchKey = cachePrefix+":"+serviceName + CACHE_RESULT_SUFFIX + "*";
//            Set<String> keysSet = redisClient.keys(matchKey);
//            Set<String> scan = redisClient.scan(matchKey);
//            Set<String> scan1 = redisClient.get(matchKey);
//            String prefix = redisClient.getPrefix();
//            Set<String> keysSet1 = redisClient.keys(prefix);
//            Set<String> scan2 = redisClient.scan(prefix);
//            Set<String> scan22 = redisClient.get(prefix);
//            redisClient.removeKeyPrefix(matchKey);
//            System.out.println(matchKey);
//            deleteRedisByKeySet(keysSet);
//        }
//    }

//    private void deleteRedisByKeySet(Set<String> keysSet){
//        if(keysSet != null){
//            Iterator<String> iterator = keysSet.iterator();
//            while (iterator.hasNext()){
//                String key = iterator.next();
//                redisClient.delete(key);
//            }
//        }
//    }

}
