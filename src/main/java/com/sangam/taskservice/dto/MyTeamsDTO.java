package com.sangam.taskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyTeamsDTO {
	private String teamId;
	private String teamName;
	private String roleId;
	private String roleName;
}
