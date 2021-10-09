package com.sangam.taskservice.dto;


import java.util.stream.Collectors;

import com.sangam.taskservice.utils.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// This class is created only to eliminate difficulty with VBA coding to get JSON data
public class UtilTaskDTO {
	public Integer 	slNo;
	public String 	taskId;
	public String 	createdbyUser;
	public String 	createdByTeam;
	public String 	queuedToTeam;
	public String 	assignedTo;
	public String 	latestStatus;
	public String 	comment;
	public String 	taskTitle;
	public String 	taskDetails;
	public Integer 	effortHrs;

	public String 	myUserId;
	public String 	myRoles;
	public String 	listMyTaskPrivilege;
	
	public String 	idTeamQueueOrTeam;
	
	public UtilTaskDTO(TaskDTO taskDTO) {
		this.slNo 				= Constants.getInteger(taskDTO.getSlNo());
		this.taskId 			= Constants.getString(taskDTO.getTaskId());
		this.createdByTeam 		= Constants.getString(taskDTO.getPCreatedByTeam().getTeamId());
		this.createdbyUser 		= Constants.getString((taskDTO.getPCreatedByUser() == null )? null : taskDTO.getPCreatedByUser().getUserName());
		this.queuedToTeam 		= Constants.getString((taskDTO.getPQueuedToTeam() == null)?  null : taskDTO.getPQueuedToTeam().getTeamId());
		this.assignedTo 		= Constants.getString((taskDTO.getPAssignedToUser() == null)? null : taskDTO.getPAssignedToUser().getTeamName());
		this.latestStatus 		= Constants.getString((taskDTO.getPLatestLog() == null )? null : taskDTO.getPLatestLog().getTaskStatus());
		this.comment 			= Constants.getString((taskDTO.getPLatestLog() == null )? null : taskDTO.getPLatestLog().getLogDescription());
		this.taskTitle 			= Constants.getString(taskDTO.getTitle());
		this.taskDetails		= Constants.getString(taskDTO.getDescription());
		this.effortHrs 			= Constants.getInteger(taskDTO.getEffortHrs());
		this.listMyTaskPrivilege= taskDTO.getListMyTaskPrivilege().entrySet().stream().map(e-> e.getValue()+":"+ e.getKey()).collect(Collectors.joining(","));
		this.idTeamQueueOrTeam	= (taskDTO.getPIsQueueTask())?taskDTO.getPQueuedToTeam().getTeamId():taskDTO.getPCreatedByTeam().getTeamId();
	}
}
