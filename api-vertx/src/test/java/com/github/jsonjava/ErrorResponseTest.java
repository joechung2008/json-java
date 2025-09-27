package com.github.jsonjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Unit tests for Vert.x ErrorResponse bean to ensure getters/setters are exercised. */
class ErrorResponseTest {
  @Test
  void gettersAndSettersWork() {
    ErrorResponse er = new ErrorResponse("Bad request", 400);
    // exercise getters
    assertEquals("Bad request", er.getError());
    assertEquals(400, er.getCode());

    // exercise setters
    er.setError("Not found");
    er.setCode(404);

    // verify mutations via getters
    assertEquals("Not found", er.getError());
    assertEquals(404, er.getCode());
  }
}
