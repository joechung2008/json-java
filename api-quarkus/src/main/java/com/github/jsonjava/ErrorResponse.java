package com.github.jsonjava;

/** Error response for API errors. */
public class ErrorResponse {
  private String error;
  private int code;

  /**
   * Constructs an ErrorResponse with the given error message and code.
   *
   * @param error the error message
   * @param code the error code
   */
  public ErrorResponse(String error, int code) {
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
   * Gets the error code.
   *
   * @return the error code
   */
  public int getCode() {
    return code;
  }

  /**
   * Sets the error code.
   *
   * @param code the error code
   */
  public void setCode(int code) {
    this.code = code;
  }
}
