package io.mincong.ocpjp.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;

/** @author Mincong Huang */
public class LinkedHashSetTest {

  @Test
  public void insertionOrder() throws Exception {
    Set<String> cities = new LinkedHashSet<>();
    cities.add("Seattle");
    cities.add("Copenhagen");
    cities.add("New Delhi");

    List<String> extendedCities = new ArrayList<>();
    extendedCities.add("Beijing");
    extendedCities.add("Tokyo");
    /*
     * Note that `addAll()` accepts a `Collection` object. So you can
     * add elements of an `ArrayList` to a `LinkedHashSet`. The order
     * of insertion of objects from `extendedCities` to `cities` is
     * determined by the order of objects return by the iterator of
     * `extendedCities`.
     */
    cities.addAll(extendedCities);

    String result = cities.stream().collect(Collectors.joining(", "));
    assertThat(result).isEqualTo("Seattle, Copenhagen, New Delhi, Beijing, Tokyo");
  }
}
