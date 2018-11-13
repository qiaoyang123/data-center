package com.ggj.datacenter.model.vo;

import com.ggj.datacenter.entity.CenterChannel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/10/17 17:19
 * @since 1.0
 */
@Data
public class CenterChannelVo extends CenterChannel implements Serializable {

    private static final long serialVersionUID = 7213194651544111741L;

    private String channelStatusStr;

    private String channelTypeStr;

    private String createTimeStr;

    private String updateTimeStr;
}
