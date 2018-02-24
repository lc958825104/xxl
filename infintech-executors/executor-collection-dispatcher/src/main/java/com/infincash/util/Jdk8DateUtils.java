package com.infincash.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Jdk8DateUtils {
	public static LocalDateTime date2LocalDateTime(Date date) {
//		ZoneId utc7 = ZoneId.of("Asia/Ho_Chi_Minh");
		
		Instant  ins = date.toInstant();
		return LocalDateTime.ofInstant(ins, ZoneId.systemDefault());
	}
	
	public static String localDateTime2String(LocalDateTime ldt) {
		return localDateTime2String(ldt, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String localDate2String(LocalDate ld) {
		return ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public static String localDateTime2String(LocalDateTime ldt, String formatter) {
		//LocalDate 格式化 HH:mm:ss会抛异常java.time.temporal.UnsupportedTemporalTypeException
		String sDate = ldt.format(DateTimeFormatter.ofPattern(formatter));
		return sDate;
	}
	
	public static long dateSubstract(Date a, Date b) {
		LocalDateTime aa = date2LocalDateTime(a);
		LocalDateTime bb = date2LocalDateTime(b);
		return aa.toLocalDate().toEpochDay() - bb.toLocalDate().toEpochDay();
	}
	
	public static String getDateAfter(long days) {
		return localDate2String(date2LocalDateTime(new Date()).toLocalDate().plusDays(days));
	}
	
	public static void main(String[] args) {
//		System.out.println(localDateTime2String(date2LocalDateTime(new Date())));
		System.out.println(getDateAfter(-90));
	}
}
