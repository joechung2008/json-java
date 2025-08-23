package com.github.jsonjava;

public class FalseToken extends Token {
  public FalseToken(int skip) {
    super(skip);
  }

  @Override
  public String toString() {
    return "false";
  }
}
