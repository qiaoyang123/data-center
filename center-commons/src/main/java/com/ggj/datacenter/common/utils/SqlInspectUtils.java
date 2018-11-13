package com.ggj.datacenter.common.utils;

import static org.apache.commons.lang3.StringUtils.split;

/**
 * 防止sql注入语句
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/11/8 15:35
 * @since 1.0
 */
public class SqlInspectUtils {

    public static boolean checkSqlParam(String param){
        String injStr = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
        String[] injArr = split(injStr,"|");
        for (int i=0 ; i < injArr.length ; i++ )
        {
            if (param.contains(injArr[i]))
            {
                return false;
            }
        }
        return true;
    }
}
