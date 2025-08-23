package com.github.jsonjava;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ObjectParserTest {

    @Test
    void testEmptyObject() {
        Token token = JSONParser.parse("{}");
        assertTrue(token instanceof ObjectToken);
        assertEquals(2, ((ObjectToken) token).skip);
        assertEquals(0, ((ObjectToken) token).members.length);
    }

    @Test
    void testSinglePairObject() {
        Token token = JSONParser.parse("{\"a\":1}");
        assertTrue(token instanceof ObjectToken);
        assertEquals(7, ((ObjectToken) token).skip);
        assertEquals(1, ((ObjectToken) token).members.length);
        assertEquals("\"a\"", ((ObjectToken) token).members[0].key.toString());
        assertEquals("1", ((ObjectToken) token).members[0].value.toString());
    }

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

    @Test
    void testNestedObject() {
        Token token = JSONParser.parse("{\"a\": {\"b\": 2}}");
        assertTrue(token instanceof ObjectToken);
        assertEquals(15, ((ObjectToken) token).skip);
        assertEquals(1, ((ObjectToken) token).members.length);
        assertEquals("\"a\"", ((ObjectToken) token).members[0].key.toString());
        assertTrue(((ObjectToken) token).members[0].value.toString().contains("{\"b\":2}"));
    }

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

    @Test
    void testMalformedObjectMissingBrace() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("{\"a\":1"));
        assertTrue(ex.getMessage().contains("Incomplete pair expression"));
    }

    @Test
    void testMalformedObjectTrailingComma() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("{\"a\":1,}"));
        assertTrue(ex.getMessage().contains("Unexpected ','"));
    }

    @Test
    void testMalformedObjectInvalidPair() {
        Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("{a:1}"));
        assertTrue(ex.getMessage().contains("Expected"));
    }
}
