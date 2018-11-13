package com.ggj.datacenter.model.vo;

import com.ggj.datacenter.entity.CenterSqlChannel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/10/17 17:20
 * @since 1.0
 */
@Data
public class CenterSqlChannelVo extends CenterSqlChannel implements Serializable {

    private static final long serialVersionUID = 7213194650544111721L;

    private String channelName;
}
