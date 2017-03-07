package com.tm.crm.exception.messages;

import org.springframework.http.HttpStatus;

public class TMErrorMessage extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6700281683021544857L;

	private int errorCode;
	private String errorMessage;
	private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	
	public TMErrorMessage() {
		super();
	}
	public TMErrorMessage(int errorCode, String errorMessage, HttpStatus status) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.status = status == null ?  HttpStatus.INTERNAL_SERVER_ERROR : status;
	}

	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
