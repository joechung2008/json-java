package com.github.jsonjava;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

/**
 * Main REST controller class for the Micronaut JSON parsing API. This class provides REST endpoints
 * for JSON parsing functionality using Micronaut. It handles HTTP requests for parsing JSON strings
 * and returns structured responses.
 *
 * <p>The controller is mapped to the "/api/v1" base path and provides endpoints for JSON parsing
 * operations.
 *
 * @author json-java
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
@Controller("/api/v1")
public class MicronautApiMain {

  /**
   * Parses the provided plain text JSON string and returns the parsed token. This endpoint accepts
   * JSON strings via HTTP POST request body and attempts to parse them using the JSONParser. If
   * parsing is successful, it returns the parsed Token object. If parsing fails or input is
   * invalid, it returns an error response with appropriate HTTP status code.
   *
   * @param body the JSON string to parse, provided in the request body
   * @return HttpResponse containing either: - 200 OK with the parsed Token object if successful -
   *     400 Bad Request with ErrorResponse if parsing fails or input is invalid
   */
  @Post("/parse")
  @Consumes(MediaType.TEXT_PLAIN)
  public HttpResponse<?> parsePlainText(@Body String body) {
    try {
      if (body == null || body.trim().isEmpty()) {
        return HttpResponse.badRequest(new ErrorResponse("Request body cannot be empty", 400));
      }

      Token result = JSONParser.parse(body);
      return HttpResponse.ok(result);
    } catch (Exception ex) {
      return HttpResponse.badRequest(new ErrorResponse(ex.getMessage(), 400));
    }
  }
}
