package com.github.jsonjava;

public class PairToken extends Token {
  public PairToken(int skip, StringToken key, Token value) {
    super(skip);
    this.key = key;
    this.value = value;
  }

  public StringToken key;
  public Token value;

  @Override
  public String toString() {
    return String.format("%s:%s", key, value);
  }
}
