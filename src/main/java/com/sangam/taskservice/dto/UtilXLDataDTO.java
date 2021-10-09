package com.sangam.taskservice.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import lombok.Data;

@Data
public class UtilXLDataDTO {

	private String utilKey;
	private String utilUserId;
	private String utilFileName;
	private String latestStaticUpdatedTime;
	private LocalDateTime pLatestStaticUpdatedTime;
	private List<UtilTaskDTO> utilValue = new ArrayList<UtilTaskDTO>();
	private FilterTaskDTO filterTaskDTO = new FilterTaskDTO();
	
	@PostConstruct
	public void updateLatestUpdatedTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy hh:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.parse(latestStaticUpdatedTime, formatter);
		this.pLatestStaticUpdatedTime = localDateTime;
	}
	
}
