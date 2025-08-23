package com.github.jsonjava;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringParserTest {

    @Test
    void testEmptyString() {
        Token token = JSONParser.parse("\"\"");
        assertTrue(token instanceof StringToken);
        assertEquals(2, token.skip);
        assertEquals("", ((StringToken)token).value);
    }

    @Test
    void testNormalString() {
        Token token = JSONParser.parse("\"hello\"");
        assertTrue(token instanceof StringToken);
        assertEquals(7, token.skip);
        assertEquals("hello", ((StringToken)token).value);
    }

    @Test
    void testStringWithEscapedQuote() {
        Token token = JSONParser.parse("\"he\\\"llo\"");
        assertTrue(token instanceof StringToken);
        assertEquals(9, token.skip);
        assertEquals("he\"llo", ((StringToken)token).value);
    }

    @Test
    void testStringWithEscapedBackslash() {
        Token token = JSONParser.parse("\"a\\\\b\"");
        assertTrue(token instanceof StringToken);
        assertEquals(6, token.skip);
        assertEquals("a\\b", ((StringToken)token).value);
    }

    @Test
    void testStringWithEscapedSlash() {
        Token token = JSONParser.parse("\"a\\/b\"");
        assertTrue(token instanceof StringToken);
        assertEquals(6, token.skip);
        assertEquals("a/b", ((StringToken)token).value);
    }

    @Test
    void testStringWithEscapedControlChars() {
        Token token = JSONParser.parse("\"a\\nb\\tb\"");
        assertTrue(token instanceof StringToken);
        assertEquals(9, token.skip);
        assertEquals("a\nb\tb", ((StringToken)token).value);
    }

    @Test
    void testStringWithUnicode() {
        StringToken token = StringParser.parse("\"\\u0041\"");
        assertEquals(8, token.skip);
        assertEquals("\\u0041", token.value);
    }

    @Test
    void testStringWithWhitespace() {
        Token token = JSONParser.parse("  \"abc\"  ");
        assertTrue(token instanceof StringToken);
        assertEquals(7, token.skip);
        assertEquals("abc", ((StringToken)token).value);
    }

    @Test
    void testMalformedStringMissingQuote() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("\"abc"));
        assertTrue(ex.getMessage().contains("Incomplete string expression"));
    }

    @Test
    void testMalformedStringUnexpectedChar() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("\"ab\nc\""));
        assertTrue(ex.getMessage().contains("Unexpected character"));
    }

    @Test
    void testMalformedStringBadEscape() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("\"a\\x\""));
        assertTrue(ex.getMessage().contains("Unexpected escape character"));
    }

    @Test
    void testMalformedStringBadUnicode() {
        Exception ex =
                assertThrows(RuntimeException.class, () -> JSONParser.parse("\"\\u00G1\""));
        assertTrue(ex.getMessage().contains("Unexpected Unicode code"));
    }
}
