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

## Using REST Client in VS Code

Install the [REST Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) extension. Open any `.rest` file in `testdata/` and click "Send Request" above an HTTP request to send test data to APIs.
