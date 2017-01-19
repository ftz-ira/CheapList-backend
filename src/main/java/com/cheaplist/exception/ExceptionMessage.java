package com.cheaplist.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class ExceptionMessage extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	private BindingResult result;

	public String getErrorMessage() {
		result.getAllErrors();
		int code = 0;
		for (ObjectError e : result.getAllErrors()) {
			errorMessage = errorMessage + "\n error n" + code + " : " + e.getCode();
			code++;
		}
		return errorMessage;
	}

	public ExceptionMessage(String errorMessage) {

		super(errorMessage);

		this.errorMessage = errorMessage;

	}

	public ExceptionMessage(String errorMessage, BindingResult result) {
		super(errorMessage);
		this.errorMessage = errorMessage;
		this.result = result;
	}

	public ExceptionMessage() {
		super();
	}

}
