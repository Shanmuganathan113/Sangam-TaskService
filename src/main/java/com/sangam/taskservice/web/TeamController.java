package com.sangam.taskservice.web;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sangam.taskservice.dto.TeamDTO;
import com.sangam.taskservice.service.TaskService;
import com.sangam.taskservice.utils.Constants;

@RestController
@RequestMapping(Constants.REQUEST_MAPPING_TEAM)
public class TeamController {
	@Autowired
	TaskService taskService;
	
	@RequestMapping
	public Set<TeamDTO> fetchTeamDetails(@RequestParam Map<String,String> listParams) throws Exception{
		return taskService.fetchTeam(listParams) ;
	}

}
