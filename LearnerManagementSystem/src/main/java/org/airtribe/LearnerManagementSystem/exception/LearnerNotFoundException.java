package org.airtribe.LearnerManagementSystem.exception;

// extends Exception -> Checked exception -> must be handled or declared to be thrown
// try catch or add throws clause

// extends RuntimeException -> Unchecked exception -> does not need to be handled or declared to be thrown

public class LearnerNotFoundException extends Exception {
  public LearnerNotFoundException(String message) {
    super(message);
  }
}
