package com.github.jsonjava;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Unit tests for NumberParser and JSON number parsing. */
class NumberParserTest {

  /** Tests parsing an integer. */
  @Test
  void testInteger() {
    Token token = JSONParser.parse("42");
    assertInstanceOf(NumberToken.class, token);
    assertEquals(2, token.skip);
    assertEquals(42.0, ((NumberToken) token).value);
    assertEquals("42", ((NumberToken) token).valueAsString);
  }

  /** Tests parsing a negative integer. */
  @Test
  void testNegativeInteger() {
    Token token = JSONParser.parse("-7");
    assertInstanceOf(NumberToken.class, token);
    assertEquals(2, token.skip);
    assertEquals(-7.0, ((NumberToken) token).value);
    assertEquals("-7", ((NumberToken) token).valueAsString);
  }

  /** Tests parsing a decimal number. */
  @Test
  void testDecimal() {
    Token token = JSONParser.parse("3.14");
    assertInstanceOf(NumberToken.class, token);
    assertEquals(4, token.skip);
    assertEquals(3.14, ((NumberToken) token).value);
    assertEquals("3.14", ((NumberToken) token).valueAsString);
  }

  /** Tests parsing a negative decimal number. */
  @Test
  void testNegativeDecimal() {
    Token token = JSONParser.parse("-0.99");
    assertInstanceOf(NumberToken.class, token);
    assertEquals(5, token.skip);
    assertEquals(-0.99, ((NumberToken) token).value);
    assertEquals("-0.99", ((NumberToken) token).valueAsString);
  }

  /** Tests parsing a number in scientific notation with a positive exponent. */
  @Test
  void testScientificNotationPositiveExponent() {
    Token token = JSONParser.parse("6.02e23");
    assertInstanceOf(NumberToken.class, token);
    assertEquals(7, token.skip);
    assertEquals(6.02e23, ((NumberToken) token).value);
    assertEquals("6.02e23", ((NumberToken) token).valueAsString);
  }

  /** Tests parsing a number in scientific notation with a negative exponent. */
  @Test
  void testScientificNotationNegativeExponent() {
    Token token = JSONParser.parse("1.23e-4");
    assertInstanceOf(NumberToken.class, token);
    assertEquals(7, token.skip);
    assertEquals(1.23e-4, ((NumberToken) token).value);
    assertEquals("1.23e-4", ((NumberToken) token).valueAsString);
  }

  /** Tests parsing a number in scientific notation with a plus sign. */
  @Test
  void testScientificNotationWithPlus() {
    Token token = JSONParser.parse("2.5E+10");
    assertInstanceOf(NumberToken.class, token);
    assertEquals(7, token.skip);
    assertEquals(2.5E+10, ((NumberToken) token).value);
    assertEquals("2.5E+10", ((NumberToken) token).valueAsString);
  }

  /** Tests parsing zero. */
  @Test
  void testZero() {
    Token token = JSONParser.parse("0");
    assertInstanceOf(NumberToken.class, token);
    assertEquals(1, token.skip);
    assertEquals(0.0, ((NumberToken) token).value);
    assertEquals("0", ((NumberToken) token).valueAsString);
  }

  /** Tests parsing a number with surrounding whitespace. */
  @Test
  void testNumberWithWhitespace() {
    Token token = JSONParser.parse("  123 ");
    assertInstanceOf(NumberToken.class, token);
    assertEquals(5, token.skip);
    assertEquals(123.0, ((NumberToken) token).value);
    assertEquals("123", ((NumberToken) token).valueAsString);
  }

  /** Tests parsing a malformed number containing alphabetic characters. */
  @Test
  void testMalformedNumberAlpha() {
    Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("12a"));
    assertTrue(ex.getMessage().contains("Expected 'e' or 'E', actual 'a'"));
  }

  /** Tests parsing a malformed number with an incomplete exponent. */
  @Test
  void testMalformedNumberIncompleteExponent() {
    Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("1.2e"));
    assertTrue(ex.getMessage().contains("Number ended prematurely"));
  }

  /** Tests parsing a malformed number with double dots. */
  @Test
  void testMalformedNumberDoubleDot() {
    Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("1..2"));
    assertTrue(ex.getMessage().contains("Unexpected character '.'"));
  }

  /** Tests parsing an empty string as a number. */
  @Test
  void testMalformedNumberEmpty() {
    Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse(""));
    assertTrue(ex.getMessage().contains("value cannot be empty"));
  }

  /** Tests parsing negative zero. */
  @Test
  void testNegativeZero() {
    Token token = JSONParser.parse("-0");
    assertInstanceOf(NumberToken.class, token);
    assertEquals(2, token.skip);
    assertEquals(-0.0, ((NumberToken) token).value);
    assertEquals("-0", ((NumberToken) token).valueAsString);
  }

  /** Tests parsing zero with an exponent. */
  @Test
  void testZeroWithExponent() {
    Token token = JSONParser.parse("0e5");
    assertInstanceOf(NumberToken.class, token);
    assertEquals(3, token.skip);
    assertEquals(0.0, ((NumberToken) token).value);
    assertEquals("0e5", ((NumberToken) token).valueAsString);
  }

  /** Tests parsing a number in scientific notation without a decimal point. */
  @Test
  void testScientificWithoutDecimal() {
    Token token = JSONParser.parse("123e4");
    assertInstanceOf(NumberToken.class, token);
    assertEquals(5, token.skip);
    assertEquals(1230000.0, ((NumberToken) token).value);
    assertEquals("123e4", ((NumberToken) token).valueAsString);
  }

  /** Tests parsing a number with a very large exponent. */
  @Test
  void testLargeExponent() {
    Token token = JSONParser.parse("1e308");
    assertInstanceOf(NumberToken.class, token);
    assertEquals(5, token.skip);
    assertEquals(1e308, ((NumberToken) token).value);
    assertEquals("1e308", ((NumberToken) token).valueAsString);
  }

  /** Tests parsing a number with a very small exponent. */
  @Test
  void testSmallExponent() {
    Token token = JSONParser.parse("1e-324");
    assertInstanceOf(NumberToken.class, token);
    assertEquals(6, token.skip);
    assertEquals(0.0, ((NumberToken) token).value);
    assertEquals("1e-324", ((NumberToken) token).valueAsString);
  }

  /** Tests parsing a malformed number with leading zeros. */
  @Test
  void testLeadingZero() {
    Exception ex = assertThrows(RuntimeException.class, () -> JSONParser.parse("00"));
    assertTrue(ex.getMessage().contains("Expected 'e' or 'E'"));
  }

  /** Tests parsing a number with a delimiter. */
  @Test
  void testNumberWithDelimiter() {
    NumberToken token = NumberParser.parse("123,", ",");
    assertEquals(3, token.skip);
    assertEquals(123.0, token.value);
    assertEquals("123", token.valueAsString);
  }
}
