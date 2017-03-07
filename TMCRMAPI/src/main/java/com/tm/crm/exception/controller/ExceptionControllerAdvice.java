package com.tm.crm.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tm.crm.exception.messages.ErrorResponse;
import com.tm.crm.exception.messages.TMErrorMessage;

@ControllerAdvice
public class ExceptionControllerAdvice {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(500);
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(TMErrorMessage.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(TMErrorMessage ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(ex.getErrorCode());
		error.setMessage(ex.getErrorMessage());
		return new ResponseEntity<ErrorResponse>(error, ex.getStatus());
	}
}
