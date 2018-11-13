package com.ggj.datacenter.business.mapper;

import com.ggj.datacenter.model.vo.CenterSqlExpandVo;
import org.springframework.stereotype.Repository;

/**
 * mapper
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
@Repository
public interface CenterSqlExpandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CenterSqlExpandVo record);

    int insertSelective(CenterSqlExpandVo record);

    CenterSqlExpandVo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CenterSqlExpandVo record);

    int updateByPrimaryKey(CenterSqlExpandVo record);

    CenterSqlExpandVo findBySqlId(long sqlId);
}