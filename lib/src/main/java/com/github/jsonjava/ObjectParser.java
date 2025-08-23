package com.github.jsonjava;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ObjectParser {
  public enum Mode {
    Scanning, Pair, Delimiter, End
  };

  public static ObjectToken parse(String object) {
    Mode mode = Mode.Scanning;
    int pos = 0;
    ArrayList<PairToken> tokens = new ArrayList<PairToken>();

    while (pos < object.length() && mode != Mode.End) {
      char ch = object.charAt(pos);

      switch (mode) {
        case Scanning:
          if (Pattern.matches("[ \\n\\r\\t]", Character.toString(ch))) {
            pos++;
          } else if (ch == '{') {
            pos++;
            mode = Mode.Pair;
          } else {
            throw new RuntimeException(String.format("Expected '{', actual '%s'", ch));
          }
          break;

        case Pair:
          if (Pattern.matches("[ \\n\\r\\t]", Character.toString(ch))) {
            pos++;
          } else if (ch == '}') {
            if (tokens.size() > 0) {
              throw new RuntimeException("Unexpected ','");
            }
            pos++;
            mode = Mode.End;
          } else {
            String slice = object.substring(pos);
            PairToken pair = PairParser.parse(slice);
            tokens.add(pair);
            pos += pair.skip;
            mode = Mode.Delimiter;
          }
          break;

        case Delimiter:
          if (Pattern.matches("[ \\n\\r\\t]", Character.toString(ch))) {
            pos++;
          } else if (ch == ',') {
            pos++;
            mode = Mode.Pair;
          } else if (ch == '}') {
            pos++;
            mode = Mode.End;
          } else {
            throw new RuntimeException(String.format("Expected ',' or '}', actual '%s'", ch));
          }
          break;

        case End:
          break;

        default:
          throw new RuntimeException(String.format("Unexpected mode %s", mode));
      }
    }

    if (mode != Mode.End) {
      throw new RuntimeException("Incomplete object expression");
    } else {
      PairToken[] members = new PairToken[tokens.size()];
      return new ObjectToken(pos, tokens.toArray(members));
    }
  }
}
