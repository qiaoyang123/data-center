package com.ggj.datacenter.conntroller;

import com.alibaba.fastjson.JSONObject;
import com.ggj.bigdata.enums.TopicEnum;
import com.ggj.bigdata.producer.KafkaProducerClient;
import com.ggj.datacenter.business.service.CenterChannelService;
import com.ggj.datacenter.common.utils.DateUtils;
import com.ggj.datacenter.entity.page.PageResult;
import com.ggj.datacenter.enums.ResultCode;
import com.ggj.datacenter.model.param.CenterChannelParam;
import com.ggj.datacenter.model.query.CenterChannelQuery;
import com.ggj.datacenter.model.vo.CenterChannelVo;
import com.ggj.datacenter.model.vo.common.SelectOptionVo;
import com.ggj.datacenter.protocol.Result;
import com.ggj.platform.gsf.result.PlainResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据中心渠道controller
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/10/18 10:34
 * @since 1.0
 */
@RestController
@RequestMapping("/channel")
@Slf4j
public class CenterChannelController {

    @Autowired
    private CenterChannelService channelService;

    @RequestMapping("/getByPage")
    public Result getByPage(@RequestBody CenterChannelQuery query){
        List<CenterChannelVo> channelVos = channelService.findAll(query.convert(query));
        channelVos = channelVos.stream().map(e->{
            if(e.getChannelStatus() == -1){
                e.setChannelStatusStr("无效");
            }else if(e.getChannelStatus() == 0){
                e.setChannelStatusStr("冻结");
            }else{
                e.setChannelStatusStr("生效");
            }

            e.setChannelTypeStr(e.getChannelType() == 1 ? "内部平台":"其他");
            e.setCreateTimeStr(DateUtils.getTimeStampStr(e.getCreateTime()));
            e.setUpdateTimeStr(DateUtils.getTimeStampStr(e.getUpdateTime()));
            return e;
        }).collect(Collectors.toList());

        Long count = channelService.findCount(query.convert(query));

        PageResult pageResult = new PageResult();
        pageResult.setList(channelVos);
        pageResult.setTotal(count.intValue());

        testKafka();

        return Result.success(pageResult);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody CenterChannelParam param){
        if(param.getChannelId() == null){
            return Result.failure(ResultCode.FAILD.getCode().longValue(),"渠道id不能为空");
        }
        Long id = param.getChannelId();
        CenterChannelVo channelVo = channelService.getById(id);
        if(channelVo == null){
            return Result.failure(ResultCode.FAILD.getCode().longValue(),"该记录不存在");
        }

        if(!StringUtils.isEmpty(param.getChannelName()) &&!channelVo.getChannelName().equals(param.getChannelName())){
            CenterChannelVo checkVo = new CenterChannelVo();
            checkVo.setChannelName(param.getChannelName());
            List<CenterChannelVo> checkRes = channelService.findAll(checkVo);
            if(checkRes != null){
                return Result.failure(ResultCode.FAILD.getCode().longValue(),"渠道名称已存在");
            }
        }

        channelService.updateById(param.convert(param));
        return Result.success();
    }

    @RequestMapping("/add")
    public Result add(@RequestBody CenterChannelParam param){
        syncAdd(param.convert(param));
        return Result.success();
    }

    @RequestMapping("/delete")
    public Result deleteById(@RequestBody CenterChannelParam param){
        if(param.getChannelId() == null){
            return Result.failure(ResultCode.FAILD.getCode().longValue(),"渠道id不能为空");
        }

        channelService.delete(param.getChannelId());
        return Result.success();
    }

    @RequestMapping("getAll")
    public Result getAll(){
        CenterChannelVo vo = new CenterChannelVo();
        vo.setChannelStatus(1);
        List<CenterChannelVo> vos = channelService.findAll(vo);
        List<SelectOptionVo> list = vos.stream().map(e -> {
            SelectOptionVo option = new SelectOptionVo();
            option.setLabel(e.getChannelName());
            option.setValue(e.getId() + "");
            return option;
        }).collect(Collectors.toList());
        return Result.success(list);
    }

    private synchronized Result syncAdd(CenterChannelVo vo){
        String channelName = vo.getChannelName();
        CenterChannelVo checkVo = new CenterChannelVo();
        checkVo.setChannelName(channelName);
        List<CenterChannelVo> checks = channelService.findAll(checkVo);
        if(!CollectionUtils.isEmpty(checks)){
            return Result.failure(ResultCode.FAILD.getCode().longValue(),"该渠道已经存在");
        }
        channelService.add(vo);
        return Result.success();
    }

    private void testKafka(){
        PlainResult test = KafkaProducerClient.send(TopicEnum.LOG_HQBS_GENERAL, "test");
        log.info("kafka发送数据结果："+test.getMessage());
        log.info("kafka发送数据结果："+ JSONObject.toJSONString(test));
    }

}
