package com.ggj.datacenter.model.query;

import com.ggj.datacenter.entity.CenterSql;
import com.ggj.datacenter.entity.page.PageEntity;
import com.ggj.datacenter.model.vo.CenterSqlVo;
import com.ggj.platform.adonis.validator.NotBlank;
import com.ggj.platform.adonis.validator.Validator;
import com.ggj.platform.adonis.validator.ValidatorApi;
import lombok.Data;

import java.util.Queue;

/**
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
@Data
public class CenterSqlQuery extends PageEntity{

    private String name;

    private Integer status;

    private Integer type;

    private Integer isCacheSql;

    private Long cacheSqlTime;

    private Integer isCacheResult;

    private Long cacheResultTime;

    private Long channelId;

    public CenterSqlVo convert(CenterSqlQuery query){
        CenterSqlVo sql = new CenterSqlVo();
        sql.setName(query.getName());
        sql.setStatus(query.getStatus());
        sql.setType(query.getType());
        sql.setCacheSql(query.getIsCacheSql());
        sql.setCacheSqlTime(query.getCacheSqlTime());
        sql.setCacheResult(query.getIsCacheResult());
        sql.setCacheResultTime(query.getCacheResultTime());
        sql.setPage(query.getPage());
        sql.setRows(query.getRows());
        sql.setOrders(query.getOrders());
        sql.setChannelId(query.getChannelId());
        return sql;
    }
}
