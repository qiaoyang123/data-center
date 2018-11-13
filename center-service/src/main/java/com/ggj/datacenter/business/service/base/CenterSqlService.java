package com.ggj.datacenter.business.service.base;

import com.ggj.datacenter.business.mapper.CenterSqlMapper;
import com.ggj.datacenter.entity.CenterSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据中心sql Service
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/10/10 17:14
 * @since 1.0
 */
@Service
public class CenterSqlService {

    @Autowired
    private CenterSqlMapper mapper;

    public int insert(CenterSql sql) {
        return mapper.insert(sql);
    }

    public int deleteById(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public List<CenterSql> findAll(CenterSql record) {
        return mapper.findAll(record);
    }

    public Long findCount(CenterSql record) {
        return mapper.findCount(record);
    }
}
