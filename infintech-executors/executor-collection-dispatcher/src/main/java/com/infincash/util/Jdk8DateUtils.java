package com.infincash.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Jdk8DateUtils {
	public static LocalDateTime toLocalDateTime(Date date) {
//		ZoneId utc7 = ZoneId.of("Asia/Ho_Chi_Minh");
		
		Instant  ins = date.toInstant();
		return LocalDateTime.ofInstant(ins, ZoneId.systemDefault());
	}
	
	public static String toString(LocalDateTime ldt) {
		return toString(ldt, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String toString(LocalDateTime ldt, String formatter) {
		LocalDate localDate = ldt.toLocalDate();
		//LocalDate 格式化 HH:mm:ss会抛异常java.time.temporal.UnsupportedTemporalTypeException
		String sDate = ldt.format(DateTimeFormatter.ofPattern(formatter));
		return sDate;
	}
	
	public static long substract(Date a, Date b) {
		LocalDateTime aa = toLocalDateTime(a);
		LocalDateTime bb = toLocalDateTime(b);
		return aa.toLocalDate().toEpochDay() - bb.toLocalDate().toEpochDay();
	}
	
	public static void main(String[] args) {
		System.out.println(toString(toLocalDateTime(new Date())));
	}
}
