package com.github.jsonjava;

/** Entry point for parsing JSON strings into tokens. */
public class JSONParser {
  /**
   * Parses a JSON string and returns the root Token.
   *
   * @param json JSON string to parse.
   * @return Root Token representing the parsed JSON.
   */
  public static Token parse(String json) {
    return ValueParser.parse(json, "[ \\n\\r\\t]");
  }
}
