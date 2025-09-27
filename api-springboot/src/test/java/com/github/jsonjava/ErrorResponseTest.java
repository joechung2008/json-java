package com.github.jsonjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/** Unit tests for Spring Boot ErrorResponse bean to ensure getters/setters are exercised. */
class ErrorResponseTest {
  @Test
  void gettersAndSettersWork() {
    ErrorResponse er = new ErrorResponse("Bad request", HttpStatus.BAD_REQUEST);
    // exercise getters
    assertEquals("Bad request", er.getError());
    assertEquals(HttpStatus.BAD_REQUEST, er.getCode());

    // exercise setters
    er.setError("Not found");
    er.setCode(HttpStatus.NOT_FOUND);

    // verify mutations via getters
    assertEquals("Not found", er.getError());
    assertEquals(HttpStatus.NOT_FOUND, er.getCode());
  }
}
