package com.sangam.taskservice.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface FunctionsUtils {
	
	public static String  formatTimeStamp(LocalDateTime timeStamp) {
		if(timeStamp!= null)
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
			String dateTimeString = timeStamp.format(formatter); 
			return dateTimeString;
		}
		return null;
	}
	
	
}
