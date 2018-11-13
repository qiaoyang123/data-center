package com.ggj.datacenter.model.vo;

import com.ggj.datacenter.entity.CenterSqlParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/10/15 09:53
 * @since 1.0
 */
@Data
public class CenterSqlParamVo extends CenterSqlParam implements Serializable {
    private static final long serialVersionUID = 7213194650544111741L;

    private String paramStatusStr;

    private String isNecessaryStr;
}
