package com.github.jsonjava;

/**
 * Represents a parsed JSON string token.
 */
public class StringToken extends Token {
  /**
   * Constructs a StringToken with the given skip value and string value.
   *
   * @param skip Number of characters to skip after parsing.
   * @param value The string value of the token.
   */
  public StringToken(int skip, String value) {
    super(skip);
    this.value = value;
  }

  /**
   * The string value of the token.
   */
  public String value;

  /**
   * Returns the string representation of the JSON string token.
   *
   * @return JSON string as a quoted string.
   */
  @Override
  public String toString() {
    return String.format("\"%s\"", this.value);
  }
}
