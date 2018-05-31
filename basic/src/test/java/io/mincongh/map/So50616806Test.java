package io.mincongh.map;

import java.util.Comparator;
import java.util.NavigableMap;
import java.util.TreeMap;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Stack Overflow: How create range map in Java when the keys are strings
 *
 * @author Mincong Huang
 */
public class So50616806Test {

  @Test
  public void name() {
    Comparator<String> comparator =
        Comparator.comparing((String key) -> key.split("#")[0]) //
            .thenComparingLong(key -> Long.parseLong(key.split("#")[1]));

    NavigableMap<String, String> map = new TreeMap<>(comparator);

    map.put("var2#" + 0L, "out0"); // "var2#0..100        => out0
    map.put("var2#" + 100L, "out1"); // "var2#100..200"     => out1
    map.put("var2#" + 200L, "out2"); // "var2#200..300"     => out2
    map.put("var2#" + 300L, "out3"); // "var2#300..+"       => out3

    assertThat(map.floorEntry("var2#" + 150L).getValue()).isEqualTo("out1");
    assertThat(map.floorEntry("var2#" + 2000L).getValue()).isEqualTo("out3");
  }
}
