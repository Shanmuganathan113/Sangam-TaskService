package com.sangam.taskservice.domain;

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
@Table(name = Constants.TABLE_TASK)
//@SqlResultSetMapping(
//	    name="resultTempTaskDetails",
//	    classes={
//	      @ConstructorResult(
//	        targetClass=com.sangam.taskservice.dto.TaskDetailsTempDTO.class,
//	        columns={
//	        		@ColumnResult(name="taskId", type=Long.class),
//	        		@ColumnResult(name="taskTitle", type=String.class),
//	        		@ColumnResult(name="taskDescription", type=String.class),
//	        		@ColumnResult(name="pageId", type=Integer.class),
//	        		@ColumnResult(name="pageName", type=String.class),
//	        		@ColumnResult(name="byUserName", type=String.class),
//	        		@ColumnResult(name="byUserPic", type=String.class),
//	        		@ColumnResult(name="toUserName", type=String.class),
//	        		@ColumnResult(name="taskStatusId", type=Integer.class),
//	        		@ColumnResult(name="taskLogStatusDetails", type=String.class),
//	        		@ColumnResult(name="taskLogId", type=Long.class),
//	        		@ColumnResult(name="taskLogDescription", type=String.class),
//	        		@ColumnResult(name="taskLogTime", type=LocalDateTime.class),
//	        })}
//	    )
//@NamedNativeQuery( name = "Task.fetchTempTaskDetails", query = Constants.FETCH_TEMP_TASK_DETAILS, resultSetMapping = "resultTempTaskDetails" )
@Data
public class Task {
	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.IDENTITY)
	 * 
	 * @Column(name=Constants.COLUMN_TASK_ID) private Long taskId;
	 */
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_seq")
    @GenericGenerator(
        name = "table_seq", 
        strategy = "com.sangam.taskservice.utils.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "TSK"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%08d"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_COLUMN_PARAM, value = "next_task_id")
        })
	
	//@GeneratedValue(generator = "task_seq", strategy=GenerationType.SEQUENCE)
    
	@Column(name=Constants.COLUMN_TASK_ID) 
	private String taskId;
	
	@Column(name=Constants.COLUMN_TITLE)
	private String title;

	@Column(name=Constants.COLUMN_DESCRIPTION)
	private String description;
	
	@Column(name=Constants.COLUMN_EFFORT_HRS)
	private Integer effortHrs;

}


