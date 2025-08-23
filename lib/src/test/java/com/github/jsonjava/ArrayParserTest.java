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
}
