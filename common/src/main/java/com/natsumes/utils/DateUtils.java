package com.natsumes.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {

    //private static Calendar calendar = Calendar.getInstance();

//    public static void main(String[] args) throws ParseException {
//        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
//        ca.set(2020, 4, 29);//月份是从0开始的，所以11表示12月
//        Date now = ca.getTime();
//
//        System.out.println(isBeforeDay(7));
//        //ca.add(Calendar.MONTH, -1); //月份减1
//        Date lastMonth = ca.getTime(); //结果
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(now);
//        System.out.println(getStartDayOfMonth(now));
//        System.out.println(getEndDayOfMonth(now));
//        //System.out.println(isPointMonth(new Date(), 0));
//        System.out.println(isBeforeMonth(now, -1));
//    }

    /**
     * 判断选择的日期是否是本周
     *
     * @param date 日期
     * @return true or false
     */
    public static boolean isThisWeek(Date date) {
        return isPointWeek(date.getTime(), 0);
    }

    /**
     * 判断选择的日期是否是指定前后周
     *
     * @param date 日期
     * @param num  指定周数，-1表示前一周，1表示后一周
     * @return true or false
     */
    public static boolean isPointWeek(Date date, int num) {
        return isPointWeek(date.getTime(), num);
    }

    /**
     * 判断选择的日期是否是本周
     *
     * @param time 日期
     * @return true or false
     */
    public static boolean isThisWeek(long time) {
        return isPointWeek(time, 0);
    }

    /**
     * 判断选择的日期是否是本周
     *
     * @param time 日期
     * @param num  指定周数，-1表示前一周，1表示后一周, 0 表示当前周
     * @return true or false
     */
    public static boolean isPointWeek(long time, int num) {
        Calendar calendar = getCalendar();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR) + num;
        calendar.setTime(new Date(time));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        return paramWeek == currentWeek;
    }

    public static Boolean isBeforeWeek(long time, int num) {
        Calendar calendar = getCalendar();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR) + num;
        calendar.setTime(new Date(time));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        return paramWeek <= currentWeek;
    }

    public static Boolean isBeforeWeek(Date time, int num) {
        return isBeforeWeek(time.getTime(), num);
    }

    public static Integer getWeekByTime(Date time) {
        return getWeekByTime(time.getTime());
    }

    public static Integer getYearByTime(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(time);
        return calendar.get(Calendar.YEAR);
    }

    public static Integer getWeekByTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(new Date(time));
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    //获取这一年的日历
    private static Calendar getCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal;
    }

    //获取这一年的日历
    private static Calendar getCalendarFormYear(int year) {
        Calendar cal = getCalendar();
        cal.set(Calendar.YEAR, year);
        return cal;
    }

    private static Date doGetEndDayOfWeek(Calendar cal) {
        cal.add(Calendar.DAY_OF_WEEK, 6);
        //设置23点59分59秒
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private static Date doGetFirstDayOfWeek(Calendar cal) {
        //cal.add(Calendar.DAY_OF_WEEK, 6);
        //设置23点59分59秒
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //获取当年的当前之前第week周的周日日期
    public static Date getEndDayOfWeekBefore(int week) {
        Calendar cal = getCalendar();
        cal.set(Calendar.WEEK_OF_YEAR, getWeekByTime(cal.getTime()) - week);
        return doGetEndDayOfWeek(cal);
    }

    //获取某一年的某一周的周一日期
    public static Date getStartDayOfWeekBefore(int week) {
        Calendar cal = getCalendar();
        cal.set(Calendar.WEEK_OF_YEAR, getWeekByTime(cal.getTime()) - week);
        return doGetFirstDayOfWeek(cal);
    }

    //获取当年的某一周的周日日期
    public static Date getEndDayOfWeek(int weekNo) {
        Calendar cal = getCalendar();
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        return doGetEndDayOfWeek(cal);
    }

    //获取某一年的某一周的周一日期
    public static Date getStartDayOfWeek(int weekNo) {
        Calendar cal = getCalendar();
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        return doGetFirstDayOfWeek(cal);
    }

    //获取某一年的某一周的周日日期
    public static Date getEndDayOfWeek(int year, int weekNo) {
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        //设置23点59分59秒
        return doGetEndDayOfWeek(cal);
    }

    //获取某一年的某一周的周一日期
    public static Date getStartDayOfWeek(int year, int weekNo) {
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        return doGetFirstDayOfWeek(cal);
    }


    //获取周区间
    public static Date getStartDayOfWeek(Date date) {
        return getStartDayOfWeek(getYearByTime(date), getWeekByTime(date));
    }

    //获取周区间
    public static Date getEndDayOfWeek(Date date) {
        return getEndDayOfWeek(getYearByTime(date), getWeekByTime(date));
    }

    /**
     * 根据传入的参数，来对日期区间进行拆分，返回拆分后的日期List
     *
     * @param statisticsType
     * @param map
     * @return
     * @throws ParseException
     * @author lihq 2019-6-24
     * @editor
     * @editcont
     */
    public static List<String> doDateByStatisticsType(String statisticsType, Map<String, Object> map) throws ParseException {
        List<String> listWeekOrMonth = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = (String) map.get("startDate");
        String endDate = (String) map.get("endDate");
        Date sDate = dateFormat.parse(startDate);

        Calendar sCalendar = Calendar.getInstance();
        sCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        sCalendar.setTime(sDate);
        Date eDate = dateFormat.parse(endDate);
        Calendar eCalendar = Calendar.getInstance();
        eCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        eCalendar.setTime(eDate);
        boolean bool = true;
        if (statisticsType.equals("week")) {
            while (sCalendar.getTime().getTime() < eCalendar.getTime().getTime()) {
                if (bool || sCalendar.get(Calendar.DAY_OF_WEEK) == 2 || sCalendar.get(Calendar.DAY_OF_WEEK) == 1) {
                    listWeekOrMonth.add(dateFormat.format(sCalendar.getTime()));
                    bool = false;
                }
                sCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            listWeekOrMonth.add(dateFormat.format(eCalendar.getTime()));
            if (listWeekOrMonth.size() % 2 != 0) {
                listWeekOrMonth.add(dateFormat.format(eCalendar.getTime()));
            }
        }

        return listWeekOrMonth;
    }


    /**
     * 判断选择的日期是否是本月
     *
     * @param date 日期
     * @return true or false
     */
    public static boolean isThisMonth(Date date) {
        return isPointMonth(date, 0);
    }

    /**
     * 判断选择的日期是否是本月
     *
     * @param date 日期
     * @param num  指定yue数，-1表示前一月，1表示后一月, 0 表示当前月
     * @return true or false
     */
    public static boolean isPointMonth(Date date, int num) {

        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.add(Calendar.MONTH, num); //年份减1
        Date paramMonth = ca.getTime(); //结果

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String paramTime = sf.format(date).substring(0, 7);
        String currentTime = sf.format(paramMonth).substring(0, 7);

        return paramTime.equals(currentTime);
    }

    //获取周区间
    public static Date getStartDayOfMonth(Date date) {

        Calendar cale = Calendar.getInstance();//得到一个Calendar的实例
        //cale.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        cale.setTime(date);
        // 获取前月的第一天
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        cale.set(Calendar.HOUR_OF_DAY, 0);
        cale.set(Calendar.SECOND, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.MILLISECOND, 0);
        return cale.getTime();
    }

    //获取周区间
    public static Date getEndDayOfMonth(Date date) {
        Calendar cale = Calendar.getInstance();//得到一个Calendar的实例
        //cale.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        cale.setTime(date);
        // 获取前月的第一天
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        cale.set(Calendar.HOUR_OF_DAY, 23);
        cale.set(Calendar.SECOND, 59);
        cale.set(Calendar.MINUTE, 59);
        cale.set(Calendar.MILLISECOND, 0);
        return cale.getTime();
    }

    public static Boolean isBeforeMonth(Date time, int num) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.add(Calendar.MONTH, num + 1); //年份减1
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.MILLISECOND, 0);
        Date paramMonth = ca.getTime(); //结果
        return time.getTime() < paramMonth.getTime();
    }

    /**
     * 判断当前时间是否为当月的某天之前的时间
     *
     * @param day 日期
     * @return
     */
    public static Boolean isBeforeDay(int day) {

        Calendar cale = Calendar.getInstance();//得到一个Calendar的实例
        //cale.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 获取前月的第一天
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, day);
        cale.set(Calendar.HOUR_OF_DAY, 0);
        cale.set(Calendar.SECOND, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.MILLISECOND, 0);

        return new Date().getTime() < cale.getTime().getTime();
    }

    public static Boolean isAfterDay(int day) {

        return !isBeforeDay(day);
    }

}
