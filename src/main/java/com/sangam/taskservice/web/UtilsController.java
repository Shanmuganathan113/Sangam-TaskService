package com.sangam.taskservice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sangam.taskservice.dto.UtilXLDataDTO;
import com.sangam.taskservice.service.TaskUtilService;
import com.sangam.taskservice.utils.Constants;

@RestController
@RequestMapping(Constants.REQUEST_MAPPING_UTIL)
public class UtilsController {
	
	@Autowired
	TaskUtilService taskUtilService;
	
	// URL : http://localhost:8083/util/utilManageTask
	@RequestMapping(value = Constants.REQUEST_MAPPING_UTIL_MANAGE_TASK, method = RequestMethod.POST,  consumes = Constants.REQUEST_CONSUME_JSON, produces = MediaType.APPLICATION_JSON_VALUE)
	public String manageTasks(@RequestBody UtilXLDataDTO utilXLDataDTO) throws Exception{
		return taskUtilService.manageTask(utilXLDataDTO);
	}
	
	// URL : http://localhost:8083/util/utilFilterTask
	@RequestMapping(value = Constants.REQUEST_MAPPING_UTIL_FILTER_TASK, method = RequestMethod.POST,  consumes = Constants.REQUEST_CONSUME_JSON, produces = MediaType.APPLICATION_JSON_VALUE)
	public String fetchTaskReport(@RequestBody UtilXLDataDTO utilXLDataDTO) throws Exception{
		return taskUtilService.fetchTaskReport(null,utilXLDataDTO) ;
	}
	
	// URL : http://localhost:8083/util/utilRefreshStatics
	@RequestMapping(value = Constants.REQUEST_MAPPING_UTIL_REFRESH_STATICS,method = RequestMethod.POST,consumes = Constants.REQUEST_CONSUME_JSON,  produces = MediaType.APPLICATION_JSON_VALUE)
	public String fetchStatics(@RequestBody UtilXLDataDTO utilXLDataDTO) throws Exception{
		return taskUtilService.refreshStatics(utilXLDataDTO) ;
	}
		
}
