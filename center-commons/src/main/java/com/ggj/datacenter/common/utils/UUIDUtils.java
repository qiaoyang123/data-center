package com.ggj.datacenter.common.utils;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
public class UUIDUtils {

    public static String genMD5Salt() {
        return UUID.randomUUID().toString().substring(0, 10);
    }

    public static void main(String[] args) {
//        System.out.println(genMD5Salt());
    }
}

