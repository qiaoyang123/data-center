package com.ggj.datacenter.business.service;

import com.ggj.datacenter.business.mapper.CenterSqlParamMapper;
import com.ggj.datacenter.entity.CenterSqlParam;
import com.ggj.datacenter.model.vo.CenterSqlParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据中心 sql 参数Service
 *
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/10/10 17:15
 * @since 1.0
 */
@Service
public class CenterSqlParamService {

    @Autowired
    private CenterSqlParamMapper mapper;

    public int insert(CenterSqlParamVo param) {
        return mapper.insert(param);
    }

    public int deleteById(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public int updateById(CenterSqlParamVo param){
        return mapper.updateByPrimaryKeySelective(param);
    }

    public List<CenterSqlParamVo> findAll(CenterSqlParamVo param){
        return mapper.findAll(param);
    }

    public Long findCount(CenterSqlParamVo param){
        return mapper.findCount(param);
    }

    public List<CenterSqlParamVo> getBySqlId(CenterSqlParamVo param) {
        return mapper.selectBySqlId(param);
    }
}
