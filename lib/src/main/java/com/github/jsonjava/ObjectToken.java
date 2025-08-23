package com.github.jsonjava;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ObjectToken extends Token {
  public ObjectToken(int skip, PairToken[] members) {
    super(skip);
    this.members = members;
  }

  public PairToken[] members;

  @Override
  public String toString() {
    return Arrays.asList(members).stream().map(member -> member.toString())
        .collect(Collectors.joining(",", "{", "}"));
  }
}
