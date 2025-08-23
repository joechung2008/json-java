package com.github.jsonjava;

public class JSONParser {
  public static Token parse(String json) {
    return ValueParser.parse(json, "[ \\n\\r\\t]");
  }
}
