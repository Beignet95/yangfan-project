package com.ruoyi.common.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 * 
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     * 
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final String parseIntegerToDateStr(final String format, final Integer days)
    {
        Calendar calendar = new GregorianCalendar(1900,0,-1);
        Date d = calendar.getTime();
        Date dd = DateUtils.addDays(d,Integer.valueOf(days));
        String dateStr = parseDateToStr(format,dd);
        return dateStr;
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            //2020-12-12T22:31:24+00:00 在面对这种弱智格式时，手动把不必要的字符去除
            String dateStr = (String)str;
            if(dateStr.contains("T")&&dateStr.contains("+00:00"))
                return parseDate(str.toString().replaceAll("T"," ").replace("+00:00",""), parsePatterns);
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算相差天数
     */
    public static int differentDaysByMillisecond(Date date1, Date date2)
    {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 获取本月月份字符串
     * @return
     */
    public static String  getCurrentMonthStr(String formatStr){
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date m = c.getTime();
        String curMonthStr = format.format(m);
        return curMonthStr;
//        c.add(Calendar.MONTH, -1);
//        m = c.getTime();
//        String preMonthStr = format.format(m);
//        System.out.println("本月为："+curMonthStr+",上月为："+preMonthStr);
    }

    /**
     *
     * @param currentMonthStr
     * @return
     */
    public static String  getPreviousMonthStr(String currentMonthStr,String formatStr){
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            // 注意格式需要与上面一致，不然会出现异常
            date = format.parse(currentMonthStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String preMonthStr = format.format(m);
        return preMonthStr;
//        c.add(Calendar.MONTH, -1);
//        m = c.getTime();
//        String preMonthStr = format.format(m);
//        System.out.println("本月为："+curMonthStr+",上月为："+preMonthStr);
    }

    public static Date parseUTCDate4CSV(Object str){
       return parseUTCDate4CSV(str,null);
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseUTCDate4CSV(Object str,Locale locale)
    {
       return parseUTCDate4CSV(str,locale,null);
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseUTCDate4CSV(Object str,Locale locale,String formatStr)
    {
        if (str == null)
        {
            return null;
        }
        if(locale==null){
            locale = Locale.CHINA;
        }
        if(locale.getLanguage().toUpperCase().equals("UK")||locale.getLanguage().toUpperCase().equals("US")||locale.getLanguage().toUpperCase().equals("EN")){
            locale = new Locale("en");
        }
        try
        {
            //String formatStr = "d MMM yyyy HH:mm:ss 'UTC'";
            //TODO Feb 1, 2021 12:28:04 AM PST
            if(StringUtils.isEmpty(formatStr)){
                formatStr = "d MMM yyyy HH:mm:ss 'UTC'";
            }
            //String formatStr = "MMM d',' yyyy HH:mm:ss a 'PST'";
            if (locale.getLanguage().toLowerCase().equals("de")){
                //SimpleDateFormat sdf = new SimpleDateFormat("d.MM.yyyy HH:mm:ss 'UTC'",locale);
                formatStr = "d.MM.yyyy HH:mm:ss 'UTC'";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr, locale);
            Date date =sdf.parse((String) str);
            return date;
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     *
     * 描述:获取下一个月的第一天.
     *
     * @return
     */
    public static String getPerFirstDayOfMonth(Date date) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }


}
