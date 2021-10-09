package com.sangam.taskservice.service.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sangam.taskservice.comparator.TaskComparator;
import com.sangam.taskservice.config.DroolsBeanFactory;
import com.sangam.taskservice.domain.Log;
import com.sangam.taskservice.domain.Task;
import com.sangam.taskservice.dto.FilterTaskDTO;
import com.sangam.taskservice.dto.LogDTO;
import com.sangam.taskservice.dto.MyTeamsDTO;
import com.sangam.taskservice.dto.TaskDTO;
import com.sangam.taskservice.dto.TeamDTO;
import com.sangam.taskservice.dto.TeamUserRoleDTO;
import com.sangam.taskservice.exception.NoTaskExistsException;
import com.sangam.taskservice.repository.LogRepository;
import com.sangam.taskservice.repository.TaskRepository;
import com.sangam.taskservice.service.TaskService;
import com.sangam.taskservice.utils.Constants;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	LogRepository logRepository;
	
	@Override
	public Set<TaskDTO> fetchTask(Map<String,String> listParams) throws Exception {
		String id 			= listParams.get(Constants.REQUEST_PARAMS_ID);
		String myUserId		= listParams.get(Constants.REQUEST_PARAMS_MY_USER_ID);
		String taskStatus 	= listParams.get(Constants.REQUEST_PARAMS_STATUS);
		String flag 		= listParams.get(Constants.REQUEST_PARAMS_FLAG);
		
		List<Object[]> tempListTask = taskRepository.fetchTasks(id, 
																Constants.getInteger(taskStatus), 
																Constants.getInteger(flag));

		if(tempListTask.isEmpty())
			throw new NoTaskExistsException("Requested task does not exist");
		
		Set<TaskDTO> tempSetTask = new HashSet<TaskDTO>();
		Set<TaskDTO> setTask = new TreeSet<TaskDTO>(new TaskComparator());
		
		try {
			tempListTask.forEach( e -> { TaskDTO t = new TaskDTO(	Constants.getString(e[0]),
																	Constants.getString(e[1]),
																	Constants.getString(e[2]),
																	Constants.getString(e[12]),
																	Constants.getInteger(e[13]));
											tempSetTask.add(t);
										});
			
			tempListTask.forEach( e -> { LogDTO tl = new LogDTO(	Constants.getString(e[3]),
																	Constants.getString(e[5]),
																	Constants.getInteger(e[6]),
																	Constants.getString(e[7]),
																	Constants.getString(e[0]),
																	Constants.getLocalDateTime(e[11]));
										tl.process(Constants.getUserTeamList(e[10]));
										tempSetTask.stream().forEach( l -> 	{ // Populate log to corresponding task
																				if( l.getTaskId().equalsIgnoreCase(tl.getId()))
																					l.getLog().add(tl);
																			}
																	);
			});
			List<MyTeamsDTO> listMyTeams = this.loadMyTeams(myUserId);
			tempSetTask.forEach( t ->  t.process(myUserId, listMyTeams));
			
//			if( flag.equals("3") && tempSetTask != null) // Queue tasks
//				tempSetTask.removeIf( e -> e.getPAassignedTo() == null || e.getPAassignedTo().getUserId() == null ||( e.getPAassignedTo().getUserId().longValue() != Constants.getLong(id).longValue()));
			setTask.addAll(tempSetTask);
			this.applyRules(setTask);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return setTask;
	}
	
	@Override
	public TaskDTO updateTask(TaskDTO taskDTO) throws Exception{
		Boolean createTask = (taskDTO.getTaskId() == null) ? true : false;
		Task task = new Task();
		task.setTaskId(taskDTO.getTaskId());
		task.setTitle(taskDTO.getTitle());
		task.setDescription(taskDTO.getDescription());
		task.setEffortHrs(taskDTO.getEffortHrs() == null ? 0 :Integer.parseInt(taskDTO.getEffortHrs()));
		task = taskRepository.save(task);
		
		LogDTO taskObj = taskDTO.getInputLog().iterator().next();
		if(createTask) // if create task then create new log
			taskObj.setLogId(null);
		taskObj.setId(task.getTaskId());	
		this.updateTaskLog(taskObj);
		
		taskDTO.setTaskId(task.getTaskId());
		FilterTaskDTO filter = new FilterTaskDTO();
		filter.setListTaskId(task.getTaskId());
		this.fetchFilterTask(filter);
		return this.fetchFilterTask(filter).iterator().next();
	}

	@Override
	public LogDTO updateTaskLog(LogDTO logObj) {
		Log log = new Log();
		log.setLogId(logObj.getLogId());
		log.setId(logObj.getId());
		log.setDescription(logObj.getLogDescription());
		log.setByUserId(logObj.getPByUser().getUserId());
		log.setToUserId(logObj.getPToUser().getUserId());
		log.setTaskStatusId(logObj.getTaskStatusId());
		log.setSegmentInd(1);
		log.setTime(LocalDateTime.now());
		
		log = logRepository.save(log);
		logObj.setLogId(log.getLogId());
		return logObj;
	}

	@Override
	public Set<TeamDTO> fetchTeam(Map<String, String> listParams) throws Exception {
		String userId 		= listParams.get(Constants.REQUEST_PARAMS_TEAM_USER_ID);
		String teamId 		= listParams.get(Constants.REQUEST_PARAMS_TEAM_ID);
		String flag 		= listParams.get(Constants.REQUEST_PARAMS_TEAM_FLAG);
		
		List<Object[]> tempListTeam = taskRepository.fetchTeams(userId, 
																teamId, 
																Constants.getInteger(flag));
		Set<TeamDTO> tempSetTeam = new HashSet<TeamDTO>(); 
		Set<TeamDTO> setTeam = new TreeSet<TeamDTO>(); 
		
		try {
			tempListTeam.forEach( e -> { 	TeamDTO t = new TeamDTO(	Constants.getString(e[0]),
																		Constants.getString(e[1]),
																		Constants.getString(e[2]),
																		(flag.equals(Constants.REQUEST_FLAG_TEAM_DETAILED_INFO) ? Constants.getString(e[3]) : ""));
											tempSetTeam.add(t);
										});
			
			tempListTeam.forEach( e -> { 	TeamUserRoleDTO tur = new TeamUserRoleDTO(	Constants.getString(e[0]),
																						Constants.getString(e[4]),
																						Constants.getString(e[5]),
																						Constants.getString(e[6]),
																						Constants.getInteger(e[7]),
																						Constants.getString(e[8]));

											tempSetTeam.stream().forEach( l -> { if( l.getTeamId().equalsIgnoreCase(tur.getTeamId()))
																						l.getListTeamUser().add(tur);
																				});
										});
			if(flag.equals(Constants.REQUEST_FLAG_USER_TEAM_LIST))
				tempSetTeam.forEach(e -> { e.process(Constants.getString(userId));});
			setTeam.addAll(tempSetTeam);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return setTeam;
	}
	
	@Override
	public Set<TaskDTO> fetchFilterTask(FilterTaskDTO filterTaskDTO) throws Exception {
		
		List<Object[]> tempListTask = taskRepository.fetchFilterTasks(	Constants.getSQLParam(filterTaskDTO.getListTaskId()),
																		Constants.getSQLParam(filterTaskDTO.getTaskTitle()),
																		Constants.getSQLParam(filterTaskDTO.getTaskDesc()),
																		Constants.getSQLParam(filterTaskDTO.getCreatedByTeamId()),
																		Constants.getSQLParam(filterTaskDTO.getCreatedByUserId()),
																		Constants.getSQLParam(filterTaskDTO.getMyUserId()),
																		Constants.getSQLParam(filterTaskDTO.getAssignedToTeamId()),
																		Constants.getSQLParam(filterTaskDTO.getTaskStatus()),
																		Constants.getSQLParam(filterTaskDTO.getLatestTaskStatus()),
																		Constants.getSQLDateForStringDate(filterTaskDTO.getCreatedBefore()),
																		Constants.getSQLDateForStringDate(filterTaskDTO.getCreatedAfter()),
																		(filterTaskDTO.getMaxRecords()!=null && filterTaskDTO.getMaxRecords().intValue() == 0)? Constants.DEFAULT_NUMBER_OF_RECORDS : filterTaskDTO.getMaxRecords());
		
		Set<TaskDTO> tempSetTask = new HashSet<TaskDTO>(); 
		Set<TaskDTO> setTask = new TreeSet<TaskDTO>(new TaskComparator()); 
		List<MyTeamsDTO> listMyTeams = loadMyTeams(filterTaskDTO.getMyUserId());
		
		try {
			tempListTask.forEach( e -> { 
				tempSetTask.add(new TaskDTO(	Constants.getString(e[0]),
																	Constants.getString(e[1]),
																	Constants.getString(e[2]),
																	Constants.getString(e[12]),
																	Constants.getInteger(e[13])));
										});
			
			tempListTask.forEach( e -> { LogDTO tl = new LogDTO(	Constants.getString(e[3]),
																	Constants.getString(e[5]),
																	Constants.getInteger(e[6]),
																	Constants.getString(e[7]),
																	Constants.getString(e[0]),
																	Constants.getLocalDateTime(e[11]));
										tl.process(Constants.getUserTeamList(e[10]));
										tempSetTask.stream().forEach( l -> 	{ // Populate log to corresponding task
																				if( l.getTaskId().equalsIgnoreCase(tl.getId()))
																					l.getLog().add(tl);
																			}
																	);
										});
			
			tempSetTask.forEach( t ->  t.process(filterTaskDTO.getMyUserId(),listMyTeams));
			setTask.addAll(tempSetTask);
			this.applyRules(setTask);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return setTask;
	}
	
	private void applyRules(Set<TaskDTO> setTask) {
		KieSession kieSession = new DroolsBeanFactory().getKieSession();
		for(TaskDTO task : setTask) 
			kieSession.insert(task);
		kieSession.fireAllRules();
		kieSession.dispose();
	}

	public List<MyTeamsDTO> loadMyTeams(String userId) {
		List<Object[]> listTempMyTeams 	= taskRepository.fetchUtilStatics(Constants.PARAM_SQL_NULL,userId,Constants.PARAM_SQL_FETCH_MY_TEAMS);
		
		List<MyTeamsDTO> listMyTeams = new ArrayList<MyTeamsDTO>();
		listTempMyTeams.forEach( e -> { listMyTeams.add( new MyTeamsDTO(	Constants.getString(e[0]),
																			Constants.getString(e[1]),
																			Constants.getString(e[2]),
																			Constants.getString(e[3])));
									});
		return listMyTeams;
	}


}
