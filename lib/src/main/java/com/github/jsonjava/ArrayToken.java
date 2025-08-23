package com.github.jsonjava;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ArrayToken extends Token {
  public ArrayToken(int skip, Token[] elements) {
    super(skip);
    this.elements = elements;
  }

  public Token[] elements;

  @Override
  public String toString() {
    return Arrays.asList(this.elements).stream().map(element -> element.toString())
        .collect(Collectors.joining(",", "[", "]"));
  }
}
