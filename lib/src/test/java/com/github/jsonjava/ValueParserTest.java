package com.github.jsonjava;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValueParserTest {

    @Test
    void testParseFalse() {
        Token token = JSONParser.parse("false");
        assertTrue(token instanceof FalseToken);
        assertEquals(5, token.skip);
    }

    @Test
    void testParseNull() {
        Token token = JSONParser.parse("null");
        assertTrue(token instanceof NullToken);
        assertEquals(4, token.skip);
    }

    @Test
    void testParseTrue() {
        Token token = JSONParser.parse("true");
        assertTrue(token instanceof TrueToken);
        assertEquals(4, token.skip);
    }

    @Test
    void testParseNumber() {
        Token token = JSONParser.parse("123.45");
        assertTrue(token instanceof NumberToken);
        assertEquals(6, token.skip);
        assertEquals(123.45, ((NumberToken) token).value);
    }

    @Test
    void testParseString() {
        Token token = JSONParser.parse("\"hello\"");
        assertTrue(token instanceof StringToken);
        assertEquals(7, token.skip);
        assertEquals("hello", ((StringToken) token).value);
    }

    @Test
    void testParseArray() {
        Token token = JSONParser.parse("[1,2]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(5, token.skip);
        assertEquals(2, ((ArrayToken) token).elements.length);
    }

    @Test
    void testParseObject() {
        Token token = JSONParser.parse("{\"a\":1}");
        assertTrue(token instanceof ObjectToken);
        assertEquals(7, token.skip);
        assertEquals(1, ((ObjectToken) token).members.length);
    }

    @Test
    void testParseWhitespace() {
        Token token = JSONParser.parse("   true   ");
        assertTrue(token instanceof TrueToken);
        assertEquals(7, token.skip);
    }

    @Test
    void testMalformedValueUnexpectedChar() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("?"));
        assertTrue(ex.getMessage().contains("Unexpected character"));
    }

    @Test
    void testMalformedValueEmpty() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse(""));
        assertTrue(ex.getMessage().contains("value cannot be empty"));
    }
}
