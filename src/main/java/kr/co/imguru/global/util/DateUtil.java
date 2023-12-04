package kr.co.imguru.global.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author firstkhw
 * @date : 2019. 03. 04.
 * 날짜 관련 유틸
 *
 */
public class DateUtil {
	
	/*
	 *현재 날짜+시간+밀리세컨드 문자열 리턴
	 */
	public static String getDateTimeMillisecond() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String now = format.format(new Date());
		return now;
	}
	
	/**
	* 현재 시간을 'yyyymmdd'형식으로 리턴
	*/
	public static String currentDateString() {
		Calendar cal = Calendar.getInstance( );//TimeZone.getTimeZone("JST") );
		int y = cal.get(Calendar.YEAR) ;
		int m = cal.get(Calendar.MONTH) +1;
		int d = cal.get(Calendar.DAY_OF_MONTH);
	
		String month = String.valueOf(m);
		String day = String.valueOf(d);
	
		if( m < 10 ) month = "0" + month;
		if( d < 10 ) day = "0" + day;
	
		return "" + y + month + day;
	}
	
	/**
	* 현재 시간을 'hhmmss'형식으로 리턴
	*/
	public static String currentTimeString() {
		Calendar cal = Calendar.getInstance( );//TimeZone.getTimeZone("JST") );
		int h = cal.get(Calendar.HOUR_OF_DAY);
		int m = cal.get(Calendar.MINUTE);
		int s = cal.get(Calendar.SECOND);
	
		String hour = String.valueOf(h);
		String minute = String.valueOf(m);
		String second = String.valueOf(s);
		
		if( h < 10 ) hour = "0" + hour;
		if( m < 10 ) minute = "0" + minute;
		if( s < 10 ) second = "0" + second;
	
		return hour + minute + second;
	}
}
