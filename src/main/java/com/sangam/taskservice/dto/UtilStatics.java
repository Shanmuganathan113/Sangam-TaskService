package com.sangam.taskservice.dto;

import java.util.List;

import lombok.Data;
@Data
public class UtilStatics {
	private String statusMessage = "";
	private String pTeamList = "";
	private List<UtilTeamMemberDTO> listTeamMembers;
	private List<UtilTeamDTO> listTeams;
	private List<DataControlDTO> listDataControl;
	private List<RolePrivilegeDTO> listRolePrivilge;
	private List<MyTeamsDTO> listMyTeams;
	private UtilUserDetailDTO userDetails;
	
}
