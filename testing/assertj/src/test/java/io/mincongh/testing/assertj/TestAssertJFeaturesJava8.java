package io.mincongh.testing.assertj;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.junit.Test;

/**
 * AssertJ new features in Java 8.
 * <p>
 * This is inspired by blog post <i>AssertJ’s Java 8 Features</i>,
 * written by Eugen Paraschiv.
 *
 * @author Eugen Paraschiv
 * @author Mincong Huang
 * @see <a href="http://www.baeldung.com/assertJ-java-8-features">AssertJ’s Java 8 Features</a>
 */
public class TestAssertJFeaturesJava8 {

  @Test
  public void optionalAssertions() throws Exception {
    Optional<String> optional;

    optional = Optional.of("something");
    assertThat(optional).isPresent().hasValue("something");

    optional = Optional.empty();
    assertThat(optional).isEmpty();
  }

  @Test
  public void predicateAssertions() throws Exception {
    Predicate<String> moreThan4 = s -> s.length() > 4;

    assertThat(moreThan4).acceptsAll(Arrays.asList("hello", "world"));
    assertThat(moreThan4).accepts("hello", "world");
    assertThat(moreThan4).rejectsAll(Arrays.asList("hi", "java"));
    assertThat(moreThan4).rejects("hi", "java");
  }

  @Test
  public void localDateAssertions() throws Exception {
    LocalDate d = LocalDate.of(2017, 10, 22);

    assertThat(d).isBefore(LocalDate.of(2017, 10, 23));
    assertThat(d).isAfter(LocalDate.of(2017, 10, 21));
  }

  @Test
  public void localDateTimeAssertions() throws Exception {
    LocalDateTime d = LocalDateTime.of(2017, 10, 22, 10, 44, 0);

    assertThat(d).isBefore(LocalDateTime.of(2017, 10, 22, 10, 44, 1));
    assertThat(d).isAfter(LocalDateTime.of(2017, 10, 22, 10, 43, 59));
  }

  @Test
  public void localTimeAssertions() throws Exception {
    LocalTime t = LocalTime.of(10, 46);

    assertThat(t).isBefore(LocalTime.of(10, 47));
    assertThat(t).isAfter(LocalTime.of(10, 45));
  }

  @Test
  public void flatExtractingHelper() throws Exception {
    LocalDate a = LocalDate.ofYearDay(2015, 1);
    LocalDate b = LocalDate.ofYearDay(2016, 1);
    LocalDate c = LocalDate.ofYearDay(2016, 2);
    List<LocalDate> dates = Arrays.asList(a, b, c);
    assertThat(dates).flatExtracting(LocalDate::getYear).containsOnly(2015, 2016);
  }

  @Test
  public void satisfiesHelper() throws Exception {
    assertThat("Hello").satisfies(s -> {
      assertThat(s).startsWith("H");
      assertThat(s).hasSize(5);
    });
  }

  @Test
  public void hasOnlyOneElementSatisfyingHelper() throws Exception {
    Set<String> words = Collections.singleton("");
    assertThat(words).hasOnlyOneElementSatisfying(s -> assertThat(s).isEmpty());
  }

  @Test
  public void matchesHelper() throws Exception {
    assertThat("Hello2017").matches(Pattern.compile("\\w+"));
    assertThat("Hello2017").matches("\\w+");
    assertThat("").matches(String::isEmpty);
  }

}
