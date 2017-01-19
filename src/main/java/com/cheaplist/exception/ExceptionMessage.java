package com.cheaplist.exception;

public class ExceptionMessage extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public ExceptionMessage(String errorMessage) {

		super(errorMessage);

		this.errorMessage = errorMessage;

	}

	public ExceptionMessage() {
		super();
	}

}
