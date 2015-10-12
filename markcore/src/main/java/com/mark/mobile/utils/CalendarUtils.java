package com.mark.mobile.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarUtils {

	private CalendarUtils() {
	}
	
	public static Calendar createCalendar(String timestamp){
		int index = timestamp.indexOf(".");
		if(timestamp!=null && index!=-1){
			timestamp = timestamp.substring(0, index);
		}
		long timeInMillis = Long.parseLong(timestamp)*1000;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeInMillis);
		return cal;
	}

	public static Calendar createCalendar(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar;
	}

	/**
	 * yyyymmdd 00:00:00 000
	 * @param calendar
	 * @return
	 */
	public static Calendar clearTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar;
	}

	public static long clearTime(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);

		return clearTime(calendar).getTimeInMillis();
	}

	public static String dayOfWeekString(String[] dayOfWeekStringArray, int dayOfWeek) {
		return dayOfWeekStringArray[dayOfWeek - 1];
	}

	public static Calendar YYYYMMDDHHMMSSToCalendar(String str) {
		if (str != null) {
			Calendar cal = Calendar.getInstance();
			try {
				SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
				cal.setTime(FORMAT.parse(str));
				return cal;
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
	
	public static String toYYYYMMDDHHMMSS(Calendar source) {
		if (source != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
			return format.format(source.getTime());
		}
		return null;
	}
	
	public static String toYYYYMMDDHHMM(Calendar source) {
		if (source != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault());
			return format.format(source.getTime());
		}
		return null;
	}

	public static String toYYYYMMDD(Calendar source) {
		if (source != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
			return format.format(source.getTime());
		}
		return null;
	}
	
	public static boolean isSameDay(Calendar source, Calendar target){
		int yearSrc = source.get(Calendar.YEAR);
		int yearTag = target.get(Calendar.YEAR);
		
		int daySrc = source.get(Calendar.DAY_OF_YEAR);
		int dayTag = target.get(Calendar.DAY_OF_YEAR);
		
		if(yearSrc == yearTag && daySrc == dayTag){
			return true;
		}
		
		return false;
	}
	

	public static String toYYYYMM(Calendar source) {
		if (source != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
			return format.format(source.getTime());
		}
		return null;
	}

	public static String toHHMM(Calendar source) {
		if (source == null) {
			return "Can't HH:mm, source null";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
		//SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
		String strDate = sdf.format(source.getTime());

		return strDate;
	}
	
	private static int[] rang = new int[] { 1000,// just
			1000 * 60,// just
			1000 * 60 * 3,// 3m
			1000 * 60 * 10,// 10m
			1000 * 60 * 30,// 30m
			1000 * 60 * 60,// 1h
			1000 * 60 * 60 * 2,// 2h
			1000 * 60 * 60 * 3,// 3h
			1000 * 60 * 60 * 6,// 6h
			1000 * 60 * 60 * 24,// 1d
			1000 * 60 * 60 * 24 * 3,// 3day
			1000 * 60 * 60 * 24 * 7,// week
	};

	public static int getPassTime(Calendar source) {
		Calendar now = Calendar.getInstance();
		long time = now.getTimeInMillis() - source.getTimeInMillis();
		if(time<0){
			return 0;
		}

		for (int i = rang.length - 1; i >= 0; i--) {
			if (time > rang[i]) {
				return i;
			}
		}
		return rang.length - 1;
	}
	
	/**
	 * format 99:59:59, 1 day to 24 hours.
	 * @param offsetToFuture
	 * @return
	 */
	public static String toMax99MMSS(Calendar offsetToFuture) {
		SimpleDateFormat format = new SimpleDateFormat("kk:mm:ss", Locale.getDefault());
		String mmss = format.format(offsetToFuture.getTime());
		
		int day = offsetToFuture.get(Calendar.DAY_OF_MONTH);
		int hourforDay = offsetToFuture.get(Calendar.HOUR_OF_DAY);
		
		//1970-01-01 08:00:00
		day -=1;
		
		int hour = day * 24 + hourforDay;
		hour = Math.min(hour, 99);
		if(hour<10){
			mmss = "0"+hour+mmss.substring(2);
		}else{
			mmss = hour+mmss.substring(2);
		}
		return mmss;
	}
	
	public static String toMMDDHHMM(long timeInMillis){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeInMillis);
		
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
		return format.format(cal.getTime());
	}
	
	public static String toMMDD(long timeInMillis){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeInMillis);
		
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日", Locale.getDefault());
		return format.format(cal.getTime());
	}
	
	public static String toMMDDHHMM(String timestamp){
		long timeInMillis = Long.parseLong(timestamp)*1000;
		return CalendarUtils.toMMDDHHMM(timeInMillis);
	}
	
	/**
	 * Times": "/Date(1425956629171)/" to calendar
	 * @param string
	 * @return
	 */
	public static Calendar parseToCalendar(String string){
		Calendar cal = Calendar.getInstance();
		long timeMillis = CalendarUtils.parseToTimeInMillis(string);
		cal.setTimeInMillis(timeMillis);
		return cal;
	}
	
	/**
	 * Times": "(1425956629171)" to timeInMillis
	 * @param string
	 * @return
	 */
	public static long parseToTimeInMillis(String string){
		int start = string.indexOf("(")+1;
		int end = string.indexOf(")");
		String timeString = string.substring(start, end);
		
		long timeMillis = Long.parseLong(timeString);
		return timeMillis;
	}
	
	public static String formatSimple(int num){
		if(num<10){
			return "0"+num;
		}
		return ""+num;
	}
	
	/**
	 * 获取当前世间的时间戳
	 * @return
	 */
	public static String getTimestamp(){
		Calendar cal = Calendar.getInstance();
		long timestamp = cal.getTimeInMillis()/1000;
		return String.valueOf(timestamp);
	}
	
	/**
	 * "07:01" = 7*60*60*1000+1000;
	 * @return
	 */
	public static long parseToTimeInMillis2(String string){
		if(string.contains(":")){
			String[] srs = string.split(":");
			int h = Integer.valueOf(srs[0]);
			int m = Integer.valueOf(srs[1]);
			long hour = h *60 * 60 * 1000;
			long mm =  m * 60 * 1000;
			return hour+mm;
		}
		return 0;
	}
	
}
