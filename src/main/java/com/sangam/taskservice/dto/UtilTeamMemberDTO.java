package com.sangam.taskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UtilTeamMemberDTO {
	private String teamId;
	private String userId;
	private String userName;
	private String roleName;
}
