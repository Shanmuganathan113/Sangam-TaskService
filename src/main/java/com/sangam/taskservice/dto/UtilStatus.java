package com.sangam.taskservice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UtilStatus {
	public String message;
	private List<UtilTaskDTO> utilValue = new ArrayList<UtilTaskDTO>();
}
