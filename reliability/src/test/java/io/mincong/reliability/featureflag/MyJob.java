package io.mincong.reliability.featureflag;

import java.util.Set;

public class MyJob {

  private final Set<String> values;

  public MyJob(Set<String> values) {
    this.values = values;
  }

  public void run() {
    var isNewFeatureEnabled = Boolean.getBoolean("NEW_FEATURE_ENABLED");
    if (isNewFeatureEnabled) {
      values.add("new");
    } else {
      values.add("old");
    }
  }
}
