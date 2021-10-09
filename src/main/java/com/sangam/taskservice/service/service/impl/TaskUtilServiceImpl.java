package com.sangam.taskservice.service.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.sangam.taskservice.config.DroolsBeanFactory;
import com.sangam.taskservice.domain.Log;
import com.sangam.taskservice.domain.Task;
import com.sangam.taskservice.dto.DataControlDTO;
import com.sangam.taskservice.dto.FilterTaskDTO;
import com.sangam.taskservice.dto.MyTeamsDTO;
import com.sangam.taskservice.dto.RolePrivilegeDTO;
import com.sangam.taskservice.dto.TaskDTO;
import com.sangam.taskservice.dto.UtilStatics;
import com.sangam.taskservice.dto.UtilStatusManageTasks;
import com.sangam.taskservice.dto.UtilTaskDTO;
import com.sangam.taskservice.dto.UtilTeamDTO;
import com.sangam.taskservice.dto.UtilTeamMemberDTO;
import com.sangam.taskservice.dto.UtilUserDetailDTO;
import com.sangam.taskservice.dto.UtilXLDataDTO;
import com.sangam.taskservice.exception.UnAuthorizedException;
import com.sangam.taskservice.repository.LogRepository;
import com.sangam.taskservice.repository.TaskRepository;
import com.sangam.taskservice.service.TaskService;
import com.sangam.taskservice.service.TaskUtilService;
import com.sangam.taskservice.utils.Constants;

@Service
public class TaskUtilServiceImpl implements TaskUtilService{
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	LogRepository logRepository;
	
	@Autowired
	TaskService taskService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String manageTask(UtilXLDataDTO utilTasks) throws Exception{
			if(utilTasks == null)
				return null;
			
			UtilStatusManageTasks utilStatus = new UtilStatusManageTasks();
			utilStatus.setUtilKey(utilTasks.getUtilKey());
			utilStatus.setIsSuccessInd(false);
			List<String> listTasks = new ArrayList<String>();
			
			UtilStatics utilStatics = new UtilStatics();
			loadTeamsAndRoles(utilStatics,utilTasks.getUtilUserId());
			
			for(UtilTaskDTO utilTask:utilTasks.getUtilValue()) {
				
				if(utilTasks.getUtilKey().equalsIgnoreCase(Constants.XL_PARAM_UTIL_KEY_CREATE_TASK)) // Check  if  is admin
					if(!isValidationSuccess(utilStatics.getListRolePrivilge(),utilTasks.getUtilUserId(), Constants.STATIC_ROLE_PRIVILEGE_CREATE_TASK.toString(),utilTask.getCreatedByTeam()))
						throw new UnAuthorizedException("Unauthorized to create task - Sl No "+utilTask.getSlNo());
				else if(utilTasks.getUtilKey().equalsIgnoreCase("updateTask")) 
					if(!isValidationSuccess(utilStatics.getListRolePrivilge(),utilTasks.getUtilUserId(), utilTask.getLatestStatus(),utilTask.getAssignedTo()))
						throw new UnAuthorizedException("Unauthorized to create update task - Sl No "+utilTask.getSlNo());
				
					Task task = new Task();
					task.setTaskId(utilTask.getTaskId());
					task.setTitle(utilTask.getTaskTitle());
					task.setDescription(utilTask.getTaskDetails());
					task.setEffortHrs(utilTask.getEffortHrs());
					task = taskRepository.save(task);
					
					utilTask.setTaskId(task.getTaskId());
					listTasks.add(task.getTaskId());
				
					if(utilTasks.getUtilKey().equalsIgnoreCase(Constants.XL_PARAM_UTIL_KEY_CREATE_TASK))
						utilCreateOrUpdateLog(true,utilTasks.getUtilFileName(),utilTasks.getUtilUserId(),utilTask);

				if(utilTask.getTaskId() == null)
					throw new UnAuthorizedException("Task id cannot be null  - "+utilTask.getSlNo());
				
				utilCreateOrUpdateLog(false,null,utilTasks.getUtilUserId(),utilTask);
				
				if(utilTasks.getUtilKey().equalsIgnoreCase(Constants.XL_PARAM_UTIL_KEY_UPDATE_TASK))
					listTasks.add(utilTask.getTaskId());
					
				utilStatus.getTaskStatus().add(utilStatus.new TaskStatus(utilTask.getSlNo().toString(), utilTask.getTaskId(), true, "Task created/updated successfully"));
			}
			utilStatus.setIsSuccessInd(true);
			
			if(utilTasks.getUtilKey().equalsIgnoreCase(Constants.XL_PARAM_UTIL_KEY_UPDATE_TASK))
				return fetchTaskReport(listTasks, utilTasks);
			return new Gson().toJson(utilStatus);
	}
	
	public Boolean isValidationSuccess(List<RolePrivilegeDTO> listRolePrivilege, String userId, String roleId, String teamId ){
		Optional<RolePrivilegeDTO> isLead = listRolePrivilege.stream().filter(e ->e.getTeamId().equalsIgnoreCase(teamId) && e.getRoleId().equalsIgnoreCase(roleId) ).findAny();
		if(isLead == null )
			return false;
		if(!isLead.isPresent())
			return false;
		return true;
	}
	
	public void utilCreateOrUpdateLog(Boolean isNewTask,String utilFileName , String byUserId,UtilTaskDTO utilTask) throws Exception{
		Log log = new Log();
		log.setId(utilTask.getTaskId());
		log.setDescription((isNewTask)? "Task created from xl "+ utilFileName +" - Sl No : "+utilTask.getSlNo() : utilTask.getComment());
		log.setByUserId(byUserId);
		log.setToUserId((isNewTask)? utilTask.createdByTeam : utilTask.getAssignedTo());
		log.setTaskStatusId((isNewTask)? 101 : Integer.parseInt(utilTask.getLatestStatus()));
		log.setSegmentInd(1);
		log.setTime(LocalDateTime.now());
		
		logRepository.save(log);
	}
	
	@Override
	public String fetchTaskReport(List<String> listTasks, UtilXLDataDTO utilXLDataDTO) throws Exception {
		if(listTasks != null) {
			utilXLDataDTO.getFilterTaskDTO().setListTaskId(String.join(",",listTasks));
			utilXLDataDTO.setUtilKey(Constants.XL_PARAM_UTIL_KEY_UPDATE_TASK);
		}
		
		if(utilXLDataDTO.getUtilKey().equalsIgnoreCase(Constants.XL_PARAM_UTIL_KEY_MY_TASK)) {
			FilterTaskDTO filter = new FilterTaskDTO();
			filter.setTaskStatus(utilXLDataDTO.getFilterTaskDTO().getTaskStatus());
			filter.setMyUserId(utilXLDataDTO.getUtilUserId());
			filter.setMaxRecords(utilXLDataDTO.getFilterTaskDTO().getMaxRecords());
			utilXLDataDTO.setFilterTaskDTO(filter);
		}
		
		utilXLDataDTO.getFilterTaskDTO().setMyUserId(utilXLDataDTO.getUtilUserId());
		Set<TaskDTO> setTasks = taskService.fetchFilterTask(utilXLDataDTO.getFilterTaskDTO());

		this.applyRules(setTasks);
		if(setTasks != null)
			return this.covertTaskDTOToJson(setTasks);
		return null;
	}
	
	private void applyRules(Set<TaskDTO> setTask) {
		KieSession kieSession = new DroolsBeanFactory().getKieSession();
		for(TaskDTO task : setTask) 
			kieSession.insert(task);
		kieSession.fireAllRules();
		kieSession.dispose();
	}
	
	public String covertTaskDTOToJson(Set<TaskDTO> setTasks) {
		List<UtilTaskDTO> listUtilTasks = new ArrayList<UtilTaskDTO>();
		
		setTasks.forEach( e -> {listUtilTasks.add(new UtilTaskDTO(e));});
		return new Gson().toJson(listUtilTasks);
	}
	
	public String getSlNo(List<Pair<String,String>> listTaskWithSlNo, String taskId) {
		if(listTaskWithSlNo == null)
			return null;
		return listTaskWithSlNo.stream().filter(e-> e.getSecond().equalsIgnoreCase(taskId)).findFirst().get().getFirst();
	}
	
	@Override
	public String refreshStatics(UtilXLDataDTO utilXLDTO) throws Exception {
		
		String userId = utilXLDTO.getUtilUserId();
		
		UtilStatics utilStatics = new UtilStatics();
		loadMyDetails(utilStatics,userId);
		loadTeamsAndRoles(utilStatics,userId);
		loadMyTeams(utilStatics,userId);
		loadDataControl(utilStatics);
		loadTeamMembers(utilStatics);
		loadTeams(utilStatics);
		String jsonString = new Gson().toJson(utilStatics);
		return jsonString;
	}
	
	public void loadMyDetails(UtilStatics utilStatics, String userId) {
		List<Object[]> listTempMyDetail	= taskRepository.fetchUtilStatics(Constants.PARAM_SQL_NULL,userId,Constants.PARAM_SQL_FETCH_MY_DETAILS);
		
		Object[] temoObj = listTempMyDetail.get(0);
		UtilUserDetailDTO userDetail = new UtilUserDetailDTO(	Constants.getString(temoObj[0]),
																Constants.getString(temoObj[1]),
																Constants.getString(temoObj[2]),
																Constants.getString(temoObj[3])
											);
		utilStatics.setUserDetails(userDetail);
	}
	
	public void loadDataControl(UtilStatics utilStatics) {
		List<Object[]> listTempDataControl 	= taskRepository.fetchUtilStatics(utilStatics.getPTeamList(),Constants.PARAM_SQL_NULL,Constants.PARAM_SQL_FETCH_DATA_CONTROL);
		List<DataControlDTO> listDataControl = new ArrayList<DataControlDTO>();
		
		listTempDataControl.forEach( e -> { listDataControl.add(new DataControlDTO(	Constants.getString(e[0]),
																					Constants.getString(e[1]),
																					Constants.getString(e[2]),
																					Constants.getString(e[3])));
											});
		utilStatics.setListDataControl(listDataControl);
	}
	
	public void loadTeamMembers(UtilStatics utilStatics) {
		List<Object[]> listTempTeamMembers 	= taskRepository.fetchUtilStatics(utilStatics.getPTeamList(),Constants.PARAM_SQL_NULL,Constants.PARAM_SQL_FETCH_TEAM_MEMBERS);
		
		List<UtilTeamMemberDTO> listTeamMembers = new ArrayList<UtilTeamMemberDTO>();
		listTempTeamMembers.forEach( e -> { listTeamMembers.add	( new UtilTeamMemberDTO(	Constants.getString(e[0]),
																							Constants.getString(e[1]),
																							Constants.getString(e[2])+getRoleInitial(Constants.getString(e[3])),
																							Constants.getString(e[3])));
											});
		utilStatics.setStatusMessage( utilStatics.getStatusMessage().concat("Team members have been updated"));
		utilStatics.setListTeamMembers(listTeamMembers);
	}
	
	public void loadMyTeams(UtilStatics utilStatics, String userId) {
		List<Object[]> listTempMyTeams 	= taskRepository.fetchUtilStatics(Constants.PARAM_SQL_NULL,userId,Constants.PARAM_SQL_FETCH_MY_TEAMS);
		
		List<MyTeamsDTO> listMyTeams = new ArrayList<MyTeamsDTO>();
		listTempMyTeams.forEach( e -> { listMyTeams.add( new MyTeamsDTO(	Constants.getString(e[0]),
																			Constants.getString(e[1]),
																			Constants.getString(e[2]),
																			Constants.getString(e[3])));
											});
		utilStatics.setStatusMessage( utilStatics.getStatusMessage().concat("My Teams have been updated"));
		utilStatics.setListMyTeams(listMyTeams);
	}
	
	public String getRoleInitial(String roleName) {
		if(roleName.equalsIgnoreCase("TEAM_LEAD"))
			return "(Lead)";
		if(roleName.equalsIgnoreCase("TEAM_MEMBER"))
			return "(Member)";

		return "(Global Admin)";
			
	}
	public void loadTeams(UtilStatics utilStatics) {
		List<Object[]> listTempTeams = taskRepository.fetchUtilStatics(Constants.PARAM_SQL_NULL,Constants.PARAM_SQL_NULL,Constants.PARAM_SQL_FETCH_LIST_TEAMS);
		
		List<UtilTeamDTO> listTeams = new ArrayList<UtilTeamDTO>();
		listTempTeams.forEach( e-> { listTeams.add( new UtilTeamDTO(Constants.getString(e[0]),
																	Constants.getString(e[1]))); 
									});
		utilStatics.setStatusMessage( utilStatics.getStatusMessage().concat(" ; ").concat("Teams have been updated"));
		utilStatics.setListTeams(listTeams);
	}
	
	public void loadTeamsAndRoles(UtilStatics utilStatics,String userId ) {
		List<Object[]> listTempRolePrivilege 	= taskRepository.fetchUtilStatics(Constants.PARAM_SQL_NULL,userId,Constants.PARAM_SQL_FETCH_STATIC_ROLES);
		
		List<RolePrivilegeDTO> listRolePrivilege = new ArrayList<RolePrivilegeDTO>();
		listTempRolePrivilege.forEach( e -> { listRolePrivilege.add	( new RolePrivilegeDTO(	Constants.getString(e[0]),
																							Constants.getString(e[1]),
																							Constants.getString(e[2]),
																							Constants.getString(e[3])));
											});
		utilStatics.setPTeamList(listRolePrivilege.stream().map(e -> e.getTeamId()).distinct().collect(Collectors.joining(",")));
		utilStatics.setStatusMessage( utilStatics.getStatusMessage().concat(" ; ").concat("Roles have been updated"));
		utilStatics.setListRolePrivilge(listRolePrivilege);
	}
	
	public Optional<DataControlDTO> findDataControl(List<DataControlDTO> listDataControl,String label, String key){
		if(listDataControl == null)
			return null;
		Optional<DataControlDTO> dataControl= listDataControl.stream()
				.filter( e -> e.getLabel().equalsIgnoreCase(label) && e.getLabel().equalsIgnoreCase(key))
				.findFirst();
		return dataControl;
	}
	
}
