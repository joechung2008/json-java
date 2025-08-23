package com.github.jsonjava;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents a parsed JSON object token.
 */
public class ObjectToken extends Token {
  /**
   * Constructs an ObjectToken with the given skip value and members.
   *
   * @param skip Number of characters to skip after parsing.
   * @param members Array of PairToken representing the object's members.
   */
  public ObjectToken(int skip, PairToken[] members) {
    super(skip);
    this.members = members;
  }

  /**
   * The parsed members of the JSON object.
   */
  public PairToken[] members;

  /**
   * Returns the string representation of the JSON object.
   *
   * @return JSON object as a string.
   */
  @Override
  public String toString() {
    return Arrays.asList(members).stream().map(member -> member.toString())
        .collect(Collectors.joining(",", "{", "}"));
  }
}
