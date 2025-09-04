package com.github.jsonjava;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ArrayParser and JSON array parsing.
 */
class ArrayParserTest {

    /**
     * Tests parsing an empty array.
     */
    @Test
    void testEmptyArray() {
        Token token = JSONParser.parse("[]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(2, ((ArrayToken) token).skip);
        assertEquals(0, ((ArrayToken) token).elements.length);
    }

    /**
     * Tests parsing an array with a single element.
     */
    @Test
    void testSingleElementArray() {
        Token token = JSONParser.parse("[1]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(3, ((ArrayToken) token).skip);
        assertEquals(1, ((ArrayToken) token).elements.length);
        assertEquals("1", ((ArrayToken) token).elements[0].toString());
    }

    /**
     * Tests parsing an array with multiple elements.
     */
    @Test
    void testMultipleElementsArray() {
        Token token = JSONParser.parse("[1, 2, 3]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(9, ((ArrayToken) token).skip);
        assertEquals(3, ((ArrayToken) token).elements.length);
        assertEquals("1", ((ArrayToken) token).elements[0].toString());
        assertEquals("2", ((ArrayToken) token).elements[1].toString());
        assertEquals("3", ((ArrayToken) token).elements[2].toString());
    }

    /**
     * Tests parsing a nested array.
     */
    @Test
    void testNestedArray() {
        Token token = JSONParser.parse("[[1], [2, 3]]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(13, ((ArrayToken) token).skip);
        assertEquals(2, ((ArrayToken) token).elements.length);
        assertTrue(((ArrayToken) token).elements[0].toString().contains("[1]"));
        assertTrue(((ArrayToken) token).elements[1].toString().contains("[2,3]"));
    }

    /**
     * Tests parsing an array with whitespace.
     */
    @Test
    void testArrayWithWhitespace() {
        Token token = JSONParser.parse("[ 1 , 2 ]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(9, ((ArrayToken) token).skip);
        assertEquals(2, ((ArrayToken) token).elements.length);
        assertEquals("1", ((ArrayToken) token).elements[0].toString());
        assertEquals("2", ((ArrayToken) token).elements[1].toString());
    }

    /**
     * Tests parsing a malformed array missing a closing bracket.
     */
    @Test
    void testMalformedArrayMissingBracket() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("[1, 2"));
        assertTrue(ex.getMessage().contains("Incomplete array expression"));
    }

    /**
     * Tests parsing a malformed array with a trailing comma.
     */
    @Test
    void testMalformedArrayTrailingComma() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("[1, 2,]"));
        assertTrue(ex.getMessage().contains("Unexpected ','"));
    }

    /**
     * Tests parsing a malformed array with an invalid element.
     */
    @Test
    void testMalformedArrayInvalidElement() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("[1, ?]"));
        assertTrue(ex.getMessage().contains("Unexpected character '?'"));
    }

    /**
     * Tests parsing an array with string elements.
     */
    @Test
    void testArrayWithStrings() {
        Token token = JSONParser.parse("[\"hello\", \"world\"]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(18, ((ArrayToken) token).skip);
        assertEquals(2, ((ArrayToken) token).elements.length);
        assertEquals("\"hello\"", ((ArrayToken) token).elements[0].toString());
        assertEquals("\"world\"", ((ArrayToken) token).elements[1].toString());
    }

    /**
     * Tests parsing an array with boolean elements.
     */
    @Test
    void testArrayWithBooleans() {
        Token token = JSONParser.parse("[true, false]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(13, ((ArrayToken) token).skip);
        assertEquals(2, ((ArrayToken) token).elements.length);
        assertEquals("true", ((ArrayToken) token).elements[0].toString());
        assertEquals("false", ((ArrayToken) token).elements[1].toString());
    }

    /**
     * Tests parsing an array with null elements.
     */
    @Test
    void testArrayWithNulls() {
        Token token = JSONParser.parse("[null, null]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(12, ((ArrayToken) token).skip);
        assertEquals(2, ((ArrayToken) token).elements.length);
        assertEquals("null", ((ArrayToken) token).elements[0].toString());
        assertEquals("null", ((ArrayToken) token).elements[1].toString());
    }

    /**
     * Tests parsing an array with object elements.
     */
    @Test
    void testArrayWithObjects() {
        Token token = JSONParser.parse("[{\"key\": \"value\"}, {}]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(22, ((ArrayToken) token).skip);
        assertEquals(2, ((ArrayToken) token).elements.length);
        assertTrue(((ArrayToken) token).elements[0].toString().contains("{\"key\":\"value\"}"));
        assertEquals("{}", ((ArrayToken) token).elements[1].toString());
    }

    /**
     * Tests parsing an array with mixed element types.
     */
    @Test
    void testMixedTypeArray() {
        Token token = JSONParser.parse("[1, \"string\", true, null, {}]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(29, ((ArrayToken) token).skip);
        assertEquals(5, ((ArrayToken) token).elements.length);
        assertEquals("1", ((ArrayToken) token).elements[0].toString());
        assertEquals("\"string\"", ((ArrayToken) token).elements[1].toString());
        assertEquals("true", ((ArrayToken) token).elements[2].toString());
        assertEquals("null", ((ArrayToken) token).elements[3].toString());
        assertEquals("{}", ((ArrayToken) token).elements[4].toString());
    }

    /**
     * Tests parsing a deeply nested array.
     */
    @Test
    void testDeeplyNestedArray() {
        Token token = JSONParser.parse("[[[1]]]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(7, ((ArrayToken) token).skip);
        assertEquals(1, ((ArrayToken) token).elements.length);
        assertTrue(((ArrayToken) token).elements[0].toString().contains("[[1]]"));
    }

    /**
     * Tests parsing an array with leading and trailing whitespace.
     */
    @Test
    void testArrayWithLeadingTrailingWhitespace() {
        Token token = JSONParser.parse(" [1, 2] ");
        assertTrue(token instanceof ArrayToken);
        assertEquals(7, ((ArrayToken) token).skip);
        assertEquals(2, ((ArrayToken) token).elements.length);
        assertEquals("1", ((ArrayToken) token).elements[0].toString());
        assertEquals("2", ((ArrayToken) token).elements[1].toString());
    }

    /**
     * Tests parsing an array with escaped characters in strings.
     */
    @Test
    void testArrayWithEscapedStrings() {
        Token token = JSONParser.parse("[\"hello\\nworld\", \"tab\\there\"]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(29, ((ArrayToken) token).skip);
        assertEquals(2, ((ArrayToken) token).elements.length);
        assertEquals("\"hello\nworld\"", ((ArrayToken) token).elements[0].toString());
        assertEquals("\"tab\there\"", ((ArrayToken) token).elements[1].toString());
    }
}
