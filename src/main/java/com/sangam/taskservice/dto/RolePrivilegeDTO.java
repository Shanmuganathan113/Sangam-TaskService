package com.sangam.taskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RolePrivilegeDTO {
	private String teamId;
	private String roleId;
	private String roleName;
	private String taskStatus;
}
