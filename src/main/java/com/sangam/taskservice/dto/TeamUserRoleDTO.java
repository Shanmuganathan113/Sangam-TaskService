package com.sangam.taskservice.dto;

import lombok.Data;

@Data
public class TeamUserRoleDTO {
	private String teamId;
	private String userId;
	private String userName;
	private String userImage;
	private Integer roleId;
	private String roleName;
	public TeamUserRoleDTO(String teamId,String userId, String userName, String userImage, Integer roleId, String roleName) {
		this.teamId = teamId;
		this.userId = userId;
		this.userName = userName;
		this.userImage = userImage;
		this.roleId = roleId;
		this.roleName = roleName;
	}
}
