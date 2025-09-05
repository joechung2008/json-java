package com.github.jsonjava;

/**
 * Command-line entry point for the JSON parser application. Reads JSON input from standard input
 * and prints the parsed result.
 */
public class Main {
  /**
   * Main entry point.
   *
   * @param args Command line arguments.
   */
  public static void main(final String[] args) {
    try {
      try (java.util.Scanner scanner = new java.util.Scanner(System.in).useDelimiter("\\A")) {
        String json = scanner.hasNext() ? scanner.next() : "";
        System.out.println(json);
        System.out.println(String.format("%s", JSONParser.parse(json)));
      }
    } catch (Exception e) {
      System.err.println("Error reading file: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
