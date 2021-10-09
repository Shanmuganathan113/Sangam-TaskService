package com.sangam.taskservice.dto;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Data;
/*
 * Used to map data from DB
 */
@Data
public class ViewMessageDTO {
	private Long messageId;
	private String messageSubject;
	private String messageContent;
	private String phoneNo;
	private Integer messageTypeId;
	private String messageType;
	private Long taskMessageLogId;
	private String taskMessageLogDescription;
	private Long byUserId;
	private String byUserName;
	private String byUserImage;
	private Integer messageStatusId;
	private String messageStatus;
	private LocalDateTime time;
	
	public ViewMessageDTO(Object[] data) {
		this.messageId = (long)(int)data[0];
		this.messageSubject = (String)data[1];
		this.messageContent = (String)data[2];
		this.phoneNo = (data[3] == null)? "" : (String)data[3];
		this.messageTypeId = Integer.parseInt(data[4].toString());
		this.messageType = data[5].toString();
		this.taskMessageLogId = (long)(int)data[6];
		this.taskMessageLogDescription = (data[7] != null)? "": data[7].toString();
		this.byUserId = (data[8]==null) ? 0 : (long)(int)data[8];
		this.byUserName = (data[9] == null) ? "": (String)data[9];
		this.byUserImage = (data[10] == null) ? "": (String)data[10];
		this.messageStatusId = Integer.parseInt(data[11].toString());
		this.messageStatus =  data[12].toString();
		this.time = Timestamp.valueOf(data[13].toString()).toLocalDateTime();
	}
}
