package io.mincong.ocpjp.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.Test;

/** @author Mincong Huang */
public class OptionalTest {

  @Test
  public void empty() throws Exception {
    Optional<String> o = Optional.empty();
    assertThat(o.isPresent()).isFalse();
  }

  @Test
  public void of() throws Exception {
    Optional<String> o = Optional.of("v");
    assertThat(o.isPresent()).isTrue();
    assertThat(o.orElse("-")).isEqualTo("v");
  }

  @Test
  public void ofNullable() throws Exception {
    List<String> in = Arrays.asList("v", null);
    List<Optional<String>> out = in.stream().map(Optional::ofNullable).collect(Collectors.toList());
    assertThat(out.get(0)).isPresent();
    assertThat(out.get(1)).isNotPresent();
  }

  @Test
  public void filter() throws Exception {
    Optional<String> o = Optional.of("v");
    assertThat(o.filter("v"::equalsIgnoreCase)).isPresent();
    assertThat(o.filter("a"::equalsIgnoreCase)).isNotPresent();
  }

  @Test
  public void ifPresent() throws Exception {
    Optional<String> o = Optional.of("v");
    List<String> values = new ArrayList<>();
    o.ifPresent(values::add);
    assertThat(values).containsExactly("v");
  }

  @Test
  public void orElse() throws Exception {
    Optional<String> o = Optional.empty();
    assertThat(o.orElse("v")).isEqualTo("v");
  }

  @Test
  public void orElseGet() throws Exception {
    Optional<List<String>> o = Optional.empty();
    assertThat(o.orElseGet(ArrayList::new)).isEmpty();
  }

  @Test(expected = RuntimeException.class)
  public void orElseThrow() throws Exception {
    Optional<String> o = Optional.empty();
    o.orElseThrow(RuntimeException::new);
  }

  @Test
  public void flatMap() throws Exception {
    Optional<String> fifty = Optional.of("50");
    Optional<String> empty = Optional.empty();
    assertThat(fifty.flatMap(this::parseOptionally)).isPresent();
    assertThat(empty.flatMap(this::parseOptionally)).isNotPresent();
  }

  private Optional<Integer> parseOptionally(String v) {
    return v == null ? Optional.empty() : Optional.of(Integer.parseInt(v));
  }
}
