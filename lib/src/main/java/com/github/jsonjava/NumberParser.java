package com.github.jsonjava;

import java.util.regex.Pattern;

public class NumberParser {
    public enum Mode {
        Scanning, Characteristic, CharacteristicDigit, DecimalPoint, Mantissa, Exponent, ExponentSign, ExponentFirstDigit, ExponentDigits, End
    };

    public static NumberToken parse(String number) {
        return parse(number, null);
    }

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
                        throw new RuntimeException(
                                String.format("Expected digit, actual '%s'", ch));
                    }
                    break;

                case CharacteristicDigit:
                    if (Pattern.matches("\\d", Character.toString(ch))) {
                        valueAsString.append(ch);
                        pos++;
                    } else if (delimiters != null
                            && Pattern.matches(delimiters, Character.toString(ch))) {
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
                    } else if (delimiters != null
                            && Pattern.matches(delimiters, Character.toString(ch))) {
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
                    } else if (delimiters != null
                            && Pattern.matches(delimiters, Character.toString(ch))) {
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
                        throw new RuntimeException(
                                String.format("Expected 'e' or 'E', actual '%s'", ch));
                    }
                    break;

                case ExponentSign:
                    if (ch == '+' || ch == '-') {
                        valueAsString.append(ch);
                        pos++;
                        mode = Mode.ExponentFirstDigit;
                    } else {
                        mode = Mode.ExponentFirstDigit;
                    }
                    break;

                case ExponentFirstDigit:
                    if (Pattern.matches("\\d", Character.toString(ch))) {
                        valueAsString.append(ch);
                        pos++;
                        mode = Mode.ExponentDigits;
                    } else {
                        throw new RuntimeException(
                                String.format("Expected digit, actual '%s'", ch));
                    }
                    break;

                case ExponentDigits:
                    if (Pattern.matches("\\d", Character.toString(ch))) {
                        valueAsString.append(ch);
                        pos++;
                    } else if (delimiters != null
                            && Pattern.matches(delimiters, Character.toString(ch))) {
                        mode = Mode.End;
                    } else {
                        throw new RuntimeException(
                                String.format("Expected digit, actual '%s'", ch));
                    }
                    break;

                case End:
                    break;

                default:
                    throw new RuntimeException(String.format("Unexpected mode %s", mode));
            }
        }

        switch (mode) {
            case Mode.Characteristic:
            case Mode.ExponentFirstDigit:
            case Mode.ExponentSign:
                throw new RuntimeException("Number ended prematurely");

            default:
                String string = valueAsString.toString();
                return new NumberToken(pos, Double.parseDouble(string), string);
        }
    }
}
