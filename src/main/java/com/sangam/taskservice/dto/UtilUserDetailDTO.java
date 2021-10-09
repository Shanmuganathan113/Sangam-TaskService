package com.sangam.taskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UtilUserDetailDTO {
	private String userId;
	private String emailId;
	private String name;
	private String imageUrl;
	
}
