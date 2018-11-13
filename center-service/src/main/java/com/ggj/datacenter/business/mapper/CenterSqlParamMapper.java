package com.ggj.datacenter.business.mapper;

import com.ggj.datacenter.entity.CenterSqlParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * mapper
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
@Repository
public interface CenterSqlParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CenterSqlParam record);

    int insertSelective(CenterSqlParam record);

    CenterSqlParam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CenterSqlParam record);

    int updateByPrimaryKey(CenterSqlParam record);

    /**
     * 通过多条件查询
     * @param param
     * @return list
     */
    List<CenterSqlParam> findAll(CenterSqlParam param);

    /**
     * 多条件查询记录数
     * @param param
     * @return
     */
    Long findCount(CenterSqlParam param);

    /**
     * 通过sqlId查询记录
     * @param param
     * @return
     */
    List<CenterSqlParam> selectBySqlId(CenterSqlParam param);
}