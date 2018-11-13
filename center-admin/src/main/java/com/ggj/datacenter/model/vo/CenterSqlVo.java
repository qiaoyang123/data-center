package com.ggj.datacenter.model.vo;

import com.ggj.datacenter.entity.CenterSql;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:zhongchao@gegejia.com">珞玉</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
@Data
public class CenterSqlVo extends  CenterSql implements Serializable {
    private static final long serialVersionUID = 7213194650544111740L;

    private String description;

    private String statusStr;

    private String cacheSqlStr;

    private String cacheSqlResultStr;

    private String createTimeStr;

    private String updateTimeStr;

    private String sqlId;

    private String channels;

    private String channelNames;

    private Long channelId;
}
