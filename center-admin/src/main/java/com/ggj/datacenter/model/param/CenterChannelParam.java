package com.ggj.datacenter.model.param;

import com.ggj.datacenter.model.vo.CenterChannelVo;
import com.ggj.platform.adonis.validator.NotBlank;
import lombok.Data;

/**
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/10/18 11:00
 * @since 1.0
 */
@Data
public class CenterChannelParam {

    private Long channelId;

    @NotBlank
    private String channelName;

    private Integer channelStatus;

    private Integer channelType;

    public CenterChannelVo convert(CenterChannelParam param){
        CenterChannelVo channelVo = new CenterChannelVo();

        channelVo.setId(param.getChannelId());
        channelVo.setChannelName(param.getChannelName());
        channelVo.setChannelType(param.getChannelType());
        channelVo.setChannelStatus(param.getChannelStatus());
        return channelVo;
    }
}
