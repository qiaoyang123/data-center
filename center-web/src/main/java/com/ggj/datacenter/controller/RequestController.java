package com.ggj.datacenter.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ggj.datacenter.api.server.DataSpoutAPI;
import com.ggj.datacenter.common.utils.ExcelUtils;
import com.ggj.platform.gsf.result.PlainResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 统一请求的controller
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
@RestController
@Slf4j
public class RequestController {

    @Reference
    private DataSpoutAPI dataSpoutApi;

    @RequestMapping("/get/{name}")
    public PlainResult getData(@PathVariable("name") String name, @RequestBody String params, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotEmpty(params)) {
            paramMap = JSONObject.parseObject(params, Map.class);
            paramMap.put("ip",ip);
        }

        PlainResult result = dataSpoutApi.getData(name, paramMap);
        return result;
    }

    @RequestMapping("/export/{serviceName}")
    public void export(@PathVariable("serviceName") String serviceName, String param, String fileName, String sheetName, HttpServletResponse response) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotEmpty(param)) {
            paramMap = JSONObject.parseObject(param, Map.class);
        }
        PlainResult data = dataSpoutApi.getData(serviceName, paramMap);
        if (!data.isOk()) {
            throw new RuntimeException(data.getMessage());
        }

        Object result = data.getData();
        if (result == null) {
            return;
        }
        List<Map<String, Object>> resutlList = JSONObject.parseObject(result.toString(), List.class);

        String[][] excelContent = getExcelContent(resutlList);
        String[] excelTitle = getExcelTitle(resutlList.get(0));

        HSSFWorkbook workbook = ExcelUtils.getHSSFWorkbook(sheetName, excelTitle, excelContent, null);


        try {
            setResponseHeader(response, fileName+".xls");
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.error("商家工单生成完结汇总导出异常",e);
        }

    }

    public String[][] getExcelContent(List<Map<String, Object>> resultList) {
        int size = resultList.get(0).size();
        String[][] content = new String[resultList.size()][size];
        for (int i = 0; i < resultList.size(); i++) {
            Map<String, Object> resultMap = resultList.get(i);
            Set<String> keys = resultMap.keySet();
            Object[] keysArr = keys.toArray();
            for (int j = 0; j < keysArr.length; j++) {
                String key = keysArr[j].toString();
                String value = resultMap.get(key).toString();
                content[i][j] = value;
            }

        }
        return content;
    }

    public String[] getExcelTitle(Map<String, Object> titleMap) {
        String[] title = new String[titleMap.size()];
        Set<String> keys = titleMap.keySet();
        Object[] titleArr = keys.toArray();
        for(int i=0;i<titleArr.length;i++){
            title[i] = titleArr[i].toString();
        }
        return title;
    }

    //发送响应流方法
    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                log.error("导出格式化导出文件名称异常" + fileName, e);
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            log.error("导出数据异常" + fileName, ex);
        }
    }
}
