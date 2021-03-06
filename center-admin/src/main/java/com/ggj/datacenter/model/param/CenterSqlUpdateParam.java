package com.ggj.datacenter.model.param;

import com.ggj.datacenter.entity.CenterSql;
import com.ggj.datacenter.entity.page.PageResult;
import com.ggj.datacenter.model.vo.CenterSqlVo;
import com.ggj.platform.adonis.validator.NotBlank;
import com.ggj.platform.adonis.validator.Validator;
import com.ggj.platform.adonis.validator.ValidatorApi;
import lombok.Data;

/**
 * @author <a href="mailto:zhongchao@gegejia.com">珞玉</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
@Data
@Validator
public class CenterSqlUpdateParam implements ValidatorApi {

    @NotBlank
    private String serviceName;

    private Long sqlId;

    @NotBlank
    private String serviceSql;

    private Integer serviceStatus;

    private Integer type;

    private Integer cacheSql;

    private Long cacheSqlTime;

    private Integer cacheResult;

    private Long cacheResultTime;

    private Boolean deleteCacheSql;

    private Boolean deleteCacheResult;

    private String description;

    private String channelIds;

    public CenterSqlVo convert(CenterSqlUpdateParam param){
        CenterSqlVo centerSql = new CenterSqlVo();
        centerSql.setId(param.getSqlId());
        centerSql.setName(param.getServiceName());
        centerSql.setSql(param.getServiceSql());
        centerSql.setStatus(param.getServiceStatus());
        centerSql.setType(param.getType());
        centerSql.setCacheSql(param.getCacheSql());
        centerSql.setCacheSqlTime(param.getCacheSqlTime());
        centerSql.setCacheResult(param.getCacheResult());
        centerSql.setCacheResultTime(param.getCacheResultTime());
        centerSql.setDescription(param.getDescription());
        centerSql.setChannels(channelIds);
        return centerSql;
    }
}
