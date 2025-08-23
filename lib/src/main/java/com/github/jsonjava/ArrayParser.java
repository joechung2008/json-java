package com.github.jsonjava;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Parses JSON array strings into ArrayToken objects.
 */
public class ArrayParser {
  /**
   * Parsing states for JSON array processing.
   */
  public enum Mode {
    Scanning, Element, Delimiter, End
  };

  /**
   * Parses a JSON array string and returns an ArrayToken.
   *
   * @param array JSON array string to parse.
   * @return ArrayToken representing the parsed array.
   * @throws RuntimeException if the array is malformed.
   */
  public static ArrayToken parse(String array) {
    Mode mode = Mode.Scanning;
    int pos = 0;
    ArrayList<Token> tokens = new ArrayList<Token>();

    while (pos < array.length() && mode != Mode.End) {
      char ch = array.charAt(pos);

      switch (mode) {
        case Scanning:
          if (Pattern.matches("[ \\n\\r\\t]", Character.toString(ch))) {
            pos++;
          } else if (ch == '[') {
            pos++;
            mode = Mode.Element;
          } else {
            throw new RuntimeException(String.format("Expected '[', actual '%s'", ch));
          }
          break;

        case Element:
          if (Pattern.matches("[ \\n\\r\\t]", Character.toString(ch))) {
            pos++;
          } else if (ch == ']') {
            if (tokens.size() > 0) {
              throw new RuntimeException("Unexpected ','");
            }
            pos++;
            mode = Mode.End;
          } else {
            String slice = array.substring(pos);
            Token element = ValueParser.parse(slice, "[ \\n\\r\\t\\],]");
            tokens.add(element);
            pos += element.skip;
            mode = Mode.Delimiter;
          }
          break;

        case Delimiter:
          if (Pattern.matches("[ \\n\\r\\t]", Character.toString(ch))) {
            pos++;
          } else if (ch == ',') {
            pos++;
            mode = Mode.Element;
          } else if (ch == ']') {
            pos++;
            mode = Mode.End;
          } else {
            throw new RuntimeException(String.format("Expected ',' or ']', actual '%s'", ch));
          }
          break;

        case End:
          break;

        default:
          throw new RuntimeException(String.format("Unexpected mode %s", mode));
      }
    }

    if (mode != Mode.End) {
      throw new RuntimeException("Incomplete array expression");
    } else {
      Token[] elements = new Token[tokens.size()];
      return new ArrayToken(pos, tokens.toArray(elements));
    }
  }
}
