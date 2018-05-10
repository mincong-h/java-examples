package io.mincong.ocpjp.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author Mincong Huang
 */
final class MatcherHelper {

  private MatcherHelper() {
    // Utility class, do not instantiate.
  }

  /**
   * Collects all the matched groups inside a given matcher and
   * returns a list of results in order with the following format:
   * <pre>
   * '$group': [$start, $end[
   * </pre>
   *
   * @param m The matcher created using a regex against a string value
   * @return A list of results in order.
   */
  static List<String> collect(Matcher m) {
    List<String> results = new ArrayList<>();
    while(m.find()) {
      String s = String.format("'%s': [%d,%d[", m.group(), m.start(), m.end());
      results.add(s);
    }
    return results;
  }

}
