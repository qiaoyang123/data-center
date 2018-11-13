package com.ggj.datacenter.common.utils;

import com.google.common.hash.*;
import com.google.common.io.Files;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * MD5工具类
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
public class Md5Utils {
    private static HashFunction hf = Hashing.md5();
    private static Charset defaultCharset = Charset.forName("UTF-8");

    private Md5Utils() {
        throw new AssertionError("不要实例化工具类");
    }

    public static String md5(String data) {
        HashCode hash = hf.newHasher().putString(data, defaultCharset).hash();
        return hash.toString();
    }

    public static String md5(String data, Charset charset, boolean isUpperCase) {
        HashCode hash = hf.newHasher().putString(data, charset == null ? defaultCharset : charset).hash();
        return isUpperCase ? hash.toString().toUpperCase() : hash.toString();
    }

    public static String md5(byte[] bytes, boolean isUpperCase) {
        HashCode hash = hf.newHasher().putBytes(bytes).hash();
        return isUpperCase ? hash.toString().toUpperCase() : hash.toString();
    }

    public static String md5(File sourceFile, boolean isUpperCase) {
        HashCode hash = hf.newHasher().putObject(sourceFile, new Funnel<File>() {

            private static final long serialVersionUID = 2757585325527511209L;

            @Override
            public void funnel(File from, PrimitiveSink into) {
                try {
                    into.putBytes(Files.toByteArray(from));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).hash();
        return isUpperCase ? hash.toString().toUpperCase() : hash.toString();
    }

    /**
     * 将其转换为json后，再进行md5
     *
     * @param object      数据源可以是任何类型的对象
     * @param isUpperCase 结果是否大写
     * @param charset     涉及到字符串时的操作编码，默认是utf-8
     * @return
     */
    public static String md5(Object object, boolean isUpperCase, Charset charset) {
        Hasher hasher = hf.newHasher();
        Gson gson = new Gson();
        String json = gson.toJson(object);

        HashCode hash = hasher.putString(json, charset == null ? defaultCharset : charset).hash();
        return isUpperCase ? hash.toString().toUpperCase() : hash.toString();
    }

    public static void main(String[] args) {
        HashCode hash = hf.newHasher().putString("123456", defaultCharset).hash();
        HashCode hash1 = hf.newHasher().putString(hash.toString() + "abcd1234", defaultCharset).hash();
    }
}
