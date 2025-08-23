package com.github.jsonjava;

import java.util.regex.Pattern;

public class StringParser {
  public enum Mode {
    Scanning, Char, EscapedChar, Unicode, End
  };

  public static StringToken parse(String string) throws RuntimeException {
    Mode mode = Mode.Scanning;
    int pos = 0;
    StringBuilder value = new StringBuilder();

    while (pos < string.length() && mode != Mode.End) {
      char ch = string.charAt(pos);

      switch (mode) {
        case Scanning:
          if (Pattern.matches("[ \\n\\r\\t]", Character.toString(ch))) {
            pos++;
          } else if (ch == '"') {
            pos++;
            mode = Mode.Char;
          } else {
            throw new RuntimeException(String.format("Expected '\"', actual '%s'", ch));
          }
          break;

        case Char:
          if (ch == '\\') {
            pos++;
            mode = Mode.EscapedChar;
          } else if (ch == '"') {
            pos++;
            mode = Mode.End;
          } else if (ch != '\n' && ch != '\r') {
            value.append(ch);
            pos++;
          } else {
            throw new RuntimeException(String.format("Unexpected character '%s'", ch));
          }
          break;

        case EscapedChar:
          if (ch == '"' || ch == '\\' || ch == '/') {
            value.append(ch);
            pos++;
            mode = Mode.Char;
          } else if (ch == 'b') {
            value.append('\b');
            pos++;
            mode = Mode.Char;
          } else if (ch == 'f') {
            value.append('\f');
            pos++;
            mode = Mode.Char;
          } else if (ch == 'n') {
            value.append('\n');
            pos++;
            mode = Mode.Char;
          } else if (ch == 'r') {
            value.append('\r');
            pos++;
            mode = Mode.Char;
          } else if (ch == 't') {
            value.append('\t');
            pos++;
            mode = Mode.Char;
          } else if (ch == 'u') {
            pos++;
            mode = Mode.Unicode;
          } else {
            throw new RuntimeException(String.format("Unexpected escape character '%s'", ch));
          }
          break;

        case Unicode:
          String slice = string.substring(pos, pos + 4);
          if (slice.length() < 4) {
            throw new RuntimeException(String.format("Incomplete Unicode code %s", slice));
          }
          try {
            Long.parseLong(slice, 16);
          } catch (NumberFormatException ex) {
            throw new RuntimeException(String.format("Unexpected Unicode code %s", slice));
          }
          value.append(String.format("\\u%s", slice));
          pos += 4;
          mode = Mode.Char;
          break;

        case End:
          break;

        default:
          throw new RuntimeException(String.format("Unexpected mode %s", mode));
      }
    }

    if (mode != Mode.End) {
      throw new RuntimeException(String.format("Incomplete string expression, mode %s", mode));
    } else {
      return new StringToken(pos, value.toString());
    }
  }
}
