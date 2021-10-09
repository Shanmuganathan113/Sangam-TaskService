package com.sangam.taskservice.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.kie.api.runtime.KieSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangam.taskservice.config.DroolsBeanFactory;
import com.sangam.taskservice.dto.UserTeamDTO;

public interface Constants {
	public static final String TABLE_LOG = "table_log";

	public static final String COLUMN_LOG_ID = "t_l_id";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_EFFORT_HRS = "effort_hrs";
	public static final String COLUMN_TASK_STATUS_ID = "status_id";
	public static final String COLUMN_BY_USER_ID = "by_user_id";
	public static final String COLUMN_TO_USER_ID = "to_user_id";
	public static final String COLUMN_SEGMENT_IND = "segment_ind";
	public static final String COLUMN_TIME = "time";

	public static final String TABLE_TASK = "table_task";
	public static final String COLUMN_TASK_ID = "t_t_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_TASK_DESCRIPTION = "description";
	public static final String COLUMN_PAGE_ID = "page_id";
	
	public static final String TABLE_MESSAGE = "table_message";
	public static final String COLUMN_MESSAGE_ID ="t_m_id";
	public static final String COLUMN_MESSAGE_SUBJECT = "subject";
	public static final String COLUMN_MESSAGE_CONTENT = "content";
	public static final String COLUMN_MESSAGE_PHONE_NO = "phone_no";
	public static final String COLUMN_MESSAGE_TYPE = "message_type";
	

	public static final String REQUEST_MAPPING_TASK = "/task";
	public static final String REQUEST_MAPPING_UTIL = "/util";
	public static final String REQUEST_PARAMS_ID = "id";
	public static final String REQUEST_PARAMS_MY_USER_ID = "userId";
	public static final String REQUEST_PARAMS_STATUS = "status";
	public static final String REQUEST_PARAMS_FLAG = "flag";
	public static final String REQUEST_MAPPING_TASK_DETAILS = "/taskDetails";
	public static final String REQUEST_MAPPING_FILTER_TASK = "/filterTask";
	public static final String REQUEST_MAPPING_UPDATE_TASK = "/updateTask";
	public static final String REQUEST_MAPPING_UTIL_MANAGE_TASK = "/utilManageTask";
	public static final String REQUEST_MAPPING_UTIL_FILTER_TASK = "/utilFilterTask";
	public static final String REQUEST_MAPPING_UTIL_REFRESH_STATICS = "/utilRefreshStatics";
	public static final String REQUEST_PARAMS_UTIL_FLAG = "flag";
	public static final String REQUEST_PARAMS_UTIL_TEAM_ID = "teamId";
	public static final String REQUEST_PARAMS_UTIL_USER_ID = "userId";
	
	
	public static final String REQUEST_MAPPING_TEAM = "/team";
	public static final String REQUEST_PARAMS_TEAM_ID = "teamId";
	public static final String REQUEST_PARAMS_TEAM_USER_ID = "userId";
	public static final String REQUEST_PARAMS_TEAM_FLAG = "flag";
	
	public static final String REQUEST_FLAG_TEAM_DETAILED_INFO = "2";
	public static final String REQUEST_FLAG_USER_TEAM_LIST = "1";
	
	public static final String PARAM_SQL_NULL = null;
	public static final String PARAM_SQL_FETCH_TEAM_MEMBERS = "2";
	public static final String PARAM_SQL_FETCH_LIST_TEAMS = "3";
	public static final String PARAM_SQL_FETCH_STATIC_ROLES = "4";
	public static final String PARAM_SQL_FETCH_DATA_CONTROL = "5";
	public static final String PARAM_SQL_FETCH_MY_DETAILS = "6";
	public static final String PARAM_SQL_FETCH_MY_TEAMS = "7";
	public static final String PARAM_SQL_FETCH_ROLE_PRIVILEGE = "8";
	
	public static final String REQUEST_MAPPING_MESSAGE = "/message";
	public static final String REQUEST_PARAMS_MESSAGE_STATUS = "messageStatus";
	public static final String REQUEST_PARAMS_MESSAGE_TYPE = "messageType";
	public static final String REQUEST_MAPPING_MESSAGE_DETAILS = "/messageDetails";
	public static final String REQUEST_MAPPING_UPDATE_MESSAGE = "/updateMessage";
	public static final String REQUEST_MAPPING_UPDATE_MESSAGE_LOG = "/updateMessageLog";
	
	public static final String REQUEST_MAPPING_TASK_TO_EDIT = "/taskToEdit";
	
	public static final String REQUEST_MAPPING_MY_TASKS = "/myTasks";
	public static final String REQUEST_PARAMS_USER_ID = "userId";
	
	public static final String REQUEST_MAPPING_PAGE_TASKS = "/pageTasks";
	public static final String REQUEST_PARAMS_PAGE_ID = "pageId";
	
	public static final String REQUEST_MAPPING_UPDATE_TASK_LOG = "/updateTaskLog";
	public static final String REQUEST_CONSUME_JSON = "application/json";
	
	public static final Integer TASK_CREATED_BY_IND = 1;
	public static final Integer TASK_STATUS_ASSIGNED = 8;
	public static final Integer TASK_STATUS_REASSIGNED = 9;
	
	public static final String STATUS_SUCCESS = "Success";
	public static final String STATUS_FAILURE = "Failure";
	
	public static final String TASK_LOG_STATUS_CONCAT = " to ";
	
	enum TEAM_OR_USER_IND{TEAM,USER};
	
	public static final	 Integer STATIC_ROLE_PRIVILEGE_CREATE_TASK 	= 101;
	public static final  Integer STATIC_ROLE_PRIVILEGE_ASSIGNED_TO 	= 102;
	public static final  Integer STATIC_ROLE_PRIVILEGE_QUEUE 			= 151;
	public static final  Integer STATIC_ROLE_PRIVILEGE_CLOSED 		= 109;
	
	public static final  Integer STATIC_ROLE_PRIVILEGE_T_CREATE_TASK 					= 101;
	public static final  Integer STATIC_ROLE_PRIVILEGE_T_ASSIGN 						= 102;
	public static final  Integer STATIC_ROLE_PRIVILEGE_T_STARTED_IN_PROGRESS			= 103;
	public static final  Integer STATIC_ROLE_PRIVILEGE_T_ADD_COMMENT 					= 104;
	public static final  Integer STATIC_ROLE_PRIVILEGE_T_REQUIRE_MORE_CLARIFICATION		= 105;
	public static final  Integer STATIC_ROLE_PRIVILEGE_T_REASSIGN 						= 106;
	public static final  Integer STATIC_ROLE_PRIVILEGE_T_COMPLETE 						= 107;
	public static final  Integer STATIC_ROLE_PRIVILEGE_T_REJECT 						= 108;
	public static final  Integer STATIC_ROLE_PRIVILEGE_T_CLOSED 						= 109;
	
	public static final  Integer STATIC_ROLE_PRIVILEGE_Q_QUEUE 							= 151;
	public static final  Integer STATIC_ROLE_PRIVILEGE_Q_ASSIGN 						= 152;
	public static final  Integer STATIC_ROLE_PRIVILEGE_Q_STARTED_IN_PROGRESS 			= 153;
	public static final  Integer STATIC_ROLE_PRIVILEGE_Q_ADD_COMMENT 					= 154;
	public static final  Integer STATIC_ROLE_PRIVILEGE_Q_REQUIRE_MORE_CLARIFICATION 	= 155;
	public static final  Integer STATIC_ROLE_PRIVILEGE_Q_REASSIGN 						= 156;
	public static final  Integer STATIC_ROLE_PRIVILEGE_Q_COMPLETE 						= 157;
	public static final  Integer STATIC_ROLE_PRIVILEGE_Q_REJECT 						= 158;
	public static final  Integer STATIC_ROLE_PRIVILEGE_Q_CLOSED 						= 159;	
	
	public static final String STATIC_RULE_ADD 			= "ADD";
	public static final String STATIC_RULE_REMOVE 		= "REMOVE";
	public static final String STATIC_RULE_REMOVE_ALL 	= "REMOVEALL";
	
	public static final String STATIC_RULE_KEY_isTeamLead 				= "isTeamLead";
	public static final String STATIC_RULE_KEY_isQueueLead 				= "isQueueLead";
	public static final String STATIC_RULE_KEY_isTeamTask 				= "isTeamTask";
	public static final String STATIC_RULE_KEY_isQTask 					= "isQTask";
	public static final String STATIC_RULE_KEY_isTTaskCreatedByMe 		= "isTTaskCreatedByMe";
	public static final String STATIC_RULE_KEY_isTTaskRejected 			= "isTTaskRejected";
	public static final String STATIC_RULE_KEY_isQTaskRejected 			= "isQTaskRejected";
	public static final String STATIC_RULE_KEY_isTTaskAssigned 			= "isTTaskAssigned";
	public static final String STATIC_RULE_KEY_isQTaskAssigned 			= "isQTaskAssigned";
	public static final String STATIC_RULE_KEY_isTTaskAssignedToMe 		= "isTTaskAssignedToMe";
	public static final String STATIC_RULE_KEY_isQTaskAssignedToMe 		= "isQTaskAssignedToMe";
	public static final String STATIC_RULE_KEY_isTTaskCompleted 		= "isTTaskCompleted";
	public static final String STATIC_RULE_KEY_isQTaskCompleted 		= "isQTaskCompleted";
	public static final String STATIC_RULE_KEY_isTTaskClosed 			= "isTTaskClosed";
	public static final String STATIC_RULE_KEY_isQTaskClosed 			= "isQTaskClosed";
	public static final String STATIC_RULE_KEY_isTTaskStartedInProgress = "isTTaskStartedInProgress";
	public static final String STATIC_RULE_KEY_isQTaskStartedInProgress = "isQTaskStartedInProgress";
	
	public static final String XL_PARAM_UTIL_KEY_CREATE_TASK 	= "createTask";
	public static final String XL_PARAM_UTIL_KEY_UPDATE_TASK 	= "updateTask";
	public static final String XL_PARAM_UTIL_KEY_MY_TASK  		= "MyTasks";
	
	public static final String ROLE_LEAD 	= "TEAM_LEAD";
	public static final String ROLE_MEMBER 	= "TEAM_MEMBER";
	
	public static final String TEAM_LEAD_IND = "1";
	
	public static final String SP_FETCH_TASK 	= "CALL task_fetch_task( :param_id, :param_status,:param_flag)";
	public static final String SP_FETCH_MESSAGE = "CALL task_fetch_message( :param_message_status, :param_message_type,:param_flag)";
	public static final String SP_FETCH_TEAM 	= "CALL task_fetch_team(:param_user_id, :param_team_id, :param_flag)";
	public static final String SP_FETCH_FILTER_TASK = "CALL task_fetch_filter_task( :param_list_task_id, :param_task_title, :param_task_desc, :param_created_by_team_id, :param_created_by_user_id, "
																	+ ":param_my_user_id, :param_assigned_to_team_id, :param_task_status,:param_latest_task_status, :param_created_before, :param_created_after, :param_max_records)";
	public static final String SP_FETCH_TASK_UTIL = "CALL task_util(:param_list_task_id, :param_id, :param_flag)";
	public static final String QUERY_TEAM_MEMBERS = "SELECT * FROM lookup_team_member_role";
	
	public static final  Integer DEFAULT_NUMBER_OF_RECORDS = 100;
	
	public static String getString(Object obj) {
		if(obj!= null)
			return obj.toString();
		return null;
	}
	
	public static Boolean getBoolean(Object obj) {
		if(obj!= null)
			return (Boolean) obj;
		return false;
	}
	
	public static Long getLong(Object obj) {
		if(obj!= null)
			return Long.parseLong(obj.toString());
		return null;
	}
	
	public static Integer getInteger(Object obj) {
		if(obj!= null)
			return Integer.parseInt(obj.toString());
		return null;
	}
	
	public static List<UserTeamDTO> getUserTeamList(Object data){
		if(data == null)
			return null;
		ObjectMapper objectMapper  = new ObjectMapper();
		try {
			return objectMapper.readValue("["+data+"]",new TypeReference<List<UserTeamDTO>>(){});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static LocalDateTime getLocalDateTime(Object data) {
		if (data != null)
			return Timestamp.valueOf(data.toString()).toLocalDateTime();
		else 
			return null;
	}
	
	public static String getStringTime(LocalDateTime time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return time.format(formatter);
	}
	
	public static java.sql.Date getSQLDateForJavaDate(java.util.Date date) {
		if(date == null)
			return null;
	    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	    return sqlDate;
	}
	
	public static java.sql.Date getSQLDateForStringDate(String date) {
		if(date == null)
			return null;
		SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");  
		try {
			Date date1=formatter1.parse(date);
			return new java.sql.Date(date1.getTime());
		}catch(Exception e) {
			return null;
		}
	}
	
	public static String getSQLParam(String val) {
		if(val != null && val.equalsIgnoreCase(""))
			return null;
		else 
			return val;
	}
	
}
