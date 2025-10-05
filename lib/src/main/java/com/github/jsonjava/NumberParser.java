package com.github.jsonjava;

import java.util.regex.Pattern;

/** Parses JSON number strings into NumberToken objects. */
public class NumberParser {
  /** Parsing states for JSON number processing. */
  public enum Mode {
    Scanning,
    Characteristic,
    CharacteristicDigit,
    DecimalPoint,
    Mantissa,
    Exponent,
    ExponentSign,
    ExponentFirstDigit,
    ExponentDigits,
    End
  }

  /**
   * Parses a JSON number string and returns a NumberToken.
   *
   * @param number JSON number string to parse.
   * @return NumberToken representing the parsed number.
   * @throws RuntimeException if the number is malformed.
   */
  public static NumberToken parse(String number) {
    return parse(number, null);
  }

  /**
   * Parses a JSON number string with custom delimiters and returns a NumberToken.
   *
   * @param number JSON number string to parse.
   * @param delimiters Regex pattern for delimiters.
   * @return NumberToken representing the parsed number.
   * @throws RuntimeException if the number is malformed.
   */
  public static NumberToken parse(String number, String delimiters) {
    Mode mode = Mode.Scanning;
    int pos = 0;
    StringBuilder valueAsString = new StringBuilder();

    while (pos < number.length() && mode != Mode.End) {
      char ch = number.charAt(pos);

      switch (mode) {
        case Scanning:
          if (Pattern.matches("[ \\n\\r\\t]", Character.toString(ch))) {
            pos++;
          } else if (ch == '-') {
            valueAsString.append(ch);
            pos++;
            mode = Mode.Characteristic;
          } else {
            mode = Mode.Characteristic;
          }
          break;

        case Characteristic:
          if (ch == '0') {
            valueAsString.append(ch);
            pos++;
            mode = Mode.DecimalPoint;
          } else if (Pattern.matches("[1-9]", Character.toString(ch))) {
            valueAsString.append(ch);
            pos++;
            mode = Mode.CharacteristicDigit;
          } else {
            throw new RuntimeException(String.format("Expected digit, actual '%s'", ch));
          }
          break;

        case CharacteristicDigit:
          if (Pattern.matches("\\d", Character.toString(ch))) {
            valueAsString.append(ch);
            pos++;
          } else if (delimiters != null && Pattern.matches(delimiters, Character.toString(ch))) {
            mode = Mode.End;
          } else {
            mode = Mode.DecimalPoint;
          }
          break;

        case DecimalPoint:
          if (ch == '.') {
            valueAsString.append(ch);
            pos++;
            mode = Mode.Mantissa;
          } else if (delimiters != null && Pattern.matches(delimiters, Character.toString(ch))) {
            mode = Mode.End;
          } else {
            mode = Mode.Exponent;
          }
          break;

        case Mantissa:
          if (Pattern.matches("\\d", Character.toString(ch))) {
            valueAsString.append(ch);
            pos++;
          } else if (ch == 'e' || ch == 'E') {
            mode = Mode.Exponent;
          } else if (delimiters != null && Pattern.matches(delimiters, Character.toString(ch))) {
            mode = Mode.End;
          } else {
            throw new RuntimeException(String.format("Unexpected character '%s'", ch));
          }
          break;

        case Exponent:
          if (ch == 'e' || ch == 'E') {
            valueAsString.append(ch);
            pos++;
            mode = Mode.ExponentSign;
          } else {
            throw new RuntimeException(String.format("Expected 'e' or 'E', actual '%s'", ch));
          }
          break;

        case ExponentSign:
          if (ch == '+' || ch == '-') {
            valueAsString.append(ch);
            pos++;
          }

          mode = Mode.ExponentFirstDigit;
          break;

        case ExponentFirstDigit:
          if (Pattern.matches("\\d", Character.toString(ch))) {
            valueAsString.append(ch);
            pos++;
            mode = Mode.ExponentDigits;
          } else {
            throw new RuntimeException(String.format("Expected digit, actual '%s'", ch));
          }
          break;

        case ExponentDigits:
          if (Pattern.matches("\\d", Character.toString(ch))) {
            valueAsString.append(ch);
            pos++;
          } else if (delimiters != null && Pattern.matches(delimiters, Character.toString(ch))) {
            mode = Mode.End;
          } else {
            throw new RuntimeException(String.format("Expected digit, actual '%s'", ch));
          }
          break;

        default:
          throw new RuntimeException(String.format("Unexpected mode %s", mode));
      }
    }

    return switch (mode) {
      case Characteristic, ExponentFirstDigit, ExponentSign ->
          throw new RuntimeException("Number ended prematurely");
      default -> {
        String string = valueAsString.toString();
        yield new NumberToken(pos, Double.parseDouble(string), string);
      }
    };
  }
}
