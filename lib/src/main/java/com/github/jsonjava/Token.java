package com.github.jsonjava;

public abstract class Token {
  public Token(int skip) {
    this.skip = skip;
  }

  public int skip;
}
