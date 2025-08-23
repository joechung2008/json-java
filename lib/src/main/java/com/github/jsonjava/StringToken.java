package com.github.jsonjava;

public class StringToken extends Token {
  public StringToken(int skip, String value) {
    super(skip);
    this.value = value;
  }

  public String value;

  @Override
  public String toString() {
    return String.format("\"%s\"", this.value);
  }
}
