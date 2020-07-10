package io.mincong.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author Mincong Huang */
public class App {
  private static final Logger logger = LoggerFactory.getLogger(App.class);

  public static void sayHi(String username) {
    logger.info("Hi, {}!", username);
  }

  public static void logException(Throwable t) {
    logger.error("Something wrong", t);
  }
}
