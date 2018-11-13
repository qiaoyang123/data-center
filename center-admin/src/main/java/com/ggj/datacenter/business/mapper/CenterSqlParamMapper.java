package com.ggj.datacenter.business.mapper;

import com.ggj.datacenter.model.vo.CenterSqlParamVo;
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

    int insert(CenterSqlParamVo record);

    int insertSelective(CenterSqlParamVo record);

    CenterSqlParamVo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CenterSqlParamVo record);

    int updateByPrimaryKey(CenterSqlParamVo record);

    /**
     * 通过多条件查询
     * @param param
     * @return list
     */
    List<CenterSqlParamVo> findAll(CenterSqlParamVo param);

    /**
     * 多条件查询记录数
     * @param param
     * @return
     */
    Long findCount(CenterSqlParamVo param);

    /**
     * 通过sqlId查询记录
     * @param param
     * @return
     */
    List<CenterSqlParamVo> selectBySqlId(CenterSqlParamVo param);

    /**
     * 通过sqlId批量删除参数
     * @param sqlId
     * @return
     */
    int deleteBySqlId(Long sqlId);
}