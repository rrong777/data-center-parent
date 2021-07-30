package com.slzh.utils;


import org.apache.commons.lang3.RandomUtils;

import java.security.MessageDigest;
import java.text.NumberFormat;

/**
 * 字符串工具类
 *
 * @author lanb
 * @date Sep 1, 2018
 */
public class StringUtils {

    /**
     * 判空操作
     *
     * @param value
     * @return
     */
    public static boolean isBlank(String value) {
        return value == null || "".equals(value) || "null".equals(value) || "undefined".equals(value) || "".equals(value.trim());
    }

    public static boolean isEmpty(String value) {
        return value == null || "".equals(value) || "null".equals(value) || "undefined".equals(value) || "".equals(value.trim());
    }

    public static String parseString(Object value) {
        return value == null ? "null" : String.valueOf(value);
    }

    public static String replaceBlank(String oldStr) {
        String newStr = oldStr.replace(" ", "%20");
        return newStr;
    }

    /**
     * 获得一个百分数字符串
     *
     * @param num 分子
     * @param den 分母
     * @return
     */
    public static String getPercentString(Integer num, Integer den) {

        if (den == 0) {
            return null;
            // 分母不为0 分子为0
        } else if (num == 0) {
            return "0%";
        } else {
            Double result = (double) num / den;
            result = getDoubleNum(result, 4);
            return (result * 100) + "%";
        }
    }


    /**
     * 返回一个保留n位小数的double类型
     *
     * @param num    num 目标值
     * @param retain 保留小数位
     * @return
     */
    public static Double getDoubleNum(Double num, int retain) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(retain);
        return Double.valueOf(nf.format(num));
    }

    /**
     * 传入一个百分比字符串获得这个百分比字符串的double数值
     *
     * @param percent
     * @return
     */
    public static Double getPercentDoubleValue(String percent) {
        String valStr = percent.substring(0, percent.indexOf("%"));
        return Double.parseDouble(valStr);
    }

    /**
     * 获得剩余百分比
     *
     * @param percent
     * @return
     */
    public static String getSurplusPercent(String percent) {
        return getDoubleNum((100 - StringUtils.getPercentDoubleValue(percent)), 4) + "%";
    }

    public static void main(String[] args) {
        String percent = StringUtils.getPercentString(4, 7);
        String percent2 = getSurplusPercent(percent);
//		System.out.println(percent + ":" + percent2);
    }

    //	double d = (double)11/13;
//        NumberFormat nf = NumberFormat.getNumberInstance();
//        nf.setMaximumFractionDigits(4);
//        System.out.println(Double.valueOf(nf.format(d)));
//	public static boolean
    public static String shal(String src) {
        //获取一个加密对象
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("sha1");

            char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            StringBuilder sb = new StringBuilder();
            //加密
            byte[] digest = md.digest(src.getBytes());
            //处理加密结果
            for (byte b : digest) {
                sb.append(chars[(b >> 4) & 15]);
                sb.append(chars[b & 15]);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 随机生成字符串
     * @return
     */
//    public static String getRandomId() {
//
//        return String.valueOf(System.currentTimeMillis()) + (RandomUtils.nextInt(89999)+10000);
//    }

}
