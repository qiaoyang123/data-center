package com.ggj.datacenter.business.service;

import com.ggj.datacenter.business.mapper.CenterSqlChannelMapper;
import com.ggj.datacenter.business.mapper.CenterSqlExpandMapper;
import com.ggj.datacenter.business.mapper.CenterSqlMapper;
import com.ggj.datacenter.business.mapper.CenterSqlParamMapper;
import com.ggj.datacenter.entity.CenterSql;
import com.ggj.datacenter.entity.CenterSqlExpand;
import com.ggj.datacenter.enums.ResultCode;
import com.ggj.datacenter.model.vo.CenterSqlChannelVo;
import com.ggj.datacenter.model.vo.CenterSqlExpandVo;
import com.ggj.datacenter.model.vo.CenterSqlVo;
import com.ggj.datacenter.protocol.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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

    @Autowired
    private CenterSqlParamMapper paramMapper;

    @Autowired
    private CenterSqlExpandMapper expandMapper;

    @Autowired
    private CenterSqlChannelMapper sqlChannelMapper;

    @Transactional(rollbackFor = Exception.class)
    public Result add(CenterSqlVo sql) {
        mapper.insert(sql);
        String description = sql.getDescription();
        if(!StringUtils.isEmpty(description)){
            CenterSqlExpandVo expandVo = new CenterSqlExpandVo();
            expandVo.setSqlId(sql.getId());
            expandVo.setDescription(description);
            expandMapper.insert(expandVo);
        }

        if(!StringUtils.isEmpty(sql.getChannels())){
            String[] channels = sql.getChannels().split(",");
            for (String channelId:channels) {
                CenterSqlChannelVo sqlChannelVo = new CenterSqlChannelVo();
                sqlChannelVo.setSqlId(sql.getId());
                sqlChannelVo.setChannelId(new Long(channelId));
                sqlChannelMapper.insert(sqlChannelVo);
            }
        }
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        mapper.deleteByPrimaryKey(id);
        paramMapper.deleteBySqlId(id);
        expandMapper.deleteByPrimaryKey(id);
        sqlChannelMapper.deleteBySqlId(id);
    }


    public int update(CenterSqlVo sql){
        return mapper.updateByPrimaryKeySelective(sql);
    }

    public List<CenterSqlVo> findAll(CenterSqlVo record) {
        return mapper.findAll(record);
    }

    public Long findCount(CenterSqlVo record) {
        return mapper.findCount(record);
    }

    public CenterSqlVo findById(Long id){
        return mapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updateSql(CenterSqlVo sql){
        Long id = sql.getId();
        CenterSqlVo vo = mapper.selectByPrimaryKey(id);
        if(vo == null){
            return Result.failure(ResultCode.FAILD.getCode().longValue(),"该记录不存在");
        }

        //判断是否重复服务名称
        if(!sql.getName().equals(vo.getName())){
            CenterSqlVo checkQueryParam = new CenterSqlVo();
            checkQueryParam.setName(sql.getName());
            List<CenterSqlVo> nameCheck = mapper.findAll(checkQueryParam);
            if(!CollectionUtils.isEmpty(nameCheck)){
                return Result.failure(ResultCode.FAILD.getCode().longValue(),"服务名称已存在");
            }
        }


        //更新center_sql
        update(sql);

        //更新渠道
        String channels = sql.getChannels();
        sqlChannelMapper.deleteBySqlId(id);
        if(!StringUtils.isEmpty(channels)){
            String[] channelArr = channels.split(",");
            for (String channelId:channelArr) {
                CenterSqlChannelVo centerSqlChannelVo = new CenterSqlChannelVo();
                centerSqlChannelVo.setSqlId(id);
                centerSqlChannelVo.setChannelId(new Long(channelId));
                sqlChannelMapper.insert(centerSqlChannelVo);
            }
        }


        //更新sql_expand 如果存在更新，否则插入
        CenterSqlExpandVo expandVo = expandMapper.findBySqlId(id);
        String description = sql.getDescription();
        if(StringUtils.isEmpty(description) && expandVo == null){
            return Result.success();
        }
        if(expandVo == null){
            CenterSqlExpandVo insert = new CenterSqlExpandVo();
            insert.setSqlId(id);
            insert.setDescription(sql.getDescription());
            expandMapper.insert(insert);
        }else{
            CenterSqlExpandVo update = new CenterSqlExpandVo();
            update.setId(expandVo.getId());
            update.setDescription(sql.getDescription());
            expandMapper.updateByPrimaryKeySelective(update);
        }

        return Result.success();
    }
}
