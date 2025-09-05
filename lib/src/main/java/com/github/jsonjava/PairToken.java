package com.github.jsonjava;

/** Represents a key-value pair token in a JSON object. */
public class PairToken extends Token {
  /**
   * Constructs a PairToken with the given skip value, key, and value.
   *
   * @param skip Number of characters to skip after parsing.
   * @param key StringToken representing the key.
   * @param value Token representing the value.
   */
  public PairToken(int skip, StringToken key, Token value) {
    super(skip);
    this.key = key;
    this.value = value;
  }

  /** The key of the pair. */
  public StringToken key;

  /** The value of the pair. */
  public Token value;

  /**
   * Returns the string representation of the key-value pair.
   *
   * @return Key-value pair as a string.
   */
  @Override
  public String toString() {
    return String.format("%s:%s", key, value);
  }
}
