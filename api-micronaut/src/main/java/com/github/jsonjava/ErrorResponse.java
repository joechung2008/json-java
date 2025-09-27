package com.github.jsonjava;

/**
 * Error response class for API error handling. This class represents a standardized error response
 * format used across the JSON parsing API. It encapsulates error information including a
 * descriptive message and an error code for consistent error communication to API clients.
 *
 * <p>The class follows JavaBean conventions with getter and setter methods for JSON
 * serialization/deserialization.
 *
 * @author json-java
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
public class ErrorResponse {
  /** The error message describing what went wrong */
  private String error;

  /** The numeric error code for programmatic error handling */
  private int code;

  /**
   * Constructs an ErrorResponse with the given error message and code.
   *
   * @param error the error message describing the issue
   * @param code the numeric error code (e.g., HTTP status codes like 400, 404, 500)
   */
  public ErrorResponse(String error, int code) {
    this.error = error;
    this.code = code;
  }

  /**
   * Gets the error message.
   *
   * @return the error message describing what went wrong
   */
  public String getError() {
    return error;
  }

  /**
   * Sets the error message.
   *
   * @param error the error message to set
   */
  public void setError(String error) {
    this.error = error;
  }

  /**
   * Gets the error code.
   *
   * @return the numeric error code
   */
  public int getCode() {
    return code;
  }

  /**
   * Sets the error code.
   *
   * @param code the numeric error code to set
   */
  public void setCode(int code) {
    this.code = code;
  }
}
