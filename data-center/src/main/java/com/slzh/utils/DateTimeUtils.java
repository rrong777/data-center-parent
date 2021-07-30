package com.slzh.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间相关工具
 *
 * @author lanb
 * @date Jan 14, 2019
 */
public class DateTimeUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String DATE_FORMAT_YYYYMMDDD = "yyyy-MM-dd";
    public static final String  DATE_FORMAT_YYYYMM="yyyy-MM";
    public static final String DATE_FORMAT_ONLY_HH_MM = "HH:mm";
    public static final String DATE_FORMAT_ONLY_HH_MM_SS = "HH:mm:ss";

    public static final String DATE_FORMAT_ONLY_MM = "mm";
    public static final String DATE_FORMAT_FOR_ID = "yyyyMMddHHmmss";


    /**
     * 获得昨天的日期对象
     * @return
     */
    public static Date getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 获得昨天的日期对象
     * @return
     */
    public static Date getYesterdayDateYyMmDd() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 获得n天前的日期对象
     * @return
     */
    public static Date getBeforeNdate(Date d,int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DATE, -n);
        return calendar.getTime();
    }

    /**
     * 获得n分钟前的日期对象
     * @return
     */
    public static Date getBeforeNminute(Date d,int n) {
        String minute =  "" ;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MINUTE, -n);
        return  calendar.getTime();
    }

    /**
     * 获得n秒前的日期对象
     * @return
     */
    public static Date getBeforeNsecond(Date d,int n) {
        String minute =  "" ;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.SECOND, -n);
        return  calendar.getTime();
    }

    /*
    * 计算两日期间隔(分钟)
    * */
    public static long dateDiffOfMin(String startTime, String endTime) {
        //按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(DATE_FORMAT);
        long nd = 1000*24*60*60;//一天的毫秒数
        long nh = 1000*60*60;//一小时的毫秒数
        long nm = 1000*60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        try {
            //获得两个时间的毫秒时间差异
            long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
//            long day = diff/nd;//计算差多少天
//            long hour = diff%nd/nh;//计算差多少小时
            long min = diff/nm;//计算差多少分钟
//            long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
            return min ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 标准格式化日期时间
     *
     * @return
     */
    public static String getDateTimeForId() {
        return (new SimpleDateFormat(DATE_FORMAT_FOR_ID)).format(new Date());
    }

    /**
     * 获取当前标准格式化日期时间
     *
     * @return
     */
    public static String getDateTime() {
        return getDateTime(new Date());
    }

    /**
     * 标准格式化日期时间
     *
     * @param date
     * @return
     */
    public static String getDateTime(Date date) {
        return (new SimpleDateFormat(DATE_FORMAT)).format(date);
    }

    public static Date parseDateTime(String dateStr) throws Exception {
        return new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
    }
    public static Date parseDateTimeWithoutHHmmss(String dateStr) throws Exception {
        return new SimpleDateFormat(DATE_FORMAT_YYYYMMDDD).parse(dateStr);
    }

    public static Long parseDateTimeLong(String dateStr) throws Exception {
        return new SimpleDateFormat(DATE_FORMAT).parse(dateStr).getTime();
    }

    public static String getDateHhMm(Date date) {
        return (new SimpleDateFormat(DATE_FORMAT_ONLY_HH_MM)).format(date);
    }

    public static String getDateHhMmSs(Date date) {
        return (new SimpleDateFormat(DATE_FORMAT_ONLY_HH_MM_SS)).format(date);
    }

    public static String getDateMmTime(Date date) {
        return (new SimpleDateFormat(DATE_FORMAT_ONLY_MM)).format(date);
    }

    public static String getDateYyyyMmDdHhMm(Long time) {
        return (new SimpleDateFormat(DATE_FORMAT_HH_MM)).format(new Date(time));
    }

    /**
     * 获得n天后的日期
     * @param nums 多少天（可为负数）
     * @return
     */
    public static Date getNDayYearAgoDate(Integer nums) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, nums);
        return calendar.getTime();
    }



    /**
     * 获得一个日期n天后的日期
     * @param nums 多少天（可为负数）
     * @return
     */
    public static Date getNDayYearAgoDate(Date date,Integer nums) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, nums);
        return calendar.getTime();
    }


    /**
     * 获得一个日期某个时间段后得日期
     * @param nums 时间段（可为负数）
     * @return
     */
    public static Date getDateAdd(Date date,Integer calType,Integer nums) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calType, nums);
        return calendar.getTime();
    }

    public static String getDateAddString(Date date,Integer calType,Integer nums) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calType, nums);
        return getDateTime(calendar.getTime());
    }

    /**
     * 获得一个日期n天后的日期
     * @param nums 多少月（可为负数）
     * @return
     */
    public static Date getNMonthAfterDate(Date date, Integer nums) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, nums);
        return calendar.getTime();
    }
    public static String getDateYyyyMmDd(Long time) {
        return (new SimpleDateFormat(DATE_FORMAT_YYYYMMDDD)).format(new Date(time));
    }
    public static String getDateYyyyMm(Date time) {
        return (new SimpleDateFormat(DATE_FORMAT_YYYYMM)).format(time);
    }

    public static String getDateHhMmSs(Long time) {
        return (new SimpleDateFormat(DATE_FORMAT_ONLY_HH_MM_SS)).format(new Date(time));
    }

    public static String getDateTime(Long date) {
        return (new SimpleDateFormat(DATE_FORMAT)).format(date);
    }


    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static long getHSM(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        return calendar.get(Calendar.HOUR_OF_DAY)*3600+calendar.get(Calendar.MINUTE)*60+calendar.get(Calendar.SECOND);
        return date.getTime() / 1000 % 86400;
    }

    /**
     * 获得一个日期的Date 不带时分秒
     * @return
     */
    public static Date getDateWithoutOthers(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
    /**
     * 获得一个日期的Date 当天的最后一秒
     * @return
     */
    public static Date getDateWithDayEnd(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND,999);
        return calendar.getTime();
    }
    public static Date getDateWithoutSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }

    public static String secondToTime(long second) {
        long days = second / 86400;//转换天数
        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数
        if (0 < days){
            return days + "天"+hours+"小时"+minutes+"分"+second+"秒";
        }else {
            return hours+"小时"+minutes+"分"+second+"秒";
        }
    }
    public static String secondToTimeNoDay(long second) {
        long days = second / 86400;//转换天数
        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数
        if (0 < days){
            return (hours+days*24)+"小时"+minutes+"分"+second+"秒";
        }else {
            return hours+"小时"+minutes+"分"+second+"秒";
        }
    }

    public static int getCalendar(Date date,int calType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calType);
    }
}
