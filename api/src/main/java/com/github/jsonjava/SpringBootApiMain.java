package com.github.jsonjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1")
public class SpringBootApiMain {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApiMain.class, args);
    }

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
