# Copilot Instructions for json-java

## Project Overview

- **json-java** is a Java library and CLI for parsing and handling JSON data, inspired by [json.org](http://json.org).
- The codebase is split into five Maven modules:
  - `api-micronaut/`: Micronaut REST API for JSON parsing and validation
  - `api-quarkus/`: Quarkus REST API for JSON parsing and validation
  - `api-springboot/`: Spring Boot REST API for JSON parsing and validation
  - `api-vertx/`: Vert.x REST API for JSON parsing and validation
  - `cli/`: Command-line interface for interacting with the library
  - `lib/`: Core JSON parsing logic and utilities

## Key Directories & Files

- `api-micronaut/src/main/java/com/github/jsonjava/`: Micronaut API entry point and controllers
- `api-micronaut/src/main/resources/`: Micronaut API configuration files (e.g., `application.yml`)
- `api-quarkus/src/main/java/com/github/jsonjava/`: Quarkus API entry point and resource classes
- `api-quarkus/src/main/resources/`: Quarkus API configuration files (e.g., `application.properties`)
- `api-springboot/src/main/java/com/github/jsonjava/`: Spring Boot API entry point and controllers
- `api-springboot/src/main/resources/`: API configuration files (e.g., `application.properties`)
- `api-vertx/src/main/java/com/github/jsonjava/`: Vert.x API entry point and handlers
- `cli/src/main/java/com/github/jsonjava/`: CLI entry point and related code
- `lib/src/main/java/com/github/jsonjava/`: Main JSON parser classes (e.g., `ArrayParser`, `ObjectParser`, `StringParser`, etc.)
- `lib/src/test/java/com/github/jsonjava/`: Unit tests for core logic
- `testdata/`: Contains `.rest` files for API/CLI testing with VS Code REST Client
- `pom.xml`, `lib/pom.xml`, `cli/pom.xml`, `api-springboot/pom.xml`, `api-vertx/pom.xml`, `api-quarkus/pom.xml`, `api-micronaut/pom.xml`: Maven build configuration

## Build & Test Workflow

- Build all modules: `mvn clean install`
- Run all tests: `mvn test`
- Run CLI: `mvn exec:java -pl cli`
- Run Micronaut API: `mvn exec:java -pl api-micronaut`
  - The API will be available at [http://localhost:8000](http://localhost:8000) by default.
- Run Quarkus API: `mvn quarkus:dev -pl api-quarkus` (development mode with hot reload)
  - The API will be available at [http://localhost:8080](http://localhost:8080) by default.
- Run Spring Boot API: `mvn spring-boot:run -pl api-springboot`
  - The API will be available at [http://localhost:8000](http://localhost:8000) by default.
- Run Vert.x API: `mvn exec:java -pl api-vertx`
  - The API will be available at [http://localhost:8000](http://localhost:8000) by default.
- Test API/CLI with REST Client: Open `.rest` files in `testdata/` and use the REST Client extension

## Patterns & Conventions

- **Parser Classes**: Each JSON type (array, object, string, number, etc.) has a dedicated parser and token class (e.g., `ArrayParser`, `ArrayToken`).
- **Mode Enums**: Parsing state is managed via inner `Mode` enums (e.g., `ArrayParser.Mode`).
- **Test Naming**: Test classes are named after the parser they test (e.g., `ArrayParserTest` for `ArrayParser`).
- **No External JSON Libraries**: The project implements its own JSON parsing logic; do not use third-party JSON libraries.
- **REST Test Data**: `.rest` files are used for manual and automated API/CLI testing.
- **API Content Types**: All APIs accept `text/plain` content type for JSON parsing requests.

## Integration Points

- **VS Code REST Client**: Used for sending requests to the CLI/API for testing. Quarkus, Spring Boot, Vert.x, and Micronaut APIs use the same endpoint structure and work with the same `.rest` files.
- **Maven**: Handles builds, dependency management, and running the CLI.

## Tips for AI Agents

- Always follow the existing parser/token class structure and naming conventions.
- Use Maven for all build and test operations.
- Reference `.rest` files in `testdata/` for example requests and expected responses.
- Avoid introducing external dependencies unless explicitly required.
- All four APIs (`api-micronaut`, `api-quarkus`, `api-springboot`, and `api-vertx`) provide equivalent functionality - choose based on preference:
  - Micronaut (lightweight, annotation-based, fast startup)
  - Quarkus (fast startup, low memory footprint, container-optimized)
  - Spring Boot (familiar, annotation-based)
  - Vert.x (reactive, programmatic configuration)

---

If any section is unclear or missing important project-specific details, please provide feedback for further refinement.
