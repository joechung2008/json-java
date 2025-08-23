# Copilot Instructions for json-java

## Project Overview

- **json-java** is a Java library and CLI for parsing and handling JSON data, inspired by [json.org](http://json.org).
- The codebase is split into two Maven modules:
  - `lib/`: Core JSON parsing logic and utilities
  - `cli/`: Command-line interface for interacting with the library

## Key Directories & Files

- `lib/src/main/java/com/github/jsonjava/`: Main JSON parser classes (e.g., `ArrayParser`, `ObjectParser`, `StringParser`, etc.)
- `lib/src/test/java/com/github/jsonjava/`: Unit tests for core logic
- `cli/src/main/java/com/github/jsonjava/`: CLI entry point and related code
- `testdata/`: Contains `.rest` files for API/CLI testing with VS Code REST Client
- `pom.xml`, `lib/pom.xml`, `cli/pom.xml`: Maven build configuration

## Build & Test Workflow

- Build all modules: `mvn clean install`
- Run all tests: `mvn test`
- Run CLI: `mvn exec:java -pl cli`
- Test API/CLI with REST Client: Open `.rest` files in `testdata/` and use the REST Client extension

## Patterns & Conventions

- **Parser Classes**: Each JSON type (array, object, string, number, etc.) has a dedicated parser and token class (e.g., `ArrayParser`, `ArrayToken`).
- **Mode Enums**: Parsing state is managed via inner `Mode` enums (e.g., `ArrayParser.Mode`).
- **Test Naming**: Test classes are named after the parser they test (e.g., `ArrayParserTest` for `ArrayParser`).
- **No External JSON Libraries**: The project implements its own JSON parsing logic; do not use third-party JSON libraries.
- **REST Test Data**: `.rest` files are used for manual and automated API/CLI testing.

## Integration Points

- **VS Code REST Client**: Used for sending requests to the CLI/API for testing.
- **Maven**: Handles builds, dependency management, and running the CLI.

## Example: Adding a New JSON Type

1. Create a new parser class in `lib/src/main/java/com/github/jsonjava/` (e.g., `DateParser.java`).
2. Add a corresponding token class if needed (e.g., `DateToken.java`).
3. Add unit tests in `lib/src/test/java/com/github/jsonjava/` (e.g., `DateParserTest.java`).
4. Update CLI logic if the new type should be supported interactively.

## Tips for AI Agents

- Always follow the existing parser/token class structure and naming conventions.
- Use Maven for all build and test operations.
- Reference `.rest` files in `testdata/` for example requests and expected responses.
- Avoid introducing external dependencies unless explicitly required.

---

If any section is unclear or missing important project-specific details, please provide feedback for further refinement.
