package com.github.jsonjava;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Unit tests for ObjectParser and JSON object parsing. */
class ObjectParserTest {

  /** Tests parsing an empty object. */
  @Test
  void testEmptyObject() {
    Token token = JSONParser.parse("{}");
    assertTrue(token instanceof ObjectToken);
    assertEquals(2, ((ObjectToken) token).skip);
    assertEquals(0, ((ObjectToken) token).members.length);
  }

  /** Tests parsing an object with a single key-value pair. */
  @Test
  void testSinglePairObject() {
    Token token = JSONParser.parse("{\"a\":1}");
    assertTrue(token instanceof ObjectToken);
    assertEquals(7, ((ObjectToken) token).skip);
    assertEquals(1, ((ObjectToken) token).members.length);
    assertEquals("\"a\"", ((ObjectToken) token).members[0].key.toString());
    assertEquals("1", ((ObjectToken) token).members[0].value.toString());
  }

  /** Tests parsing an object with multiple key-value pairs. */
  @Test
  void testMultiplePairsObject() {
    Token token = JSONParser.parse("{\"a\":1, \"b\":2}");
    assertTrue(token instanceof ObjectToken);
    assertEquals(14, ((ObjectToken) token).skip);
    assertEquals(2, ((ObjectToken) token).members.length);
    assertEquals("\"a\"", ((ObjectToken) token).members[0].key.toString());
    assertEquals("1", ((ObjectToken) token).members[0].value.toString());
    assertEquals("\"b\"", ((ObjectToken) token).members[1].key.toString());
    assertEquals("2", ((ObjectToken) token).members[1].value.toString());
  }

  /** Tests parsing a nested object. */
  @Test
  void testNestedObject() {
    Token token = JSONParser.parse("{\"a\": {\"b\": 2}}");
    assertTrue(token instanceof ObjectToken);
    assertEquals(15, ((ObjectToken) token).skip);
    assertEquals(1, ((ObjectToken) token).members.length);
    assertEquals("\"a\"", ((ObjectToken) token).members[0].key.toString());
    assertTrue(((ObjectToken) token).members[0].value.toString().contains("{\"b\":2}"));
  }

  /** Tests parsing an object with whitespace. */
  @Test
  void testObjectWithWhitespace() {
    Token token = JSONParser.parse("{ \"x\" : 10 , \"y\" : 20 }");
    assertTrue(token instanceof ObjectToken);
    assertEquals(23, ((ObjectToken) token).skip);
    assertEquals(2, ((ObjectToken) token).members.length);
    assertEquals("\"x\"", ((ObjectToken) token).members[0].key.toString());
    assertEquals("10", ((ObjectToken) token).members[0].value.toString());
    assertEquals("\"y\"", ((ObjectToken) token).members[1].key.toString());
    assertEquals("20", ((ObjectToken) token).members[1].value.toString());
  }

  /** Tests parsing a malformed object missing a closing brace. */
  @Test
  void testMalformedObjectMissingBrace() {
    Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("{\"a\":1"));
    assertTrue(ex.getMessage().contains("Incomplete pair expression"));
  }

  /** Tests parsing a malformed object with a trailing comma. */
  @Test
  void testMalformedObjectTrailingComma() {
    Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("{\"a\":1,}"));
    assertTrue(ex.getMessage().contains("Unexpected ','"));
  }

  /** Tests parsing a malformed object with an invalid pair. */
  @Test
  void testMalformedObjectInvalidPair() {
    Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("{a:1}"));
    assertTrue(ex.getMessage().contains("Expected"));
  }

  /** Tests parsing an object with diverse value types. */
  @Test
  void testObjectWithDiverseValues() {
    Token token = JSONParser.parse("{\"str\":\"hello\",\"bool\":true,\"null\":null,\"arr\":[1,2]}");
    assertTrue(token instanceof ObjectToken);
    assertEquals(51, ((ObjectToken) token).skip);
    assertEquals(4, ((ObjectToken) token).members.length);
    assertEquals("\"str\"", ((ObjectToken) token).members[0].key.toString());
    assertEquals("\"hello\"", ((ObjectToken) token).members[0].value.toString());
    assertEquals("\"bool\"", ((ObjectToken) token).members[1].key.toString());
    assertEquals("true", ((ObjectToken) token).members[1].value.toString());
    assertEquals("\"null\"", ((ObjectToken) token).members[2].key.toString());
    assertEquals("null", ((ObjectToken) token).members[2].value.toString());
    assertEquals("\"arr\"", ((ObjectToken) token).members[3].key.toString());
    assertEquals("[1,2]", ((ObjectToken) token).members[3].value.toString());
  }

  /** Tests parsing an object with escaped characters in keys. */
  @Test
  void testObjectWithEscapedKeys() {
    Token token = JSONParser.parse("{\"key\\n\": \"value\"}");
    assertTrue(token instanceof ObjectToken);
    assertEquals(18, ((ObjectToken) token).skip);
    assertEquals(1, ((ObjectToken) token).members.length);
    assertEquals("\"key\n\"", ((ObjectToken) token).members[0].key.toString());
    assertEquals("\"value\"", ((ObjectToken) token).members[0].value.toString());
  }

  /** Tests parsing an object with leading and trailing whitespace. */
  @Test
  void testObjectWithLeadingTrailingWhitespace() {
    Token token = JSONParser.parse(" { \"a\": 1 } ");
    assertTrue(token instanceof ObjectToken);
    assertEquals(11, ((ObjectToken) token).skip);
    assertEquals(1, ((ObjectToken) token).members.length);
    assertEquals("\"a\"", ((ObjectToken) token).members[0].key.toString());
    assertEquals("1", ((ObjectToken) token).members[0].value.toString());
  }

  /** Tests parsing an object with duplicate keys. */
  @Test
  void testObjectWithDuplicateKeys() {
    Token token = JSONParser.parse("{\"a\":1, \"a\":2}");
    assertTrue(token instanceof ObjectToken);
    assertEquals(14, ((ObjectToken) token).skip);
    assertEquals(2, ((ObjectToken) token).members.length);
    assertEquals("\"a\"", ((ObjectToken) token).members[0].key.toString());
    assertEquals("1", ((ObjectToken) token).members[0].value.toString());
    assertEquals("\"a\"", ((ObjectToken) token).members[1].key.toString());
    assertEquals("2", ((ObjectToken) token).members[1].value.toString());
  }

  /** Tests parsing a malformed object missing a colon. */
  @Test
  void testMalformedObjectMissingColon() {
    Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("{\"a\" 1}"));
    assertTrue(ex.getMessage().contains("Expected ':'"));
  }

  /** Tests parsing a malformed object missing a value. */
  @Test
  void testMalformedObjectMissingValue() {
    assertThrows(RuntimeException.class, () -> JSONParser.parse("{\"a\":}"));
  }

  /** Tests parsing a malformed object missing a key. */
  @Test
  void testMalformedObjectMissingKey() {
    assertThrows(RuntimeException.class, () -> JSONParser.parse("{:\"value\"}"));
  }

  /** Tests parsing a malformed object with an extra colon. */
  @Test
  void testMalformedObjectExtraColon() {
    assertThrows(RuntimeException.class, () -> JSONParser.parse("{\"a\"::1}"));
  }

  /** Tests parsing a malformed object missing a comma. */
  @Test
  void testMalformedObjectMissingComma() {
    Exception ex =
        assertThrows(RuntimeException.class, () -> JSONParser.parse("{\"a\":1 \"b\":2}"));
    assertTrue(ex.getMessage().contains("Expected ',' or '}'"));
  }
}
