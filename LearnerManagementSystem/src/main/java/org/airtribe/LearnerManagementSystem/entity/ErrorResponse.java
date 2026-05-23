package org.airtribe.LearnerManagementSystem.entity;

import org.springframework.http.HttpStatusCode;


public class ErrorResponse {

  private HttpStatusCode statusCode;

  private String message;

  private Long timestamp;

  private String stackTrace;

  public ErrorResponse(HttpStatusCode statusCode, String message, Long timestamp, String stackTrace) {
    this.statusCode = statusCode;
    this.message = message;
    this.timestamp = timestamp;
    this.stackTrace = stackTrace;
  }

  public HttpStatusCode getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(HttpStatusCode statusCode) {
    this.statusCode = statusCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public void setStackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
  }
}
