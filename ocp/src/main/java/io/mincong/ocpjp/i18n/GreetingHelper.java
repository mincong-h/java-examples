package io.mincong.ocpjp.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/** @author Mincong Huang */
final class GreetingHelper {

  private GreetingHelper() {
    // Utility class, do not instantiate
  }

  static String getWelcome(Locale locale) {
    ResourceBundle labels = ResourceBundle.getBundle("labels", locale);
    return labels.getString("welcome");
  }
}
