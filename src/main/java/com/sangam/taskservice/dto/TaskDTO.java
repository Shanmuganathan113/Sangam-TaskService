package com.sangam.taskservice.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.kie.api.runtime.KieSession;
import org.springframework.data.util.Pair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sangam.taskservice.comparator.LogComparator;
import com.sangam.taskservice.config.DroolsBeanFactory;
import com.sangam.taskservice.utils.Constants;

import lombok.Data;

@Data
public class TaskDTO {
	private String taskId;
	private String title; 
	private String description; 
	private String effortHrs;
	private Integer slNo;
	private TreeSet<LogDTO> 	log = new TreeSet<LogDTO>(new LogComparator());
	private ArrayList<LogDTO> 	inputLog = new ArrayList<LogDTO>(); // Used to read values from request json 
	
	private UserTeamDTO pCreatedByUser; 	// Task created by this user
	private UserTeamDTO pCreatedByTeam; 	// Task created by this team
	private UserTeamDTO pQueuedToTeam; 		// Task added to this team's queue
	private UserTeamDTO pAssignedToUser; 	// Task assigned to this team
	private LogDTO 		pLatestLog;			// Current status of the task
	private LocalDateTime pTime; 			// Taken from latest log and used for sorting
	
	public Boolean pIsClosed 		= false;
	public Boolean pIsAssignedToMe 	= false;
	public Boolean pIsCreatedByMe 	= false;
	public Boolean pIsTeamTask		= false;
	public Boolean pIsQueueTask		= false;
	private Map<String,String>	listMyTaskPrivilege = new TreeMap<String, String>();
	
	@JsonIgnore
	private String					myUserId;
	@JsonIgnore
	private List<MyTeamsDTO> 		listMyTeams;
	@JsonIgnore
	private Set<String> 			listRuleResult 	= new HashSet<String>();
	@JsonIgnore
	private Map<Integer,List<Pair<String,String>>> 	listPrivilegeRules = new HashMap<Integer, List<Pair<String,String>>>();
	
	@JsonIgnore
	private String isTeamLead = "";
	@JsonIgnore
	private String isQLead = "";
	@JsonIgnore
	private String isTeamTask = "";
	@JsonIgnore
	private String isQTask = "";
	@JsonIgnore
	private String isTTaskCreatedByMe = "";
	@JsonIgnore
	private String isTTaskAssignedToMe = "";
	@JsonIgnore
	private String isQTaskAssignedToMe = "";
	@JsonIgnore
	private String isTTaskCompleted = "";
	@JsonIgnore
	private String isQTaskCompleted = "";
	@JsonIgnore
	private String isTTaskClosed = "";
	@JsonIgnore
	private String isQTaskClosed = "";
	@JsonIgnore
	private String isTTaskStartedInProgress = "";
	@JsonIgnore
	private String isQTaskStartedInProgress = "";
	@JsonIgnore
	private String isTTaskRejected = "";
	@JsonIgnore
	private String isQTaskRejected = "";
	@JsonIgnore
	private String isTTaskAssigned = "";
	@JsonIgnore
	private String isQTaskAssigned = "";

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TaskDTO))
			return false;
		TaskDTO other = (TaskDTO) obj;
		return Objects.equals(taskId, other.taskId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, taskId, title);
	}

	public TaskDTO(String taskId, String title, String description,String effortHrs, Integer slNo) {
		this.taskId = taskId;
		this.title = title;
		this.description = description;
		this.effortHrs = effortHrs;
		this.slNo = slNo;
//		this.pTime = LocalDateTime.now();
	}

	public void process(String myUserId,List<MyTeamsDTO> listMyTeams) {
		this.log.forEach( e -> {
									if(this.pTime == null || this.pTime.isBefore(e.getTime())) {
										this.pLatestLog = e;
										this.pTime = this.pLatestLog.getTime();
									}
									
									if( e.getTaskStatusId().equals(Constants.STATIC_ROLE_PRIVILEGE_CREATE_TASK)) { 
										this.pCreatedByUser = e.getPByUser();
										this.pCreatedByTeam = e.getPToUser();
										if(this.pCreatedByUser.getUserId().equalsIgnoreCase(myUserId))
											this.pIsCreatedByMe = true;
									}
									
									if( e.getTaskStatusId().equals(Constants.STATIC_ROLE_PRIVILEGE_QUEUE))
										this.pQueuedToTeam = e.getPToUser();
									
									if( e.getTaskStatusId().equals(Constants.STATIC_ROLE_PRIVILEGE_ASSIGNED_TO) || e.getTaskStatusId().equals(Constants.STATIC_ROLE_PRIVILEGE_T_REASSIGN)) {
										this.pAssignedToUser = e.getPToUser();
										if(this.pAssignedToUser.getUserId().equalsIgnoreCase(myUserId))
											this.pIsAssignedToMe = true;
									}
									
									if( e.getTaskStatusId().equals(Constants.STATIC_ROLE_PRIVILEGE_CLOSED))
										this.pIsClosed = true;
								});
				this.myUserId 		= myUserId;
				this.listMyTeams 	= listMyTeams;
				this.loadRules();
				this.processRules();
		}
	
	public void loadRules() {
		// 0 - ignore, 1 - true, 2-false
		// listPrivilegeRules have general rules to follow while adding elements to listRuleResult. This will be reconciled with listStaticRolePrivilege(DB values).
		// Ex: Constants.STATIC_ROLE_PRIVILEGE_T_ASSIGN - Task assigned - Generally, if task is assigned, listRuleResult should only have isTaskAssigned and not isTaskRejected 
		// The key value "0 0000 0201 0000 0000 0000 0000" says that we have to add isTaskAssigned and remove isTaskRejected from listRuleResult
		// Once we get 102-STATIC_ROLE_PRIVILEGE_T_ASSIGN from log, then follow the rule and add isTaskAssigned and remove isTaskRejected from listRuleResult
		
		this.listPrivilegeRules.put(Constants.STATIC_ROLE_PRIVILEGE_T_CREATE_TASK, 			fetchRules("0 0012 0102 0000 0000 0000 0000"));
		this.listPrivilegeRules.put(Constants.STATIC_ROLE_PRIVILEGE_T_ASSIGN, 				fetchRules("0 0000 0201 0000 0000 0000 0000"));
		this.listPrivilegeRules.put(Constants.STATIC_ROLE_PRIVILEGE_Q_ASSIGN,				fetchRules("0 0000 0020 1000 0000 0000 0000"));
		this.listPrivilegeRules.put(Constants.STATIC_ROLE_PRIVILEGE_T_REASSIGN, 			fetchRules("0 0000 0201 0000 0000 0000 0000"));
		this.listPrivilegeRules.put(Constants.STATIC_ROLE_PRIVILEGE_Q_REASSIGN,				fetchRules("0 0000 0020 1000 0000 0000 0000"));
		this.listPrivilegeRules.put(Constants.STATIC_ROLE_PRIVILEGE_T_COMPLETE,				fetchRules("0 0000 0102 0001 0002 0000 0000"));
		this.listPrivilegeRules.put(Constants.STATIC_ROLE_PRIVILEGE_Q_COMPLETE,				fetchRules("0 0000 0010 2000 1000 2000 0000"));
		this.listPrivilegeRules.put(Constants.STATIC_ROLE_PRIVILEGE_T_CLOSED,				fetchRules("0 0000 0000 0000 0100 0000 0000"));
		this.listPrivilegeRules.put(Constants.STATIC_ROLE_PRIVILEGE_Q_CLOSED,				fetchRules("0 0012 0000 0000 0010 0000 0000"));
		this.listPrivilegeRules.put(Constants.STATIC_ROLE_PRIVILEGE_T_STARTED_IN_PROGRESS,	fetchRules("0 0000 0000 0000 0001 0000 0000"));
		this.listPrivilegeRules.put(Constants.STATIC_ROLE_PRIVILEGE_Q_STARTED_IN_PROGRESS,	fetchRules("0 0000 0000 0000 0000 1000 0000"));
		this.listPrivilegeRules.put(Constants.STATIC_ROLE_PRIVILEGE_T_REJECT,				fetchRules("0 0000 0102 0000 0000 0000 0000"));
		this.listPrivilegeRules.put(Constants.STATIC_ROLE_PRIVILEGE_Q_REJECT,				fetchRules("0 0000 0010 2000 0000 0000 0000"));
		this.listPrivilegeRules.put(Constants.STATIC_ROLE_PRIVILEGE_Q_QUEUE,				fetchRules("0 0021 0010 2000 0000 0000 0000"));
	}
	
	/* Based on the key value rules will be populated
	 * getKey method gives ruleKey for specific positions in ind
	 * First position is blank
	 * ind is of 20 chars length
	 * 
	 * 1 - Adds key to rules
	 * 2 - Removes key from rules
	 * 0 - Ignores key
	 * */
	public List<Pair<String, String>> fetchRules(String ind){
		List<Pair<String, String>> listRules = new ArrayList<Pair<String,String>>();
		for(int i=0; i< 24;i++) 
			if(!(ind.replace(" ", "").charAt(i) =='0')) // ignore if 0
				listRules.add(Pair.of(getKey(i), ((ind.replace(" ", "").charAt(i)) == '1')? Constants.STATIC_RULE_ADD : Constants.STATIC_RULE_REMOVE));
		return listRules;
	}
	
	public String getKey(Integer val) {
		switch (val) {
			case 1: 	return Constants.STATIC_RULE_KEY_isTeamLead;
			case 2: 	return Constants.STATIC_RULE_KEY_isQueueLead;
			case 3: 	return Constants.STATIC_RULE_KEY_isTeamTask;
			case 4: 	return Constants.STATIC_RULE_KEY_isQTask;
			
			case 5: 	return Constants.STATIC_RULE_KEY_isTTaskCreatedByMe;
			case 6: 	return Constants.STATIC_RULE_KEY_isTTaskRejected;
			case 7: 	return Constants.STATIC_RULE_KEY_isQTaskRejected;
			case 8: 	return Constants.STATIC_RULE_KEY_isTTaskAssigned;
			
			case 9: 	return Constants.STATIC_RULE_KEY_isQTaskAssigned;
			case 10: 	return Constants.STATIC_RULE_KEY_isTTaskAssignedToMe;
			case 11:	return Constants.STATIC_RULE_KEY_isQTaskAssignedToMe;
			case 12: 	return Constants.STATIC_RULE_KEY_isTTaskCompleted;
			
			case 13: 	return Constants.STATIC_RULE_KEY_isQTaskCompleted;
			case 14: 	return Constants.STATIC_RULE_KEY_isTTaskClosed;
			case 15: 	return Constants.STATIC_RULE_KEY_isQTaskClosed;
			case 16: 	return Constants.STATIC_RULE_KEY_isTTaskStartedInProgress;
			
			case 17: 	return Constants.STATIC_RULE_KEY_isQTaskStartedInProgress;
			default:
				return "";
		}
	}
	
	public void processRules() {
		Optional<MyTeamsDTO> createdBy = null;
		Optional<MyTeamsDTO> queuedTo = null;
		
		if(listMyTeams != null) {
			createdBy	= this.listMyTeams.stream().filter(e -> e.getTeamId().equalsIgnoreCase(this.pCreatedByTeam.getTeamId())).findAny();
			if(this.pQueuedToTeam != null)
				queuedTo = this.listMyTeams.stream().filter(e -> e.getTeamId().equalsIgnoreCase(this.pQueuedToTeam.getTeamId())).findAny();
		}
		
		if( createdBy != null && createdBy.isPresent()) 
			if(createdBy.get().getRoleId().equalsIgnoreCase(Constants.TEAM_LEAD_IND))
				populateRuleResult(Constants.STATIC_RULE_KEY_isTeamLead,Constants.STATIC_RULE_ADD);
		
		if(queuedTo!= null && queuedTo.isPresent())
			if(queuedTo.get().getRoleId().equalsIgnoreCase(Constants.TEAM_LEAD_IND))
				populateRuleResult(Constants.STATIC_RULE_KEY_isQueueLead,Constants.STATIC_RULE_ADD);
		
		for (LogDTO logDTO : ((TreeSet<LogDTO>) this.log).descendingSet()) {
			for(Integer id  :this.listPrivilegeRules.keySet()) {
				if(id.intValue() == logDTO.getTaskStatusId())
					for(Pair<String,String> rule: this.listPrivilegeRules.get(id))
						populateRuleResult(rule.getFirst(),rule.getSecond());
				
				if(logDTO.getPToUser().getUserId().equalsIgnoreCase(this.myUserId)) {
					if(	logDTO.getTaskStatusId().equals(Constants.STATIC_ROLE_PRIVILEGE_T_CREATE_TASK))
						populateRuleResult(Constants.STATIC_RULE_KEY_isTTaskCreatedByMe,	Constants.STATIC_RULE_ADD);
				
					if(	logDTO.getTaskStatusId().equals(Constants.STATIC_ROLE_PRIVILEGE_T_ASSIGN) || logDTO.getTaskStatusId().equals(Constants.STATIC_ROLE_PRIVILEGE_T_REASSIGN)) 
						populateRuleResult(Constants.STATIC_RULE_KEY_isTTaskAssignedToMe,	Constants.STATIC_RULE_ADD);
				
					if(	logDTO.getTaskStatusId().equals(Constants.STATIC_ROLE_PRIVILEGE_Q_ASSIGN) || logDTO.getTaskStatusId().equals(Constants.STATIC_ROLE_PRIVILEGE_Q_REASSIGN ))
						populateRuleResult(Constants.STATIC_RULE_KEY_isQTaskAssignedToMe,	Constants.STATIC_RULE_ADD);
				}
			}
		}
		this.loadResultToMember();
	}
	
	public void populateRuleResult(String key, String ind) {
		if(ind.equalsIgnoreCase(Constants.STATIC_RULE_REMOVE))
			this.listRuleResult.removeIf( e -> e.equalsIgnoreCase(key));
		if(ind.equalsIgnoreCase(Constants.STATIC_RULE_ADD))
			this.listRuleResult.add(key);
	}
	
	public String getRuleResult(String key) {
		for(String tKey:this.listRuleResult) 
			if(key.equalsIgnoreCase(tKey)) 
				return "TRUE";
		return "FALSE";
	}
	
	public void loadResultToMember() {
		this.isTeamLead 				= getRuleResult("isTeamLead");
		this.isQLead 					= getRuleResult("isQLead");
		this.isTeamTask 				= getRuleResult("isTeamTask");
		this.isQTask 					= getRuleResult("isQTask");
		this.isTTaskCreatedByMe 		= getRuleResult("isTTaskCreatedByMe");
		this.isTTaskAssignedToMe 		= getRuleResult("isTTaskAssignedToMe");
		this.isQTaskAssignedToMe 		= getRuleResult("isQTaskAssignedToMe");
		this.isTTaskCompleted 			= getRuleResult("isTTaskCompleted");
		this.isQTaskCompleted 			= getRuleResult("isQTaskCompleted");
		this.isTTaskClosed 				= getRuleResult("isTTaskClosed");
		this.isQTaskClosed 				= getRuleResult("isQTaskClosed");
		this.isTTaskStartedInProgress 	= getRuleResult("isTTaskStartedInProgress");
		this.isQTaskStartedInProgress 	= getRuleResult("isQTaskStartedInProgress");
		this.isTTaskRejected 			= getRuleResult("isTTaskRejected");
		this.isQTaskRejected 			= getRuleResult("isQTaskRejected");
		this.isTTaskAssigned 			= getRuleResult("isTTaskAssigned");
		this.isQTaskAssigned 			= getRuleResult("isQTaskAssigned");

	}
	
	// Drools calls this method to reconcile privileges
	public void addPrivilegeToResult(String key, String value) {
		this.listMyTaskPrivilege.put(key, value);
		return;
	}
	
	
	
}
