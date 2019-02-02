package com.dgbiztech.generator.utils;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 判断为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判读不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

}
