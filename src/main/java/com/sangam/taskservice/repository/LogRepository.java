package com.sangam.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangam.taskservice.domain.Log;


@Repository
public interface LogRepository extends JpaRepository<Log, Integer>{
	
}
