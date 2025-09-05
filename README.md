# json.java

## License

MIT

## Reference

[json.org](http://json.org)

---

## Build

```sh
mvn clean install
```

## Format

This project uses [Spotless](https://github.com/diffplug/spotless) with Google Java Format for consistent code style.

### Check Formatting

To check if all Java files are properly formatted:

```sh
mvn spotless:check
```

This will fail the build if any files need formatting changes.

### Fix Formatting

To automatically format all Java files:

```sh
mvn spotless:apply
```

This will reformat all Java source and test files according to the project's style guidelines.

## Test

If tests are present, run:

```sh
mvn test
```

## Run CLI

After building, run the CLI with:

```sh
mvn exec:java -pl cli
```

## Run Quarkus API

To start the Quarkus API server in development mode (with hot reload), run:

```sh
mvn quarkus:dev -pl api-quarkus
```

For production mode, run:

```sh
mvn clean package -pl api-quarkus
java -jar api-quarkus/target/quarkus-app/quarkus-run.jar
```

The API will be available at [http://localhost:8080](http://localhost:8080) by default.

## Run Spring Boot API

To start the Spring Boot API server, run:

```sh
mvn spring-boot:run -pl api-springboot
```

The API will be available at [http://localhost:8000](http://localhost:8000) by default.

## Run Vert.x API

To start the Vert.x API server, run:

```sh
mvn exec:java -pl api-vertx
```

The API will be available at [http://localhost:8000](http://localhost:8000) by default.

## Using REST Client in VS Code

Install the [REST Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) extension. Open any `.rest` file in `testdata/` and click "Send Request" above an HTTP request to send test data to APIs.
