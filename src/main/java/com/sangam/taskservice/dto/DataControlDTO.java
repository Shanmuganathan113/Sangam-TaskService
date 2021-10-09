package com.sangam.taskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataControlDTO {
	private String label;
	private String key;
	private String value;
	private String description;
}
