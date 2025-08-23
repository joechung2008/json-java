package com.github.jsonjava;

/**
 * Represents a parsed JSON false token.
 */
public class FalseToken extends Token {
  /**
   * Constructs a FalseToken with the given skip value.
   *
   * @param skip Number of characters to skip after parsing.
   */
  public FalseToken(int skip) {
    super(skip);
  }

  /**
   * Returns the string representation of the JSON false token.
   *
   * @return "false"
   */
  @Override
  public String toString() {
    return "false";
  }
}
