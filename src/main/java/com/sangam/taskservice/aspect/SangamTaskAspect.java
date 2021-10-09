package com.sangam.taskservice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SangamTaskAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(SangamTaskAspect.class);

	@AfterThrowing(pointcut = "execution(* com.sangam.taskservice.service..*(..)) && args(methodParams,..)",throwing = "ex")
	public void logException(JoinPoint jp,Object methodParams, Exception ex) {
		try {
			logger.error("---------------------------- " 
			+"\n Method      >>"		+	jp.toString()
			+"\n line number >>"		+	ex.getStackTrace()[0].getLineNumber()
			+"\n Arguments   >>"		+	methodParams
			+"\n Exception   >>"		+	ex.getStackTrace()[0]);
		}	
		catch(Exception e) {
		}
	}
}
