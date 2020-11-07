package io.mincong.reliability;

import java.util.ArrayList;
import java.util.List;

public class Throttler {

  private final int messageLimit;

  public Throttler(int messageLimit) {
    this.messageLimit = messageLimit;
  }

  public ThrottleResult throttle(List<String> messages) {
    var passed = new ArrayList<String>();
    var throttled = new ArrayList<String>();
    var quota = messageLimit;

    for (var message : messages) {
      if (quota > 0) {
        passed.add(message);
        quota--;
      } else {
        throttled.add(message);
      }
    }
    /*
     * You can also add metrics or logs on the output to improve the
     * observability of the system.
     */
    return new ThrottleResult(passed, throttled);
  }

  public static class ThrottleResult {
    public final List<String> passed;
    public final List<String> throttled;

    public ThrottleResult(List<String> passed, List<String> throttled) {
      this.passed = passed;
      this.throttled = throttled;
    }
  }
}
