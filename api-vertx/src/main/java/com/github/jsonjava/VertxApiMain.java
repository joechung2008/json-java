package com.github.jsonjava;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Main Vert.x verticle for the JSON parsing API.
 */
public class VertxApiMain extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        // Enable body handling for POST requests
        router.route().handler(BodyHandler.create());

        // API routes
        router.post("/api/v1/parse").handler(this::handleParse);

        // Global error handler
        router.route().failureHandler(this::handleError);

        // Start the server
        server.requestHandler(router).listen(8000, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 8000");
            } else {
                startPromise.fail(http.cause());
            }
        });
    }

    private void handleParse(RoutingContext ctx) {
        try {
            // Get the request body as string
            String body = ctx.getBodyAsString();

            if (body == null || body.trim().isEmpty()) {
                ctx.response()
                    .setStatusCode(400)
                    .putHeader("content-type", "application/json")
                    .end(Json.encode(new ErrorResponse("Request body cannot be empty", 400)));
                return;
            }

            // Parse the JSON using the library
            Token result = JSONParser.parse(body);

            // Return the parsed result as JSON
            ctx.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(Json.encode(result));

        } catch (Exception ex) {
            ctx.fail(400, ex);
        }
    }

    private void handleError(RoutingContext ctx) {
        int statusCode = ctx.statusCode() != -1 ? ctx.statusCode() : 500;
        String errorMessage = ctx.failure() != null ? ctx.failure().getMessage() : "Internal server error";

        ErrorResponse errorResponse = new ErrorResponse(errorMessage, statusCode);

        ctx.response()
            .setStatusCode(statusCode)
            .putHeader("content-type", "application/json")
            .end(Json.encode(errorResponse));
    }

    public static void main(String[] args) {
        // For running as a standalone application
        io.vertx.core.Vertx vertx = io.vertx.core.Vertx.vertx();
        vertx.deployVerticle(new VertxApiMain());
    }
}