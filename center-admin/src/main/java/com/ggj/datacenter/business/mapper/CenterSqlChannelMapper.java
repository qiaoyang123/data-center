package com.ggj.datacenter.business.mapper;

import com.ggj.datacenter.model.vo.CenterSqlChannelVo;
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
public interface CenterSqlChannelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CenterSqlChannelVo record);

    int insertSelective(CenterSqlChannelVo record);

    CenterSqlChannelVo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CenterSqlChannelVo record);

    int updateByPrimaryKey(CenterSqlChannelVo record);

    /**
     * 多条件查询
     * @param record
     * @return
     */
    List<CenterSqlChannelVo> findAll(CenterSqlChannelVo record);

    /**
     * 多条件查询总记录数
     * @param record
     * @return
     */
    Long findCount(CenterSqlChannelVo record);

    /**
     * 通过sqlId查询渠道list
     * @param sqlId
     * @return
     */
    List<CenterSqlChannelVo> findBySqlId(long sqlId);

    /**
     * 通过sql删除记录
     * @param sqlId
     * @return
     */
    int deleteBySqlId(long sqlId);
}