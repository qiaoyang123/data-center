package com.ggj.datacenter.business.mapper;

import com.ggj.datacenter.entity.CenterSql;

import java.util.List;

/**
 * 服务参数mapper
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
public interface CenterSqlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CenterSql record);

    int insertSelective(CenterSql record);

    CenterSql selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CenterSql record);

    int updateByPrimaryKey(CenterSql record);

    /**
     * 多条件查询
     * @param record
     * @author qy
     * @return
     */
    List<CenterSql> findAll(CenterSql record);

    /**
     * 多条件查询记录总数
     * @param record
     * @author qy
     * @return
     */
    Long findCount(CenterSql record);
}