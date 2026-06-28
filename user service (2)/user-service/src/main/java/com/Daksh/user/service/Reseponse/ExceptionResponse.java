
package com.Daksh.user.service.Reseponse;

import java.time.LocalDateTime;

public class ExceptionResponse {

	private String message;
	private String details;
	private LocalDateTime timestamp;

	public ExceptionResponse(String message, String details, LocalDateTime timestamp) {
		this.message = message;
		this.details = details;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}
