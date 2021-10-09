package com.sangam.taskservice.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sangam.taskservice.domain.Task;
import com.sangam.taskservice.utils.Constants;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
	
	@Query(value = Constants.SP_FETCH_TASK,nativeQuery=true)
	List<Object[]> fetchTasks(	@Param("param_id") String id,
								@Param("param_status") Integer status,
								@Param("param_flag") Integer flag );

	@Query(value = Constants.SP_FETCH_TEAM,nativeQuery=true)
	List<Object[]> fetchTeams(	@Param("param_user_id") String userId,
								@Param("param_team_id") String teamId,
								@Param("param_flag") Integer flag );
	
	@Query(value = Constants.SP_FETCH_FILTER_TASK,nativeQuery=true)
	List<Object[]> fetchFilterTasks(		@Param("param_list_task_id") 		String 	listTaskId,
											@Param("param_task_title") 			String 	taskTitle,
											@Param("param_task_desc") 			String 	taskDesc,
											@Param("param_created_by_team_id") 	String 	createdByTeamId,
											@Param("param_created_by_user_id") 	String 	createdByUseId,
											@Param("param_my_user_id")			String	myUserId,
											@Param("param_assigned_to_team_id") String	assignedToTeamId,
											@Param("param_task_status") 		String 	taskStatus,
											@Param("param_latest_task_status")	String 	latestTaskStatus,
											@Param("param_created_before") 		Date 	createdBefore,
											@Param("param_created_after") 		Date 	createdAfter,
											@Param("param_max_records") 		Integer maxRecord);
	
	@Query(value = Constants.SP_FETCH_TASK_UTIL,nativeQuery=true)
	List<Object[]> fetchUtilStatics(@Param("param_list_task_id") String userId,
									@Param("param_id") 		String teamId,
									@Param("param_flag") 	String flag );

	
	

}
