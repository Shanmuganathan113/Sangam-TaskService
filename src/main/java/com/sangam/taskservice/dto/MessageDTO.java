package com.sangam.taskservice.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;

@Data
public class MessageDTO {
	private Long messageId;
	
	private String subject;

	private String content;
	
	private String phoneNo;
	
	private Integer messageTypeId;
	private String messageType;
	
	private List<LogDTO> log = new ArrayList<LogDTO>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MessageDTO))
			return false;
		MessageDTO other = (MessageDTO) obj;
		return Objects.equals(content, other.content) && Objects.equals(messageId, other.messageId)
				&& Objects.equals(messageType, other.messageType) && Objects.equals(phoneNo, other.phoneNo)
				&& Objects.equals(subject, other.subject) && Objects.equals(log, other.log);
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, messageId, messageType, phoneNo, subject, log);
	}

	
}
