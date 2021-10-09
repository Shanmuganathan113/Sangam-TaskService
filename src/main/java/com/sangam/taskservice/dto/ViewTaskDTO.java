package com.sangam.taskservice.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
/*
 * Used to map data from DB
 */
import java.util.List;

import lombok.Data;


@Data
public class ViewTaskDTO {
	private Long taskId;
	private String taskTitle;
	private String taskDescription;
	private Long taskLogId;
	private String taskLogDescription;
	private Integer taskStatusId;
	private String taskStatus;
	private Long byUserId;
	private Long toUserId;
	List<UserTeamDTO> listUsers = new ArrayList<UserTeamDTO>();
	private LocalDateTime time;
	
//	public ViewTaskDTO(Object[] data) {
//		this.taskId = (long)(int)data[0];
//		this.taskTitle = (String)data[1];
//		this.taskDescription = (String)data[2];
//		this.taskLogId = (long)(int)data[3];
//		this.taskLogDescription = (data[4] == null )? "" : (String)data[4];
//		this.taskStatusId = Integer.parseInt(data[5].toString());
//		this.taskStatus = (String)data[6];
//		this.byUserId = (data[9]==null) ? 0 : (long)(int)data[9];
//		this.toUserId = (data[12] == null) ? 0 : (long)(int)data[12];
//		this.time = Timestamp.valueOf(data[15].toString()).toLocalDateTime();
//	}
	
	
}
