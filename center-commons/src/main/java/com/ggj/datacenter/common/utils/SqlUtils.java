package com.ggj.datacenter.common.utils;

import com.ggj.datacenter.enums.SqlOperator;

import java.util.List;
import java.util.Map;

/**
 * 构建执行sql工具类
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
public class SqlUtils {

    public static String buildQueryCondition(String sql, Map<String, Object> params, List<Map<String, String>> paramKeys) {

        if (paramKeys != null && paramKeys.size() > 0) {
            for (Map paramKey : paramKeys) {
                String key = paramKey.get("paramKey").toString();
                String isNecessary = paramKey.get("isNecessary").toString();
                String operator = paramKey.get("paramOperator").toString();
                Object paramsValue = params.get(key);

                //判断参数是否为空
                if (paramsValue == null) {
                    //必传参数为空，抛出异常
                    if (isNecessary.equals("1")) {
                        throw new RuntimeException("must param:" + key + " not be null");
                    }
                    //非必传参数，如果为空，则整个表达式替换为1=1
                    String regex = "(" + StrUtils.getMySqlField(key) + "\\s*" + operator + "\\s*" + ":" + key + ")";
                    sql = sql.replaceAll(regex, " 1 = 1 ");
                } else {

                    //校验sql参数，防止sql注入
                    boolean check = SqlInspectUtils.checkSqlParam(paramsValue.toString());
                    if(!check){
                        throw new RuntimeException("param:" + key + " value违法");
                    }

                    //替换参数
                    if (operator.equalsIgnoreCase(SqlOperator.IN.getOperator())) {
                        sql = sql.replaceAll(":" + key, paramsValue.toString());
                    } else {
                        if (paramsValue instanceof Integer) {
                            sql = sql.replaceAll(":" + key, paramsValue.toString());
                        } else {
                            sql = sql.replaceAll(":" + key, "'" + paramsValue.toString() + "'");
                        }
                    }
                }

            }
        }

        if (params.containsKey("pageNum") && params.containsKey("pageSize")) {
            Integer pageNum = (Integer) params.get("pageNum");
            Integer pageSize = (Integer) params.get("pageSize");

            sql = sql + " limit " + (pageNum - 1) * pageSize + "," + pageSize;

        }
        return sql;
    }


   /* public static String parseAndOr(Map<String, String> conditionMap, String operator, Map<String, Object> paramsMap) {
        String condition = "";
        Gson gson = new Gson();
        for (Map.Entry<String, String> entry : conditionMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Map<String, String> valueMap = gson.fromJson(value, Map.class);
            if (valueMap.containsKey("and") || valueMap.containsKey("or")) {
                String childCondition = parseAndOr(valueMap, key, paramsMap);
                condition = childCondition + key;
            } else {
                for (Map.Entry<String, String> val : valueMap.entrySet()) {
                    String k = val.getKey();
                    String v = val.getValue();
                    Map<String, String> map = gson.fromJson(v, Map.class);
                    String paramKey = map.get("paramKey");
                    Object paramValue = paramsMap.get(paramKey);
                    if (map.get("isNecessary").equals("1") && paramValue == null) {
                        throw new RuntimeException("必传参数为空");
                    }

                    if (paramValue == null) {
                        condition = " 1 = 1" + operator;
                    }

                    condition = k + map.get("operator") + paramValue;
                }
                condition = "(" + condition.substring(0, condition.lastIndexOf(operator)) + ")";
            }
        }
        return condition;
    }*/

    public static void main(String[] args) {

        String test = "where a = 1 and b  >=  b  and c =2 or (d =1 and b>=b)";

        String paramKey = "b";

        String connect = ">=";

        String param = "b";

        String s = test.replaceAll("(" + paramKey + "\\s*" + connect + "\\s*" + param + ")", " 1 = 1 ");

        System.out.println(s);
    }
}
