package com.essentia.essentiacatalog.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
	
		private LocalDateTime timestamp;
		private String error;
		private String message;
		List<Violation> violations;
		
		public ErrorResponse() {}
		
		public LocalDateTime getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}

		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public List<Violation> getViolations() {
			return violations;
		}
		public void setViolations(List<Violation> violations) {
			this.violations = violations;
		}

		

}


