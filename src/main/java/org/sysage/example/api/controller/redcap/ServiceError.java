package org.sysage.example.api.controller.redcap;

public class ServiceError {
	
	private String errorMessage;

	public ServiceError() {
	}

	public ServiceError(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "ServiceError [errorMessage=" + errorMessage + "]";
	}
}
