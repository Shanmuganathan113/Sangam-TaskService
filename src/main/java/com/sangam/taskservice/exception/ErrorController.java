

package com.sangam.taskservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(NoTaskExistsException.class)
	public ResponseEntity<String> handleNoTaskExistsException(NoTaskExistsException ex) {
//	   ErrorResponse responseMsg = new ErrorResponse(404,ex.getMessage());
//	   return responseMsg;
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				   .body("Exception occured");
	}
}
