package com.sangam.taskservice.dto;

import lombok.Data;

@Data
public class StaticRolePrivilege {
	private String 	rolePrivilegeId;
	private String 	rolePrivilege;
	private Boolean isTeamLead;
	private Boolean isTeamMember;
	private Boolean isGlobalAdmin;
	
	public StaticRolePrivilege(String rolePrivilegeId,String rolePrivilege,Boolean isTeamLead, 
								Boolean isTeamMember, Boolean isGlobalAdmin, String rules) {
		this.rolePrivilegeId 	= rolePrivilegeId;
		this.rolePrivilege 		= rolePrivilege;
		this.isTeamLead 		= isTeamLead;
		this.isTeamMember 		= isTeamMember;
		this.isGlobalAdmin 		= isGlobalAdmin;
	}
}
