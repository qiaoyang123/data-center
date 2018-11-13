package com.ggj.datacenter.model.query;

import com.ggj.datacenter.common.utils.StrUtils;
import com.ggj.datacenter.entity.page.PageEntity;
import com.ggj.datacenter.model.vo.CenterChannelVo;
import com.ggj.platform.adonis.validator.In;
import lombok.Data;

/**
 * 数据中心渠道分页查询参数
 *
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/10/18 10:37
 * @since 1.0
 */
@Data
public class CenterChannelQuery extends PageEntity {

    private String channelName;

    private Integer channelStatus;

    private Integer channelType;

    public CenterChannelVo convert(CenterChannelQuery param) {
        CenterChannelVo vo = new CenterChannelVo();
        vo.setChannelName(param.getChannelName());
        vo.setChannelStatus(param.getChannelStatus());
        vo.setChannelType(param.getChannelType());
        vo.setPage(param.getPage());
        vo.setOrders(param.getOrders());
        vo.setOrderMap(StrUtils.getOrderMap(param.getOrders()));
        vo.setRows(param.getRows());
        return vo;
    }


}
