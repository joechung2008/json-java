package com.github.jsonjava;

import java.util.regex.Pattern;

/** Parses any JSON value string into the appropriate Token type. */
public class ValueParser {
  /** Parsing states for JSON value processing. */
  public enum Mode {
    Scanning,
    Array,
    False,
    Null,
    Number,
    Object,
    String,
    True,
    End
  }

  /**
   * Parses a JSON value string and returns the corresponding Token.
   *
   * @param value JSON value string to parse.
   * @return Token representing the parsed value.
   * @throws RuntimeException if the value is malformed.
   */
  public static Token parse(String value) {
    return parse(value, null);
  }

  /**
   * Parses a JSON value string with custom delimiters and returns the corresponding Token.
   *
   * @param value JSON value string to parse.
   * @param delimiters Regex pattern for delimiters.
   * @return Token representing the parsed value.
   * @throws RuntimeException if the value is malformed.
   */
  public static Token parse(String value, String delimiters) {
    Mode mode = Mode.Scanning;
    int pos = 0;

    while (pos < value.length() && mode != Mode.End) {
      char ch = value.charAt(pos);
      String slice;

      switch (mode) {
        case Scanning:
          if (Pattern.matches("[ \\n\\r\\t]", Character.toString(ch))) {
            pos++;
          } else if (ch == '[') {
            mode = Mode.Array;
          } else if (ch == 'f') {
            mode = Mode.False;
          } else if (ch == 'n') {
            mode = Mode.Null;
          } else if (Pattern.matches("[-\\d]", Character.toString(ch))) {
            mode = Mode.Number;
          } else if (ch == '{') {
            mode = Mode.Object;
          } else if (ch == '"') {
            mode = Mode.String;
          } else if (ch == 't') {
            mode = Mode.True;
          } else if (delimiters != null && Pattern.matches(delimiters, Character.toString(ch))) {
            mode = Mode.End;
          } else {
            throw new RuntimeException(String.format("Unexpected character '%s'", ch));
          }
          break;

        case Array:
          slice = value.substring(pos);
          ArrayToken arrayToken = ArrayParser.parse(slice);
          arrayToken.skip += pos;
          return arrayToken;

        case False:
          slice = value.substring(pos, pos + 5);
          if (slice.equals("false")) {
            return new FalseToken(pos + 5);
          } else {
            throw new RuntimeException(String.format("Expected 'false', actual '%s'", slice));
          }

        case Null:
          slice = value.substring(pos, pos + 4);
          if (slice.equals("null")) {
            return new NullToken(pos + 4);
          } else {
            throw new RuntimeException(String.format("Expected 'null', actual '%s'", slice));
          }

        case Number:
          slice = value.substring(pos);
          NumberToken numberToken = NumberParser.parse(slice, delimiters);
          numberToken.skip += pos;
          return numberToken;

        case Object:
          slice = value.substring(pos);
          ObjectToken objectToken = ObjectParser.parse(slice);
          objectToken.skip += pos;
          return objectToken;

        case String:
          slice = value.substring(pos);
          StringToken stringToken = StringParser.parse(slice);
          stringToken.skip += pos;
          return stringToken;

        case True:
          slice = value.substring(pos, pos + 4);
          if (slice.equals("true")) {
            return new TrueToken(pos + 4);
          } else {
            throw new RuntimeException(String.format("Expected 'true', actual '%s'", slice));
          }

        case End:
          break;

        default:
          throw new RuntimeException(String.format("Unexpected mode %s", mode));
      }
    }

    throw new RuntimeException("value cannot be empty");
  }
}
