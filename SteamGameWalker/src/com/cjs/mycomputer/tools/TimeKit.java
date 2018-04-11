package com.cjs.mycomputer.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述:
 * <br>作者:陈俊森
 * <br>创建时间:2017年06月29日 10:28
 * <br>邮箱:chenjunsen@outlook.com
 */

public class TimeKit {
    /**普通中文时间格式*/
    public static final String FORMAT_CHINESE_TIME="yyyy年MM月dd日 HH时mm分ss秒";

    public static final String FORMAT_SIMPLE_TIME="yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_SIMPLE_TIME2="yyyy-MM-dd";
    
    public static final String FORMAT_SIMPLE_TIME4="yyyyMMddHHmmss";

    public static final String FORMAT_SIMPLE_TIME3="yy/MM/dd";
    
    static final String TAG="TimeKit";
    
    /**
     * 国际化时间<br>
     * 解析格式例如:2015-04-20T11:12:00.0+0800
     */
    public static final String FORMAT_UTC_SSSZ="yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    /**
     * 国际化时间，需要JDK1.7以上才能支持<br>
     * 解析格式例如:2017-11-30T18:00:00+00:00
     */
    public static final String FORMAT_UTC_XXX="yyyy-MM-dd'T'HH:mm:ssXXX";
    
    /**
     * 国际化时间，需要JDK1.7以上才能支持<br>
     * （SSS代表.到+之间的，默认有三位，可以只写一位；+00.00代表XXX的时区）
     * 解析格式例如:2017-11-30T18:00:00.0+00:00
     */
    public static final String FORMAT_UTC_SSSXXX="yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    /**
     * 获取格式化的时间
     * @param t 需要被格式化的时间
     * @return
     */
    public static String getFormatTime(long t){
        return getFormatTime(t, FORMAT_CHINESE_TIME);
    }


    public static String getFormatTime(long t,String pattern){
        Date date=new Date(t);
        String time="";
//		SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_CHINESE_TIME);
        SimpleDateFormat sdf=(SimpleDateFormat) SimpleDateFormat.getInstance();
        sdf.applyPattern(pattern);
        time=sdf.format(date);
        return time;
    }
    
    /**
     * 获取格式化时间
     * @param t UTC标准时间，例如:2017-11-30T18:00:00.0+00:00
     * @param pattern1 UTC转化格式
     * @param pattern2 需要显示的转化格式
     * @return
     */
    public static String getFormatTime(String t,String pattern1,String pattern2){
        return getFormatTime(getLocalCountryTimeStamp(t, pattern1), pattern2);
    }
    
    /**
     * 将UTC时间转换为国内标准时间
     * @param t UTC标准时间字符串 例如:2017-11-30T18:00:00.0+00:00
     * @param pattern UTC转换的格式
     * @return
     */
    public static long getLocalCountryTimeStamp(String t,String pattern){
        SimpleDateFormat sdf=(SimpleDateFormat) SimpleDateFormat.getInstance();
        sdf.applyPattern(pattern);
        long time=0;
        try {
			Date date=sdf.parse(t);
			time=date.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "时间转换失败:timesrc:"+t+" "+e.getMessage());
		}
        return time;
    }
}
