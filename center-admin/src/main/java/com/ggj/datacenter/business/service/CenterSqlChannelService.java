package com.ggj.datacenter.business.service;

import com.ggj.datacenter.business.mapper.CenterSqlChannelMapper;
import com.ggj.datacenter.model.vo.CenterSqlChannelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/10/17 18:32
 * @since 1.0
 */
@Service
public class CenterSqlChannelService {

    @Autowired
    private CenterSqlChannelMapper mapper;

    public List<CenterSqlChannelVo> findAll(CenterSqlChannelVo vo){
        return mapper.findAll(vo);
    }

    public Long findCount(CenterSqlChannelVo vo){
        return mapper.findCount(vo);
    }

    public List<CenterSqlChannelVo> findBySqlId(long sqlId){
        return mapper.findBySqlId(sqlId);
    }
}
