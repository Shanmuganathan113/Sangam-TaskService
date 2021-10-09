
package com.sangam.taskservice.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.sangam.taskservice.utils.Constants;
import com.sangam.taskservice.utils.StringPrefixedSequenceIdGenerator;

import lombok.Data;
 
@Entity
@Table(name = Constants.TABLE_LOG)
@Data
public class Log{
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_seq")
    @GenericGenerator(
        name = "table_seq", 
        strategy = "com.sangam.taskservice.utils.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "LOG"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%08d"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_COLUMN_PARAM, value = "next_log_id")
        })
	@Column(name=Constants.COLUMN_LOG_ID)
	private String logId; 				// this is the primary key
	
	@Column(name=Constants.COLUMN_ID) 
	private String id; 					// Have the intended segment id
	 
	@Column(name=Constants.COLUMN_DESCRIPTION)
	private String description;
	
	@Column(name=Constants.COLUMN_TASK_STATUS_ID)
	private Integer taskStatusId;
	
	@Column(name=Constants.COLUMN_BY_USER_ID)
	private String byUserId;
	
	@Column(name=Constants.COLUMN_TO_USER_ID)
	private String toUserId;
	
	@Column(name=Constants.COLUMN_SEGMENT_IND)
	private Integer segmentInd; // specifies if segment is Message or Task
	
	@Column(name=Constants.COLUMN_TIME)
	private LocalDateTime time;
	
}