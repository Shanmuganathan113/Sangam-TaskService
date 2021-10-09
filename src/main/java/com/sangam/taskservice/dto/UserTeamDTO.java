package com.sangam.taskservice.dto;

import lombok.Data;

@Data
public class UserTeamDTO {
	private String userId;
	private String userName;
	private String userImage;
	private String BY_OR_TO_USER_IND;
	private String teamId = "";
	private String teamName;
	private String taskId;
	
}
