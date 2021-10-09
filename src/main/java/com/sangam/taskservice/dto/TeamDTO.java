package com.sangam.taskservice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TeamDTO implements Comparable<TeamDTO> {
	private String	teamId;
	private String	teamName;
	private String	summary;
	private String	detailedDescription;
	private String 	pMyRole;
	private Integer pRoleId;
	List<TeamUserRoleDTO> listTeamUser = new ArrayList<TeamUserRoleDTO>();
	
	@Override
	public int compareTo(TeamDTO obj) {
		return this.teamId.compareTo(obj.getTeamId());
	}

	public TeamDTO(String teamId, String teamName, String summary,String detailedDescription) 
	{
		this.teamId = teamId;
		this.teamName = teamName;
		this.summary = summary;
		this.detailedDescription = detailedDescription;
	}
	
	public void process(String userId) {
		this.listTeamUser.forEach( e -> {if(e.getUserId().equalsIgnoreCase(userId)) {
											this.pMyRole = e.getRoleName();
											this.pRoleId = e.getRoleId();
											}
										});
	}
}
