package com.ggj.datacenter.common.utils;

import java.math.BigDecimal;

/**
 * 数字工具类
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
public class NumUnitls {

    public static Double getTwoDecimal(Double d) {
        return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
}
