package com.ggj.datacenter.business.mapper;

import com.ggj.datacenter.model.vo.CenterChannelVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * mapper
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/10/17
 * @since 1.0
 */
@Repository
public interface CenterChannelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CenterChannelVo record);

    int insertSelective(CenterChannelVo record);

    CenterChannelVo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CenterChannelVo record);

    int updateByPrimaryKey(CenterChannelVo record);

    /**
     * 多条件查询
     * @param record
     * @return
     */
    List<CenterChannelVo> findAll(CenterChannelVo record);

    /**
     * 多条件查询总记录数
     * @param record
     * @return
     */
    Long findCount(CenterChannelVo record);
}