package com.github.jsonjava;

import org.springframework.http.HttpStatus;

/** Represents an error response for the API. Contains error message and HTTP status code. */
public class ErrorResponse {
  private String error;
  private HttpStatus code;

  /**
   * Constructs an ErrorResponse with the given error message and status code.
   *
   * @param error the error message
   * @param code the HTTP status code
   */
  public ErrorResponse(String error, HttpStatus code) {
    this.error = error;
    this.code = code;
  }

  /**
   * Gets the error message.
   *
   * @return the error message
   */
  public String getError() {
    return error;
  }

  /**
   * Sets the error message.
   *
   * @param error the error message
   */
  public void setError(String error) {
    this.error = error;
  }

  /**
   * Gets the HTTP status code.
   *
   * @return the HTTP status code
   */
  public HttpStatus getCode() {
    return code;
  }

  /**
   * Sets the HTTP status code.
   *
   * @param code the HTTP status code
   */
  public void setCode(HttpStatus code) {
    this.code = code;
  }
}
