package com.sangam.taskservice.web;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sangam.taskservice.dto.FilterTaskDTO;
import com.sangam.taskservice.dto.LogDTO;
import com.sangam.taskservice.dto.TaskDTO;
import com.sangam.taskservice.service.TaskService;
import com.sangam.taskservice.utils.Constants;

@RestController
@RequestMapping(Constants.REQUEST_MAPPING_TASK)
public class TaskController {

	@Autowired
	TaskService taskService;
	
	// URL : http://localhost:9001/task/taskDetails?taskId=task_id&status=status_id&fetch=flag_id
	// @RequestMapping(value = Constants.REQUEST_MAPPING_TASK_DETAILS, params = {Constants.REQUEST_PARAMS_TASK_ID,Constants.REQUEST_PARAMS_STATUS,Constants.REQUEST_PARAMS_FLAG}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = Constants.REQUEST_MAPPING_TASK_DETAILS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<TaskDTO> fetchTaskDetails(@RequestParam Map<String,String> listParams) throws Exception{
		return taskService.fetchTask(listParams) ;
	}
	
	// URL : http://localhost:9001/task/updateTask
	@RequestMapping(value = Constants.REQUEST_MAPPING_UPDATE_TASK,method = RequestMethod.POST, consumes = Constants.REQUEST_CONSUME_JSON, produces = MediaType.APPLICATION_JSON_VALUE)
	public TaskDTO updateTask(@RequestBody TaskDTO taskDTO) throws Exception{
		return taskService.updateTask(taskDTO);
	}
	
	// URL : http://localhost:9001/task/updateTaskLog
	@RequestMapping(value = Constants.REQUEST_MAPPING_UPDATE_TASK_LOG,method = RequestMethod.POST, consumes = Constants.REQUEST_CONSUME_JSON, produces = MediaType.APPLICATION_JSON_VALUE)
	public LogDTO updateTaskLog(@RequestBody LogDTO logDTO) throws Exception{
		return taskService.updateTaskLog(logDTO);
	}
	
	// URL : http://localhost:9001/task/filterTask
	@RequestMapping(value = Constants.REQUEST_MAPPING_FILTER_TASK, method = RequestMethod.POST,  consumes = Constants.REQUEST_CONSUME_JSON, produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<TaskDTO> fetchFilterTask(@RequestBody FilterTaskDTO filterTask) throws Exception{
		return taskService.fetchFilterTask(filterTask) ;
	}
}
