package com.ggj.datacenter.business.service.base;

import com.ggj.datacenter.business.mapper.CenterSqlParamMapper;
import com.ggj.datacenter.entity.CenterSqlParam;
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

    public int insert(CenterSqlParam param) {
        return mapper.insert(param);
    }

    public int deleteById(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public int updateById(CenterSqlParam param){
        return mapper.updateByPrimaryKeySelective(param);
    }

    public List<CenterSqlParam> findAll(CenterSqlParam param){
        return mapper.findAll(param);
    }

    public Long findCount(CenterSqlParam param){
        return mapper.findCount(param);
    }

    public List<CenterSqlParam> getBySqlId(CenterSqlParam param) {
        return mapper.selectBySqlId(param);
    }
}
