package com.github.jsonjava;

public class NumberToken extends Token {
  public NumberToken(int skip, double value, String valueAsString) {
    super(skip);
    this.value = value;
    this.valueAsString = valueAsString;
  }

  public double value;
  public String valueAsString;

  @Override
  public String toString() {
    if (value == (int) value) {
      return Integer.toString((int) value);
    } else {
      return Double.toString(value);
    }
  }
}
