package com.github.jsonjava;

/**
 * Abstract base class for all JSON tokens.
 */
public abstract class Token {
  /**
   * Constructs a Token with the given skip value.
   *
   * @param skip Number of characters to skip after parsing.
   */
  public Token(int skip) {
    this.skip = skip;
  }

  /**
   * Number of characters to skip after parsing this token.
   */
  public int skip;
}
