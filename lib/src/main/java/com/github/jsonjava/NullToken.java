package com.github.jsonjava;

public class NullToken extends Token {
  public NullToken(int skip) {
    super(skip);
  }

  @Override
  public String toString() {
    return "null";
  }
}
