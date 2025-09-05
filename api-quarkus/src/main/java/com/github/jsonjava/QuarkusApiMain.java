package com.github.jsonjava;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Main REST resource class for the Quarkus JSON parsing API. This class provides REST endpoints for
 * JSON parsing functionality using Quarkus REST Reactive.
 */
@Path("/api/v1")
public class QuarkusApiMain {

  /**
   * Parses the provided plain text JSON string and returns the parsed token.
   *
   * @param body the JSON string to parse
   * @return Response containing the parsed Token or an error response
   */
  @POST
  @Path("/parse")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public Response parsePlainText(String body) {
    try {
      if (body == null || body.trim().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse("Request body cannot be empty", 400))
            .build();
      }

      Token result = JSONParser.parse(body);
      return Response.ok(result).build();
    } catch (Exception ex) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(ex.getMessage(), 400))
          .build();
    }
  }
}
