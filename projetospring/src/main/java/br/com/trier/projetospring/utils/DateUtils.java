package br.com.trier.projetospring.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	private static DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public static ZonedDateTime strToZonedDateTime(String dateStr) {
		return dateStr != null ? LocalDate.parse(dateStr, formatoBR).atStartOfDay(ZoneId.systemDefault()) : ZonedDateTime.now();
	}
	
	public static String zoneDateTimeToStr(ZonedDateTime date) {
		return date != null ? formatoBR.format(date) : "";
	}
}
