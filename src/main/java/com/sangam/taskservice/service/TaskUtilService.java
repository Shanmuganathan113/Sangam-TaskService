package com.sangam.taskservice.service;

import java.util.List;

import com.sangam.taskservice.dto.UtilXLDataDTO;

public interface TaskUtilService {

	public String manageTask(UtilXLDataDTO utilTasks) throws Exception;
	
	
	public String refreshStatics(UtilXLDataDTO utilXLDTO) throws Exception;

	String fetchTaskReport(List<String> listTasks, UtilXLDataDTO utilXLDataDTO) throws Exception;


}
