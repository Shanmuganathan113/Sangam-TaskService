package com.sangam.taskservice.comparator;

import java.util.Comparator;

import com.sangam.taskservice.dto.TaskDTO;


public class TaskComparator implements Comparator<TaskDTO> {

	@Override
	public int compare(TaskDTO task1, TaskDTO task2) {
		return Comparator.comparing(TaskDTO::getTaskId)
						.thenComparing(TaskDTO::getPTime)
						.compare(task1, task2);
	}
}
