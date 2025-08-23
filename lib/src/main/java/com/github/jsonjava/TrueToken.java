package com.github.jsonjava;

public class TrueToken extends Token {
  public TrueToken(int skip) {
    super(skip);
  }

  @Override
  public String toString() {
    return "true";
  }
}
