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

## Run Spring Boot API

To start the Spring Boot API server, run:

```sh
mvn spring-boot:run -pl api
```

The API will be available at [http://localhost:8000](http://localhost:8000) by default.

## Using REST Client in VS Code

Install the [REST Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) extension. Open any `.rest` file in `testdata/` and click "Send Request" above an HTTP request to send test data to APIs.
