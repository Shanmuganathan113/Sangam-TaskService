package com.sangam.taskservice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UtilStatusManageTasks {
	private String utilKey;
	private String utilStatusMessage;
	private List<TaskStatus> taskStatus = new  ArrayList<TaskStatus>();
	private Boolean isSuccessInd;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class TaskStatus{
		private String slNo;
		private String taskId;
		private Boolean isSuccessInd;
		private String statusMessage;
	}
	
}
