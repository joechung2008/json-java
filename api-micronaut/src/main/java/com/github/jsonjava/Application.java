package com.github.jsonjava;

import io.micronaut.runtime.Micronaut;

/**
 * Main application class for the Micronaut JSON parsing API. This class serves as the entry point
 * for the Micronaut application, responsible for bootstrapping the framework and starting the HTTP
 * server.
 *
 * @author json-java
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
public class Application {

  /**
   * Main method that starts the Micronaut application. This method initializes the Micronaut
   * framework with the Application class as the main configuration source and starts the embedded
   * HTTP server.
   *
   * @param args command line arguments passed to the application
   */
  public static void main(String[] args) {
    System.setProperty("micronaut.server.port", "8000");
    Micronaut.run(Application.class, args);
  }
}
