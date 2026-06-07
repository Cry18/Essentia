package com.essentia.essentiaadministration.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Standard error response returned by all exception handlers.
 *
 * Fields:
 *  status     - HTTP status code (e.g. 404, 403)
 *  timestamp  - when the error occurred
 *  error      - short error category
 *  message    - human-readable description
 *  path       - request path that triggered the error
 *  violations - populated only for validation errors (400)
 */
public class ErrorResponse {

    private int status;
    private LocalDateTime timestamp;
    private String error;
    private String message;
    private String path;
    private List<Violation> violations;

    public ErrorResponse() {}

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public List<Violation> getViolations() { return violations; }
    public void setViolations(List<Violation> violations) { this.violations = violations; }
}
