package com.github.jsonjava;

/**
 * Represents a parsed JSON number token.
 */
public class NumberToken extends Token {
  /**
   * Constructs a NumberToken with the given skip value, numeric value, and string representation.
   *
   * @param skip Number of characters to skip after parsing.
   * @param value Numeric value of the token.
   * @param valueAsString String representation of the number.
   */
  public NumberToken(int skip, double value, String valueAsString) {
    super(skip);
    this.value = value;
    this.valueAsString = valueAsString;
  }

  /**
   * Numeric value of the parsed number.
   */
  public double value;

  /**
   * String representation of the parsed number.
   */
  public String valueAsString;

  /**
   * Returns the string representation of the JSON number token.
   *
   * @return JSON number as a string.
   */
  @Override
  public String toString() {
    if (value == (int) value) {
      return Integer.toString((int) value);
    } else {
      return Double.toString(value);
    }
  }
}
