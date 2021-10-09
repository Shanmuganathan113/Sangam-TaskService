package com.sangam.taskservice.service;

import java.util.Map;
import java.util.Set;

import com.sangam.taskservice.dto.FilterTaskDTO;
import com.sangam.taskservice.dto.LogDTO;
import com.sangam.taskservice.dto.TaskDTO;
import com.sangam.taskservice.dto.TeamDTO;

public interface TaskService {
	
	public Set<TaskDTO> fetchTask( Map<String,String> listParams) throws Exception;
	public Set<TaskDTO> fetchFilterTask(FilterTaskDTO filterTaskDTO) throws Exception;
	
	public Set<TeamDTO> fetchTeam(Map<String,String> listParams)throws Exception;

//	public List<ViewTasksDTO> fetchMyTasks(Long userId);
//
//	public List<ViewTasksDTO> fetchPageTasks(Integer pageId);
//
	public TaskDTO updateTask(TaskDTO taskDTO) throws Exception;
	
	public LogDTO updateTaskLog(LogDTO messageDTO);

//	
//	public String updateTaskLog(TaskLogDTO taskDTO) ;
//	
//	public TaskDTO fetchTaskToEdit(Long taskId);

}
