package com.ggj.datacenter.business.service;

import com.ggj.datacenter.business.mapper.CenterChannelMapper;
import com.ggj.datacenter.business.mapper.CenterSqlChannelMapper;
import com.ggj.datacenter.model.vo.CenterChannelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据中心渠道service
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/10/17 18:26
 * @since 1.0
 */
@Service
public class CenterChannelService {

    @Autowired
    private CenterChannelMapper mapper;

    @Autowired
    private CenterSqlChannelMapper sqlChannelMapper;

    public List<CenterChannelVo> findAll(CenterChannelVo vo){
        return mapper.findAll(vo);
    }

    public Long findCount(CenterChannelVo vo){
        return mapper.findCount(vo);
    }

    public int updateById(CenterChannelVo vo){
        return mapper.updateByPrimaryKeySelective(vo);
    }

    public int add(CenterChannelVo vo){
        return mapper.insertSelective(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long channelId){
        mapper.deleteByPrimaryKey(channelId);
        sqlChannelMapper.deleteBySqlId(channelId);
    }

    public CenterChannelVo getById(Long id){
        return mapper.selectByPrimaryKey(id);
    }
}
