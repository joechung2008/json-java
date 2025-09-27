package com.github.jsonjava;

import java.util.Arrays;
import java.util.stream.Collectors;

/** Represents a parsed JSON array token. */
public class ArrayToken extends Token {
  /**
   * Constructs an ArrayToken with the given skip value and elements.
   *
   * @param skip Number of characters to skip after parsing.
   * @param elements Array of parsed tokens representing the elements.
   */
  public ArrayToken(int skip, Token[] elements) {
    super(skip);
    this.elements = elements;
  }

  /** The parsed elements of the JSON array. */
  public Token[] elements;

  /**
   * Returns the string representation of the JSON array.
   *
   * @return JSON array as a string.
   */
  @Override
  public String toString() {
    return Arrays.stream(this.elements)
        .map(Token::toString)
        .collect(Collectors.joining(",", "[", "]"));
  }
}
