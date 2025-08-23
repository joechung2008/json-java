package com.github.jsonjava;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArrayParserTest {

    @Test
    void testEmptyArray() {
        Token token = JSONParser.parse("[]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(2, ((ArrayToken) token).skip);
        assertEquals(0, ((ArrayToken) token).elements.length);
    }

    @Test
    void testSingleElementArray() {
        Token token = JSONParser.parse("[1]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(3, ((ArrayToken) token).skip);
        assertEquals(1, ((ArrayToken) token).elements.length);
        assertEquals("1", ((ArrayToken) token).elements[0].toString());
    }

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

    @Test
    void testNestedArray() {
        Token token = JSONParser.parse("[[1], [2, 3]]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(13, ((ArrayToken) token).skip);
        assertEquals(2, ((ArrayToken) token).elements.length);
        assertTrue(((ArrayToken) token).elements[0].toString().contains("[1]"));
        assertTrue(((ArrayToken) token).elements[1].toString().contains("[2,3]"));
    }

    @Test
    void testArrayWithWhitespace() {
        Token token = JSONParser.parse("[ 1 , 2 ]");
        assertTrue(token instanceof ArrayToken);
        assertEquals(9, ((ArrayToken) token).skip);
        assertEquals(2, ((ArrayToken) token).elements.length);
        assertEquals("1", ((ArrayToken) token).elements[0].toString());
        assertEquals("2", ((ArrayToken) token).elements[1].toString());
    }

    @Test
    void testMalformedArrayMissingBracket() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("[1, 2"));
        assertTrue(ex.getMessage().contains("Incomplete array expression"));
    }

    @Test
    void testMalformedArrayTrailingComma() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("[1, 2,]"));
        assertTrue(ex.getMessage().contains("Unexpected ','"));
    }

    @Test
    void testMalformedArrayInvalidElement() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("[1, ?]"));
        assertTrue(ex.getMessage().contains("Unexpected character '?'"));
    }
}
