package com.sangam.taskservice.dto;

import lombok.Data;

@Data
public class FilterTaskDTO {
	private String myUserId;
	private String listTaskId;
	private String taskTitle;
	private String taskDesc;
	private String createdByTeamId;
	private String createdByUserId;
	private String assignedToTeamId;
	private String taskStatus;
	private String latestTaskStatus;
	private String createdBefore;
	private String createdAfter;
	private Integer maxRecords;
	}
