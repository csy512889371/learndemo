package com.ctoedu.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ctoedu.common.utils.validate.ValidateUtils;

/**
 * 日期处理类
 *
 *
 */
public class DateUtil {

	private static final int[] DAY_OF_MONTH = new int[] { 31, 28, 31, 30, 31,30, 31, 31, 30, 31, 30, 31 };

	public static final String[] DATE_FORMATS = new String[] {
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy-MM",
			"yyyy-MM-dd HH:mm:ss.S" ,"yyyy年MM月dd日","yyyy年MM月dd日 HH:mm","yyyyMMdd","yyyy年MM月dd日 HH:mm:ss"};

	/**
	 * 将传入的日期转化为"yyyy-MM-dd"形式的字符串
	 * 
	 * @param dt
	 *            日期
	 * @return 指定日期格式的字符串
	 */
	public static String formatDate(Date dt) {

		return formatDate("yyyy-MM-dd", dt);
	}

	/**
	 * 将传入的日期转化为"yyyy-MM-dd HH:mm:ss"形式的字符串
	 * 
	 * @param dt
	 *            日期
	 * @return 指定日期格式的字符串
	 */
	public static String formatDateYMDHMS(Date dt) {

		return formatDate("yyyy-MM-dd HH:mm:ss", dt);
	}

	/**
	 * 将传入的日期转化为"yyyy-MM-dd HH:mm"形式的字符串
	 * 
	 * @param dt
	 *            日期
	 * @return 指定日期格式的字符串
	 */
	public static String formatDateYMDHM(Date dt) {

		return formatDate("yyyy-MM-dd HH:mm", dt);
	}

	/**
	 * 将传入的日期以指定格式转成字符串
	 * 
	 * @param format
	 * @param dt
	 * @return
	 */
	public static String formatDate(String format, Date dt) {
		if (dt == null) {
			return "";
		}
		if(StringUtils.isBlank(format)){
			format = "yyyy-MM-dd";
		}
		
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(dt);
	}

	/**
	 * 将日期字符串转为日期
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return
	 */
	public static Date parseDate(String dateStr) {

		return parseDate(dateStr, DATE_FORMATS);
	}

	/**
	 * 将日期字符串转为指定格式的日期
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static Date parseDate(String dateStr, String format) {

		return parseDate(dateStr, new String[] { format });
	}

	private static Date parseDate(String dateStr, String[] parsePatterns) {

		if (ValidateUtils.isEmpty(dateStr))
			return null;
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(dateStr,parsePatterns);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取今天的日期
	 * 
	 * @return
	 */
	public static Date getToday(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
	
	/**
	 * 得到传入日期n天后的日期,如果传入日期为null，则表示当前日期n天后的日期
	 * 
	 * @param Date  dt 日期
	 * @param days  可以为任何整数，负数表示前days天，正数表示后days天
	 * @return Date
	 */
	public static Date getAddDayDate(Date dt, int days) {
		Calendar cal = Calendar.getInstance();
		if(dt!=null){
			cal.setTime(dt);
		}
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	/**
	 * 得到当前日期几天后（plusDays>0）or 几天前（plusDays<0）的指定格式的字符串日期
	 * @param dt
	 * @param plusDays
	 * @param dateFormat
	 * @return
	 */
	public static String getAddDayDateFromToday(int plusDays,String dateFormat){
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DAY_OF_MONTH, plusDays);
		
		return formatDate(dateFormat, cal.getTime());
	}
	/**
	 * 给定的时间再加上指定小时数,如果传入日期为null，能以当前时间计算
	 * 
	 * @author Alex Zhang
	 * @param dt
	 * @param hours
	 * @return
	 */
	public static Date getAddHourDate(Date dt, int hours) {

		if (dt == null)
			dt = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.HOUR, hours);

		return cal.getTime();
	}

	/**
	 * 给定的时间再加上指定分钟数
	 * 
	 * @author Alex Zhang
	 * @param dt
	 * @param minutes
	 * @return
	 */
	public static Date getAddMinuteDate(Date dt, int minutes) {
		if (dt == null)
			dt = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.MINUTE, minutes);

		return cal.getTime();
	}
	
	/**
	 * 给定的时间再加上指定月份数
	 * 
	 * @author wei suicun
	 * @param dt
	 * @param months
	 * @return
	 */
	public static Date getAddMonthDate(Date dt, int months) {

		if (dt == null)
			dt = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.MONTH, months);

		return cal.getTime();
	}

	/**
	 * 获得某天的零点时刻0:0:0
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static Date getDayBegin(Date date) {

		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得某天的截至时刻23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {

		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * 某月的起始时间,eg：param:2011-11-10 12:10:50.999, return：2011-11-1 00:00:00.000
	 * 
	 */
	public static Date getMonthBeginTime(Date dt) {

		if (dt == null)
			dt = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 *某月的截止时间,eg：param:2011-11-10 12:10:50.999, return：2011-11-30 23:59:59.999
	 * 
	 */
	public static Date getMonthEndTime(Date dt) {

		if (dt == null)
			dt = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}

	/**
	 * 获得传入日期的年\月\日,以整型数组方式返回
	 * 
	 * @param dt
	 * @return int[]
	 */
	public static int[] getTimeArray(Date dt) {

		if (dt == null)
			dt = new Date(System.currentTimeMillis());
		int[] timeArray = new int[3];
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		timeArray[0] = cal.get(Calendar.YEAR);
		timeArray[1] = cal.get(Calendar.MONTH) + 1;
		timeArray[2] = cal.get(Calendar.DAY_OF_MONTH);
		return timeArray;
	}

	/**
	 * 获得传入日期的年\月\日\小时\分,以整型数组方式返回
	 * 
	 * @param dt
	 * @return
	 */
	public static int[] timeArray(Date dt) {

		if (dt == null)
			dt = new Date(System.currentTimeMillis());
		int[] timeArray = new int[5];
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		timeArray[0] = cal.get(Calendar.YEAR);
		timeArray[1] = cal.get(Calendar.MONTH) + 1;
		timeArray[2] = cal.get(Calendar.DAY_OF_MONTH);
		timeArray[3] = cal.get(Calendar.HOUR_OF_DAY);
		timeArray[4] = cal.get(Calendar.MINUTE);
		return timeArray;
	}

	/**
	 * 根据年月日得到Date类型时间
	 * 
	 * @author Alan he <alan.he@jongogroup.com>
	 * @param year
	 * @param month
	 * @param day
	 * @return Date
	 */
	public static Date getTime(Integer year, Integer month, Integer day) {

		Calendar cal = Calendar.getInstance();
		if (year != null)
			cal.set(Calendar.YEAR, year);
		if (month != null)
			cal.set(Calendar.MONTH, month - 1);
		if (day != null)
			cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/**
	 * @author kim <kim.qiu@jongogroup.com> 通过格式化字符串得到时间
	 * @param parrern
	 *            格式化字符串 例如：yyyy-MM-dd
	 * @param str
	 *            时间字符串 例如：2007-08-01
	 * @return 出错返回null
	 */
	public static Date getDateFromPattern(String parrern, String str) {

		if (ValidateUtils.isEmpty(str))
			return null;
		SimpleDateFormat fmt = new SimpleDateFormat(parrern);
		try {
			return fmt.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算两个日期间相隔的小时
	 * 
	 * @param d1
	 *            日期1
	 * @param d2
	 *            日期2
	 * @return
	 */
	public static int getHourBetween(Date d1, Date d2) {

		long m = d1.getTime();
		long n = d2.getTime();
		return (int) ((m - n) / 3600000);
	}

	/**
	 * 取得两个时间之间的天数，可能是负数(第二个时间的日期小于第一个时间的日期)。如果两个时间在同一天，则返回0
	 * 
	 * @param d1
	 *            第一个时间
	 * @param d2
	 *            第二个时间
	 * @return
	 * 
	 * @author Derek
	 * @version 1.0 2009-10-14
	 */
	public static int getDayBetween(Date d1, Date d2) {

		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		return (int) ((c2.getTimeInMillis() - c1.getTimeInMillis()) / 86400000);
	}

	/**
	 * 计算两个日期间相隔的秒数
	 * 
	 * @param d1
	 *            日期1
	 * @param d2
	 *            日期2
	 * @return
	 */
	public static long getSecondBetweem(Date d1, Date d2) {

		return (d1.getTime() - d2.getTime()) / 1000;
	}

	/**
	 * 计算两个日期间相隔的月份数
	 * 
	 * @param d1
	 *            日期1
	 * @param d2
	 *            日期2
	 * @return
	 */
	public static int getMonthBetween(Date d1, Date d2) {

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);

		return (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)) * 12
				+ (c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH));
	}

	/**
	 * 通过生日得到当前年龄
	 * 
	 * @param birthDay
	 *            以字符串表示的生日
	 * @return 返回以以字符串表示的年龄,最小为0
	 */
	public static String getAge(String birthDay) {

		if (ValidateUtils.isEmpty(birthDay)) {
			return "未知";
		}
		if (birthDay.startsWith("0000")) {
			return "未知";
		}
		if (!birthDay.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")) {
			return "未知";
		}
		return getAge(parseDate(birthDay, "yyyy-MM-dd"));
	}

	/**
	 * 通过生日得到当前年龄
	 * 
	 * @param birthDay
	 *            以日期表示的生日
	 * @return 返回以以字符串表示的年龄,最小为0
	 */
	public static String getAge(Date birthDate) {

		if (birthDate == null) {
			return "未知";
		}

		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDate)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDate);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
				else {
				}
			}
			else {
				age--;
			}
		}
		else {
		}
		return age + "";
	}

	@SuppressWarnings("deprecation")
	public static int getIntAge(Date brithday) {

		if (brithday != null) {
			int dateMiss = Calendar.getInstance().getTime().getDate()
					- brithday.getDate();//日差距
			int monthMiss = Calendar.getInstance().getTime().getMonth()
					- brithday.getMonth();// 月份差距
			int yearMiss = Calendar.getInstance().getTime().getYear()
					- brithday.getYear();// 年份差距
			if (monthMiss > 0||(monthMiss==0 && dateMiss>=0)) {
				return yearMiss;
			}else{
				return yearMiss - 1;// 周岁少两岁，SO在去掉一年
			}
		}
		return 0;
	}
	/**
	 * 根据周几的数字标记获得周几的汉字描述
	 */
	public static String getCnWeekDesc(int weekNum) {
		String strWeek = "";
		switch (weekNum) {
		case 1:
			strWeek = "周一";
			break;
		case 2:
			strWeek = "周二";
			break;
		case 3:
			strWeek = "周三";
			break;
		case 4:
			strWeek = "周四";
			break;
		case 5:
			strWeek = "周五";
			break;
		case 6:
			strWeek = "周六";
			break;
		case 7:
			strWeek = "周日";
			break;
		}
		return strWeek;
	}

	/**
	 * 获得'上下午'标识
	 * 
	 * @param date
	 * @return
	 */
	public static String getCnAMPM(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (Calendar.AM == cal.get(Calendar.AM_PM))
			return "上午";
		else
			return "下午";

	}

	/**
	 * 判断两个日期是否相等
	 * 
	 * @param d1
	 *            日期1
	 * @param d2
	 *            日期2
	 * @return
	 */
	public static boolean isTimeEquals(Date d1, Date d2) {

		if (d1 == null || d2 == null)
			return false;
		return Math.abs(d1.getTime() - d2.getTime()) < 50;
	}

	/**
	 * 获取一个日期的年份
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static int getYear(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获取一个日期的月份
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static int getMonthOfYear(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	/**
	 * 获取一个日期的天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取一个日期的小时数
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取一个日期的分钟
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static int getMinute(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.MINUTE);
	}
	
	/**
	 * 获取一个日期的秒数
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static int getSecond(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.SECOND);
	}
	
	/**
	 * 获取一个月的最大天数
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return
	 */
	public static int getMaxDayOfMonth(int year, int month) {

		if (month == 1 && isLeapYear(year)) {
			return 29;
		}
		return DAY_OF_MONTH[month];
	}

	/**
	 * 判断是否是润年
	 * 
	 * @param year
	 *            年份
	 * @return
	 */
	public static boolean isLeapYear(int year) {

		Calendar calendar = Calendar.getInstance();
		return ((GregorianCalendar) calendar).isLeapYear(year);
	}
	
	
	/**
	 * 得到本周的起始时间
	 * @param currentDate
	 * @return
	 */
	public static Date getBeginDateofThisWeek(Date currentDate){
		Calendar current = Calendar.getInstance();
		current.setTime(currentDate);
		int dayOfWeek  = current.get(Calendar.DAY_OF_WEEK);
		
		if(dayOfWeek==1){ //如果是星期天，星期一则往前退6天
			current.add(Calendar.DAY_OF_MONTH, -6);
		}else{
			current.add(Calendar.DAY_OF_MONTH, 2-dayOfWeek);
		}
		
		current.set(Calendar.HOUR_OF_DAY, 0);
		current.set(Calendar.MINUTE, 0);
		current.set(Calendar.SECOND, 0);
		current.set(Calendar.MILLISECOND, 0);
		
		return current.getTime();
	}
	
	public static void main(String[] args){

		Map<String,Date> map = getDateForWeekDay();
		for(String key:map.keySet()){
			System.out.println(key+":"+map.get(key));
		}
	}

    /**
     * 转化时间从指定格式日期为长整形
     *
     * @param format
     * @param time
     * @return
     */
    public static Long convertDateStringToDateLong(String format, String time)
            throws ParseException {
        if (time == null || time.trim().equals("")) {
            return null;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        Date d = fmt.parse(time);
        return d.getTime();
    }
    
    /**
     * 获得指定格式日期
     * 
     * @param date
     * 			日期
     * @param format
     * 			指定格式
     * @return
     */
    public static Date getFormatDate(Date date, String format){
    	if(null == date){
    		return null;
    	}
    	if(null == format){
    		return parseDate(formatDate("yyyy-MM-dd", date), "yyyy-MM-dd");
    	}
    	return parseDate(formatDate(format, date), format);
    }
    
    public static int getMinuteBetween(Date d1, Date d2) {
		if(d1==null || d2==null)
			return 0;
		long m = d1.getTime();
		long n = d2.getTime();
		return (int) ((m - n) / 60000);
	}
    
    /**
     * 计算创建时间到现在过去多久了
     * @param createTime
     * @return
     */
    public static String getPastTime(Date createTime) {
    	
		String pastTime;
    	Date current = new Date();
		int days  = getDayBetween(current,createTime);
		int hours = 0;
		int mins  = 0;
		if(days>0){
			pastTime = "1天前";
		}else if((hours = getHourBetween(current,createTime))>0){
			pastTime = hours+"小时前";
		}else if((mins = getMinuteBetween(current,createTime))>0){
			pastTime = mins + "分钟前";
		}else{
			long seconds = getSecondBetweem(current, createTime);
			if(seconds>5){
				pastTime = seconds +"秒前";
			}else{
				pastTime = "刚刚";
			}
		}	
		return pastTime;
	}
    
    /**
     * 获取从今天开始未来一周的星期和日期的映射表
     * 1-星期一:2014-05-12，2-星期二:2014-05-13.....
     * @return
     */
    public static Map<String,Date> getDateForWeekDay(){
		Map<String,Date> weekDayDateMap = new HashMap<String,Date>();
		Calendar calendar = Calendar.getInstance();
		for(int i=1;i<=7;i++){
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
			if(dayOfWeek==0){
				dayOfWeek = 7;
			}
			weekDayDateMap.put(dayOfWeek+"", calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return weekDayDateMap;
	}
    
    /**
	 * 获得本日星期数,星期一:1,星期日:7
	 * 如果传入null则默认为本日
	 * @return
	 */
	public static int getDayOfWeek(Calendar calendar){
		int today;
		if(calendar!=null){
			today=calendar.get(Calendar.DAY_OF_WEEK);
		}else{
			today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		}
		if(today==1)
			return 7;
		else
			return today-1;
	}
	
	/**
     * 获取日期的中国式星期几（1-7分别代表周一至周日）
     * 
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date){
    	if(date==null){
    		date = new Date();
    	}

    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getDayOfWeek(cal); 	
    }
	
	/**
	 * 判断两个日期是否为同一天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDate(Date date1,Date date2){
		if(date1==null || date2==null){
			return false;
		}
		
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(date1);
		calendar2.setTime(date2);
		
		if(calendar1.get(Calendar.YEAR)==calendar2.get(Calendar.YEAR)
				&& calendar1.get(Calendar.MONTH)==calendar2.get(Calendar.MONTH)
				&& calendar1.get(Calendar.DAY_OF_MONTH)==calendar2.get(Calendar.DAY_OF_MONTH)){
			return true;
		}
		
		return false;		
	}
	
	public static LocalDateTime utilDateToLocalDateTime(Date date) {
		if(date == null) return null;
	    Instant instant = date.toInstant();
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
	    return localDateTime;
	}
}
