package com.github.jsonjava;

/**
 * Represents a parsed JSON true token.
 */
public class TrueToken extends Token {
  /**
   * Constructs a TrueToken with the given skip value.
   *
   * @param skip Number of characters to skip after parsing.
   */
  public TrueToken(int skip) {
    super(skip);
  }

  /**
   * Returns the string representation of the JSON true token.
   *
   * @return "true"
   */
  @Override
  public String toString() {
    return "true";
  }
}
