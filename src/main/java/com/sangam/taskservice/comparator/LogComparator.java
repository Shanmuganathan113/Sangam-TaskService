package com.sangam.taskservice.comparator;

import java.util.Comparator;

import com.sangam.taskservice.dto.LogDTO;

public class LogComparator implements Comparator<LogDTO> {

	@Override
	public int compare(LogDTO o1, LogDTO o2) {
		return Comparator.comparing(LogDTO::getLogId)
				.thenComparing(LogDTO::getTime)
				.compare(o2,o1);	
		}
}
