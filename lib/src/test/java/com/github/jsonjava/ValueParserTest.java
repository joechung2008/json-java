package com.github.jsonjava;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ValueParser and JSON value parsing.
 */
class ValueParserTest {

    /**
     * Tests parsing the JSON value 'false'.
     */
    @Test
    void testParseFalse() {
        Token token = JSONParser.parse("false");
        assertTrue(token instanceof FalseToken);
        assertEquals(5, token.skip);
        assertEquals(false, ((FalseToken) token).getValue());
    }

    /**
     * Tests parsing the JSON value 'null'.
     */
    @Test
    void testParseNull() {
        Token token = JSONParser.parse("null");
        assertTrue(token instanceof NullToken);
        assertEquals(4, token.skip);
        assertNull(((NullToken) token).getValue());
    }

    /**
     * Tests parsing the JSON value 'true'.
     */
    @Test
    void testParseTrue() {
        Token token = JSONParser.parse("true");
        assertTrue(token instanceof TrueToken);
        assertEquals(4, token.skip);
        assertEquals(true, ((TrueToken) token).getValue());
    }

    /**
     * Tests parsing a JSON number value.
     */
    @Test
    void testParseNumber() {
        Token token = JSONParser.parse("123.45");
        assertTrue(token instanceof NumberToken);
        assertEquals(6, token.skip);
        assertEquals(123.45, ((NumberToken) token).value);
    }

    /**
     * Tests parsing a JSON string value.
     */
    @Test
    void testParseString() {
        Token token = JSONParser.parse("\"hello\"");
        assertTrue(token instanceof StringToken);
        assertEquals(7, token.skip);
        assertEquals("hello", ((StringToken) token).value);
    }

    /**
     * Tests parsing a JSON array value.
     */
    @Test
    void testParseArray() {
        Token token = JSONParser.parse("[1,2]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(5, token.skip);
        assertEquals(2, ((ArrayToken) token).elements.length);
    }

    /**
     * Tests parsing a JSON object value.
     */
    @Test
    void testParseObject() {
        Token token = JSONParser.parse("{\"a\":1}");
        assertTrue(token instanceof ObjectToken);
        assertEquals(7, token.skip);
        assertEquals(1, ((ObjectToken) token).members.length);
    }

    /**
     * Tests parsing a JSON value with surrounding whitespace.
     */
    @Test
    void testParseWhitespace() {
        Token token = JSONParser.parse("   true   ");
        assertTrue(token instanceof TrueToken);
        assertEquals(7, token.skip);
    }

    /**
     * Tests parsing a malformed value with an unexpected character.
     */
    @Test
    void testMalformedValueUnexpectedChar() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("?"));
        assertTrue(ex.getMessage().contains("Unexpected character"));
    }

    /**
     * Tests parsing an empty string as a value.
     */
    @Test
    void testMalformedValueEmpty() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse(""));
        assertTrue(ex.getMessage().contains("value cannot be empty"));
    }

    /**
     * Tests parsing a partial 'true' value.
     */
    @Test
    void testMalformedValuePartialTrue() {
        assertThrows(RuntimeException.class, () -> JSONParser.parse("tru"));
    }

    /**
     * Tests parsing a partial 'null' value.
     */
    @Test
    void testMalformedValuePartialNull() {
        assertThrows(RuntimeException.class, () -> JSONParser.parse("nul"));
    }

    /**
     * Tests parsing a partial 'false' value.
     */
    @Test
    void testMalformedValuePartialFalse() {
        assertThrows(RuntimeException.class, () -> JSONParser.parse("fals"));
    }

    /**
     * Tests parsing a value with a delimiter.
     */
    @Test
    void testParseWithDelimiter() {
        Token token = ValueParser.parse("123,", ",");
        assertTrue(token instanceof NumberToken);
        assertEquals(3, token.skip);
        assertEquals(123.0, ((NumberToken) token).value);
    }
}
