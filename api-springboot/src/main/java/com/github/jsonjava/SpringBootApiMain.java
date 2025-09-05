package com.github.jsonjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

/**
 * Main application class for the Spring Boot JSON parsing API.
 * This class serves as the entry point for the Spring Boot application
 * and provides REST endpoints for JSON parsing functionality.
 */
@SpringBootApplication
@RestController
@RequestMapping("/api/v1")
public class SpringBootApiMain {

    /**
     * Main method to start the Spring Boot application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApiMain.class, args);
    }

    /**
     * Parses the provided plain text JSON string and returns the parsed token.
     * @param body the JSON string to parse
     * @return ResponseEntity containing the parsed Token or an error response
     */
    @PostMapping(value = "/parse", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> parsePlainText(@RequestBody String body) {
        try {
            Token result = JSONParser.parse(body);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
}
