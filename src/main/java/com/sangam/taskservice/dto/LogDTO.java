package com.sangam.taskservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.sangam.taskservice.utils.Constants;

import lombok.Data;

@Data
public class LogDTO {
	private String logId; 	// log_id from log table
	
	public String id; 		// Intended segment ids
	
	private String logDescription;
	
	public Integer taskStatusId;
	
	public String taskStatus;
	
	public Integer segmentId;
	
	private UserTeamDTO pByUser; 	// Created by user details 
	
	private UserTeamDTO pToUser; 	//  Task assigned to this team 
	
	private LocalDateTime time;
	private String pLogTime;
	
	public LogDTO(	String logId, String taskMessageLogDescription, Integer taskStatusId,
					String taskStatus, String id, LocalDateTime time) {
		this.logId = logId;
		this.logDescription = taskMessageLogDescription;
		this.taskStatusId = taskStatusId;
		this.taskStatus = taskStatus;
		this.id = id;
		this.time = time;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LogDTO))
			return false;
		LogDTO other = (LogDTO) obj;
		return Objects.equals(logId, other.logId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(logId);
	}

	public void process(List<UserTeamDTO> listUserTeam) {
		this.pLogTime = Constants.getStringTime(this.time);
		listUserTeam.forEach(e ->	{
										if( e.getBY_OR_TO_USER_IND().equals("BY_USER"))
											this.pByUser = e;
										if( e.getBY_OR_TO_USER_IND().equals("TO_USER"))
											this.pToUser = e;
									});
	}

	

}
