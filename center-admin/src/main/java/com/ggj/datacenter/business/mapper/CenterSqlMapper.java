package com.ggj.datacenter.business.mapper;

import com.ggj.datacenter.entity.CenterSql;
import com.ggj.datacenter.model.vo.CenterSqlVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 服务参数mapper
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
@Repository
public interface CenterSqlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CenterSqlVo record);

    int insertSelective(CenterSql record);

    CenterSqlVo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CenterSqlVo record);

    int updateByPrimaryKey(CenterSqlVo record);

    /**
     * 多条件查询
     * @param record
     * @author qy
     * @return
     */
    List<CenterSqlVo> findAll(CenterSqlVo record);

    /**
     * 多条件查询记录总数
     * @param record
     * @author qy
     * @return
     */
    Long findCount(CenterSqlVo record);
}