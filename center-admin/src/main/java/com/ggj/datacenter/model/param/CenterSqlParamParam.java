package com.ggj.datacenter.model.param;

import com.ggj.datacenter.model.vo.CenterSqlParamVo;
import com.ggj.platform.adonis.validator.NotBlank;
import com.ggj.platform.adonis.validator.ValidatorApi;
import lombok.Data;

/**
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/10/15 09:45
 * @since 1.0
 */
@Data
public class CenterSqlParamParam implements ValidatorApi {

    private Long id;

    private Long sqlId;

    @NotBlank
    private String paramKey;

    private String paramTitle;

    private Integer isNecessary;

    @NotBlank
    private String paramOperator;

    private Integer paramStatus;

    public CenterSqlParamVo convert(CenterSqlParamParam param) {
        CenterSqlParamVo vo = new CenterSqlParamVo();
        vo.setId(param.getId());
        vo.setSqlId(param.getSqlId());
        vo.setParamKey(param.getParamKey());
        vo.setParamTitle(param.getParamTitle());
        vo.setIsNecessary(param.getIsNecessary());
        vo.setParamOperator(param.getParamOperator());
        vo.setParamStatus(param.getParamStatus());
        return vo;
    }
}
