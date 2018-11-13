package com.ggj.datacenter.conntroller;

import com.ggj.datacenter.business.service.CenterSqlParamService;
import com.ggj.datacenter.business.service.CenterSqlService;
import com.ggj.datacenter.entity.CenterSqlParam;
import com.ggj.datacenter.enums.ResultCode;
import com.ggj.datacenter.model.param.CenterSqlParamParam;
import com.ggj.datacenter.model.vo.CenterSqlParamVo;
import com.ggj.datacenter.protocol.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/10/12 15:50
 * @since 1.0
 */
@RestController
@RequestMapping("param")
public class CenterSqlParamController {

    @Autowired
    private CenterSqlParamService paramService;

    @RequestMapping("add")
    public Result addParam(@RequestBody CenterSqlParamParam param){
        return syncAddParam(param);
    }

    private synchronized Result syncAddParam(CenterSqlParamParam param) {
        CenterSqlParamVo vo = new CenterSqlParamVo();
        vo.setParamKey(param.getParamKey());
        vo.setSqlId(param.getSqlId());
        List<CenterSqlParamVo> params = paramService.findAll(vo);
        if(!CollectionUtils.isEmpty(params)){
            return Result.failure(ResultCode.FAILD.getCode().longValue(),"不能添加重复的参数，参数Key已存在");
        }
        paramService.insert(param.convert(param));
        return Result.success();
    }

    @RequestMapping("updateParam")
    public Result updateParam(@RequestBody CenterSqlParamParam param){
        paramService.updateById(param.convert(param));
        return Result.success();
    }

    @RequestMapping("deleteById")
    public Result deleteParam(@RequestBody  CenterSqlParamParam param){
        Long id = param.getId();
        if(id == null){
            return Result.failure(ResultCode.FAILD.getCode().longValue(),"Id不能为空");
        }
        paramService.deleteById(id);
        return Result.success();
    }

    @RequestMapping("getBySqlId")
    public Result getBySqlId(@RequestBody CenterSqlParamParam param){
        Long sqlId = param.getSqlId();
        if(sqlId == null){
            return Result.failure(ResultCode.FAILD.getCode().longValue(),"sqlId不能为空");
        }
        CenterSqlParamVo vo = new CenterSqlParamVo();
        vo.setSqlId(sqlId);
        List<CenterSqlParamVo> params = paramService.findAll(vo);

        params = params.stream().map(e -> {
            e.setParamStatusStr(e.getParamStatus() == 1?"有效":"无效");
            e.setIsNecessaryStr(e.getIsNecessary() == 1?"是":"否");
            return e;
        }).collect(Collectors.toList());
        return Result.success(params);
    }
}
