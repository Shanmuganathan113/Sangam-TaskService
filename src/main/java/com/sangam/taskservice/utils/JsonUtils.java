package com.sangam.taskservice.utils;

import com.google.gson.Gson;
import com.sangam.taskservice.dto.UtilXLDataDTO;

public class JsonUtils {

	
	public UtilXLDataDTO formManageTask(String manageTask) {
		Gson gson = new Gson();  
		UtilXLDataDTO listManageTaskDTO = gson.fromJson(manageTask,  UtilXLDataDTO.class);
		return listManageTaskDTO;
	}
}
