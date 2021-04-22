package com.pizza.model.base;

public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorDesc;

	public CustomException(String errorCode, String errorDesc) {
		super();
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	@Override
	public String getMessage() {
		return errorDesc;
	}
}
