package com.sangam.taskservice.exception;

public class NoTaskExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoTaskExistsException(String message) {
		super(message);
	}
	
}
